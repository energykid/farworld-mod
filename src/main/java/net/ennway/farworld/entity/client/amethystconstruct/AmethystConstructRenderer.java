package net.ennway.farworld.entity.client.amethystconstruct;

import com.mojang.blaze3d.vertex.PoseStack;
import net.ennway.farworld.Farworld;
import net.ennway.farworld.entity.client.bloomed.BloomedEyesLayer;
import net.ennway.farworld.entity.client.bloomed.BloomedFlowerLayer;
import net.ennway.farworld.entity.client.bloomed.BloomedModel;
import net.ennway.farworld.entity.custom.AmethystConstructEntity;
import net.ennway.farworld.entity.custom.AmethystConstructEntity;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;

public class AmethystConstructRenderer extends MobRenderer<AmethystConstructEntity, AmethystConstructModel<AmethystConstructEntity>> {

    public AmethystConstructRenderer(EntityRendererProvider.Context context) {
        super(context, new AmethystConstructModel<>(context.bakeLayer(AmethystConstructModel.LAYER_LOCATION)), 1f);
    }

    @Override
    public ResourceLocation getTextureLocation(AmethystConstructEntity entity) {
        return ResourceLocation.fromNamespaceAndPath(Farworld.MOD_ID, "textures/entity/amethyst_construct.png");
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
