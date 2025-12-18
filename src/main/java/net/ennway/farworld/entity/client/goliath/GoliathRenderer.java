package net.ennway.farworld.entity.client.goliath;

import com.mojang.blaze3d.vertex.PoseStack;
import net.ennway.farworld.Farworld;
import net.ennway.farworld.entity.client.bloomed.BloomedEyesLayer;
import net.ennway.farworld.entity.client.bloomed.BloomedFlowerLayer;
import net.ennway.farworld.entity.client.bloomed.BloomedModel;
import net.ennway.farworld.entity.custom.BloomedEntity;
import net.ennway.farworld.entity.custom.GoliathEntity;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public class GoliathRenderer extends MobRenderer<GoliathEntity, GoliathModel<GoliathEntity>> {

    public GoliathRenderer(EntityRendererProvider.Context context) {
        super(context, new GoliathModel<>(context.bakeLayer(GoliathModel.LAYER_LOCATION)), 0.4f);
        this.addLayer(new GoliathGlowLayer<>(this));
        this.addLayer(new GoliathSaddleLayer<>(this));
    }

    @Override
    public ResourceLocation getTextureLocation(GoliathEntity entity) {
        if (this.getModel().isPet) return ResourceLocation.fromNamespaceAndPath(Farworld.MOD_ID, "textures/entity/goliath_pet.png");
        return ResourceLocation.fromNamespaceAndPath(Farworld.MOD_ID, "textures/entity/goliath.png");
    }

    @Override
    public void render(GoliathEntity entity, float entityYaw, float partialTicks, PoseStack poseStack, MultiBufferSource buffer, int packedLight) {
        if (entity.isBaby())
        {
            poseStack.scale(0.5f, 0.5f, 0.5f);
        }

        super.render(entity, entityYaw, partialTicks, poseStack, buffer, packedLight);
    }
}
