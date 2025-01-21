package net.ennway.farworld.particle;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.Tesselator;
import com.mojang.blaze3d.vertex.VertexFormat;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.ParticleRenderType;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.client.particle.TextureSheetParticle;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.client.renderer.texture.TextureManager;
import org.jetbrains.annotations.NotNull;
import org.joml.Vector3f;

public class DiamondSparkleParticle extends TextureSheetParticle {

    private final SpriteSet spriteSet;
    public DiamondSparkleParticle(ClientLevel level, double x, double y, double z, SpriteSet spriteSet) {
        super(level, x, y, z);
        this.spriteSet = spriteSet;
        this.gravity = 0;
        this.lifetime = level.getRandom().nextInt(7, 14);

        this.xd = (double) level.getRandom().nextInt(-10, 10) / 15;
        this.yd = (double) level.getRandom().nextInt(-10, 10) / 15;
        this.zd = (double) level.getRandom().nextInt(-10, 10) / 15;

        this.setSpriteFromAge(spriteSet);
    }

    @Override
    public int getLightColor(float partialTick) {
        return 15728880;
    }

    @Override
    public void tick() {
        this.setSpriteFromAge(spriteSet);
        this.xd *= 0.6000000059604645;
        this.yd *= 0.6000000059604645;
        this.zd *= 0.6000000059604645;
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

