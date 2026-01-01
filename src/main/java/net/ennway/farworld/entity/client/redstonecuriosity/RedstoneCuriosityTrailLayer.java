package net.ennway.farworld.entity.client.redstonecuriosity;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.blaze3d.vertex.VertexFormat;
import com.mojang.math.Axis;
import net.ennway.farworld.Farworld;
import net.ennway.farworld.entity.custom.RedstoneCuriosityEntity;
import net.minecraft.Util;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderStateShard;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.animatable.GeoAnimatable;
import software.bernie.geckolib.cache.object.BakedGeoModel;
import software.bernie.geckolib.cache.object.GeoBone;
import software.bernie.geckolib.cache.texture.AutoGlowingTexture;
import software.bernie.geckolib.renderer.GeoRenderer;
import software.bernie.geckolib.renderer.layer.AutoGlowingGeoLayer;
import software.bernie.geckolib.renderer.layer.GeoRenderLayer;
import software.bernie.geckolib.util.ClientUtil;

import java.util.function.BiFunction;

public class RedstoneCuriosityTrailLayer extends AutoGlowingGeoLayer {

    public RedstoneCuriosityTrailLayer(GeoRenderer renderer) {
        super(renderer);
    }

    void translatePoseStackFromTo(PoseStack poseStack, Vec3 pointA, Vec3 pointB)
    {
        poseStack.translate(pointB.x - pointA.x, pointB.y - pointA.y, pointB.z - pointA.z);
    }

    @Override
    protected @Nullable RenderType getRenderType(GeoAnimatable animatable, @Nullable MultiBufferSource bufferSource) {
        return RenderType.eyes(ResourceLocation.fromNamespaceAndPath(Farworld.MOD_ID, "textures/entity/redstone_curiosity_trail.png"));
    }

    @Override
    public void render(PoseStack poseStack, GeoAnimatable animatable, BakedGeoModel bakedModel, @Nullable RenderType renderType, MultiBufferSource bufferSource, @Nullable VertexConsumer buffer, float partialTick, int packedLight, int packedOverlay) {

        RedstoneCuriosityEntity entity = (RedstoneCuriosityEntity) animatable;

        if (entity.trailPositions == null)
        {
            entity.trailPositions = new Vec3[6];
            entity.trailPositions[0] = entity.trailPositions[1] = entity.trailPositions[2] = entity.trailPositions[3] = entity.trailPositions[4] = entity.trailPositions[5] = entity.getPosition(partialTick);
        }
        else
        {
            for (int i = 5; i >= 0; i--) {
                Vec3 pos = entity.getPosition(partialTick);
                if (i > 0)
                {
                    pos = entity.trailPositions[i - 1];
                }
                entity.trailPositions[i] = entity.trailPositions[i].lerp(pos, 0.8f);
            }
        }

        for (int i = 0; i < 5; i++) {
            Vec3 pos = entity.getPosition(partialTick);
            if (i > 0)
            {
                pos = entity.trailPositions[i - 1];
            }

            //poseStack.mulPose(Axis.YP.rotationDegrees(-(float)entity.finalRot));
            translatePoseStackFromTo(poseStack, pos, entity.trailPositions[i]);
            if (pos.distanceTo(entity.trailPositions[i]) > 0.05f)
                super.render(poseStack, animatable, bakedModel, RenderType.eyes(ResourceLocation.fromNamespaceAndPath(Farworld.MOD_ID, "textures/entity/redstone_curiosity_trail")), bufferSource, buffer, partialTick, packedLight, packedOverlay);
            //poseStack.mulPose(Axis.YP.rotationDegrees((float)entity.finalRot));
        }
    }
}
