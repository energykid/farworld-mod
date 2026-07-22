package net.ennway.farworld.entity.client.obelisk;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import net.ennway.farworld.Farworld;
import net.ennway.farworld.entity.custom.ObeliskEntity;
import net.ennway.farworld.entity.custom.ScrappedEntity;
import net.ennway.farworld.utils.RenderingUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.culling.Frustum;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import org.jetbrains.annotations.Nullable;
import org.joml.Vector2f;
import software.bernie.geckolib.cache.object.BakedGeoModel;
import software.bernie.geckolib.model.DefaultedEntityGeoModel;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

@OnlyIn(Dist.CLIENT)
public class ObeliskRenderer extends GeoEntityRenderer<ObeliskEntity> {

    public ObeliskRenderer(EntityRendererProvider.Context context) {
        super(context, new DefaultedEntityGeoModel<>(ResourceLocation.fromNamespaceAndPath(Farworld.MOD_ID, "obelisk")));
    }

    @Override
    public boolean shouldRender(ObeliskEntity livingEntity, Frustum camera, double camX, double camY, double camZ) {
        return true;
    }

    float a = 0f;
    @Override
    public void actuallyRender(PoseStack poseStack, ObeliskEntity animatable, BakedGeoModel model, @Nullable RenderType renderType, MultiBufferSource bufferSource, @Nullable VertexConsumer buffer, boolean isReRender, float partialTick, int packedLight, int packedOverlay, int colour) {
        super.actuallyRender(poseStack, animatable, model, renderType, bufferSource, buffer, isReRender, partialTick, packedLight, packedOverlay, colour);

        if (animatable.isDeadOrDying())
            animatable.areaScaleVisual = Mth.lerp(0.1f, animatable.areaScaleVisual, 0f);
        else
            animatable.areaScaleVisual = Mth.lerp(0.01f, animatable.areaScaleVisual, animatable.areaScale);

        if (!Minecraft.getInstance().isPaused())
            a++;

        ResourceLocation loc1 = ResourceLocation.fromNamespaceAndPath(Farworld.MOD_ID, "textures/entity/obelisk_aura_inner.png");
        ResourceLocation loc2 = ResourceLocation.fromNamespaceAndPath(Farworld.MOD_ID, "textures/entity/obelisk_aura_middle.png");
        ResourceLocation loc3 = ResourceLocation.fromNamespaceAndPath(Farworld.MOD_ID, "textures/entity/obelisk_aura_outer.png");

        float sc = 1f + (Mth.sin(a / 45.146f) * 0.01f);
        float sc2 = 1f + (Mth.sin(a / 32.146f) * 0.01f);

        float extrude = 0.01f;

        if (Minecraft.getInstance().getCameraEntity().getViewVector(partialTick).y > 0.0f) extrude = -0.01f;

            poseStack.pushPose();
        poseStack.translate(0, 0.1, 0);
        RenderingUtils.renderSprite(poseStack, Axis.XP.rotationDegrees(90).mul(Axis.ZP.rotationDegrees(a / 40)),
                loc1, bufferSource.getBuffer(RenderType.ENTITY_TRANSLUCENT_EMISSIVE.apply(loc1, true)), packedLight, new Vector2f(animatable.areaScaleVisual * sc2, animatable.areaScaleVisual * sc));
        poseStack.translate(0, extrude, 0);RenderingUtils.renderSprite(poseStack, Axis.XP.rotationDegrees(90).mul(Axis.ZP.rotationDegrees(a / 30)),
                loc2, bufferSource.getBuffer(RenderType.ENTITY_TRANSLUCENT_EMISSIVE.apply(loc2, true)), packedLight, new Vector2f(animatable.areaScaleVisual * sc2, animatable.areaScaleVisual * sc2));
        poseStack.translate(0, extrude, 0);RenderingUtils.renderSprite(poseStack, Axis.XP.rotationDegrees(90).mul(Axis.ZP.rotationDegrees(a / 20)),
                loc3, bufferSource.getBuffer(RenderType.ENTITY_TRANSLUCENT_EMISSIVE.apply(loc3, true)), packedLight, new Vector2f(animatable.areaScaleVisual * sc, animatable.areaScaleVisual * sc));
        poseStack.popPose();
    }
}