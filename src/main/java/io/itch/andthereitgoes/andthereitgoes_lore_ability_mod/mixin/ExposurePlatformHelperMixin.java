package io.itch.andthereitgoes.andthereitgoes_lore_ability_mod.mixin;

import io.itch.andthereitgoes.andthereitgoes_lore_ability_mod.Config;
import net.minecraft.client.Minecraft;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Pseudo;


@Pseudo
@Mixin(targets = "io.github.mortuusars.exposure.PlatformHelper", remap = false)
public class ExposurePlatformHelperMixin {
  /**
   * @author _andthereitgoes
   * @reason dev env for _andthereitgoes
   */
  @Overwrite
  public static boolean isInDevEnv() {
    Minecraft minecraft = Minecraft.getInstance();
    return Config.has_andthereitgoesAbilities(minecraft.isSingleplayer(), minecraft.player);
  }
}
