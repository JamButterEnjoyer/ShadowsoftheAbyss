package mod.acats.vitaldeprivation.client.shader;

import mod.acats.fromanotherlibrary.client.shader.MultiShaderController;
import mod.acats.fromanotherlibrary.content.client.shaders.FALShaders;
import mod.acats.vitaldeprivation.interfaces.VDPlayer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.EffectInstance;
import net.minecraft.util.Mth;

public class DarknessController {

    private static float oldDarkness = 0.0F;
    private static float darkness = 0.0F;

    public static void tick(ClientLevel clientLevel) {

        if (Minecraft.getInstance().player == null) {
            return;
        }

        oldDarkness = darkness;
        darkness = ((VDPlayer) Minecraft.getInstance().player).vd$getDeprivation();

        if (darkness > 0.0F) {
            if (!MultiShaderController.isShaderLoaded(FALShaders.IMMERSED_IN_DARKNESS)) {
                MultiShaderController.loadShader(FALShaders.IMMERSED_IN_DARKNESS);
            }
        } else {
            if (MultiShaderController.isShaderLoaded(FALShaders.IMMERSED_IN_DARKNESS)) {
                MultiShaderController.unloadShader(FALShaders.IMMERSED_IN_DARKNESS);
            }
        }
    }

    public static void setUniforms(EffectInstance effect, float partialTick) {
        effect.safeGetUniform("FALDarknessProgress").set(Mth.lerp(partialTick, oldDarkness, darkness));
    }
}
