package net.ennway.farworld.entity.client.dustbug;

import com.mojang.blaze3d.vertex.PoseStack;
import net.ennway.farworld.Farworld;
import net.ennway.farworld.entity.client.dustbug.DustbugModel;
import net.ennway.farworld.entity.client.soulgolem.SoulGolemGlowLayer;
import net.ennway.farworld.entity.client.soulgolem.SoulGolemModel;
import net.ennway.farworld.entity.custom.BrittleEntity;
import net.ennway.farworld.entity.custom.DustbugEntity;
import net.ennway.farworld.entity.custom.SoulGolemEntity;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public class DustbugRenderer extends MobRenderer<DustbugEntity, DustbugModel<DustbugEntity>> {

    public DustbugRenderer(EntityRendererProvider.Context context) {
        super(context, new DustbugModel<>(context.bakeLayer(DustbugModel.LAYER_LOCATION)), 0.4f);
    }

    @Override
    public ResourceLocation getTextureLocation(DustbugEntity entity) {
        return ResourceLocation.fromNamespaceAndPath(Farworld.MOD_ID, "textures/entity/dustbug.png");
    }


    @Override
    public void render(DustbugEntity entity, float entityYaw, float partialTicks, PoseStack poseStack, MultiBufferSource buffer, int packedLight) {
        poseStack.scale(1.3f, 1.3f, 1.3f);
        super.render(entity, entityYaw, partialTicks, poseStack, buffer, packedLight);
    }
}
