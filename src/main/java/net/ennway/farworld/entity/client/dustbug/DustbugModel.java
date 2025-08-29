package net.ennway.farworld.entity.client.dustbug;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.ennway.farworld.Farworld;
import net.ennway.farworld.entity.animations.BrittleAnimations;
import net.ennway.farworld.entity.animations.DustbugAnimations;
import net.ennway.farworld.entity.custom.BrittleEntity;
import net.ennway.farworld.entity.custom.DustbugEntity;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;


public class DustbugModel<T extends DustbugEntity> extends HierarchicalModel<T> {
	// This layer location should be baked with EntityRendererProvider.Context in the entity renderer and passed into this model's constructor
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath(Farworld.MOD_ID, "dustbug"), "main");
	private final ModelPart body;
	private final ModelPart head;
	private final ModelPart mandibles;
	private final ModelPart left;
	private final ModelPart right;
	private final ModelPart tail1;
	private final ModelPart tail2;
	private final ModelPart crest;
	private final ModelPart large;
	private final ModelPart med;
	private final ModelPart small;

	public DustbugModel(ModelPart root) {
		this.body = root.getChild("body");
		this.head = this.body.getChild("head");
		this.mandibles = this.head.getChild("mandibles");
		this.left = this.mandibles.getChild("left");
		this.right = this.mandibles.getChild("right");
		this.tail1 = this.body.getChild("tail1");
		this.tail2 = this.tail1.getChild("tail2");
		this.crest = this.body.getChild("crest");
		this.large = this.crest.getChild("large");
		this.med = this.crest.getChild("med");
		this.small = this.crest.getChild("small");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition body = partdefinition.addOrReplaceChild("body", CubeListBuilder.create().texOffs(0, 0).addBox(-3.0F, -2.0F, -2.0F, 6.0F, 4.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 22.0F, -1.0F));

		PartDefinition head = body.addOrReplaceChild("head", CubeListBuilder.create().texOffs(18, 11).addBox(-2.0F, -1.0F, -4.0F, 4.0F, 3.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, -2.0F));

		PartDefinition mandibles = head.addOrReplaceChild("mandibles", CubeListBuilder.create(), PartPose.offset(0.0F, 1.6F, -5.0F));

		PartDefinition left = mandibles.addOrReplaceChild("left", CubeListBuilder.create(), PartPose.offset(1.6F, -1.5F, 0.0F));

		PartDefinition cube_r1 = left.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(14, 22).addBox(-0.4806F, -0.4937F, 0.0F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.1F, 0.2F, 0.0F, 0.0F, 0.0F, 0.2967F));

		PartDefinition right = mandibles.addOrReplaceChild("right", CubeListBuilder.create(), PartPose.offset(-1.3F, -1.2F, 0.0F));

		PartDefinition cube_r2 = right.addOrReplaceChild("cube_r2", CubeListBuilder.create().texOffs(14, 19).addBox(-0.487F, -0.7947F, 0.0F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.1F, 0.3F, 0.0F, 0.0F, 0.0F, -0.384F));

		PartDefinition tail1 = body.addOrReplaceChild("tail1", CubeListBuilder.create().texOffs(18, 18).addBox(-2.0F, -1.0F, -0.2F, 4.0F, 3.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 5.2F));

		PartDefinition tail2 = tail1.addOrReplaceChild("tail2", CubeListBuilder.create().texOffs(0, 25).addBox(-1.0F, 0.0F, -0.1F, 2.0F, 2.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 3.9F));

		PartDefinition crest = body.addOrReplaceChild("crest", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition large = crest.addOrReplaceChild("large", CubeListBuilder.create().texOffs(0, 11).addBox(-4.0F, -6.0F, 1.0F, 8.0F, 7.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition med = crest.addOrReplaceChild("med", CubeListBuilder.create().texOffs(0, 19).addBox(-3.0F, -4.0F, -4.0F, 6.0F, 5.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition small = crest.addOrReplaceChild("small", CubeListBuilder.create().texOffs(12, 25).addBox(-3.0F, -2.0F, -4.0F, 5.0F, 3.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.5F, -0.9F, 11.0F));

		return LayerDefinition.create(meshdefinition, 64, 64);
	}


	public String getGlowTexture() {
		return "textures/entity/brittle_glow.png";
	}

	@Override
	public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int i, int i1, int i2) {
		this.body.render(poseStack, vertexConsumer, i, i1, i2);
	}

	@Override
	public ModelPart root() {
		return this.body;
	}

	@Override
	public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		this.body.getAllParts().forEach(ModelPart::resetPose);

		this.animate(entity.idleAnimationState, DustbugAnimations.idle, ageInTicks, 0f);
		this.animate(entity.walkAnimationState, DustbugAnimations.crawl, ageInTicks, 1f);
	}
}