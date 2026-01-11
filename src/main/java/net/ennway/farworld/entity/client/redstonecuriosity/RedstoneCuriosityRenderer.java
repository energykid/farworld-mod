package net.ennway.farworld.entity.client.redstonecuriosity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import net.ennway.farworld.Farworld;
import net.ennway.farworld.entity.custom.redstone_curiosity.RedstoneCuriosityEntity;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.culling.Frustum;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.cache.object.BakedGeoModel;
import software.bernie.geckolib.model.DefaultedEntityGeoModel;
import software.bernie.geckolib.renderer.GeoEntityRenderer;
import software.bernie.geckolib.renderer.layer.AutoGlowingGeoLayer;
import software.bernie.geckolib.util.Color;

public class RedstoneCuriosityRenderer extends GeoEntityRenderer<RedstoneCuriosityEntity> {

    @Override
    public boolean shouldRender(RedstoneCuriosityEntity livingEntity, Frustum camera, double camX, double camY, double camZ) {
        return true;
    }

    @Override
    public @Nullable RenderType getRenderType(RedstoneCuriosityEntity animatable, ResourceLocation texture, @Nullable MultiBufferSource bufferSource, float partialTick) {
        return super.getRenderType(animatable, texture, bufferSource, partialTick);
    }

    public RedstoneCuriosityRenderer(EntityRendererProvider.Context context) {
        super(context, new DefaultedEntityGeoModel<>(ResourceLocation.fromNamespaceAndPath(Farworld.MOD_ID, "redstone_curiosity.json")));
        addRenderLayer(new AutoGlowingGeoLayer<>(this));
        addRenderLayer(new RedstoneCuriosityTrailLayer(this));
    }

    @Override
    public Color getRenderColor(RedstoneCuriosityEntity animatable, float partialTick, int packedLight) {
        return Color.ofARGB(1f, 1f, 0f, 0f);
    }

    float rotX = 0f;

    @Override
    public void actuallyRender(PoseStack poseStack, RedstoneCuriosityEntity entity, BakedGeoModel model, @Nullable RenderType renderType, MultiBufferSource bufferSource, @Nullable VertexConsumer buffer, boolean isReRender, float partialTick, int packedLight, int packedOverlay, int colour) {

        entity.finalRot = Mth.rotLerp(entity.getEntityData().get(RedstoneCuriosityEntity.ROTATION_LERP), entity.finalRot, entity.rot);

        float xrot = 0f;

        if (animatable.getEntityData().get(RedstoneCuriosityEntity.SHOULD_TILT_HEAD))
        {
            if (animatable.getTarget() != null)
            {
                Vec3 vec3 = animatable.getTarget().getEyePosition().subtract(animatable.getEyePosition());

                if (vec3.lengthSqr() != 0.0) {
                    double d0 = vec3.horizontalDistance();
                    xrot = ((float)(Mth.atan2(d0, vec3.y) * 180.0 / 3.1415927410125732) - 90.0F);
                }

            }
        }

        rotX = Mth.lerp(0.3f, rotX, xrot);

        model.getBone("extra_head_group").get().setRotX((float)Math.toRadians(-rotX));

        poseStack.mulPose(Axis.YP.rotationDegrees((float)entity.finalRot));
        super.actuallyRender(poseStack, entity, model, renderType, bufferSource, buffer, isReRender, partialTick, packedLight, packedOverlay, Color.ofARGB(1f, 1f, 1f, 1f).argbInt());
        poseStack.mulPose(Axis.YP.rotationDegrees(-(float)entity.finalRot));
    }
}
