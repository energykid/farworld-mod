package net.ennway.farworld.entity.client.soulgolem;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.ennway.farworld.Farworld;
import net.ennway.farworld.entity.animations.BloomedAnimations;
import net.ennway.farworld.entity.animations.SoulGolemAnimations;
import net.ennway.farworld.entity.custom.BloomedEntity;
import net.ennway.farworld.entity.custom.SoulGolemEntity;
import net.minecraft.client.animation.AnimationDefinition;
import net.minecraft.client.animation.KeyframeAnimations;
import net.minecraft.client.model.EntityModel;
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


public class SoulGolemModel<T extends Entity> extends HierarchicalModel<T> {
	// This layer location should be baked with EntityRendererProvider.Context in the entity renderer and passed into this model's constructor
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath(Farworld.MOD_ID, "soul_steel_golem"), "main");

	private static final Vector3f ANIMATION_VECTOR_CACHE = new Vector3f();

	public int actScale = 0;

	public float idleScale = 0f;

	private final ModelPart all;
	private final ModelPart body;
	private final ModelPart head;
	private final ModelPart bigarm;
	private final ModelPart smallarm;
	private final ModelPart leftleg;
	private final ModelPart rightleg;

	public SoulGolemModel(ModelPart root) {
		this.idleScale = 0f;
		this.actScale = 0;

		this.all = root.getChild("all");
		this.body = this.all.getChild("body");
		this.head = this.body.getChild("head");
		this.bigarm = this.body.getChild("bigarm");
		this.smallarm = this.body.getChild("smallarm");
		this.leftleg = this.body.getChild("leftleg");
		this.rightleg = this.body.getChild("rightleg");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition all = partdefinition.addOrReplaceChild("all", CubeListBuilder.create(), PartPose.offset(0.0F, 6.0F, 0.0F));

		PartDefinition body = all.addOrReplaceChild("body", CubeListBuilder.create().texOffs(0, 20).addBox(-5.0F, -16.0F, -2.5F, 10.0F, 16.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition head = body.addOrReplaceChild("head", CubeListBuilder.create().texOffs(0, 0).addBox(-5.0F, -10.0F, -5.0F, 10.0F, 10.0F, 10.0F, new CubeDeformation(0.0F))
				.texOffs(58, 0).addBox(-5.0F, -10.0F, -5.0F, 10.0F, 10.0F, 10.0F, new CubeDeformation(0.25F)), PartPose.offset(0.0F, -16.0F, 0.0F));

		PartDefinition bigarm = body.addOrReplaceChild("bigarm", CubeListBuilder.create().texOffs(40, 42).addBox(-4.0F, -2.0F, -2.5F, 4.0F, 5.0F, 5.0F, new CubeDeformation(0.0F))
				.texOffs(30, 20).addBox(-6.0F, 3.0F, -2.5F, 6.0F, 17.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offset(-5.0F, -14.0F, 0.0F));

		PartDefinition smallarm = body.addOrReplaceChild("smallarm", CubeListBuilder.create().texOffs(40, 0).addBox(0.0F, -2.0F, -2.5F, 4.0F, 6.0F, 5.0F, new CubeDeformation(0.01F))
				.texOffs(52, 31).addBox(0.0F, -2.0F, -2.5F, 4.0F, 6.0F, 5.0F, new CubeDeformation(0.25F)), PartPose.offset(5.0F, -14.0F, 0.0F));

		PartDefinition leftleg = body.addOrReplaceChild("leftleg", CubeListBuilder.create().texOffs(20, 42).addBox(-2.5F, 0.0F, -2.5F, 5.0F, 18.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offset(2.5F, 0.0F, 0.0F));

		PartDefinition rightleg = body.addOrReplaceChild("rightleg", CubeListBuilder.create().texOffs(0, 41).addBox(-2.5F, 0.0F, -2.5F, 5.0F, 18.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offset(-2.5F, 0.0F, 0.0F));

		return LayerDefinition.create(meshdefinition, 128, 128);
	}

	protected void animate(AnimationState animationState, AnimationDefinition animationDefinition, float ageInTicks, float speed, float scale) {
		animationState.updateTime(ageInTicks, speed);
		animationState.ifStarted((p_233392_) -> {
			KeyframeAnimations.animate(this, animationDefinition, p_233392_.getAccumulatedTime(), scale, ANIMATION_VECTOR_CACHE);
		});
	}

	@Override
	public void setupAnim(Entity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		this.body.getAllParts().forEach(ModelPart::resetPose);

		SoulGolemEntity golemEntity = ((SoulGolemEntity)entity);

		float targetScale = golemEntity.getEntityData().get(SoulGolemEntity.ACTIVITY_SCALE) == 2 ? 1 : (golemEntity.getEntityData().get(SoulGolemEntity.ACTIVITY_SCALE) == 1 ? 1f : 0f);

		float aa = 0.02f;
		if (golemEntity.activityScale < targetScale) aa = 0.08f;

		golemEntity.activityScale = Mth.lerp(aa, golemEntity.activityScale, targetScale);

		if (golemEntity.activityScale > 0.5f)
		{
			this.applyHeadRotation(netHeadYaw, headPitch);
		}

		this.actScale = ((SoulGolemEntity) entity).getActivity();

		this.animate(((SoulGolemEntity) entity).idleAnimationState, SoulGolemAnimations.active_idle, ageInTicks, 1f, golemEntity.activityScale);
		this.animate(((SoulGolemEntity) entity).walkAnimationState, SoulGolemAnimations.active_walking, ageInTicks, ((SoulGolemEntity) entity).walkAnimationScale, ((SoulGolemEntity) entity).walkAnimationScale * golemEntity.activityScale);
		this.animate(((SoulGolemEntity) entity).slamAnimationState, SoulGolemAnimations.slam, ageInTicks, 1f, 1f);

		this.animate(((SoulGolemEntity) entity).inactiveAnimationState, SoulGolemAnimations.inactive, ageInTicks, 1f, -golemEntity.activityScale + 1);
	}

	public String getGlowTexture()
	{
		if (this.actScale == 2)
		{
			return "textures/entity/soul_golem_full_glow.png";
		}
		else if (this.actScale == 1)
		{
			return "textures/entity/soul_golem_medium_glow.png";
		}
		else
		{
			return "textures/entity/soul_golem_low_glow.png";
		}
	}

	private void applyHeadRotation(float pNetHeadYaw, float pHeadPitch) {
		pNetHeadYaw = Mth.clamp(pNetHeadYaw, -30.0F, 30.0F);
		pHeadPitch = Mth.clamp(pHeadPitch, -25.0F, 45.0F);

		this.head.yRot = pNetHeadYaw * ((float)Math.PI / 180F);
		this.head.xRot = pHeadPitch * ((float)Math.PI / 180F);
	}

	@Override
	public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int i, int i1, int i2) {
		this.all.render(poseStack, vertexConsumer, i, i1, i2);
	}

	@Override
	public ModelPart root() {
		return this.all;
	}
}