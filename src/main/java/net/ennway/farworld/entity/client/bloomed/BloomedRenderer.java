package net.ennway.farworld.entity.client.bloomed;

import com.mojang.blaze3d.vertex.PoseStack;
import net.ennway.farworld.Farworld;
import net.ennway.farworld.entity.custom.BloomedEntity;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public class BloomedRenderer extends MobRenderer<BloomedEntity, BloomedModel<BloomedEntity>> {

    public BloomedRenderer(EntityRendererProvider.Context context) {
        super(context, new BloomedModel<>(context.bakeLayer(BloomedModel.LAYER_LOCATION)), 0.4f);
        this.addLayer(new BloomedEyesLayer<>(this));
        this.addLayer(new BloomedFlowerLayer<>(this));
    }

    @Override
    public ResourceLocation getTextureLocation(BloomedEntity bloomedEntity) {
        if (bloomedEntity.isArthur())
        {
            return ResourceLocation.fromNamespaceAndPath(Farworld.MOD_ID, "textures/entity/arthur.png");
        }
        return ResourceLocation.fromNamespaceAndPath(Farworld.MOD_ID, "textures/entity/bloomed.png");
    }

    @Override
    public void render(BloomedEntity entity, float entityYaw, float partialTicks, PoseStack poseStack, MultiBufferSource buffer, int packedLight) {
        if (entity.isBaby())
        {
            poseStack.scale(0.5f, 0.5f, 0.5f);
        }

        super.render(entity, entityYaw, partialTicks, poseStack, buffer, packedLight);
    }
}
