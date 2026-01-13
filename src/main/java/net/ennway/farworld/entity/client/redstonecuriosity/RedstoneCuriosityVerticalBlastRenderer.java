package net.ennway.farworld.entity.client.redstonecuriosity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import net.ennway.farworld.Farworld;
import net.ennway.farworld.entity.base.BaseSubattackEntity;
import net.ennway.farworld.entity.custom.redstone_curiosity.RedstoneCuriosityBlastEntity;
import net.ennway.farworld.entity.custom.redstone_curiosity.RedstoneCuriosityVerticalBlastEntity;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.cache.object.BakedGeoModel;
import software.bernie.geckolib.model.DefaultedEntityGeoModel;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

import java.util.List;

public class RedstoneCuriosityVerticalBlastRenderer extends GeoEntityRenderer<RedstoneCuriosityVerticalBlastEntity> {
    public RedstoneCuriosityVerticalBlastRenderer(EntityRendererProvider.Context context) {
        super(context, new DefaultedEntityGeoModel<>(ResourceLocation.fromNamespaceAndPath(Farworld.MOD_ID, "redstone_curiosity_vertical_blast")));
    }

    private static final List<RenderType> RENDER_TYPES = List.of(
            RenderType.entityCutout(ResourceLocation.fromNamespaceAndPath(Farworld.MOD_ID, "textures/entity/redstone_curiosity_blast/1.png")),
            RenderType.entityCutout(ResourceLocation.fromNamespaceAndPath(Farworld.MOD_ID, "textures/entity/redstone_curiosity_blast/2.png")),
            RenderType.entityCutout(ResourceLocation.fromNamespaceAndPath(Farworld.MOD_ID, "textures/entity/redstone_curiosity_blast/3.png")),
            RenderType.entityCutout(ResourceLocation.fromNamespaceAndPath(Farworld.MOD_ID, "textures/entity/redstone_curiosity_blast/4.png")),
            RenderType.entityCutout(ResourceLocation.fromNamespaceAndPath(Farworld.MOD_ID, "textures/entity/redstone_curiosity_blast/5.png")),
            RenderType.entityCutout(ResourceLocation.fromNamespaceAndPath(Farworld.MOD_ID, "textures/entity/redstone_curiosity_blast/6.png")),
            RenderType.entityCutout(ResourceLocation.fromNamespaceAndPath(Farworld.MOD_ID, "textures/entity/redstone_curiosity_blast/7.png"))
    );

    @Override
    public @Nullable RenderType getRenderType(RedstoneCuriosityVerticalBlastEntity entity, ResourceLocation texture, @Nullable MultiBufferSource bufferSource, float partialTick) {

        return RENDER_TYPES.get(entity.blastFrame - 1);
    }

    @Override
    public void actuallyRender(PoseStack poseStack, RedstoneCuriosityVerticalBlastEntity animatable, BakedGeoModel model, @Nullable RenderType renderType, MultiBufferSource bufferSource, @Nullable VertexConsumer buffer, boolean isReRender, float partialTick, int packedLight, int packedOverlay, int colour) {
        
        if (animatable.canRender)
            super.actuallyRender(poseStack, animatable, model, renderType, bufferSource, buffer, isReRender, partialTick, packedLight, packedOverlay, colour);
    }

    @Override
    protected int getBlockLightLevel(RedstoneCuriosityVerticalBlastEntity entity, BlockPos pos) {
        return 15;
    }

    @Override
    protected int getSkyLightLevel(RedstoneCuriosityVerticalBlastEntity entity, BlockPos pos) {
        return 15;
    }
}
