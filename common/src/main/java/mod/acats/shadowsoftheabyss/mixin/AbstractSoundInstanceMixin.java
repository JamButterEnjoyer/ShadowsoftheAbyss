package mod.acats.vitaldeprivation.mixin;

import mod.acats.vitaldeprivation.interfaces.VDPlayer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.sounds.AbstractSoundInstance;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(AbstractSoundInstance.class)
public abstract class AbstractSoundInstanceMixin {
    @Inject(method = "getPitch", at = @At("RETURN"), cancellable = true)
    private void vd$getPitch(CallbackInfoReturnable<Float> cir) {
        if (Minecraft.getInstance().player instanceof VDPlayer vdPlayer) {
            float senseLoss = (1.0F - vdPlayer.vd$getDeprivation());
            cir.setReturnValue(cir.getReturnValue() * senseLoss * senseLoss);
        }
    }
}
