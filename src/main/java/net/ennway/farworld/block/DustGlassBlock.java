package net.ennway.farworld.block;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.ennway.farworld.Farworld;
import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LightLayer;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.joml.Quaternionf;
import org.joml.Vector3f;

public class DustGlassBlock extends CustomRenderBlock implements EntityBlock {

    public String resourceLocation;

    public DustGlassBlock(Properties p_309186_, String loop) {
        super(p_309186_);
        this.resourceLocation = loop;
    }

    @Override
    protected VoxelShape getVisualShape(BlockState p_309057_, BlockGetter p_308936_, BlockPos p_308956_, CollisionContext p_309006_) {
        return Shapes.empty();
    }

    @Override
    protected float getShadeBrightness(BlockState p_308911_, BlockGetter p_308952_, BlockPos p_308918_) {
        return 1.0F;
    }


    @Override
    protected boolean skipRendering(BlockState state, BlockState adjacentBlockState, Direction side) {
        return adjacentBlockState.is(this) || super.skipRendering(state, adjacentBlockState, side);
    }

    @Override
    protected boolean propagatesSkylightDown(BlockState p_309084_, BlockGetter p_309133_, BlockPos p_309097_) {
        return true;
    }

    private int getLightLevel(Level level, BlockPos pos)
    {
        return LightTexture.pack(level.getBrightness(LightLayer.BLOCK, pos), level.getBrightness(LightLayer.SKY, pos));
    }

    private static void vertex(VertexConsumer consumer, PoseStack.Pose pose, float x, float y, int red, int green, int blue, float u, float v, int packedLight, float uadd, float vadd) {
        consumer.addVertex(pose, x, y, 0.0F).setColor(red, green, blue, 128).setUv(u + uadd, v + vadd).setOverlay(OverlayTexture.NO_OVERLAY).setLight(packedLight).setNormal(pose, 0.0F, 1.0F, 0.0F);
    }
    public void renderFace(Level lv, BlockPos pos, PoseStack poseStack, Quaternionf orientation, MultiBufferSource source, int packedLight, float u, float v, Direction ax)
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
            VertexConsumer vertexconsumer = source.getBuffer(RenderType.entityTranslucent(ResourceLocation.fromNamespaceAndPath(Farworld.MOD_ID, resourceLocation)));
            PoseStack.Pose posestack$pose = poseStack.last();
            vertex(vertexconsumer, posestack$pose, -0.5F, -0.5F, 255, 255, 255, left, bottom, packedLight, u, v);
            vertex(vertexconsumer, posestack$pose, 0.5F, -0.5F, 255, 255, 255, right, bottom, packedLight, u, v);
            vertex(vertexconsumer, posestack$pose, 0.5F, 0.5F, 255, 255, 255, right, top, packedLight, u, v);
            vertex(vertexconsumer, posestack$pose, -0.5F, 0.5F, 255, 255, 255, left, top, packedLight, u, v);
            poseStack.popPose();
        }
    }

    @Override
    public void render(Level lvl, BlockPos bpos, BlockState state, PoseStack poseStack) {
        MultiBufferSource source = Minecraft.getInstance().renderBuffers().bufferSource();

        Camera c = Minecraft.getInstance().getBlockEntityRenderDispatcher().camera;

        Vector3f pos = c.getPosition().toVector3f();

        float uu = (float)pos.x / 3f;
        float vv = (float)pos.y / 3f;
        float ww = (float)pos.z / 3f;

        {
            int i = getLightLevel(lvl, bpos);

            renderFace(lvl, bpos, poseStack, new Quaternionf().rotateX((float)Math.toRadians(90.0)), source, i, -uu, ww, Direction.DOWN);
            renderFace(lvl, bpos, poseStack, new Quaternionf().rotateX((float)Math.toRadians(90.0)), source, i, -uu, ww, Direction.UP);

            renderFace(lvl, bpos, poseStack, new Quaternionf().rotateZ((float)Math.toRadians(90.0)), source, i, -vv, -uu, Direction.NORTH);
            renderFace(lvl, bpos, poseStack, new Quaternionf().rotateZ((float)Math.toRadians(90.0)), source, i, -vv, -uu, Direction.SOUTH);

            renderFace(lvl, bpos, poseStack, new Quaternionf().rotateY((float)Math.toRadians(90.0)), source, i, ww, vv, Direction.EAST);
            renderFace(lvl, bpos, poseStack, new Quaternionf().rotateY((float)Math.toRadians(90.0)), source, i, ww, vv, Direction.WEST);
        }
    }
}
