package io.itch.andthereitgoes.andthereitgoes_lore_ability_mod.mixin;

import io.itch.andthereitgoes.andthereitgoes_lore_ability_mod.Config;
import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.scores.PlayerTeam;
import net.minecraft.world.scores.Team;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import javax.annotation.Nullable;


@Mixin(Entity.class)
public abstract class EntityMixin {
  @Shadow
  protected abstract boolean getSharedFlag(int flag);

  /**
   * @author _andthereitgoes
   * @reason ability - see invisible entities
   */
  @Overwrite
  public boolean isInvisible() {
    if (Config.has_andthereitgoesAbilities(Minecraft.getInstance().isSingleplayer(), Minecraft.getInstance().player)) return false;
    return this.getSharedFlag(5);
  }
}
