package net.ennway.farworld.entity.client.brittle;

import com.mojang.blaze3d.vertex.PoseStack;
import net.ennway.farworld.Farworld;
import net.ennway.farworld.entity.client.soulgolem.SoulGolemGlowLayer;
import net.ennway.farworld.entity.client.soulgolem.SoulGolemModel;
import net.ennway.farworld.entity.custom.BrittleEntity;
import net.ennway.farworld.entity.custom.SoulGolemEntity;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public class BrittleRenderer extends MobRenderer<BrittleEntity, BrittleModel<BrittleEntity>> {

    public BrittleRenderer(EntityRendererProvider.Context context) {
        super(context, new BrittleModel<>(context.bakeLayer(BrittleModel.LAYER_LOCATION)), 0.6f);
        this.addLayer(new BrittleGlowLayer<>(this));
    }

    @Override
    public ResourceLocation getTextureLocation(BrittleEntity entity) {
        return ResourceLocation.fromNamespaceAndPath(Farworld.MOD_ID, "textures/entity/brittle.png");
    }



    @Override
    public void render(BrittleEntity entity, float entityYaw, float partialTicks, PoseStack poseStack, MultiBufferSource buffer, int packedLight) {
        if (entity.isBaby())
        {
            poseStack.scale(0.5f, 0.5f, 0.5f);
        }

        super.render(entity, entityYaw, partialTicks, poseStack, buffer, packedLight);
    }
}
