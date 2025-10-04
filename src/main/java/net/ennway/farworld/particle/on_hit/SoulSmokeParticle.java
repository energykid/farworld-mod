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
import org.jetbrains.annotations.NotNull;
import org.joml.Vector3f;

public class SoulSmokeParticle extends TextureSheetParticle {

    private final SpriteSet spriteSet;
    public SoulSmokeParticle(ClientLevel level, double x, double y, double z, SpriteSet spriteSet) {
        super(level, x, y, z);
        this.spriteSet = spriteSet;
        this.gravity = -0.07f;
        this.scale(1 + (level.getRandom().nextFloat() * 2));
        this.lifetime = level.getRandom().nextInt(10, 15);

        this.xd = (double) level.getRandom().nextInt(-10, 10) / 10;
        this.yd = (double) level.getRandom().nextInt(-10, 10) / 10;
        this.zd = (double) level.getRandom().nextInt(-10, 10) / 10;

        this.setSpriteFromAge(spriteSet);
    }

    @Override
    public int getLightColor(float partialTick) {
        return 15728880;
    }

    @Override
    public void tick() {
        this.setSpriteFromAge(spriteSet);
        this.xd *= 0.5;
        this.yd *= 0.5;
        this.zd *= 0.5;
        super.tick();
    }

    @Override
    public @NotNull ParticleRenderType getRenderType() {
        return ParticleRenderType.PARTICLE_SHEET_LIT;
    }
}

