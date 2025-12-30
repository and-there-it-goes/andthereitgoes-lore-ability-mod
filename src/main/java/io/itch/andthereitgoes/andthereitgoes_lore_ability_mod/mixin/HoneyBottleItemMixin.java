package io.itch.andthereitgoes.andthereitgoes_lore_ability_mod.mixin;

import com.mojang.logging.LogUtils;
import io.itch.andthereitgoes.andthereitgoes_lore_ability_mod.Config;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.core.component.DataComponentPatch;
import net.minecraft.core.component.DataComponents;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.stats.Stats;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.item.alchemy.PotionContents;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

import javax.annotation.ParametersAreNonnullByDefault;


@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
@Mixin(HoneyBottleItem.class)
public class HoneyBottleItemMixin extends Item {

  public HoneyBottleItemMixin(Properties properties) { super(properties); }

  /**
   * @author _andthereitgoes
   * @reason honey bottle finish -> short regen potion for _andthereitgoes
   */
  @Overwrite
  public ItemStack finishUsingItem(ItemStack item, Level level, LivingEntity entity) {

    LogUtils.getLogger().info("finish using honey bottle");

    super.finishUsingItem(item, level, entity);

    if (entity instanceof ServerPlayer serverplayer) {
      CriteriaTriggers.CONSUME_ITEM.trigger(serverplayer, item);
      serverplayer.awardStat(Stats.ITEM_USED.get(this));
    }

    if (!level.isClientSide) {
      entity.removeEffectsCuredBy(net.neoforged.neoforge.common.EffectCures.HONEY);
    }

    if (item.isEmpty()) {
      if (entity instanceof Player player && level.getServer() != null && Config.has_andthereitgoesAbilities(level.getServer().isSingleplayer(), player)) {
        return PotionContents.createItemStack(Items.POTION, Potions.REGENERATION);
      } else {
        return new ItemStack(Items.GLASS_BOTTLE);
      }
    } else {
      if (entity instanceof Player player && !player.hasInfiniteMaterials()) {
        ItemStack itemstack = new ItemStack(Items.GLASS_BOTTLE);
        if (level.getServer() != null && Config.has_andthereitgoesAbilities(level.getServer().isSingleplayer(), player)) {
          itemstack = PotionContents.createItemStack(Items.POTION, Potions.REGENERATION);
        }
        if (!player.getInventory().add(itemstack)) {
          player.drop(itemstack, false);
        }
      }

      return item;
    }
  }

}
