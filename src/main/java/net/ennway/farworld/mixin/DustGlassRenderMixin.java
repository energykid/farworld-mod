package net.ennway.farworld.mixin;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import net.ennway.farworld.Farworld;
import net.ennway.farworld.utils.RenderingUtils;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.block.ModelBlockRenderer;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockAndTintGetter;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.client.model.data.ModelData;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.BitSet;
import java.util.Iterator;
import java.util.List;

@Mixin(ModelBlockRenderer.class)
public abstract class DustGlassRenderMixin {

    @Shadow protected abstract void calculateShape(BlockAndTintGetter level, BlockState state, BlockPos pos, int[] vertices, Direction direction, @Nullable float[] shape, BitSet shapeFlags);

    @Shadow protected abstract void putQuadData(BlockAndTintGetter level, BlockState state, BlockPos pos, VertexConsumer consumer, PoseStack.Pose pose, BakedQuad quad, float brightness0, float brightness1, float brightness2, float brightness3, int lightmap0, int lightmap1, int lightmap2, int lightmap3, int packedOverlay);

    @Inject(method = "renderModel(Lcom/mojang/blaze3d/vertex/PoseStack$Pose;Lcom/mojang/blaze3d/vertex/VertexConsumer;Lnet/minecraft/world/level/block/state/BlockState;Lnet/minecraft/client/resources/model/BakedModel;FFFIILnet/neoforged/neoforge/client/model/data/ModelData;Lnet/minecraft/client/renderer/RenderType;)V", at = @At("TAIL"))
    private static void A(PoseStack.Pose pose, VertexConsumer consumer, BlockState state, BakedModel model, float red, float green, float blue, int packedLight, int packedOverlay, ModelData modelData, RenderType renderType, CallbackInfo ci)
    {
        RenderingUtils.renderEntitySprite(pose, Axis.YP.rotation(0f), ResourceLocation.fromNamespaceAndPath(Farworld.MOD_ID, "dust_glass_overlay"));
    }
}
