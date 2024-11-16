package mod.acats.vitaldeprivation.client.animation.ik.legs;

import mod.acats.fromanotherlibrary.utilities.Maths;
import net.minecraft.util.Mth;
import net.minecraft.world.phys.Vec3;

public class VDLeg {
    public final float upperLength;
    public final float lowerLength;
    public VDLeg(float upperLength, float lowerLength) {
        this.upperLength = upperLength;
        this.lowerLength = lowerLength;
    }

    private float shoulderHorizontalAngleDegreesOld;
    private float shoulderHorizontalAngleDegrees;
    private float shoulderVerticalAngleDegreesOld;
    private float shoulderVerticalAngleDegrees;
    private float elbowVerticalAngleRadiansOld;
    private float elbowVerticalAngleRadians;

    public float getShoulderHorizontalAngleDegrees(float partialTick) {
        return Mth.rotLerp(partialTick, shoulderHorizontalAngleDegreesOld, shoulderHorizontalAngleDegrees);
    }
    public float getShoulderVerticalAngleDegrees(float partialTick) {
        return Mth.rotLerp(partialTick, shoulderVerticalAngleDegreesOld, shoulderVerticalAngleDegrees);
    }
    public float getElbowVerticalAngleDegrees(float partialTick) {
        return Mth.rotLerp(partialTick, elbowVerticalAngleRadiansOld * Mth.RAD_TO_DEG, elbowVerticalAngleRadians * Mth.RAD_TO_DEG);
    }

    public void tick(Vec3 shoulderPos, Vec3 footPos) {

        shoulderHorizontalAngleDegreesOld = shoulderHorizontalAngleDegrees;
        shoulderVerticalAngleDegreesOld = shoulderVerticalAngleDegrees;
        elbowVerticalAngleRadiansOld = elbowVerticalAngleRadians;

        Vec3 shoulderToFoot = footPos.subtract(shoulderPos);

        float finalSideLength = (float) shoulderToFoot.length();

        Maths.cosRuleFindAngleInRadians(upperLength, finalSideLength, lowerLength)
                .ifPresent(angle1 ->
                        Maths.cosRuleFindAngleInRadians((float) shoulderToFoot.y, finalSideLength, (float) Math.sqrt(shoulderToFoot.x * shoulderToFoot.x + shoulderToFoot.z * shoulderToFoot.z))
                                .ifPresent(angle2 ->
                                        shoulderVerticalAngleDegrees = (-angle1 + angle2) * Mth.RAD_TO_DEG - 90));


        Maths.yRotFromVec3Degrees(shoulderToFoot).ifPresent(value -> shoulderHorizontalAngleDegrees = value);

        Maths.cosRuleFindAngleInRadians(upperLength, lowerLength, finalSideLength).ifPresent(angle -> elbowVerticalAngleRadians = (float) -(Math.PI + angle));
    }
}
