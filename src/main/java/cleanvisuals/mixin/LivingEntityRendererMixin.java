package cleanvisuals.mixin;

import cleanvisuals.Config;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.entity.LivingEntityRenderer;
import net.minecraft.client.render.entity.state.LivingEntityRenderState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LivingEntityRenderer.class)
public abstract class LivingEntityRendererMixin {
    // Entfernt den roten Treffer-Tint, behaelt aber den weissen Flash (z.B. Creeper).
    @Inject(method = "getOverlay", at = @At("HEAD"), cancellable = true)
    private static void cleanvisuals$noHurtTint(LivingEntityRenderState state, float whiteOverlayProgress,
                                                CallbackInfoReturnable<Integer> cir) {
        if (Config.cleanHitting) {
            cir.setReturnValue(OverlayTexture.packUv(OverlayTexture.getU(whiteOverlayProgress), OverlayTexture.getV(false)));
        }
    }
}
