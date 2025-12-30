package io.itch.andthereitgoes.andthereitgoes_lore_ability_mod.mixin;

import com.mojang.authlib.GameProfile;
import io.itch.andthereitgoes.andthereitgoes_lore_ability_mod.Config;
import io.itch.andthereitgoes.andthereitgoes_lore_ability_mod.LoreAbilityMod_andthereitgoes;
import net.minecraft.core.BlockPos;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.network.ServerGamePacketListenerImpl;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.portal.DimensionTransition;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.common.NeoForgeMod;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import javax.annotation.Nullable;
import java.util.Arrays;
import java.util.Objects;


@Mixin(ServerPlayer.class)
public abstract class ServerPlayerMixin extends Player {

  @Shadow
  @Final
  public MinecraftServer server;

  @Shadow
  public abstract ServerLevel serverLevel();

  @Shadow
  public abstract void teleportTo(double x, double y, double z);

  @Shadow
  public ServerGamePacketListenerImpl connection;

  @Shadow
  @Nullable
  public abstract Entity changeDimension(DimensionTransition p_350472_);

  @Shadow
  private static boolean didNotMove(double dx, double dy, double dz) {
    return false;
  }

  public ServerPlayerMixin(Level p_250508_, BlockPos p_250289_, float p_251702_, GameProfile p_252153_) { super(p_250508_, p_250289_, p_251702_, p_252153_); }

  /**
   * @author _andthereitgoes
   * @reason Teleporting effect
   */
  @Inject(method = "hurt", at = @At("HEAD"), order = 20000, cancellable = true)
  public void hurtHEAD(DamageSource source, float amount, CallbackInfoReturnable<Boolean> cir) {

    if (Config.has_andthereitgoesAbilities(this.server.isSingleplayer(), this)) {

      if (source.is(DamageTypeTags.IS_EXPLOSION) && source.getEntity() == null && source.getDirectEntity() == this) {
        cir.setReturnValue(false);
        return;
      }

      if (source.is(DamageTypeTags.IS_FALL)) {
        cir.setReturnValue(false);
      }

      if (source.is(DamageTypeTags.IS_FIRE)) {
        if (this.getRandom().nextDouble() < 0.04) {
          this.clearFire();
          this.serverLevel().explode(
              this.self(),
              this.damageSources().explosion(null, this.self()),
              null,
              this.getX(), this.getY(), this.getZ(),
              12.0f,
              false,
              Level.ExplosionInteraction.MOB
          );
          return;
        }
      }

      if (!source.is(DamageTypeTags.IS_FALL) && source.is(DamageTypeTags.BYPASSES_ARMOR)) return;

      if (this.isShiftKeyDown()) {

        if (this.isPassenger()) {
          this.stopRiding();
        }
        this.ejectPassengers();

        Vec3 vec3 = this.position();

        this.noPhysics = true;
        for (int i = 0; i < 256; i++) {

          double x = this.getX() + (this.getRandom().nextDouble() - 0.5) * 64.0;
          double y = Mth.clamp(
              this.getY() + (double)(this.getRandom().nextInt(32) - 16),
              this.level().getMinBuildHeight(),
              this.level().getMaxBuildHeight()
          );
          double z = this.getZ() + (this.getRandom().nextDouble() - 0.5) * 64.0;

          if (this.randomTeleport(x, y, z, true)) {

            this.serverLevel().gameEvent(GameEvent.TELEPORT, vec3, GameEvent.Context.of(this));
            SoundEvent soundevent = SoundEvents.PLAYER_TELEPORT;
            SoundSource soundsource = SoundSource.PLAYERS;

            this.serverLevel().playSound(null, this.getX(), this.getY(), this.getZ(), soundevent, soundsource);

            this.resetFallDistance();
            this.resetCurrentImpulseContext();
            break;
          }

        }

      }

    }

  }

  @Inject(method = "hurt", at = @At("TAIL"), cancellable = true)
  public void hurtTAIL(DamageSource source, float amount, CallbackInfoReturnable<Boolean> cir) {

    if (Config.has_andthereitgoesAbilities(this.server.isSingleplayer(), this)) {
      if (source.is(DamageTypeTags.IS_FALL)) {
        cir.cancel();
        return;
      }
      if (source.is(DamageTypes.MAGIC) || source.is(DamageTypes.INDIRECT_MAGIC)) {
        amount *= 2.0f;
      } else if (!source.is(DamageTypeTags.BYPASSES_ARMOR)) {
        amount *= 0.5f;
      }
      cir.setReturnValue(super.hurt(source, amount));
    }

  }

  @Inject(method = "doTick", at = @At("HEAD"))
  public void doTickHEAD(CallbackInfo ci) {
    if (Config.has_andthereitgoesAbilities(this.server.isSingleplayer(), this)) {

      if (this.isReducedDebugInfo()) this.setReducedDebugInfo(false);

      Inventory inventory = this.getInventory();

      if (this.getEffect(MobEffects.WEAKNESS) == null || Objects.requireNonNull(this.getEffect(MobEffects.WEAKNESS)).getAmplifier() < 1) {
        this.addEffect(new MobEffectInstance(MobEffects.WEAKNESS, -1, 0, true, false));
      }

      for (int i = 0; i < inventory.armor.size(); i++) {
        ItemStack itemstack = inventory.armor.get(i);
        if (!itemstack.isEmpty()) {
          this.drop(itemstack, true);
          inventory.armor.set(i, ItemStack.EMPTY);
        }
      }

      for (int i = 0; i < inventory.items.size(); i++) {
        ItemStack itemstack = inventory.items.get(i);
        if (!itemstack.isEmpty()) {
          if (itemstack.is(Items.MACE) || Arrays.stream(LoreAbilityMod_andthereitgoes.ITEM_ID_LIST_CONTAINS_MEAT).anyMatch((s) -> s.equals(itemstack.getItem().toString()))) {
            this.drop(itemstack, true);
            inventory.items.set(i, ItemStack.EMPTY);
          }
        }
      }

      Objects.requireNonNull(this.getAttribute(NeoForgeMod.CREATIVE_FLIGHT)).setBaseValue(this.foodData.getFoodLevel() >= 6 ? 0.5 : 0.0);

    }
  }

  @Inject(method = "checkMovementStatistics", at = @At("HEAD"))
  public void checkMovementStatisticsHEAD(double dx, double dy, double dz, CallbackInfo ci) {
    if (Config.has_andthereitgoesAbilities(this.server.isSingleplayer(), this)) {
      if (!this.isPassenger() && !didNotMove(dx, dy, dz)) {
        if (
            !this.isSwimming() &&
            !this.isEyeInFluid(FluidTags.WATER) &&
            !this.isInWater() &&
            !this.onClimbable() &&
            !this.onGround() &&
            !this.isFallFlying()
        ) {
          int l = Math.round((float)Math.sqrt(dx * dx + dz * dz) * 100.0F);
          if (l > 25) {
            this.causeFoodExhaustion(0.001F * (float)l);
          }
        }
      }
    }
  }

}
