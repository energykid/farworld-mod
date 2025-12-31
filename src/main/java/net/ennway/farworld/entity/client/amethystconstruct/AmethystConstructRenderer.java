package net.ennway.farworld.entity.client.amethystconstruct;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import com.mojang.math.Transformation;
import net.ennway.farworld.Farworld;
import net.ennway.farworld.entity.client.bloomed.BloomedEyesLayer;
import net.ennway.farworld.entity.client.bloomed.BloomedFlowerLayer;
import net.ennway.farworld.entity.client.bloomed.BloomedModel;
import net.ennway.farworld.entity.custom.AmethystConstructEntity;
import net.ennway.farworld.entity.custom.AmethystConstructEntity;
import net.ennway.farworld.registries.ModDataComponents;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec3;
import org.joml.Quaternionf;
import org.joml.Vector3f;

import java.util.Objects;

public class AmethystConstructRenderer extends MobRenderer<AmethystConstructEntity, AmethystConstructModel<AmethystConstructEntity>> {

    public float itemXRot = 0f;
    public float itemYRot = 0f;

    public AmethystConstructRenderer(EntityRendererProvider.Context context) {
        super(context, new AmethystConstructModel<>(context.bakeLayer(AmethystConstructModel.LAYER_LOCATION)), 1f);
    }

    @Override
    public ResourceLocation getTextureLocation(AmethystConstructEntity entity) {
        return ResourceLocation.fromNamespaceAndPath(Farworld.MOD_ID, "textures/entity/amethyst_construct.png");
    }

    public float getJawYRot(float origYaw)
    {
        return origYaw - this.model.jaw.yRot - this.model.head.yRot - this.model.extra_head_bone.yRot - this.model.upper.yRot - this.model.root().yRot;
    }

    public float getJawXRot()
    {
        return this.model.jaw.xRot + this.model.head.xRot + this.model.extra_head_bone.xRot + this.model.upper.xRot + this.model.root().xRot;
    }

    public Quaternionf getJawRotQuat(float origYaw)
    {
        return Axis.YP.rotation(getJawYRot((float)Math.toRadians(-origYaw))).mul(Axis.XP.rotation(getJawXRot()));
    }

    @Override
    public void render(AmethystConstructEntity entity, float entityYaw, float partialTicks, PoseStack poseStack, MultiBufferSource buffer, int packedLight) {

        if (entity.level().isClientSide) {
            if (entity.getDeltaMovement().x != 0 || entity.getDeltaMovement().z != 0)
                entity.walkAnimationScale = Mth.lerp(0.15f, entity.walkAnimationScale, 1f);
            else
                entity.walkAnimationScale = Mth.lerp(0.15f, entity.walkAnimationScale, 0f);
        }

        if (entity.isBaby())
        {
            poseStack.scale(0.5f, 0.5f, 0.5f);
        }

        super.render(entity, entityYaw, partialTicks, poseStack, buffer, packedLight);
    }
}
