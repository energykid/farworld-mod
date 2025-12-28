//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package net.ennway.farworld.entity.projectile.client;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.ennway.farworld.Farworld;
import net.ennway.farworld.entity.projectile.GloomstonePickup;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.ExperienceOrb;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class GloomstonePickupRenderer extends EntityRenderer<GloomstonePickup> {
    private static final ResourceLocation EXPERIENCE_ORB_LOCATION = ResourceLocation.fromNamespaceAndPath(Farworld.MOD_ID, "textures/entity/gloomstone_pickup.png");
    private static final RenderType RENDER_TYPE;

    public GloomstonePickupRenderer(EntityRendererProvider.Context context) {
        super(context);
        this.shadowRadius = 0.15F;
        this.shadowStrength = 0.75F;
    }

    protected int getBlockLightLevel(GloomstonePickup entity, BlockPos pos) {
        return Mth.clamp(super.getBlockLightLevel(entity, pos) + 7, 0, 15);
    }

    public void render(GloomstonePickup entity, float entityYaw, float partialTicks, PoseStack poseStack, MultiBufferSource buffer, int packedLight) {
        poseStack.pushPose();
        float left = 0;
        float right = 1.0f;
        float top = 0;
        float bottom = 1.0f;
        poseStack.translate(0.0F, 0.1F, 0.0F);
        poseStack.mulPose(this.entityRenderDispatcher.cameraOrientation());
        poseStack.scale(0.3F, 0.3F, 0.3F);
        VertexConsumer vertexconsumer = buffer.getBuffer(RENDER_TYPE);
        PoseStack.Pose posestack$pose = poseStack.last();
        vertex(vertexconsumer, posestack$pose, -0.5F, -0.25F, 255, 255, 255, left, bottom, packedLight);
        vertex(vertexconsumer, posestack$pose, 0.5F, -0.25F, 255, 255, 255, right, bottom, packedLight);
        vertex(vertexconsumer, posestack$pose, 0.5F, 0.75F, 255, 255, 255, right, top, packedLight);
        vertex(vertexconsumer, posestack$pose, -0.5F, 0.75F, 255, 255, 255, left, top, packedLight);
        poseStack.popPose();
        super.render(entity, entityYaw, partialTicks, poseStack, buffer, packedLight);
    }

    @Override
    public ResourceLocation getTextureLocation(GloomstonePickup gloomstoneSaturationPickup) {
        return ResourceLocation.fromNamespaceAndPath(Farworld.MOD_ID, "textures/entity/gloomstone_pickup.png");
    }

    private static void vertex(VertexConsumer consumer, PoseStack.Pose pose, float x, float y, int red, int green, int blue, float u, float v, int packedLight) {
        consumer.addVertex(pose, x, y, 0.0F).setColor(red, green, blue, 128).setUv(u, v).setOverlay(OverlayTexture.NO_OVERLAY).setLight(packedLight).setNormal(pose, 0.0F, 1.0F, 0.0F);
    }

    static {
        RENDER_TYPE = RenderType.entityCutout(EXPERIENCE_ORB_LOCATION);
    }
}
