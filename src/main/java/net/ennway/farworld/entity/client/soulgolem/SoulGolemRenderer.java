package net.ennway.farworld.entity.client.soulgolem;

import com.mojang.blaze3d.vertex.PoseStack;
import net.ennway.farworld.Farworld;
import net.ennway.farworld.entity.client.bloomed.BloomedModel;
import net.ennway.farworld.entity.custom.BloomedEntity;
import net.ennway.farworld.entity.custom.SoulGolemEntity;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public class SoulGolemRenderer extends MobRenderer<SoulGolemEntity, SoulGolemModel<SoulGolemEntity>> {

    public SoulGolemRenderer(EntityRendererProvider.Context context) {
        super(context, new SoulGolemModel<>(context.bakeLayer(SoulGolemModel.LAYER_LOCATION)), 0.6f);
        this.addLayer(new SoulGolemGlowLayer<>(this));
    }

    @Override
    public ResourceLocation getTextureLocation(SoulGolemEntity entity) {
        return ResourceLocation.fromNamespaceAndPath(Farworld.MOD_ID, "textures/entity/soul_golem.png");
    }

    @Override
    public void render(SoulGolemEntity entity, float entityYaw, float partialTicks, PoseStack poseStack, MultiBufferSource buffer, int packedLight) {
        if (entity.isBaby())
        {
            poseStack.scale(0.5f, 0.5f, 0.5f);
        }

        poseStack.scale(10/9f, 10/9f, 10/9f);
        super.render(entity, entityYaw, partialTicks, poseStack, buffer, packedLight);
        poseStack.scale(9/10f, 9/10f, 9/10f);
        super.render(entity, entityYaw, partialTicks, poseStack, buffer, packedLight);
    }
}
