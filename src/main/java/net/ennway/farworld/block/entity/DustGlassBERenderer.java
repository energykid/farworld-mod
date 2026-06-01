package net.ennway.farworld.block.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import net.ennway.farworld.Farworld;
import net.ennway.farworld.utils.BehaviorUtils;
import net.ennway.farworld.utils.QuaternionUtils;
import net.ennway.farworld.utils.RenderingUtils;
import net.ennway.farworld.utils.ServerUtils;
import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.chat.report.ReportEnvironment;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LightLayer;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import org.joml.Quaternionf;
import org.joml.Vector3f;

public class DustGlassBERenderer implements BlockEntityRenderer<DustGlassBE> {

    public DustGlassBERenderer() {}

    @Override
    public void render(DustGlassBE dustGlassBE, float v, PoseStack poseStack, MultiBufferSource multiBufferSource, int ii, int i1) {
        Camera c = Minecraft.getInstance().gameRenderer.getMainCamera();

        Vector3f pos = c.getPosition().toVector3f();

        float uu = (float)pos.x / 3f;
        float vv = (float)pos.y / 3f;
        float ww = (float)pos.z / 3f;

        if (dustGlassBE.getLevel() != null)
        {
            int i = getLightLevel(dustGlassBE.getLevel(), dustGlassBE.getBlockPos());

            renderFace(dustGlassBE.getLevel(), dustGlassBE.getBlockPos(), poseStack, new Quaternionf().rotateX((float)Math.toRadians(90.0)), multiBufferSource, i, -uu, ww, Direction.DOWN);
            renderFace(dustGlassBE.getLevel(), dustGlassBE.getBlockPos(), poseStack, new Quaternionf().rotateX((float)Math.toRadians(90.0)), multiBufferSource, i, -uu, ww, Direction.UP);

            renderFace(dustGlassBE.getLevel(), dustGlassBE.getBlockPos(), poseStack, new Quaternionf().rotateZ((float)Math.toRadians(90.0)), multiBufferSource, i, -vv, -uu, Direction.NORTH);
            renderFace(dustGlassBE.getLevel(), dustGlassBE.getBlockPos(), poseStack, new Quaternionf().rotateZ((float)Math.toRadians(90.0)), multiBufferSource, i, -vv, -uu, Direction.SOUTH);

            renderFace(dustGlassBE.getLevel(), dustGlassBE.getBlockPos(), poseStack, new Quaternionf().rotateY((float)Math.toRadians(90.0)), multiBufferSource, i, ww, vv, Direction.EAST);
            renderFace(dustGlassBE.getLevel(), dustGlassBE.getBlockPos(), poseStack, new Quaternionf().rotateY((float)Math.toRadians(90.0)), multiBufferSource, i, ww, vv, Direction.WEST);
        }
    }

    private int getLightLevel(Level level, BlockPos pos)
    {
        return LightTexture.pack(level.getBrightness(LightLayer.BLOCK, pos), level.getBrightness(LightLayer.SKY, pos));
    }

    private static void vertex(VertexConsumer consumer, PoseStack.Pose pose, float x, float y, int red, int green, int blue, float u, float v, int packedLight, float uadd, float vadd) {
        consumer.addVertex(pose, x, y, 0.0F).setColor(red, green, blue, 128).setUv(u + uadd, v + vadd).setOverlay(OverlayTexture.NO_OVERLAY).setLight(packedLight).setNormal(pose, 0.0F, 1.0F, 0.0F);
    }
    public static void renderFace(Level lv, BlockPos pos, PoseStack poseStack, Quaternionf orientation, MultiBufferSource source, int packedLight, float u, float v, Direction ax)
    {
        BlockPos p = pos.mutable().move(ax);
        if (!lv.getBlockState(p).isFaceSturdy(lv, p, ax.getOpposite())) {
            poseStack.pushPose();
            float left = 0;
            float right = 1.0f;
            float top = 0;
            float bottom = 1.0f;
            poseStack.translate(0.5, 0.5, 0.5);
            poseStack.translate(ax.step().x * 0.501, ax.step().y * 0.501, ax.step().z * 0.501);
            poseStack.mulPose(orientation);
            poseStack.scale(1F, 1F, 1F);
            VertexConsumer vertexconsumer = source.getBuffer(RenderType.entityTranslucent(ResourceLocation.fromNamespaceAndPath(Farworld.MOD_ID, "textures/block/dust_glass_overlay.png")));
            PoseStack.Pose posestack$pose = poseStack.last();
            vertex(vertexconsumer, posestack$pose, -0.5F, -0.5F, 255, 255, 255, left, bottom, packedLight, u, v);
            vertex(vertexconsumer, posestack$pose, 0.5F, -0.5F, 255, 255, 255, right, bottom, packedLight, u, v);
            vertex(vertexconsumer, posestack$pose, 0.5F, 0.5F, 255, 255, 255, right, top, packedLight, u, v);
            vertex(vertexconsumer, posestack$pose, -0.5F, 0.5F, 255, 255, 255, left, top, packedLight, u, v);
            poseStack.popPose();
        }
    }
}
