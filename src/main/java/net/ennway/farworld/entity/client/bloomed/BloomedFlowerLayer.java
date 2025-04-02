package net.ennway.farworld.entity.client.bloomed;

import com.mojang.blaze3d.vertex.PoseStack;
import net.ennway.farworld.Farworld;
import net.ennway.farworld.entity.custom.BloomedEntity;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class BloomedFlowerLayer<T extends BloomedEntity, M extends BloomedModel<T>> extends RenderLayer<T, M> {

    public String flowerString = "poppy";

    public BloomedFlowerLayer(RenderLayerParent<T, M> p_117507_) {
        super(p_117507_);
    }

    @Override
    protected ResourceLocation getTextureLocation(T entity) {
        flowerString = ((BloomedEntity)entity).flowerType;
        if (((BloomedEntity)entity).isArthur()) flowerString = "bush";
        return ResourceLocation.fromNamespaceAndPath(Farworld.MOD_ID, "textures/entity/bloomed_flower/bloomed_" + this.flowerString + ".png");
    }

    @Override
    public void render(PoseStack poseStack, MultiBufferSource buffer, int packedLight, T livingEntity, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {

        this.flowerString = ((BloomedEntity)livingEntity).flowerType;
        if (((BloomedEntity)livingEntity).isArthur()) this.flowerString = "bush";

        this.getParentModel().prepareMobModel(livingEntity, limbSwing, limbSwingAmount, partialTicks);
        this.getParentModel().setupAnim(livingEntity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);
        this.getParentModel().renderToBuffer(poseStack, buffer.getBuffer(renderType()), packedLight, OverlayTexture.NO_OVERLAY);
    }

    public RenderType renderType() {
        return RenderType.entityCutout(ResourceLocation.fromNamespaceAndPath(Farworld.MOD_ID, "textures/entity/bloomed_flower/bloomed_" + this.flowerString + ".png"));
    }
}
