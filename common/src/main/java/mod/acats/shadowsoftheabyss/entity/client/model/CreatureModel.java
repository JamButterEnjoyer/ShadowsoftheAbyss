package mod.acats.vitaldeprivation.entity.client.model;// Made with Blockbench 4.8.3
// Exported for Minecraft version 1.17 or later with Mojang mappings
// Paste this class into your mod and generate all required imports


import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import mod.acats.vitaldeprivation.VitalDeprivation;
import mod.acats.vitaldeprivation.entity.Creature;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.client.renderer.RenderType;
import org.jetbrains.annotations.NotNull;

public class CreatureModel extends EntityModel<Creature> {
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(VitalDeprivation.resourceLocation("creature_model"), "main");
	public final ModelPart Body;

	public CreatureModel(ModelPart root) {
		super(RenderType::entityTranslucent);
		this.Body = root.getChild("Body");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition Body = partdefinition.addOrReplaceChild("Body", CubeListBuilder.create().texOffs(0, 30).addBox(-10.0F, -5.0F, 0.0F, 20.0F, 12.0F, 16.0F, new CubeDeformation(0.0F))
		.texOffs(36, 54).addBox(0.0F, -11.0F, 0.0F, 0.0F, 6.0F, 16.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -4.0F, -11.0F));

		PartDefinition LowerBody = Body.addOrReplaceChild("LowerBody", CubeListBuilder.create().texOffs(0, 0).addBox(-8.0F, -4.0F, 0.0F, 16.0F, 10.0F, 20.0F, new CubeDeformation(0.0F))
		.texOffs(0, 52).addBox(0.0F, -10.0F, 2.0F, 0.0F, 6.0F, 18.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 13.0F, -0.2182F, 0.0F, 0.0F));

		PartDefinition RightArm = Body.addOrReplaceChild("RightArm", CubeListBuilder.create(), PartPose.offsetAndRotation(-10.0F, 1.0F, 5.0F, 0.0F, -0.3927F, 0.0F));

		PartDefinition RightArmPivot2 = RightArm.addOrReplaceChild("RightArmPivot2", CubeListBuilder.create().texOffs(92, 64).addBox(-23.0F, -3.0F, -3.0F, 24.0F, 6.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.3927F));

		PartDefinition RightArm2 = RightArmPivot2.addOrReplaceChild("RightArm2", CubeListBuilder.create().texOffs(68, 52).addBox(-31.0F, -3.0F, -3.0F, 31.0F, 6.0F, 6.0F, new CubeDeformation(-0.1F)), PartPose.offsetAndRotation(-20.0F, 0.0F, 0.0F, 0.0F, 0.0F, -1.9635F));

		PartDefinition RightHand = RightArm2.addOrReplaceChild("RightHand", CubeListBuilder.create().texOffs(106, 135).addBox(-6.0F, -3.5F, -3.5F, 6.0F, 7.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offset(-30.0F, 0.0F, 0.0F));

		PartDefinition LeftArm = Body.addOrReplaceChild("LeftArm", CubeListBuilder.create(), PartPose.offsetAndRotation(10.0F, 1.0F, 5.0F, 0.0F, 0.3927F, 0.0F));

		PartDefinition LeftArmPivot2 = LeftArm.addOrReplaceChild("LeftArmPivot2", CubeListBuilder.create().texOffs(92, 64).mirror().addBox(-1.0F, -3.0F, -3.0F, 24.0F, 6.0F, 6.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, -0.3927F));

		PartDefinition LeftArm2 = LeftArmPivot2.addOrReplaceChild("LeftArm2", CubeListBuilder.create().texOffs(68, 52).mirror().addBox(0.0F, -3.0F, -3.0F, 31.0F, 6.0F, 6.0F, new CubeDeformation(-0.1F)).mirror(false), PartPose.offsetAndRotation(20.0F, 0.0F, 0.0F, 0.0F, 0.0F, 1.9635F));

		PartDefinition LeftHand = LeftArm2.addOrReplaceChild("LeftHand", CubeListBuilder.create().texOffs(106, 135).mirror().addBox(0.0F, -3.5F, -3.5F, 6.0F, 7.0F, 7.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(30.0F, 0.0F, 0.0F));

		PartDefinition Neck1 = Body.addOrReplaceChild("Neck1", CubeListBuilder.create().texOffs(48, 101).addBox(-3.0F, -3.0F, -15.0F, 6.0F, 6.0F, 17.0F, new CubeDeformation(-0.1F))
		.texOffs(0, 0).addBox(0.0F, -8.0F, -11.0F, 0.0F, 6.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -0.1309F, 0.0F, 0.0F));

		PartDefinition Neck2 = Neck1.addOrReplaceChild("Neck2", CubeListBuilder.create().texOffs(114, 26).addBox(-3.0F, -3.0F, -14.0F, 6.0F, 6.0F, 16.0F, new CubeDeformation(0.0F))
		.texOffs(72, 10).addBox(0.0F, -7.0F, -12.0F, 0.0F, 4.0F, 14.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, -14.0F, 0.4363F, 0.0F, 0.0F));

		PartDefinition Head = Neck2.addOrReplaceChild("Head", CubeListBuilder.create().texOffs(128, 76).addBox(-4.0F, -4.0F, -6.0F, 8.0F, 8.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, -14.0F, -0.2618F, 0.0F, 0.0F));

		PartDefinition RightHeadArm = Head.addOrReplaceChild("RightHeadArm", CubeListBuilder.create().texOffs(114, 108).addBox(-1.0F, -1.5F, -1.0F, 2.0F, 12.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-3.0F, 3.0F, -4.0F, -0.3927F, 0.0F, 0.7854F));

		PartDefinition LowerRightHeadArm = RightHeadArm.addOrReplaceChild("LowerRightHeadArm", CubeListBuilder.create().texOffs(106, 108).addBox(-1.0F, 0.0F, -1.0F, 2.0F, 12.0F, 2.0F, new CubeDeformation(-0.1F)), PartPose.offsetAndRotation(0.0F, 9.5F, 0.0F, 0.0F, 0.0F, -0.7854F));

		PartDefinition RightHeadHand = LowerRightHeadArm.addOrReplaceChild("RightHeadHand", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 11.5F, 0.0F, 0.0F, 0.0F, -0.2618F));

		PartDefinition RightHeadFinger1 = RightHeadHand.addOrReplaceChild("RightHeadFinger1", CubeListBuilder.create().texOffs(54, 82).addBox(0.0F, -1.0F, -1.0F, 1.0F, 5.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.5F, 1.0F, 0.0F, -0.1963F, 0.0F, 0.0654F));

		PartDefinition RightHeadClaw1 = RightHeadFinger1.addOrReplaceChild("RightHeadClaw1", CubeListBuilder.create().texOffs(12, 0).addBox(-1.0F, -1.0F, 0.0F, 2.0F, 6.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.5F, 4.0F, -0.5F, 0.0F, 0.0F, -0.3927F));

		PartDefinition RightHeadFinger2 = RightHeadHand.addOrReplaceChild("RightHeadFinger2", CubeListBuilder.create().texOffs(68, 70).addBox(0.0F, -1.0F, 0.0F, 1.0F, 5.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.5F, 1.0F, 0.0F, 0.1963F, 0.0F, -0.1963F));

		PartDefinition RightHeadClaw2 = RightHeadFinger2.addOrReplaceChild("RightHeadClaw2", CubeListBuilder.create().texOffs(8, 0).addBox(-1.0F, -1.0F, 0.0F, 2.0F, 6.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.5F, 4.0F, 0.5F, 0.0F, 0.0F, -0.3927F));

		PartDefinition LeftHeadArm = Head.addOrReplaceChild("LeftHeadArm", CubeListBuilder.create().texOffs(114, 108).mirror().addBox(-1.0F, -1.5F, -1.0F, 2.0F, 12.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(3.0F, 3.0F, -4.0F, -0.3927F, 0.0F, -0.7854F));

		PartDefinition LowerLeftHeadArm = LeftHeadArm.addOrReplaceChild("LowerLeftHeadArm", CubeListBuilder.create().texOffs(106, 108).mirror().addBox(-1.0F, 0.0F, -1.0F, 2.0F, 12.0F, 2.0F, new CubeDeformation(-0.1F)).mirror(false), PartPose.offsetAndRotation(0.0F, 9.5F, 0.0F, 0.0F, 0.0F, 0.7854F));

		PartDefinition LeftHeadHand = LowerLeftHeadArm.addOrReplaceChild("LeftHeadHand", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 11.5F, 0.0F, 0.0F, 0.0F, 0.2618F));

		PartDefinition LeftHeadFinger1 = LeftHeadHand.addOrReplaceChild("LeftHeadFinger1", CubeListBuilder.create().texOffs(54, 82).mirror().addBox(-1.0F, -1.0F, -1.0F, 1.0F, 5.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(0.5F, 1.0F, 0.0F, -0.1963F, 0.0F, -0.0654F));

		PartDefinition LeftHeadClaw1 = LeftHeadFinger1.addOrReplaceChild("LeftHeadClaw1", CubeListBuilder.create().texOffs(12, 0).mirror().addBox(-1.0F, -1.0F, 0.0F, 2.0F, 6.0F, 0.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-0.5F, 4.0F, -0.5F, 0.0F, 0.0F, 0.3927F));

		PartDefinition LeftHeadFinger2 = LeftHeadHand.addOrReplaceChild("LeftHeadFinger2", CubeListBuilder.create().texOffs(68, 70).mirror().addBox(-1.0F, -1.0F, 0.0F, 1.0F, 5.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(0.5F, 1.0F, 0.0F, 0.1963F, 0.0F, 0.1963F));

		PartDefinition LeftHeadClaw2 = LeftHeadFinger2.addOrReplaceChild("LeftHeadClaw2", CubeListBuilder.create().texOffs(8, 0).mirror().addBox(-1.0F, -1.0F, 0.0F, 2.0F, 6.0F, 0.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-0.5F, 4.0F, 0.5F, 0.0F, 0.0F, 0.3927F));

		PartDefinition RightHeadArm2 = Head.addOrReplaceChild("RightHeadArm2", CubeListBuilder.create().texOffs(8, 95).addBox(-1.0F, -1.5F, -1.0F, 2.0F, 12.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-3.0F, 3.0F, -2.0F, 0.0F, 0.0F, 0.48F));

		PartDefinition LowerRightHeadArm2 = RightHeadArm2.addOrReplaceChild("LowerRightHeadArm2", CubeListBuilder.create().texOffs(0, 95).addBox(-1.0F, 0.0F, -1.0F, 2.0F, 12.0F, 2.0F, new CubeDeformation(-0.1F)), PartPose.offsetAndRotation(0.0F, 9.5F, 0.0F, 0.0F, 0.0F, -0.5236F));

		PartDefinition RightHeadHand2 = LowerRightHeadArm2.addOrReplaceChild("RightHeadHand2", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 11.5F, 0.0F, 0.0F, 0.0F, -0.2618F));

		PartDefinition RightHeadFinger3 = RightHeadHand2.addOrReplaceChild("RightHeadFinger3", CubeListBuilder.create().texOffs(4, 125).addBox(0.0F, -1.0F, -1.0F, 1.0F, 5.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.5F, 1.0F, 0.0F, -0.1963F, 0.0F, 0.0654F));

		PartDefinition RightHeadClaw3 = RightHeadFinger3.addOrReplaceChild("RightHeadClaw3", CubeListBuilder.create().texOffs(4, 0).addBox(-1.0F, -1.0F, 0.0F, 2.0F, 6.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.5F, 4.0F, -0.5F, 0.0F, 0.0F, -0.3927F));

		PartDefinition RightHeadFinger4 = RightHeadHand2.addOrReplaceChild("RightHeadFinger4", CubeListBuilder.create().texOffs(17, 126).addBox(0.0F, -1.0F, 0.0F, 1.0F, 5.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.5F, 1.0F, 0.0F, 0.1963F, 0.0F, -0.1963F));

		PartDefinition RightHeadClaw4 = RightHeadFinger4.addOrReplaceChild("RightHeadClaw4", CubeListBuilder.create().texOffs(0, 0).addBox(-1.0F, -1.0F, 0.0F, 2.0F, 6.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.5F, 4.0F, 0.5F, 0.0F, 0.0F, -0.3927F));

		PartDefinition LeftHeadArm2 = Head.addOrReplaceChild("LeftHeadArm2", CubeListBuilder.create().texOffs(8, 95).mirror().addBox(-1.0F, -1.5F, -1.0F, 2.0F, 12.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(3.0F, 3.0F, -2.0F, 0.0F, 0.0F, -0.48F));

		PartDefinition LowerLeftHeadArm2 = LeftHeadArm2.addOrReplaceChild("LowerLeftHeadArm2", CubeListBuilder.create().texOffs(0, 95).mirror().addBox(-1.0F, 0.0F, -1.0F, 2.0F, 12.0F, 2.0F, new CubeDeformation(-0.1F)).mirror(false), PartPose.offsetAndRotation(0.0F, 9.5F, 0.0F, 0.0F, 0.0F, 0.5236F));

		PartDefinition LeftHeadHand2 = LowerLeftHeadArm2.addOrReplaceChild("LeftHeadHand2", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 11.5F, 0.0F, 0.0F, 0.0F, 0.2618F));

		PartDefinition LeftHeadFinger3 = LeftHeadHand2.addOrReplaceChild("LeftHeadFinger3", CubeListBuilder.create().texOffs(4, 125).mirror().addBox(-1.0F, -1.0F, -1.0F, 1.0F, 5.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(0.5F, 1.0F, 0.0F, -0.1963F, 0.0F, -0.0654F));

		PartDefinition LeftHeadClaw3 = LeftHeadFinger3.addOrReplaceChild("LeftHeadClaw3", CubeListBuilder.create().texOffs(4, 0).mirror().addBox(-1.0F, -1.0F, 0.0F, 2.0F, 6.0F, 0.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-0.5F, 4.0F, -0.5F, 0.0F, 0.0F, 0.3927F));

		PartDefinition LeftHeadFinger4 = LeftHeadHand2.addOrReplaceChild("LeftHeadFinger4", CubeListBuilder.create().texOffs(17, 126).mirror().addBox(-1.0F, -1.0F, 0.0F, 1.0F, 5.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(0.5F, 1.0F, 0.0F, 0.1963F, 0.0F, 0.1963F));

		PartDefinition LeftHeadClaw4 = LeftHeadFinger4.addOrReplaceChild("LeftHeadClaw4", CubeListBuilder.create().texOffs(0, 0).mirror().addBox(-1.0F, -1.0F, 0.0F, 2.0F, 6.0F, 0.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-0.5F, 4.0F, 0.5F, 0.0F, 0.0F, 0.3927F));

		PartDefinition RightLeg = Body.addOrReplaceChild("RightLeg", CubeListBuilder.create(), PartPose.offsetAndRotation(-7.0F, 4.0F, 25.0F, 0.0F, 0.3927F, 0.0F));

		PartDefinition RightLegPivot2 = RightLeg.addOrReplaceChild("RightLegPivot2", CubeListBuilder.create().texOffs(0, 83).addBox(-23.0F, -3.0F, -3.0F, 24.0F, 6.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.3927F));

		PartDefinition RightLeg2 = RightLegPivot2.addOrReplaceChild("RightLeg2", CubeListBuilder.create().texOffs(52, 0).addBox(-31.0F, -3.0F, -3.0F, 31.0F, 6.0F, 6.0F, new CubeDeformation(-0.1F)), PartPose.offsetAndRotation(-20.0F, 0.0F, 0.0F, 0.0F, 0.0F, -1.9635F));

		PartDefinition RightFoot = RightLeg2.addOrReplaceChild("RightFoot", CubeListBuilder.create().texOffs(80, 130).addBox(-6.0F, -3.5F, -3.5F, 6.0F, 7.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offset(-30.0F, 0.0F, 0.0F));

		PartDefinition LeftLeg = Body.addOrReplaceChild("LeftLeg", CubeListBuilder.create(), PartPose.offsetAndRotation(7.0F, 4.0F, 25.0F, 0.0F, -0.3927F, 0.0F));

		PartDefinition LeftLegPivot2 = LeftLeg.addOrReplaceChild("LeftLegPivot2", CubeListBuilder.create().texOffs(0, 83).mirror().addBox(-1.0F, -3.0F, -3.0F, 24.0F, 6.0F, 6.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, -0.3927F));

		PartDefinition LeftLeg2 = LeftLegPivot2.addOrReplaceChild("LeftLeg2", CubeListBuilder.create().texOffs(52, 0).mirror().addBox(0.0F, -3.0F, -3.0F, 31.0F, 6.0F, 6.0F, new CubeDeformation(-0.1F)).mirror(false), PartPose.offsetAndRotation(20.0F, 0.0F, 0.0F, 0.0F, 0.0F, 1.9635F));

		PartDefinition LeftFoot = LeftLeg2.addOrReplaceChild("LeftFoot", CubeListBuilder.create().texOffs(80, 130).mirror().addBox(0.0F, -3.5F, -3.5F, 6.0F, 7.0F, 7.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(30.0F, 0.0F, 0.0F));

		return LayerDefinition.create(meshdefinition, 256, 256);
	}

	@Override
	public void setupAnim(@NotNull Creature entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
	}

	public float translucency = 1.0F;

	@Override
	public void renderToBuffer(@NotNull PoseStack poseStack, @NotNull VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
		Body.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha * translucency);
	}
}