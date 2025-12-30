package net.ennway.farworld.entity.client.amethystconstruct;// Made with Blockbench 5.0.6
// Exported for Minecraft version 1.17 or later with Mojang mappings
// Paste this class into your mod and generate all required imports


import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.ennway.farworld.Farworld;
import net.ennway.farworld.entity.custom.AmethystConstructEntity;
import net.minecraft.client.animation.AnimationDefinition;
import net.minecraft.client.animation.KeyframeAnimations;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.AnimationState;
import net.minecraft.world.entity.Entity;
import org.joml.Vector3f;

public class AmethystConstructModel<T extends Entity> extends HierarchicalModel<T> {

	private static final Vector3f ANIMATION_VECTOR_CACHE = new Vector3f();

	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath(Farworld.MOD_ID, "amethyst_construct"), "main");
	private final ModelPart construct;
	private final ModelPart upper;
	private final ModelPart arm_left;
	private final ModelPart arm_right;
	private final ModelPart extra_head_bone;
	private final ModelPart head;
	private final ModelPart jaw;
	private final ModelPart leg_right;
	private final ModelPart leg_left;

	public AmethystConstructModel(ModelPart root) {
		this.construct = root.getChild("construct");
		this.upper = this.construct.getChild("upper");
		this.arm_left = this.upper.getChild("arm_left");
		this.arm_right = this.upper.getChild("arm_right");
		this.extra_head_bone = this.upper.getChild("extra_head_bone");
		this.head = this.extra_head_bone.getChild("head");
		this.jaw = this.head.getChild("jaw");
		this.leg_right = this.construct.getChild("leg_right");
		this.leg_left = this.construct.getChild("leg_left");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition construct = partdefinition.addOrReplaceChild("construct", CubeListBuilder.create(), PartPose.offset(9.0F, 24.0F, 5.0F));

		PartDefinition upper = construct.addOrReplaceChild("upper", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition cube_r1 = upper.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(66, 0).addBox(-6.0F, -11.0F, -7.0F, 13.0F, 11.0F, 13.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-9.0F, -10.0F, 0.0F, 0.3927F, 0.0F, 0.0F));

		PartDefinition arm_left = upper.addOrReplaceChild("arm_left", CubeListBuilder.create().texOffs(0, 31).addBox(0.0F, -2.0F, -4.0F, 8.0F, 25.0F, 8.0F, new CubeDeformation(0.0F))
				.texOffs(32, 43).addBox(0.0F, -6.0F, -4.0F, 8.0F, 4.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -23.0F, -12.0F, 0.0F, -0.0436F, 0.0F));

		PartDefinition arm_right = upper.addOrReplaceChild("arm_right", CubeListBuilder.create().texOffs(0, 31).mirror().addBox(-8.0F, -2.0F, -4.0F, 8.0F, 25.0F, 8.0F, new CubeDeformation(0.0F)).mirror(false)
				.texOffs(32, 31).mirror().addBox(-8.0F, -6.0F, -4.0F, 8.0F, 4.0F, 8.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-18.0F, -23.0F, -12.0F, 0.0F, 0.0436F, 0.0F));

		PartDefinition extra_head_bone = upper.addOrReplaceChild("extra_head_bone", CubeListBuilder.create(), PartPose.offset(-9.0F, -18.0F, -9.0F));

		PartDefinition head = extra_head_bone.addOrReplaceChild("head", CubeListBuilder.create().texOffs(0, 85).addBox(-9.0F, -8.0F, -11.0F, 19.0F, 19.0F, 19.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition jaw = head.addOrReplaceChild("jaw", CubeListBuilder.create().texOffs(52, 47).addBox(-9.0F, -13.0F, -11.25F, 19.0F, 19.0F, 19.0F, new CubeDeformation(0.5F)), PartPose.offsetAndRotation(0.0F, 4.0F, -0.5F, 0.1745F, 0.0F, 0.0F));

		PartDefinition cube_r2 = jaw.addOrReplaceChild("cube_r2", CubeListBuilder.create().texOffs(76, 105).addBox(-9.0F, -11.0F, -4.0F, 19.0F, 10.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 7.175F, 1.275F, -0.0873F, 0.0F, 0.0F));

		PartDefinition cube_r3 = jaw.addOrReplaceChild("cube_r3", CubeListBuilder.create().texOffs(68, 115).addBox(-9.0F, 2.0F, -12.0F, 19.0F, 0.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 4.0F, 1.5F, -0.0873F, 0.0F, 0.0F));

		PartDefinition leg_right = construct.addOrReplaceChild("leg_right", CubeListBuilder.create().texOffs(0, 64).mirror().addBox(-4.9962F, 0.0F, -4.0872F, 8.0F, 13.0F, 8.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-17.0F, -13.0F, 0.0F, 0.0F, 0.0873F, 0.0F));

		PartDefinition leg_left = construct.addOrReplaceChild("leg_left", CubeListBuilder.create().texOffs(0, 64).addBox(-3.0038F, 0.0F, -4.0872F, 8.0F, 13.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.0F, -13.0F, 0.0F, 0.0F, -0.0873F, 0.0F));

		return LayerDefinition.create(meshdefinition, 128, 128);
	}

	private void applyHeadRotation(float pNetHeadYaw, float pHeadPitch) {
		pNetHeadYaw = Mth.clamp(pNetHeadYaw, -30.0F, 30.0F);
		pHeadPitch = Mth.clamp(pHeadPitch, -25.0F, 45.0F);

		this.head.yRot = pNetHeadYaw * ((float)Math.PI / 180F);
		this.head.xRot = pHeadPitch * ((float)Math.PI / 180F);
	}

	protected void animate(AnimationState animationState, AnimationDefinition animationDefinition, float ageInTicks, float speed, float scale) {
		animationState.updateTime(ageInTicks, speed);
		animationState.ifStarted((p_233392_) -> {
			KeyframeAnimations.animate(this, animationDefinition, p_233392_.getAccumulatedTime(), scale, ANIMATION_VECTOR_CACHE);
		});
	}

	@Override
	public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		root().getAllParts().forEach(ModelPart::resetPose);
		this.applyHeadRotation(netHeadYaw, headPitch);

		this.animate(((AmethystConstructEntity) entity).idleAnimationState, AmethystConstructAnimations.idle, ageInTicks, 1f);
		this.animate(((AmethystConstructEntity) entity).walkAnimationState, AmethystConstructAnimations.walk, ageInTicks, ((AmethystConstructEntity) entity).walkAnimationScale, ((AmethystConstructEntity) entity).walkAnimationScale);
		this.animate(((AmethystConstructEntity) entity).attackAnimationState, AmethystConstructAnimations.attack, ageInTicks, 1f, 1f);
		this.animate(((AmethystConstructEntity) entity).eatAnimationState, AmethystConstructAnimations.eat, ageInTicks, 1f, 1f);
		this.animate(((AmethystConstructEntity) entity).eatSpitAnimationState, AmethystConstructAnimations.eatspitfoodout, ageInTicks, 1f, 1f);
	}

	@Override
	public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int i, int i1, int i2) {
		root().render(poseStack, vertexConsumer, i, i1, i2);
	}

	@Override
	public ModelPart root() {
		return construct;
	}
}