package mod.acats.vitaldeprivation.client.animation.ik;

import mod.acats.vitaldeprivation.client.animation.ik.legs.VDLeg;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.util.Mth;
import org.jetbrains.annotations.Nullable;

public class ModelAnimator {
    public static void setVDLegAngles(VDLeg vdLeg,
                                      float preLerpedBodyRot,
                                      float partialTick,
                                      ModelPart upperLegPivot1,
                                      ModelPart upperLegPivot2,
                                      ModelPart lowerLeg,
                                      @Nullable ModelPart foot,
                                      boolean flip) {
        int k = flip ? -1 : 1;
        upperLegPivot1.yRot = (vdLeg.getShoulderHorizontalAngleDegrees(partialTick) - preLerpedBodyRot - k * 90) * Mth.DEG_TO_RAD;
        upperLegPivot2.zRot = k * (-vdLeg.getShoulderVerticalAngleDegrees(partialTick)) * Mth.DEG_TO_RAD;
        lowerLeg.zRot = k * -vdLeg.getElbowVerticalAngleDegrees(partialTick) * Mth.DEG_TO_RAD;
        if (foot != null) {
            foot.zRot = -((float) Math.PI * k / 2.0F + upperLegPivot2.zRot + lowerLeg.zRot);
        }
    }
}
