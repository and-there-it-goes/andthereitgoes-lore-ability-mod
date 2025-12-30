package io.itch.andthereitgoes.andthereitgoes_lore_ability_mod.mixin;

import com.mojang.logging.LogUtils;
import io.itch.andthereitgoes.andthereitgoes_lore_ability_mod.Config;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.stats.Stats;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.item.alchemy.PotionContents;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import javax.annotation.ParametersAreNonnullByDefault;


@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
@Mixin(HoneyBottleItem.class)
public class HoneyBottleItemMixin extends Item {

  public HoneyBottleItemMixin(Properties properties) { super(properties); }

  @Inject(method = "finishUsingItem", at = @At("TAIL"), cancellable=true)
  public void finishUsingItemTAIL(ItemStack item, Level level, LivingEntity entity, CallbackInfoReturnable<ItemStack> cir) {
    if (entity instanceof Player player && level.getServer() != null && Config.has_andthereitgoesAbilities(level.getServer().isSingleplayer(), player)) {
      cir.setReturnValue(PotionContents.createItemStack(Items.POTION, Potions.REGENERATION));
    }
  }

}
