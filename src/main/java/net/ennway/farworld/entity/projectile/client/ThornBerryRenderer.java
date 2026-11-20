package net.ennway.farworld.entity.projectile.client;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.ennway.farworld.Farworld;
import net.ennway.farworld.entity.projectile.GloomstonePickup;
import net.ennway.farworld.entity.projectile.ThornBerryProjectile;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.fml.common.Mod;

@OnlyIn(Dist.CLIENT)
public class ThornBerryRenderer extends EntityRenderer<ThornBerryProjectile> {
    EntityModel<ThornBerryProjectile> model;

    public ThornBerryRenderer(EntityRendererProvider.Context context) {
        super(context);
        model = ThornBerryModel.create(context);
    }

    @Override
    public ResourceLocation getTextureLocation(ThornBerryProjectile thornBerryProjectile) {
        return ResourceLocation.fromNamespaceAndPath(Farworld.MOD_ID, "textures/entity/thorn_berry.png");
    }

    @Override
    public void render(ThornBerryProjectile p_entity, float entityYaw, float partialTick, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight) {
        p_entity.visualRoll = Mth.lerp(0.1f, p_entity.visualRoll, p_entity.getEntityData().get(ThornBerryProjectile.ROLL));

        poseStack.pushPose();
        poseStack.mulPose(Axis.YP.rotationDegrees(p_entity.getYRot()));
        poseStack.mulPose(Axis.XP.rotationDegrees(p_entity.visualRoll));
        model.renderToBuffer(poseStack, bufferSource.getBuffer(RenderType.entitySmoothCutout(getTextureLocation(p_entity))), packedLight, 0);
        poseStack.popPose();
    }
}
