package net.ennway.farworld.entity.client.scrapped;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.ennway.farworld.Farworld;
import net.ennway.farworld.entity.custom.ScrappedLaserEntity;
import net.ennway.farworld.utils.RenderingUtils;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.phys.Vec3;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.cache.object.BakedGeoModel;
import software.bernie.geckolib.model.DefaultedEntityGeoModel;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

import java.util.List;

@OnlyIn(Dist.CLIENT)
public class ScrappedLaserRenderer extends GeoEntityRenderer<ScrappedLaserEntity> {
    public ScrappedLaserRenderer(EntityRendererProvider.Context context) {
        super(context, new DefaultedEntityGeoModel<>(ResourceLocation.fromNamespaceAndPath(Farworld.MOD_ID, "scrapped_laser")));
    }

    private static final RenderType RENDER_TYPE = RenderType.eyes(ResourceLocation.fromNamespaceAndPath(Farworld.MOD_ID, "textures/entity/redstone_curiosity_laser.png"));

    @Override
    public @Nullable RenderType getRenderType(ScrappedLaserEntity entity, ResourceLocation texture, @Nullable MultiBufferSource bufferSource, float partialTick) {
        return RENDER_TYPE;
    }

    @Override
    public void actuallyRender(PoseStack poseStack, ScrappedLaserEntity animatable, BakedGeoModel model, @Nullable RenderType renderType, MultiBufferSource bufferSource, @Nullable VertexConsumer buffer, boolean isReRender, float partialTick, int packedLight, int packedOverlay, int colour) {
        animatable.visualDistInBlocks = Mth.lerp(0.3f, animatable.visualDistInBlocks, animatable.distInBlocks);

        animatable.visualScale = Mth.lerp(0.2f, animatable.visualScale, animatable.scale);

        RenderingUtils.autoRotateRender(poseStack, animatable.getDirectionVector());

        poseStack.scale(animatable.visualScale, animatable.visualScale, animatable.visualDistInBlocks);

        super.actuallyRender(poseStack, animatable, model, renderType, bufferSource, buffer, isReRender, partialTick, packedLight, packedOverlay, colour);
    }

    @Override
    protected int getBlockLightLevel(ScrappedLaserEntity entity, BlockPos pos) {
        return 15;
    }

    @Override
    protected int getSkyLightLevel(ScrappedLaserEntity entity, BlockPos pos) {
        return 15;
    }
}