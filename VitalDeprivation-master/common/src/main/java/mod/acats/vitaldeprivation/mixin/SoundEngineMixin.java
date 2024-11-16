package mod.acats.vitaldeprivation.mixin;

import mod.acats.vitaldeprivation.interfaces.VDPlayer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.sounds.SoundEngine;
import net.minecraft.sounds.SoundSource;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(SoundEngine.class)
public abstract class SoundEngineMixin {
    @Inject(method = "getVolume", at = @At("RETURN"), cancellable = true)
    private void vd$getVolume(SoundSource soundSource, CallbackInfoReturnable<Float> cir) {
        if (Minecraft.getInstance().player instanceof VDPlayer vdPlayer) {
            float senseLoss = (1.0F - vdPlayer.vd$getDeprivation());
            cir.setReturnValue(cir.getReturnValue() * senseLoss * senseLoss);
        }
    }
}
