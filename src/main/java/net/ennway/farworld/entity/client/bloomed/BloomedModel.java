package net.ennway.farworld.entity.client.bloomed;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.ennway.farworld.Farworld;
import net.ennway.farworld.entity.animations.BloomedAnimations;
import net.ennway.farworld.entity.custom.BloomedEntity;
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

public class BloomedModel<T extends BloomedEntity> extends HierarchicalModel<T> {

	public boolean isArthur = false;

	private static final Vector3f ANIMATION_VECTOR_CACHE = new Vector3f();

	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath(Farworld.MOD_ID, "bloomed"), "main");

	public final ModelPart body;
	public final ModelPart abdomen;
	public final ModelPart head;
	public final ModelPart tail;
	public final ModelPart rose;
	public final ModelPart rightlegs;
	public final ModelPart outerlegsright;
	public final ModelPart innerlegright;
	public final ModelPart leftlegs;
	public final ModelPart innerlegleft;
	public final ModelPart outerlegsleft;

	public BloomedModel(ModelPart root) {
		this.body = root.getChild("body");
		this.abdomen = this.body.getChild("abdomen");
		this.head = this.abdomen.getChild("head");
		this.tail = this.abdomen.getChild("tail");
		this.rose = this.tail.getChild("rose");
		this.rightlegs = this.abdomen.getChild("rightlegs");
		this.outerlegsright = this.rightlegs.getChild("outerlegsright");
		this.innerlegright = this.rightlegs.getChild("innerlegright");
		this.leftlegs = this.abdomen.getChild("leftlegs");
		this.innerlegleft = this.leftlegs.getChild("innerlegleft");
		this.outerlegsleft = this.leftlegs.getChild("outerlegsleft");
	}

	protected void animate(AnimationState animationState, AnimationDefinition animationDefinition, float ageInTicks, float speed, float scale) {
		animationState.updateTime(ageInTicks, speed);
		animationState.ifStarted((p_233392_) -> {
			KeyframeAnimations.animate(this, animationDefinition, p_233392_.getAccumulatedTime(), scale, ANIMATION_VECTOR_CACHE);
		});
	}

	protected void animateWalk(AnimationDefinition animationDefinition, float limbSwing, float limbSwingAmount, float maxAnimationSpeed, float animationScaleFactor) {
		long i = (long)(limbSwing * 50.0F * maxAnimationSpeed);
		float f = Math.min(limbSwingAmount * animationScaleFactor, 1.0F);
		KeyframeAnimations.animate(this, animationDefinition, i, f, ANIMATION_VECTOR_CACHE);
	}

	public static LayerDefinition createBodyLayer() {MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();
		float flowerScale = 30.0f;

		PartDefinition body = partdefinition.addOrReplaceChild("body", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));

		PartDefinition abdomen = body.addOrReplaceChild("abdomen", CubeListBuilder.create().texOffs(3, 19).addBox(-3.0F, -2.0F, 3.0F, 6.0F, 4.0F, 7.0F, new CubeDeformation(0.0F))
				.texOffs(4, 19).addBox(-2.0F, -1.0F, -2.0F, 4.0F, 2.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -5.0F, -4.0F));

		PartDefinition head = abdomen.addOrReplaceChild("head", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition head_r1 = head.addOrReplaceChild("head_r1", CubeListBuilder.create().texOffs(0, 30).addBox(-2.5F, -4.0F, -5.0F, 5.0F, 5.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F));

		PartDefinition tail = abdomen.addOrReplaceChild("tail", CubeListBuilder.create(), PartPose.offset(0.0F, -1.0F, 10.0F));

		PartDefinition tail_r1 = tail.addOrReplaceChild("tail_r1", CubeListBuilder.create().texOffs(20, 30).addBox(-2.0F, -5.0F, -1.0F, 4.0F, 4.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 2.0F, 0.0F, 0.2618F, 0.0F, 0.0F));

		PartDefinition rose = tail.addOrReplaceChild("rose", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, -3.0F, -1.0F, 0.2182F, 0.0F, 0.0F));

		PartDefinition face1_r1 = rose.addOrReplaceChild("face1_r1", CubeListBuilder.create().texOffs(0, -17).addBox(0.0F, -16.0F, -11.0F, 0.0F, 16.0F, 17.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.4F, 0.0F, 2.5F, 0.0F, -0.7854F, 0.0F));

		PartDefinition face2_r1 = rose.addOrReplaceChild("face2_r1", CubeListBuilder.create().texOffs(0, -17).addBox(0.0F, -16.0F, -9.0F, 0.0F, 16.0F, 17.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.6F, 0.0F, -2.3562F, 0.0F));

		PartDefinition face1_r2 = rose.addOrReplaceChild("face1_r2", CubeListBuilder.create().texOffs(0, -17).addBox(0.0F, -16.0F, -7.0F, 0.0F, 16.0F, 17.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.4F, 0.0F, 2.5F, 0.0F, -0.7854F + Mth.PI, 0.0F));

		PartDefinition face2_r2 = rose.addOrReplaceChild("face2_r2", CubeListBuilder.create().texOffs(0, -17).addBox(0.0F, -16.0F, -9.0F, 0.0F, 16.0F, 17.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.6F, 0.0F, -2.3562F + Mth.PI, 0.0F));

		PartDefinition rightlegs = abdomen.addOrReplaceChild("rightlegs", CubeListBuilder.create(), PartPose.offset(-2.0F, 0.0F, 4.3F));

		PartDefinition outerlegsright = rightlegs.addOrReplaceChild("outerlegsright", CubeListBuilder.create(), PartPose.offset(0.0F, 1.0F, -2.75F));

		PartDefinition cube_r1 = outerlegsright.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(0, 40).addBox(-7.0F, -1.0F, -1.0F, 7.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, -0.25F, 0.0F, -0.3491F, -0.5236F));

		PartDefinition cube_r2 = outerlegsright.addOrReplaceChild("cube_r2", CubeListBuilder.create().texOffs(0, 40).addBox(-7.0F, -1.0F, -1.0F, 7.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.45F, 0.0F, 0.3491F, -0.5236F));

		PartDefinition innerlegright = rightlegs.addOrReplaceChild("innerlegright", CubeListBuilder.create(), PartPose.offset(0.0F, 1.0F, -2.7F));

		PartDefinition cube_r3 = innerlegright.addOrReplaceChild("cube_r3", CubeListBuilder.create().texOffs(0, 40).addBox(-7.0F, -1.0F, -1.0F, 7.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -0.7854F, -0.5236F));

		PartDefinition cube_r4 = innerlegright.addOrReplaceChild("cube_r4", CubeListBuilder.create().texOffs(0, 40).addBox(-7.0F, -1.0F, -1.0F, 7.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, -0.5236F));

		PartDefinition leftlegs = abdomen.addOrReplaceChild("leftlegs", CubeListBuilder.create(), PartPose.offsetAndRotation(2.0F, 0.0F, 5.3F, 0.0F, 3.1416F, 0.0F));

		PartDefinition innerlegleft = leftlegs.addOrReplaceChild("innerlegleft", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 3.8F));

		PartDefinition cube_r5 = innerlegleft.addOrReplaceChild("cube_r5", CubeListBuilder.create().texOffs(0, 40).addBox(-7.5F, -0.134F, -1.0F, 7.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, -0.5F, 0.0F, 0.7854F, -0.5236F));

		PartDefinition cube_r6 = innerlegleft.addOrReplaceChild("cube_r6", CubeListBuilder.create().texOffs(0, 40).addBox(-7.5F, -0.134F, 2.0F, 7.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, -3.5F, 0.0F, 0.0F, -0.5236F));

		PartDefinition outerlegsleft = leftlegs.addOrReplaceChild("outerlegsleft", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 3.2F));

		PartDefinition cube_r7 = outerlegsleft.addOrReplaceChild("cube_r7", CubeListBuilder.create().texOffs(0, 40).addBox(-8.4959F, -0.134F, 1.6481F, 7.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, -2.5F, 0.0F, 0.3491F, -0.5236F));

		PartDefinition cube_r8 = outerlegsleft.addOrReplaceChild("cube_r8", CubeListBuilder.create().texOffs(0, 40).addBox(-6.4438F, -0.134F, 1.9901F, 7.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, -3.2F, 0.0F, -0.3491F, -0.5236F));

		return LayerDefinition.create(meshdefinition, 64, 64);
	}

	@Override
	public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		this.body.getAllParts().forEach(ModelPart::resetPose);
		this.applyHeadRotation(netHeadYaw, headPitch);

		if (((BloomedEntity) entity).isArthur())
			isArthur = true;
		else isArthur = false;

		this.animate(((BloomedEntity) entity).idleAnimationState, BloomedAnimations.BloomedIdle, ageInTicks, 1f);
		this.animate(((BloomedEntity) entity).walkAnimationState, BloomedAnimations.BloomedWalking, ageInTicks, ((BloomedEntity) entity).walkAnimationScale, ((BloomedEntity) entity).walkAnimationScale);
		this.animate(((BloomedEntity) entity).tamedAnimationState, BloomedAnimations.BloomedTamed, ageInTicks, 1f, ((BloomedEntity) entity).tamedAnimationScale);
	}

	private void applyHeadRotation(float pNetHeadYaw, float pHeadPitch) {
		pNetHeadYaw = Mth.clamp(pNetHeadYaw, -30.0F, 30.0F);
		pHeadPitch = Mth.clamp(pHeadPitch, -25.0F, 45.0F);

		this.head.yRot = pNetHeadYaw * ((float)Math.PI / 180F);
		this.head.xRot = pHeadPitch * ((float)Math.PI / 180F);
	}

	@Override
	public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int i, int i1, int i2) {
		body.render(poseStack, vertexConsumer, i, i1, i2);
	}

	@Override
	public ModelPart root() {
		return this.body;
	}
}