//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package net.ennway.farworld.entity.projectile.client;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.ennway.farworld.Farworld;
import net.ennway.farworld.entity.projectile.ApocalypseBreathProjectile;
import net.ennway.farworld.entity.projectile.ApocalypseBreathProjectile;
import net.ennway.farworld.utils.RenderingUtils;
import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.ExperienceOrb;
import net.minecraft.world.entity.player.Player;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.neoforge.common.NeoForgeConfig;
import org.jetbrains.annotations.NotNull;
import org.joml.Vector3f;
import software.bernie.geckolib.util.ClientUtil;

import java.util.List;

@OnlyIn(Dist.CLIENT)
public class ApocalypseBreathRenderer extends EntityRenderer<ApocalypseBreathProjectile> {

    public ApocalypseBreathRenderer(EntityRendererProvider.Context context) {
        super(context);
        this.shadowRadius = 0.15F;
        this.shadowStrength = 0.75F;
    }

    protected int getBlockLightLevel(ApocalypseBreathProjectile entity, BlockPos pos) {
        return Mth.clamp(super.getBlockLightLevel(entity, pos) + 7, 0, 15);
    }

    public void render(ApocalypseBreathProjectile entity, float entityYaw, float partialTicks, PoseStack poseStack, MultiBufferSource buffer, int packedLight) {
        Vector3f v3 = new Vector3f(0f, -0.25f + (entity.scale / 8f), 0f);
        RenderingUtils.renderEntitySprite(poseStack, this.entityRenderDispatcher.cameraOrientation(), RenderType.eyes(getTextureLocation(entity)), buffer, 16777215, v3, entity.scale * 3.5f);
        if (!Minecraft.getInstance().isPaused())
            entity.scale = Mth.lerp(0.05f, entity.scale, 2f);
    }

    List<ResourceLocation> textures = List.of(
            ResourceLocation.fromNamespaceAndPath(Farworld.MOD_ID, "textures/entity/apocalypse_breath/1.png"),
            ResourceLocation.fromNamespaceAndPath(Farworld.MOD_ID, "textures/entity/apocalypse_breath/2.png"),
            ResourceLocation.fromNamespaceAndPath(Farworld.MOD_ID, "textures/entity/apocalypse_breath/3.png"),
            ResourceLocation.fromNamespaceAndPath(Farworld.MOD_ID, "textures/entity/apocalypse_breath/4.png"),
            ResourceLocation.fromNamespaceAndPath(Farworld.MOD_ID, "textures/entity/apocalypse_breath/5.png"),
            ResourceLocation.fromNamespaceAndPath(Farworld.MOD_ID, "textures/entity/apocalypse_breath/6.png"),
            ResourceLocation.fromNamespaceAndPath(Farworld.MOD_ID, "textures/entity/apocalypse_breath/7.png"),
            ResourceLocation.fromNamespaceAndPath(Farworld.MOD_ID, "textures/entity/apocalypse_breath/8.png"),
            ResourceLocation.fromNamespaceAndPath(Farworld.MOD_ID, "textures/entity/apocalypse_breath/9.png"),
            ResourceLocation.fromNamespaceAndPath(Farworld.MOD_ID, "textures/entity/apocalypse_breath/10.png"),
            ResourceLocation.fromNamespaceAndPath(Farworld.MOD_ID, "textures/entity/apocalypse_breath/11.png"),
            ResourceLocation.fromNamespaceAndPath(Farworld.MOD_ID, "textures/entity/apocalypse_breath/12.png"),
            ResourceLocation.fromNamespaceAndPath(Farworld.MOD_ID, "textures/entity/apocalypse_breath/13.png"),
            ResourceLocation.fromNamespaceAndPath(Farworld.MOD_ID, "textures/entity/apocalypse_breath/14.png")
    );

    @Override
    protected int getSkyLightLevel(ApocalypseBreathProjectile entity, BlockPos pos) {
        return 15;
    }

    @Override
    protected float getShadowRadius(ApocalypseBreathProjectile entity) {
        return 0f;
    }

    @Override
    public ResourceLocation getTextureLocation(ApocalypseBreathProjectile apocalypseBreathProjectile) {
        return textures.get(apocalypseBreathProjectile.frame);
    }
}
