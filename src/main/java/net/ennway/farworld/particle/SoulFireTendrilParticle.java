package net.ennway.farworld.particle;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.*;
import net.ennway.farworld.utils.MathUtils;
import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.multiplayer.chat.ChatListener;
import net.minecraft.client.multiplayer.chat.ChatLog;
import net.minecraft.client.particle.ParticleRenderType;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.client.particle.TextureSheetParticle;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.PlayerChatMessage;
import net.minecraft.util.Mth;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import org.joml.Quaternionf;
import org.joml.Vector3f;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SoulFireTendrilParticle extends TextureSheetParticle {

    private static final Logger log = LoggerFactory.getLogger(SoulFireTendrilParticle.class);
    private final SpriteSet spriteSet;
    public SoulFireTendrilParticle(ClientLevel level, double x, double y, double z, SpriteSet spriteSet, float roll) {
        super(level, x, y, z);
        this.spriteSet = spriteSet;

        this.hasPhysics = false;

        this.lifetime = 10;

        this.scale(10f);

        this.roll = roll;
        this.oRoll = roll;

        this.setSpriteFromAge(spriteSet);
    }

    @Override
    public int getLightColor(float partialTick) {
        return 15728880;
    }

    @Override
    public void tick() {
        this.setSpriteFromAge(spriteSet);
        super.tick();
    }

    @Override
    public @NotNull ParticleRenderType getRenderType() {

        Vector3f v3 = new Vector3f(1f, 1f, 1f);

        return new ParticleRenderType() {
            public BufferBuilder begin(@NotNull Tesselator tesselator, @NotNull TextureManager textureManager) {
                RenderSystem.disableBlend();
                RenderSystem.depthMask(true);
                RenderSystem.setShaderLights(v3, v3);
                RenderSystem.setShaderTexture(0, TextureAtlas.LOCATION_PARTICLES);
                return tesselator.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.PARTICLE);
            }

            public String toString() {
                return "PARTICLE_SHEET_LIT";
            }

            public boolean isTranslucent() {
                return false;
            }
        };
    }
}

