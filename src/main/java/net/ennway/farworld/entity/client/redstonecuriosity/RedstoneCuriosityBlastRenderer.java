package net.ennway.farworld.entity.client.redstonecuriosity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import net.ennway.farworld.Farworld;
import net.ennway.farworld.entity.base.BaseSubattackEntity;
import net.ennway.farworld.entity.custom.redstone_curiosity.RedstoneCuriosityBlastEntity;
import net.ennway.farworld.entity.custom.redstone_curiosity.RedstoneCuriosityEntity;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.cache.object.BakedGeoModel;
import software.bernie.geckolib.model.DefaultedEntityGeoModel;
import software.bernie.geckolib.renderer.GeoEntityRenderer;
import software.bernie.geckolib.renderer.layer.AutoGlowingGeoLayer;

import java.util.List;

public class RedstoneCuriosityBlastRenderer extends GeoEntityRenderer<RedstoneCuriosityBlastEntity> {
    public RedstoneCuriosityBlastRenderer(EntityRendererProvider.Context context) {
        super(context, new DefaultedEntityGeoModel<>(ResourceLocation.fromNamespaceAndPath(Farworld.MOD_ID, "redstone_curiosity_blast")));
    }

    private static final List<RenderType> RENDER_TYPES = List.of(
            RenderType.eyes(ResourceLocation.fromNamespaceAndPath(Farworld.MOD_ID, "textures/entity/redstone_curiosity_blast/1.png")),
            RenderType.eyes(ResourceLocation.fromNamespaceAndPath(Farworld.MOD_ID, "textures/entity/redstone_curiosity_blast/2.png")),
            RenderType.eyes(ResourceLocation.fromNamespaceAndPath(Farworld.MOD_ID, "textures/entity/redstone_curiosity_blast/3.png")),
            RenderType.eyes(ResourceLocation.fromNamespaceAndPath(Farworld.MOD_ID, "textures/entity/redstone_curiosity_blast/4.png")),
            RenderType.eyes(ResourceLocation.fromNamespaceAndPath(Farworld.MOD_ID, "textures/entity/redstone_curiosity_blast/5.png")),
            RenderType.eyes(ResourceLocation.fromNamespaceAndPath(Farworld.MOD_ID, "textures/entity/redstone_curiosity_blast/6.png")),
            RenderType.eyes(ResourceLocation.fromNamespaceAndPath(Farworld.MOD_ID, "textures/entity/redstone_curiosity_blast/7.png"))
    );

    @Override
    public @Nullable RenderType getRenderType(RedstoneCuriosityBlastEntity entity, ResourceLocation texture, @Nullable MultiBufferSource bufferSource, float partialTick) {

        return RENDER_TYPES.get(entity.blastFrame - 1);
    }

    @Override
    public void actuallyRender(PoseStack poseStack, RedstoneCuriosityBlastEntity animatable, BakedGeoModel model, @Nullable RenderType renderType, MultiBufferSource bufferSource, @Nullable VertexConsumer buffer, boolean isReRender, float partialTick, int packedLight, int packedOverlay, int colour) {
        poseStack.mulPose(Axis.YP.rotationDegrees(animatable.getEntityData().get(BaseSubattackEntity.ROTATION)));
        super.actuallyRender(poseStack, animatable, model, renderType, bufferSource, buffer, isReRender, partialTick, packedLight, packedOverlay, colour);
    }

    @Override
    protected int getBlockLightLevel(RedstoneCuriosityBlastEntity entity, BlockPos pos) {
        return 15;
    }

    @Override
    protected int getSkyLightLevel(RedstoneCuriosityBlastEntity entity, BlockPos pos) {
        return 15;
    }
}
