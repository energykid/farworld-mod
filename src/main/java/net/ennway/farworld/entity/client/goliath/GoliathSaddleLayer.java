package net.ennway.farworld.entity.client.goliath;

import com.mojang.blaze3d.vertex.PoseStack;
import net.ennway.farworld.Farworld;
import net.ennway.farworld.entity.custom.GoliathEntity;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.EyesLayer;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class GoliathSaddleLayer<T extends GoliathEntity, M extends GoliathModel<T>> extends RenderLayer<T, M> {
    private static final RenderType SADDLE = RenderType.armorCutoutNoCull(ResourceLocation.fromNamespaceAndPath(Farworld.MOD_ID, "textures/entity/goliath_saddle.png"));
    private static final RenderType NO_SADDLE = RenderType.armorCutoutNoCull(ResourceLocation.fromNamespaceAndPath(Farworld.MOD_ID, "textures/entity/goliath_nosaddle.png"));

    public GoliathSaddleLayer(RenderLayerParent<T, M> p_117507_) {
        super(p_117507_);
    }

    @Override
    public void render(PoseStack poseStack, MultiBufferSource buffer, int packedLight, T livingEntity, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
        this.getParentModel().prepareMobModel(livingEntity, limbSwing, limbSwingAmount, partialTicks);
        this.getParentModel().setupAnim(livingEntity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);
        this.getParentModel().renderToBuffer(poseStack, buffer.getBuffer(renderType()), packedLight, OverlayTexture.NO_OVERLAY);
    }

    public RenderType renderType() {
        if (this.getParentModel().saddled) return SADDLE;
        else return NO_SADDLE;
    }
}
