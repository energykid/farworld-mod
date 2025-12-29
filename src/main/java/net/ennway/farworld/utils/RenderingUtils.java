package net.ennway.farworld.utils;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import org.joml.Quaternionf;
import org.joml.Vector3f;

public class RenderingUtils {

    private static void vertex(VertexConsumer consumer, PoseStack.Pose pose, float x, float y, int red, int green, int blue, float u, float v, int packedLight) {
        consumer.addVertex(pose, x, y, 0.0F).setColor(red, green, blue, 128).setUv(u, v).setOverlay(OverlayTexture.NO_OVERLAY).setLight(packedLight).setNormal(pose, 0.0F, 1.0F, 0.0F);
    }
    public static void renderEntitySprite(PoseStack poseStack, Quaternionf orientation, ResourceLocation spriteLocation, MultiBufferSource source, int packedLight)
    {
        poseStack.pushPose();
        float left = 0;
        float right = 1.0f;
        float top = 0;
        float bottom = 1.0f;
        poseStack.mulPose(orientation);
        poseStack.scale(1F, 1F, 1F);
        VertexConsumer vertexconsumer = source.getBuffer(RenderType.entityCutout(spriteLocation));
        PoseStack.Pose posestack$pose = poseStack.last();
        vertex(vertexconsumer, posestack$pose, -0.5F, -0.25F, 255, 255, 255, left, bottom, packedLight);
        vertex(vertexconsumer, posestack$pose, 0.5F, -0.25F, 255, 255, 255, right, bottom, packedLight);
        vertex(vertexconsumer, posestack$pose, 0.5F, 0.75F, 255, 255, 255, right, top, packedLight);
        vertex(vertexconsumer, posestack$pose, -0.5F, 0.75F, 255, 255, 255, left, top, packedLight);
        poseStack.popPose();
    }
    public static void renderEntitySprite(PoseStack poseStack, Quaternionf orientation, ResourceLocation spriteLocation, MultiBufferSource source, int packedLight, Vector3f translation)
    {
        poseStack.pushPose();
        float left = 0;
        float right = 1.0f;
        float top = 0;
        float bottom = 1.0f;
        poseStack.mulPose(orientation);
        poseStack.translate(translation.x, translation.y, translation.z);
        poseStack.scale(1f, 1f, 1f);
        VertexConsumer vertexconsumer = source.getBuffer(RenderType.entityCutout(spriteLocation));
        PoseStack.Pose posestack$pose = poseStack.last();
        vertex(vertexconsumer, posestack$pose, -0.5F, -0.25F, 255, 255, 255, left, bottom, packedLight);
        vertex(vertexconsumer, posestack$pose, 0.5F, -0.25F, 255, 255, 255, right, bottom, packedLight);
        vertex(vertexconsumer, posestack$pose, 0.5F, 0.75F, 255, 255, 255, right, top, packedLight);
        vertex(vertexconsumer, posestack$pose, -0.5F, 0.75F, 255, 255, 255, left, top, packedLight);
        poseStack.popPose();
    }
    public static void renderEntitySprite(PoseStack poseStack, Quaternionf orientation, ResourceLocation spriteLocation, MultiBufferSource source, int packedLight, Vector3f translation, float scale)
    {
        poseStack.pushPose();
        float left = 0;
        float right = 1.0f;
        float top = 0;
        float bottom = 1.0f;
        poseStack.mulPose(orientation);
        poseStack.translate(translation.x, translation.y, translation.z);
        poseStack.scale(scale, scale, scale);
        VertexConsumer vertexconsumer = source.getBuffer(RenderType.entityCutout(spriteLocation));
        PoseStack.Pose posestack$pose = poseStack.last();
        vertex(vertexconsumer, posestack$pose, -0.5F, -0.25F, 255, 255, 255, left, bottom, packedLight);
        vertex(vertexconsumer, posestack$pose, 0.5F, -0.25F, 255, 255, 255, right, bottom, packedLight);
        vertex(vertexconsumer, posestack$pose, 0.5F, 0.75F, 255, 255, 255, right, top, packedLight);
        vertex(vertexconsumer, posestack$pose, -0.5F, 0.75F, 255, 255, 255, left, top, packedLight);
        poseStack.popPose();
    }
    public static void renderEntitySprite(PoseStack poseStack, Quaternionf orientation, ResourceLocation spriteLocation, MultiBufferSource source, int packedLight, float scale)
    {
        poseStack.pushPose();
        float left = 0;
        float right = 1.0f;
        float top = 0;
        float bottom = 1.0f;
        poseStack.mulPose(orientation);
        poseStack.scale(scale, scale, scale);
        VertexConsumer vertexconsumer = source.getBuffer(RenderType.entityCutout(spriteLocation));
        PoseStack.Pose posestack$pose = poseStack.last();
        vertex(vertexconsumer, posestack$pose, -0.5F, -0.25F, 255, 255, 255, left, bottom, packedLight);
        vertex(vertexconsumer, posestack$pose, 0.5F, -0.25F, 255, 255, 255, right, bottom, packedLight);
        vertex(vertexconsumer, posestack$pose, 0.5F, 0.75F, 255, 255, 255, right, top, packedLight);
        vertex(vertexconsumer, posestack$pose, -0.5F, 0.75F, 255, 255, 255, left, top, packedLight);
        poseStack.popPose();
    }
}
