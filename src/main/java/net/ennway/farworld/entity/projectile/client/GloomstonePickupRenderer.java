//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package net.ennway.farworld.entity.projectile.client;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.ennway.farworld.Farworld;
import net.ennway.farworld.entity.projectile.GloomstonePickup;
import net.ennway.farworld.utils.RenderingUtils;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.ExperienceOrb;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;
import org.joml.Vector3f;

@OnlyIn(Dist.CLIENT)
public class GloomstonePickupRenderer extends EntityRenderer<GloomstonePickup> {
    private static final ResourceLocation EXPERIENCE_ORB_LOCATION = ResourceLocation.fromNamespaceAndPath(Farworld.MOD_ID, "textures/entity/gloomstone_pickup.png");
    private static final RenderType RENDER_TYPE;

    public GloomstonePickupRenderer(EntityRendererProvider.Context context) {
        super(context);
        this.shadowRadius = 0.15F;
        this.shadowStrength = 0.75F;
    }

    protected int getBlockLightLevel(GloomstonePickup entity, BlockPos pos) {
        return Mth.clamp(super.getBlockLightLevel(entity, pos) + 7, 0, 15);
    }

    public void render(GloomstonePickup entity, float entityYaw, float partialTicks, PoseStack poseStack, MultiBufferSource buffer, int packedLight) {

        RenderingUtils.renderEntitySprite(poseStack, this.entityRenderDispatcher.cameraOrientation(), getTextureLocation(entity), buffer, packedLight, new Vector3f(0f, 0.1f, 0f), 0.3f);

        super.render(entity, entityYaw, partialTicks, poseStack, buffer, packedLight);
    }

    @Override
    public @NotNull ResourceLocation getTextureLocation(@NotNull GloomstonePickup pickup) {
        return ResourceLocation.fromNamespaceAndPath(Farworld.MOD_ID, "textures/entity/gloomstone_pickup.png");
    }

    private static void vertex(VertexConsumer consumer, PoseStack.Pose pose, float x, float y, int red, int green, int blue, float u, float v, int packedLight) {
        consumer.addVertex(pose, x, y, 0.0F).setColor(red, green, blue, 128).setUv(u, v).setOverlay(OverlayTexture.NO_OVERLAY).setLight(packedLight).setNormal(pose, 0.0F, 1.0F, 0.0F);
    }

    static {
        RENDER_TYPE = RenderType.entityCutout(EXPERIENCE_ORB_LOCATION);
    }
}
