package mod.acats.vitaldeprivation.entity.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import mod.acats.vitaldeprivation.VitalDeprivation;
import mod.acats.vitaldeprivation.client.animation.ik.ModelAnimator;
import mod.acats.vitaldeprivation.entity.Creature;
import mod.acats.vitaldeprivation.entity.client.model.CreatureModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.culling.Frustum;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

public class CreatureRenderer extends MobRenderer<Creature, CreatureModel> {
    public static final ResourceLocation TEXTURE = VitalDeprivation.resourceLocation("textures/entity/creature.png");
    public CreatureRenderer(EntityRendererProvider.Context ctx) {
        super(ctx, new CreatureModel(ctx.bakeLayer(CreatureModel.LAYER_LOCATION)), 0.0F);
    }

    @Override
    public @NotNull ResourceLocation getTextureLocation(@NotNull Creature creature) {
        return TEXTURE;
    }

    @Override
    public void render(@NotNull Creature entity, float $$1, float partialTick, @NotNull PoseStack $$3, @NotNull MultiBufferSource $$4, int $$5) {
        float preLerpedBodyRot = Mth.rotLerp(partialTick, entity.yBodyRotO, entity.yBodyRot);

        ModelPart body = this.model.Body;

        ModelPart rightArm = body.getChild("RightArm");
        ModelPart rightArmPivot2 = rightArm.getChild("RightArmPivot2");
        ModelPart rightArm2 = rightArmPivot2.getChild("RightArm2");
        ModelPart rightHand = rightArm2.getChild("RightHand");

        ModelAnimator.setVDLegAngles(entity.frontRight, preLerpedBodyRot, partialTick, rightArm, rightArmPivot2, rightArm2, rightHand, false);

        ModelPart leftArm = body.getChild("LeftArm");
        ModelPart leftArmPivot2 = leftArm.getChild("LeftArmPivot2");
        ModelPart leftArm2 = leftArmPivot2.getChild("LeftArm2");
        ModelPart leftHand = leftArm2.getChild("LeftHand");

        ModelAnimator.setVDLegAngles(entity.frontLeft, preLerpedBodyRot, partialTick, leftArm, leftArmPivot2, leftArm2, leftHand, true);

        ModelPart rightLeg = body.getChild("RightLeg");
        ModelPart rightLegPivot2 = rightLeg.getChild("RightLegPivot2");
        ModelPart rightLeg2 = rightLegPivot2.getChild("RightLeg2");
        ModelPart rightFoot = rightLeg2.getChild("RightFoot");

        ModelAnimator.setVDLegAngles(entity.backRight, preLerpedBodyRot, partialTick, rightLeg, rightLegPivot2, rightLeg2, rightFoot, false);

        ModelPart leftLeg = body.getChild("LeftLeg");
        ModelPart leftLegPivot2 = leftLeg.getChild("LeftLegPivot2");
        ModelPart leftLeg2 = leftLegPivot2.getChild("LeftLeg2");
        ModelPart leftFoot = leftLeg2.getChild("LeftFoot");

        ModelAnimator.setVDLegAngles(entity.backLeft, preLerpedBodyRot, partialTick, leftLeg, leftLegPivot2, leftLeg2, leftFoot, true);

        ModelPart neck1 = body.getChild("Neck1");
        neck1.yRot = entity.lerpedNeck1Yaw(partialTick) - preLerpedBodyRot * Mth.DEG_TO_RAD;
        neck1.xRot = entity.lerpedNeck1Pitch(partialTick);

        ModelPart neck2 = neck1.getChild("Neck2");
        neck2.yRot = entity.lerpedNeck2Yaw(partialTick) - preLerpedBodyRot * Mth.DEG_TO_RAD - neck1.yRot;
        neck2.xRot = entity.lerpedNeck2Pitch(partialTick) - neck1.xRot;

        float headActivation = Mth.lerp(partialTick, entity.oldHeadRotate, entity.headRotate);

        ModelPart head = neck2.getChild("Head");
        head.yRot = entity.lerpedHeadYaw(partialTick) - preLerpedBodyRot * Mth.DEG_TO_RAD - neck2.yRot - neck1.yRot;
        head.xRot = entity.lerpedHeadPitch(partialTick)- neck2.xRot - neck1.xRot - headActivation * Mth.HALF_PI;

        this.model.translucency = entity.getSubjectiveActuality();

        float time = entity.tickCount + partialTick;
        float f1 = Mth.sin(time) * (Mth.PI / 8.0F) * headActivation;
        float f2 = Mth.sin(time + Mth.HALF_PI) * (Mth.PI / 8.0F) * headActivation;
        float f3 = Mth.sin(time + Mth.PI) * (Mth.PI / 8.0F) * headActivation;
        float f4 = Mth.sin(time + Mth.HALF_PI * 3) * (Mth.PI / 8.0F) * headActivation;

        head.getChild("RightHeadArm").zRot = 0.7854F - f1;
        head.getChild("LeftHeadArm").zRot = -0.7854F + f2;
        head.getChild("RightHeadArm2").zRot = 0.48F - f3;
        head.getChild("LeftHeadArm2").zRot = -0.48F + f4;

        super.render(entity, $$1, partialTick, $$3, $$4, $$5);
    }

    @Override
    public @NotNull Vec3 getRenderOffset(@NotNull Creature entity, float partialTick) {
        return new Vec3(0.0D, entity.verticalOffset(partialTick), 0.0D);
    }

    @Override
    public boolean shouldRender(@NotNull Creature $$0, @NotNull Frustum $$1, double $$2, double $$3, double $$4) {
        if ($$0.getSubjectiveActuality() == 0.0F) {
            return false;
        }
        return super.shouldRender($$0, $$1, $$2, $$3, $$4);
    }
}
