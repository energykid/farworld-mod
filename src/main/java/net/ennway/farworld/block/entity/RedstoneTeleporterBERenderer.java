package net.ennway.farworld.block.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.ennway.farworld.utils.BehaviorUtils;
import net.ennway.farworld.utils.MathUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Vec3i;
import net.minecraft.util.Mth;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LightLayer;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.phys.Vec3;
import org.joml.Vector2f;

public class RedstoneTeleporterBERenderer implements BlockEntityRenderer<RedstoneTeleporterBE> {

    public RedstoneTeleporterBERenderer() {}

    @Override
    public void render(RedstoneTeleporterBE entity, float v, PoseStack poseStack, MultiBufferSource multiBufferSource, int i, int i1) {

        ItemStack stack = entity.inventory.getStackInSlot(0);
        if (entity.hasLevel())
        {
            if (!stack.isEmpty())
            {
                for (int j = 0; j < 4; j++) {

                    ItemRenderer renderer = Minecraft.getInstance().getItemRenderer();

                    poseStack.pushPose();

                    poseStack.translate(0.5, 0, 0.5);
                    poseStack.mulPose(Axis.YP.rotationDegrees(j * 90));
                    poseStack.translate(0.5, 0.5, 0);
                    poseStack.mulPose(Axis.YP.rotationDegrees(90));
                    poseStack.scale(entity.sc, entity.sc, entity.sc);

                    Vector2f pos = new Vector2f(1, 0);
                    MathUtils.rotateVector2f(pos, Math.toDegrees(j * 90f));

                    renderer.renderStatic(stack, ItemDisplayContext.FIXED, getLightLevel(entity.getLevel(), entity.getBlockPos().above()), OverlayTexture.NO_OVERLAY, poseStack, multiBufferSource, entity.getLevel(), 1);

                    poseStack.popPose();
                }
            }
        }
        entity.sc = Mth.lerp(0.6f, entity.sc, 0.5f);
        if (stack.isEmpty()) entity.sc = 0.7f;
    }

    private int getLightLevel(Level level, BlockPos pos)
    {
        return LightTexture.pack(level.getBrightness(LightLayer.BLOCK, pos), level.getBrightness(LightLayer.SKY, pos));
    }
}
