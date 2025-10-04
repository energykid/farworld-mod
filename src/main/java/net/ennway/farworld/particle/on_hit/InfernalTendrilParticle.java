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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class InfernalTendrilParticle extends TextureSheetParticle {

    private static final Logger log = LoggerFactory.getLogger(InfernalTendrilParticle.class);
    private final SpriteSet spriteSet;
    public InfernalTendrilParticle(ClientLevel level, double x, double y, double z, SpriteSet spriteSet, float roll) {
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
        return ParticleRenderType.PARTICLE_SHEET_LIT;
    }
}

