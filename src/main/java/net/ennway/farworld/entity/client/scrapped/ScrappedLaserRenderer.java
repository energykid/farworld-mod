package net.ennway.farworld.entity.client.scrapped;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.ennway.farworld.Farworld;
import net.ennway.farworld.entity.custom.ScrappedLaserEntity;
import net.ennway.farworld.utils.MathUtils;
import net.ennway.farworld.utils.RenderingUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.culling.Frustum;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.ColorRGBA;
import net.minecraft.util.Mth;
import net.minecraft.world.phys.Vec3;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import org.jetbrains.annotations.Nullable;
import org.lwjgl.system.MathUtil;
import software.bernie.geckolib.cache.object.BakedGeoModel;
import software.bernie.geckolib.model.DefaultedEntityGeoModel;
import software.bernie.geckolib.renderer.GeoEntityRenderer;
import software.bernie.geckolib.util.Color;

import java.util.List;

@OnlyIn(Dist.CLIENT)
public class ScrappedLaserRenderer extends GeoEntityRenderer<ScrappedLaserEntity> {
    public ScrappedLaserRenderer(EntityRendererProvider.Context context) {
        super(context, new DefaultedEntityGeoModel<>(ResourceLocation.fromNamespaceAndPath(Farworld.MOD_ID, "scrapped_laser")));
    }

    @Override
    public boolean shouldRender(ScrappedLaserEntity livingEntity, Frustum camera, double camX, double camY, double camZ) {
        return true;
    }

    private static final RenderType RENDER_TYPE = RenderType.eyes(ResourceLocation.fromNamespaceAndPath(Farworld.MOD_ID, "textures/entity/redstone_curiosity_laser.png"));

    @Override
    public @Nullable RenderType getRenderType(ScrappedLaserEntity entity, ResourceLocation texture, @Nullable MultiBufferSource bufferSource, float partialTick) {
        return RENDER_TYPE;
    }

    @Override
    public void actuallyRender(PoseStack poseStack, ScrappedLaserEntity animatable, BakedGeoModel model, @Nullable RenderType renderType, MultiBufferSource bufferSource, @Nullable VertexConsumer buffer, boolean isReRender, float partialTick, int packedLight, int packedOverlay, int colour) {
        if (!animatable.visualStarted)
        {
            animatable.visualFinder = animatable.getDirectionVector();
            animatable.visualStarted = true;
        }

        if (animatable.timer < 5)
        {
            animatable.darkScale = 1f;
        }
        else
        {
            animatable.darkScale = Mth.lerp(0.1f, animatable.darkScale, 0f);
        }

        animatable.visualFinder = animatable.visualFinder.lerp(animatable.getDirectionVector(), 0.2f);

        animatable.visualDistInBlocks = Mth.lerp(0.3f, animatable.visualDistInBlocks, animatable.distInBlocks);

        animatable.visualScale = Mth.lerp(0.2f, animatable.visualScale, animatable.scale);

        poseStack.pushPose();
        RenderingUtils.autoRotateRender(poseStack, animatable.visualFinder);

        assert Minecraft.getInstance().cameraEntity != null;
        float r = 1f + ((float)MathUtils.randomDouble(Minecraft.getInstance().cameraEntity.getRandom(), -0.5f, 0.75f) * animatable.darkScale);

        poseStack.scale(animatable.visualScale * r, animatable.visualScale * r, animatable.visualDistInBlocks);

        poseStack.scale(1f + (animatable.darkScale / 2f), 1f + (animatable.darkScale / 2f), 1f);
        super.actuallyRender(poseStack, animatable, model, renderType, bufferSource, buffer, isReRender, partialTick, packedLight, packedOverlay, colour);
        poseStack.popPose();
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