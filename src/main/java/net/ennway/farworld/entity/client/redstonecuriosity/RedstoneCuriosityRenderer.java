package net.ennway.farworld.entity.client.redstonecuriosity;

import com.mojang.blaze3d.vertex.MeshData;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import net.ennway.farworld.Farworld;
import net.ennway.farworld.entity.client.redstonecuriosity.RedstoneCuriosityModel;
import net.ennway.farworld.entity.custom.RedstoneCuriosityEntity;
import net.ennway.farworld.entity.custom.SoulGolemEntity;
import net.ennway.farworld.utils.BehaviorUtils;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.ColorRGBA;
import net.minecraft.util.Mth;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;
import org.joml.Vector2f;
import software.bernie.geckolib.cache.object.BakedGeoModel;
import software.bernie.geckolib.model.DefaultedEntityGeoModel;
import software.bernie.geckolib.renderer.GeoEntityRenderer;
import software.bernie.geckolib.renderer.GeoRenderer;
import software.bernie.geckolib.renderer.layer.AutoGlowingGeoLayer;
import software.bernie.geckolib.renderer.layer.GeoRenderLayer;
import software.bernie.geckolib.util.Color;

public class RedstoneCuriosityRenderer extends GeoEntityRenderer<RedstoneCuriosityEntity> {


    public RedstoneCuriosityRenderer(EntityRendererProvider.Context context) {
        super(context, new DefaultedEntityGeoModel<>(ResourceLocation.fromNamespaceAndPath(Farworld.MOD_ID, "redstone_curiosity")));
        addRenderLayer(new AutoGlowingGeoLayer<>(this));
        addRenderLayer(new RedstoneCuriosityTrailLayer(this));
    }

    @Override
    public Color getRenderColor(RedstoneCuriosityEntity animatable, float partialTick, int packedLight) {
        return Color.ofARGB(1f, 1f, 0f, 0f);
    }

    @Override
    public void actuallyRender(PoseStack poseStack, RedstoneCuriosityEntity entity, BakedGeoModel model, @Nullable RenderType renderType, MultiBufferSource bufferSource, @Nullable VertexConsumer buffer, boolean isReRender, float partialTick, int packedLight, int packedOverlay, int colour) {

        if (entity.getEntityData().get(RedstoneCuriosityEntity.ATTACK_STATE) != RedstoneCuriosityEntity.ATTACK_STATE_BUILDUP)
            entity.finalRot = Mth.rotLerp(0.1f, entity.finalRot, entity.rot);

        poseStack.mulPose(Axis.YP.rotationDegrees((float)entity.finalRot));
        super.actuallyRender(poseStack, entity, model, renderType, bufferSource, buffer, isReRender, partialTick, packedLight, packedOverlay, Color.ofARGB(1f, 1f, 1f, 1f).argbInt());
        poseStack.mulPose(Axis.YP.rotationDegrees(-(float)entity.finalRot));
    }
}
