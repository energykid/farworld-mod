package net.ennway.farworld.entity.client.goliath;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.ennway.farworld.Farworld;
import net.ennway.farworld.entity.animations.BloomedAnimations;
import net.ennway.farworld.entity.animations.GoliathAnimations;
import net.ennway.farworld.entity.custom.BloomedEntity;
import net.ennway.farworld.entity.custom.GoliathEntity;
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

public class GoliathModel<T extends GoliathEntity> extends HierarchicalModel<T> {
	public boolean isPet = false;
	public boolean saddled = false;
	// This layer location should be baked with EntityRendererProvider.Context in the entity renderer and passed into this model's constructor
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath("modid", "goliath"), "main");
	private final ModelPart critter;
	private final ModelPart body;
	private final ModelPart head;
	private final ModelPart mouththings;
	private final ModelPart mandible;
	private final ModelPart m1;
	private final ModelPart m2;
	private final ModelPart rear;
	private final ModelPart leftlegs;
	private final ModelPart leg1_left;
	private final ModelPart elbow5;
	private final ModelPart leg2_left;
	private final ModelPart elbow6;
	private final ModelPart leg3_left;
	private final ModelPart elbow7;
	private final ModelPart leg4_left;
	private final ModelPart elbow8;
	private final ModelPart rightlegs;
	private final ModelPart leg1_right;
	private final ModelPart elbow;
	private final ModelPart leg2_right;
	private final ModelPart elbow2;
	private final ModelPart leg3_right;
	private final ModelPart elbow3;
	private final ModelPart leg4_right;
	private final ModelPart elbow4;

	public GoliathModel(ModelPart root) {
		this.critter = root.getChild("critter");
		this.body = this.critter.getChild("body");
		this.head = this.body.getChild("head");
		this.mouththings = this.head.getChild("mouththings");
		this.mandible = this.head.getChild("mandible");
		this.m1 = this.mandible.getChild("m1");
		this.m2 = this.mandible.getChild("m2");
		this.rear = this.body.getChild("rear");
		this.leftlegs = this.critter.getChild("leftlegs");
		this.leg1_left = this.leftlegs.getChild("leg1_left");
		this.elbow5 = this.leg1_left.getChild("elbow5");
		this.leg2_left = this.leftlegs.getChild("leg2_left");
		this.elbow6 = this.leg2_left.getChild("elbow6");
		this.leg3_left = this.leftlegs.getChild("leg3_left");
		this.elbow7 = this.leg3_left.getChild("elbow7");
		this.leg4_left = this.leftlegs.getChild("leg4_left");
		this.elbow8 = this.leg4_left.getChild("elbow8");
		this.rightlegs = this.critter.getChild("rightlegs");
		this.leg1_right = this.rightlegs.getChild("leg1_right");
		this.elbow = this.leg1_right.getChild("elbow");
		this.leg2_right = this.rightlegs.getChild("leg2_right");
		this.elbow2 = this.leg2_right.getChild("elbow2");
		this.leg3_right = this.rightlegs.getChild("leg3_right");
		this.elbow3 = this.leg3_right.getChild("elbow3");
		this.leg4_right = this.rightlegs.getChild("leg4_right");
		this.elbow4 = this.leg4_right.getChild("elbow4");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition critter = partdefinition.addOrReplaceChild("critter", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));

		PartDefinition body = critter.addOrReplaceChild("body", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition cube_r1 = body.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(0, 98).addBox(-6.0F, -5.4387F, -4.9998F, 12.0F, 12.0F, 14.0F, new CubeDeformation(0.4F))
		.texOffs(0, 42).addBox(-6.0F, -5.4387F, -4.9998F, 12.0F, 12.0F, 14.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -12.5613F, -1.0002F, -3.1416F, 0.0F, 3.1416F));

		PartDefinition head = body.addOrReplaceChild("head", CubeListBuilder.create(), PartPose.offset(0.0F, -10.5613F, -10.0002F));

		PartDefinition cube_r2 = head.addOrReplaceChild("cube_r2", CubeListBuilder.create().texOffs(86, 0).addBox(-5.0F, -5.4387F, 0.0002F, 10.0F, 10.0F, 9.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -3.1416F, 0.0F, 3.1416F));

		PartDefinition mouththings = head.addOrReplaceChild("mouththings", CubeListBuilder.create(), PartPose.offset(1.5F, 0.0F, -9.0F));

		PartDefinition cube_r3 = mouththings.addOrReplaceChild("cube_r3", CubeListBuilder.create().texOffs(54, 90).addBox(0.0F, 0.5613F, 0.0002F, 3.0F, 5.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.0F, 0.0F, 0.0F, -3.1416F, 0.0F, 3.1416F));

		PartDefinition cube_r4 = mouththings.addOrReplaceChild("cube_r4", CubeListBuilder.create().texOffs(64, 90).addBox(-2.0F, 0.5613F, 0.0002F, 3.0F, 5.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -3.1416F, 0.0F, 3.1416F));

		PartDefinition mandible = head.addOrReplaceChild("mandible", CubeListBuilder.create(), PartPose.offset(6.0F, 3.0613F, -10.0002F));

		PartDefinition m1 = mandible.addOrReplaceChild("m1", CubeListBuilder.create(), PartPose.offset(-9.3796F, -2.8828F, 2.3825F));

		PartDefinition cube_r5 = m1.addOrReplaceChild("cube_r5", CubeListBuilder.create().texOffs(86, 28).addBox(-3.0F, -5.5F, 0.0F, 6.0F, 11.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.0F, 3.0F, -5.0F, -1.1034F, 0.3542F, 0.1733F));

		PartDefinition m2 = mandible.addOrReplaceChild("m2", CubeListBuilder.create(), PartPose.offset(-2.6204F, -2.8828F, 2.3825F));

		PartDefinition cube_r6 = m2.addOrReplaceChild("cube_r6", CubeListBuilder.create().texOffs(86, 28).mirror().addBox(-3.0F, -5.5F, 0.0F, 6.0F, 11.0F, 0.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(2.0F, 3.0F, -5.0F, -1.1034F, -0.3542F, -0.1733F));

		PartDefinition rear = body.addOrReplaceChild("rear", CubeListBuilder.create(), PartPose.offset(0.0F, -12.5613F, 0.4998F));

		PartDefinition cube_r7 = rear.addOrReplaceChild("cube_r7", CubeListBuilder.create().texOffs(0, 0).addBox(-10.0F, -10.5469F, -23.6056F, 20.0F, 19.0F, 23.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 2.0F, 3.0107F, 0.0F, -3.1416F));

		PartDefinition leftlegs = critter.addOrReplaceChild("leftlegs", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition leg1_left = leftlegs.addOrReplaceChild("leg1_left", CubeListBuilder.create().texOffs(86, 19).mirror().addBox(-1.0F, -2.0F, -3.0F, 20.0F, 5.0F, 4.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(6.0F, -8.0F, -10.0F, -0.4687F, 0.801F, -0.7251F));

		PartDefinition elbow5 = leg1_left.addOrReplaceChild("elbow5", CubeListBuilder.create(), PartPose.offsetAndRotation(18.5F, -1.0F, 0.0F, 0.0F, 0.0F, 0.6981F));

		PartDefinition cube_r8 = elbow5.addOrReplaceChild("cube_r8", CubeListBuilder.create().texOffs(0, 72).mirror().addBox(-2.5F, -3.0F, -4.0F, 21.0F, 4.0F, 6.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(0.2856F, 2.0321F, 0.0F, 0.0F, 0.0F, 0.7854F));

		PartDefinition leg2_left = leftlegs.addOrReplaceChild("leg2_left", CubeListBuilder.create().texOffs(54, 72).mirror().addBox(-5.0F, -2.0F, -3.0F, 23.0F, 5.0F, 4.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(10.0F, -12.0F, -6.0F, -0.0386F, 0.2616F, -0.4545F));

		PartDefinition elbow6 = leg2_left.addOrReplaceChild("elbow6", CubeListBuilder.create(), PartPose.offsetAndRotation(17.5F, -1.0F, 0.0F, 0.0F, 0.0F, 0.6981F));

		PartDefinition cube_r9 = elbow6.addOrReplaceChild("cube_r9", CubeListBuilder.create().texOffs(52, 52).mirror().addBox(-2.5F, -3.0F, -4.0F, 23.0F, 4.0F, 6.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(0.2856F, 2.0321F, 0.0F, 0.0F, 0.0F, 0.7854F));

		PartDefinition leg3_left = leftlegs.addOrReplaceChild("leg3_left", CubeListBuilder.create().texOffs(54, 81).mirror().addBox(-5.0F, -2.0F, -3.0F, 23.0F, 5.0F, 4.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(10.0F, -12.0F, -1.0F, 0.0782F, -0.0927F, -0.4463F));

		PartDefinition elbow7 = leg3_left.addOrReplaceChild("elbow7", CubeListBuilder.create(), PartPose.offsetAndRotation(17.5F, -1.0F, 0.0F, 0.0F, 0.0F, 0.6981F));

		PartDefinition cube_r10 = elbow7.addOrReplaceChild("cube_r10", CubeListBuilder.create().texOffs(52, 62).mirror().addBox(-2.5F, -3.0F, -4.0F, 23.0F, 4.0F, 6.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(0.2856F, 2.0321F, 0.0F, 0.0F, 0.0F, 0.7854F));

		PartDefinition leg4_left = leftlegs.addOrReplaceChild("leg4_left", CubeListBuilder.create().texOffs(0, 82).mirror().addBox(-5.0F, -2.0F, -3.0F, 23.0F, 5.0F, 4.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(10.0F, -12.0F, 4.0F, 0.3258F, -0.4746F, -0.7078F));

		PartDefinition elbow8 = leg4_left.addOrReplaceChild("elbow8", CubeListBuilder.create(), PartPose.offsetAndRotation(17.5F, -1.0F, 0.0F, 0.0F, 0.0F, 1.0472F));

		PartDefinition cube_r11 = elbow8.addOrReplaceChild("cube_r11", CubeListBuilder.create().texOffs(52, 42).mirror().addBox(-2.5F, -3.0F, -4.0F, 25.0F, 4.0F, 6.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(0.2856F, 2.0321F, 0.0F, 0.0F, 0.0F, 0.7854F));

		PartDefinition rightlegs = critter.addOrReplaceChild("rightlegs", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition leg1_right = rightlegs.addOrReplaceChild("leg1_right", CubeListBuilder.create().texOffs(86, 19).addBox(-19.0F, -2.0F, -3.0F, 20.0F, 5.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-6.0F, -8.0F, -10.0F, -0.4687F, -0.801F, 0.7251F));

		PartDefinition elbow = leg1_right.addOrReplaceChild("elbow", CubeListBuilder.create(), PartPose.offsetAndRotation(-18.5F, -1.0F, 0.0F, 0.0F, 0.0F, -0.6981F));

		PartDefinition cube_r12 = elbow.addOrReplaceChild("cube_r12", CubeListBuilder.create().texOffs(0, 72).addBox(-18.5F, -3.0F, -4.0F, 21.0F, 4.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.2856F, 2.0321F, 0.0F, 0.0F, 0.0F, -0.7854F));

		PartDefinition leg2_right = rightlegs.addOrReplaceChild("leg2_right", CubeListBuilder.create().texOffs(54, 72).addBox(-18.0F, -2.0F, -3.0F, 23.0F, 5.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-10.0F, -12.0F, -6.0F, -0.0386F, -0.2616F, 0.4545F));

		PartDefinition elbow2 = leg2_right.addOrReplaceChild("elbow2", CubeListBuilder.create(), PartPose.offsetAndRotation(-17.5F, -1.0F, 0.0F, 0.0F, 0.0F, -0.6981F));

		PartDefinition cube_r13 = elbow2.addOrReplaceChild("cube_r13", CubeListBuilder.create().texOffs(52, 52).addBox(-20.5F, -3.0F, -4.0F, 23.0F, 4.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.2856F, 2.0321F, 0.0F, 0.0F, 0.0F, -0.7854F));

		PartDefinition leg3_right = rightlegs.addOrReplaceChild("leg3_right", CubeListBuilder.create().texOffs(54, 81).addBox(-18.0F, -2.0F, -3.0F, 23.0F, 5.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-10.0F, -12.0F, -1.0F, 0.0782F, 0.0927F, 0.4463F));

		PartDefinition elbow3 = leg3_right.addOrReplaceChild("elbow3", CubeListBuilder.create(), PartPose.offsetAndRotation(-17.5F, -1.0F, 0.0F, 0.0F, 0.0F, -0.6981F));

		PartDefinition cube_r14 = elbow3.addOrReplaceChild("cube_r14", CubeListBuilder.create().texOffs(52, 62).addBox(-20.5F, -3.0F, -4.0F, 23.0F, 4.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.2856F, 2.0321F, 0.0F, 0.0F, 0.0F, -0.7854F));

		PartDefinition leg4_right = rightlegs.addOrReplaceChild("leg4_right", CubeListBuilder.create().texOffs(0, 82).addBox(-18.0F, -2.0F, -3.0F, 23.0F, 5.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-10.0F, -12.0F, 4.0F, 0.3258F, 0.4746F, 0.7078F));

		PartDefinition elbow4 = leg4_right.addOrReplaceChild("elbow4", CubeListBuilder.create(), PartPose.offsetAndRotation(-17.5F, -1.0F, 0.0F, 0.0F, 0.0F, -1.0472F));

		PartDefinition cube_r15 = elbow4.addOrReplaceChild("cube_r15", CubeListBuilder.create().texOffs(52, 42).addBox(-22.5F, -3.0F, -4.0F, 25.0F, 4.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.2856F, 2.0321F, 0.0F, 0.0F, 0.0F, -0.7854F));

		return LayerDefinition.create(meshdefinition, 134, 134);
	}

	@Override
	public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int i, int i1, int i2) {
		critter.render(poseStack, vertexConsumer, i, i1, i2);
	}

	@Override
	public ModelPart root() {
		return this.critter;
	}

	private void applyHeadRotation(float pNetHeadYaw, float pHeadPitch) {
		pNetHeadYaw = Mth.clamp(pNetHeadYaw, -30.0F, 30.0F);
		pHeadPitch = Mth.clamp(pHeadPitch, -25.0F, 45.0F);

		this.head.yRot = pNetHeadYaw * ((float)Math.PI / 180F);
		this.head.xRot = pHeadPitch * ((float)Math.PI / 180F);
	}

	private static final Vector3f ANIMATION_VECTOR_CACHE = new Vector3f();

	protected void animate(AnimationState animationState, AnimationDefinition animationDefinition, float ageInTicks, float speed, float scale) {
		animationState.updateTime(ageInTicks, speed);
		animationState.ifStarted((p_233392_) -> {
			KeyframeAnimations.animate(this, animationDefinition, p_233392_.getAccumulatedTime(), scale, ANIMATION_VECTOR_CACHE);
		});
	}

	@Override
	public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		isPet = entity.getEntityData().get(GoliathEntity.PET_TICKS) > 0;
		saddled = entity.getEntityData().get(GoliathEntity.SADDLED);

		this.critter.getAllParts().forEach(ModelPart::resetPose);
		this.applyHeadRotation(netHeadYaw, headPitch);

		this.animate(entity.idleAnimationState, GoliathAnimations.idle, ageInTicks, 1f);
		this.animate(entity.walkAnimationState, GoliathAnimations.walk, ageInTicks, entity.walkAnimationSpeed, entity.walkAnimationScale);
		this.animate(entity.attackAnimationState, GoliathAnimations.attack, ageInTicks, 1f);
		this.animate(entity.petAnimationState, GoliathAnimations.pet, ageInTicks, 1f);
	}
}