package io.itch.andthereitgoes.andthereitgoes_lore_ability_mod.mixin;

import io.itch.andthereitgoes.andthereitgoes_lore_ability_mod.Config;
import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;


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
