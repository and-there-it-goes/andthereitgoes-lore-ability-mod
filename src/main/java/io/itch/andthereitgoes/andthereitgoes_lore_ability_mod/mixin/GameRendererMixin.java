package io.itch.andthereitgoes.andthereitgoes_lore_ability_mod.mixin;

import io.itch.andthereitgoes.andthereitgoes_lore_ability_mod.Config;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;


@Mixin(GameRenderer.class)
public class GameRendererMixin {
//  @Inject(method = "getNightVisionScale", at = @At("HEAD"), cancellable = true)
//  private static void getNightVisionScaleHEAD(LivingEntity entity, float nanoTime, CallbackInfoReturnable<Float> cir) {
//    if (entity instanceof Player player) {
//      if (Config.has_andthereitgoesAbilities(player.getServer() != null ? player.getServer().isSingleplayer() : player.isLocalPlayer(), player)) {
//        cir.setReturnValue(null);
//      }
//    }
//  }
}
