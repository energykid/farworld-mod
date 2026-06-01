package net.ennway.farworld.block.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.ennway.farworld.Farworld;
import net.ennway.farworld.block.CustomRenderBlock;
import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
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
import org.joml.Quaternionf;
import org.joml.Vector3f;

public class CustomRenderBERenderer implements BlockEntityRenderer<CustomRenderBlockBE> {

    public CustomRenderBERenderer() {}

    @Override
    public void render(CustomRenderBlockBE be, float v, PoseStack poseStack, MultiBufferSource multiBufferSource, int ii, int i1)
    {
        if (be.getLevel() != null)
        {
            if (be.getLevel().getBlockState(be.getBlockPos()).getBlock() instanceof CustomRenderBlock renderer)
            {
                renderer.render(be.getLevel(), be.getBlockPos(), be.getBlockState(), poseStack);
            }
        }
    }
}
