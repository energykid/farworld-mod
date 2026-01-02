package net.ennway.farworld.entity.client.redstonecuriosity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import net.ennway.farworld.Farworld;
import net.ennway.farworld.entity.base.BaseSubattackEntity;
import net.ennway.farworld.entity.custom.redstone_curiosity.RedstoneCuriosityLaserEntity;
import net.ennway.farworld.entity.custom.redstone_curiosity.RedstoneCuriosityLaserEntity;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.cache.object.BakedGeoModel;
import software.bernie.geckolib.model.DefaultedEntityGeoModel;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

import java.util.List;

public class RedstoneCuriosityLaserRenderer extends GeoEntityRenderer<RedstoneCuriosityLaserEntity> {
    public RedstoneCuriosityLaserRenderer(EntityRendererProvider.Context context) {
        super(context, new DefaultedEntityGeoModel<>(ResourceLocation.fromNamespaceAndPath(Farworld.MOD_ID, "redstone_curiosity_laser")));
    }

    private static final RenderType RENDER_TYPE = RenderType.eyes(ResourceLocation.fromNamespaceAndPath(Farworld.MOD_ID, "textures/entity/redstone_curiosity_laser.png"));

    @Override
    public @Nullable RenderType getRenderType(RedstoneCuriosityLaserEntity entity, ResourceLocation texture, @Nullable MultiBufferSource bufferSource, float partialTick) {

        return RENDER_TYPE;
    }

    @Override
    public void actuallyRender(PoseStack poseStack, RedstoneCuriosityLaserEntity animatable, BakedGeoModel model, @Nullable RenderType renderType, MultiBufferSource bufferSource, @Nullable VertexConsumer buffer, boolean isReRender, float partialTick, int packedLight, int packedOverlay, int colour) {

        float xrot = 0f;
        float yrot = 0f;

        Vec3 vec3 = animatable.getDeltaMovement();
        if (vec3.lengthSqr() != 0.0) {
            double d0 = vec3.horizontalDistance();
            yrot = ((float)(Mth.atan2(vec3.z, vec3.x) * 180.0 / 3.1415927410125732) + 90.0F);
            xrot = ((float)(Mth.atan2(d0, vec3.y) * 180.0 / 3.1415927410125732) - 90.0F);
        }

        poseStack.mulPose(Axis.YP.rotationDegrees(-yrot));
        poseStack.mulPose(Axis.XP.rotationDegrees(-xrot));

        super.actuallyRender(poseStack, animatable, model, renderType, bufferSource, buffer, isReRender, partialTick, packedLight, packedOverlay, colour);
    }

    @Override
    protected int getBlockLightLevel(RedstoneCuriosityLaserEntity entity, BlockPos pos) {
        return 15;
    }

    @Override
    protected int getSkyLightLevel(RedstoneCuriosityLaserEntity entity, BlockPos pos) {
        return 15;
    }
}
