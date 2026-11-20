package net.ennway.farworld.event;

import com.mojang.blaze3d.vertex.PoseStack;
import net.ennway.farworld.Farworld;
import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.DimensionSpecialEffects;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.phys.Vec3;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RegisterDimensionSpecialEffectsEvent;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;
import org.joml.Matrix4f;
import org.joml.Vector3f;

@EventBusSubscriber
public class GloomyDenseForestLighting {

    public static float dimnessIntensity = 0f;
    public static final ResourceLocation DENSE_FOREST = ResourceLocation.fromNamespaceAndPath(Farworld.MOD_ID, "dense_forest");
    @SubscribeEvent
    public static void handleLighting(PlayerTickEvent.Post evt)
    {
        if (evt.getEntity().level().getBiome(evt.getEntity().getOnPos()).is(ResourceLocation.fromNamespaceAndPath(Farworld.MOD_ID, "dense_forest")))
        {
            dimnessIntensity = Mth.lerp(0.02f, dimnessIntensity, 1f);
        }
        else
        {
            dimnessIntensity = Mth.lerp(0.04f, dimnessIntensity, 0f);
        }
    }

    public static class GloomyLightingEffect extends DimensionSpecialEffects
    {
        public static Vec3 dimness = new Vec3(1, 1, 1);

        public GloomyLightingEffect() {
            super(OverworldEffects.CLOUD_LEVEL, true, SkyType.NORMAL, false, false);
        }

        @Override
        public boolean renderSky(ClientLevel level, int ticks, float partialTick, Matrix4f modelViewMatrix, Camera camera, Matrix4f projectionMatrix, boolean isFoggy, Runnable setupFog) {
            dimness = new Vec3(
                    Mth.lerp(0.05f, dimness.x, 1f - (GloomyDenseForestLighting.dimnessIntensity * 0.7f)),
                    Mth.lerp(0.05f, dimness.y, 1f - (GloomyDenseForestLighting.dimnessIntensity * 0.6f)),
                    Mth.lerp(0.05f, dimness.z, 1f - (GloomyDenseForestLighting.dimnessIntensity * 0.4f)));

            return super.renderSky(level, ticks, partialTick, modelViewMatrix, camera, projectionMatrix, isFoggy, setupFog);
        }

        @Override
        public void adjustLightmapColors(ClientLevel level, float partialTicks, float skyDarken, float blockLightRedFlicker, float skyLight, int pixelX, int pixelY, Vector3f colors) {
            super.adjustLightmapColors(level, partialTicks, skyDarken, blockLightRedFlicker, skyLight, pixelX, pixelY, colors.mul((float)dimness.x, (float)dimness.y, (float)dimness.z));
        }

        @Override
        public Vec3 getBrightnessDependentFogColor(Vec3 vec3, float v) {
            float f = Mth.lerp(GloomyDenseForestLighting.dimnessIntensity, 1, 0);
            return vec3.multiply(f, f, f);
        }

        @Override
        public boolean isFoggyAt(int i, int i1) {
            return true;
        }
    }

    @SubscribeEvent
    public static void lightColorSetup(RegisterDimensionSpecialEffectsEvent event) {
        event.register(ResourceLocation.fromNamespaceAndPath(Farworld.MOD_ID, "bystone"), new GloomyLightingEffect());
    }
}
