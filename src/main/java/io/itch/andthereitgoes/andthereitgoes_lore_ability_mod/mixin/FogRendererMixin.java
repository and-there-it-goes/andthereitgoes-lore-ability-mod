package io.itch.andthereitgoes.andthereitgoes_lore_ability_mod.mixin;

import io.itch.andthereitgoes.andthereitgoes_lore_ability_mod.Config;
import net.minecraft.client.renderer.FogRenderer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import javax.annotation.Nullable;


@Mixin(FogRenderer.class)
public class FogRendererMixin {
  @Inject(method = "getPriorityFogFunction", at = @At("HEAD"), cancellable=true)
  private static void getPriorityFogFunctionHEAD(Entity entity, float f, CallbackInfoReturnable<?> cir) {
    if (entity instanceof Player player) {
      if (Config.has_andthereitgoesAbilities(player.getServer() != null ? player.getServer().isSingleplayer() : player.isLocalPlayer(), player)) {
        cir.setReturnValue(null);
      }
    }
  }
}
