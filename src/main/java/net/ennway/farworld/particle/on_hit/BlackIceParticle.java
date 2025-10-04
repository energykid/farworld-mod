package net.ennway.farworld.particle.on_hit;

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
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import org.joml.Vector3f;

public class BlackIceParticle extends TextureSheetParticle {

    private final SpriteSet spriteSet;
    public BlackIceParticle(ClientLevel level, double x, double y, double z, Vec3 vel, SpriteSet spriteSet) {
        super(level, x, y, z);
        this.spriteSet = spriteSet;
        this.scale(1 + (level.getRandom().nextFloat() * 1));
        this.lifetime = 5;
        this.xd = vel.x;
        this.yd = vel.y;
        this.zd = vel.z;
        this.setSpriteFromAge(spriteSet);
    }

    @Override
    public int getLightColor(float partialTick) {
        return 15728880;
    }

    @Override
    public void tick() {
        this.setSpriteFromAge(spriteSet);
        this.xd *= 0.75;
        this.yd *= 0.75;
        this.zd *= 0.75;
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

