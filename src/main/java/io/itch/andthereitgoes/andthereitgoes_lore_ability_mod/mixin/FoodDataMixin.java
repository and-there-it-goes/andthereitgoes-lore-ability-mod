package io.itch.andthereitgoes.andthereitgoes_lore_ability_mod.mixin;

import io.itch.andthereitgoes.andthereitgoes_lore_ability_mod.Config;
import net.minecraft.world.Difficulty;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.food.FoodData;
import net.minecraft.world.level.GameRules;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;


@Mixin(FoodData.class)
public abstract class FoodDataMixin {

  @Shadow
  private int lastFoodLevel;

  @Shadow
  private int foodLevel;

  @Shadow
  private float exhaustionLevel;

  @Shadow
  private float saturationLevel;

  @Shadow
  private int tickTimer;

  @Shadow
  public abstract void addExhaustion(float exhaustion);

  @Inject(method = "tick", at = @At("HEAD"), cancellable = true)
  public void tickHEAD(Player player, CallbackInfo ci) {

    if (Config.has_andthereitgoesAbilities(player.getServer() != null ? player.getServer().isSingleplayer() : player.isLocalPlayer(), player)) {

      Difficulty difficulty = player.level().getDifficulty();
      this.lastFoodLevel = this.foodLevel;
      if (this.exhaustionLevel > 4.0F) {
        this.exhaustionLevel -= 4.0F;
        if (this.saturationLevel > 0.0F) {
          this.saturationLevel = Math.max(this.saturationLevel - 1.0F, 0.0F);
        } else if (difficulty != Difficulty.PEACEFUL) {
          this.foodLevel = Math.max(this.foodLevel - 1, 0);
        }
      }

      boolean naturalRegen = player.level().getGameRules().getBoolean(GameRules.RULE_NATURAL_REGENERATION);
      if (naturalRegen && this.saturationLevel > 0.0F && player.isHurt() && this.foodLevel >= 16) {
        this.tickTimer++;
        if (this.tickTimer >= 10) {
          player.heal(2.0F);
          this.addExhaustion(2.0F);
          this.tickTimer = 0;
        }
      } else if (naturalRegen && this.foodLevel >= 6 && player.isHurt()) {
        this.tickTimer++;
        if (this.tickTimer >= 20) {
          player.heal(1.0F);
          this.addExhaustion(0.5F);
          this.tickTimer = 0;
        }
      } else if (this.foodLevel <= 0) {
        this.tickTimer++;
        if (this.tickTimer >= 160) {
          if (player.getHealth() > 15.0F || player.getHealth() > 5.0F && difficulty == Difficulty.HARD || player.getHealth() > 10.0F && difficulty == Difficulty.NORMAL) {
            player.hurt(player.damageSources().starve(), 1.0F);
          }

          this.tickTimer = 0;
        }
      } else {
        this.tickTimer = 0;
      }

      ci.cancel();
    }

  }

}
