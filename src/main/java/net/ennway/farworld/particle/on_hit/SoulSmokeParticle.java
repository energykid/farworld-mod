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

public class SoulSmokeParticle extends TextureSheetParticle {

    private final SpriteSet spriteSet;
    public SoulSmokeParticle(ClientLevel level, double x, double y, double z, SpriteSet spriteSet) {
        super(level, x, y, z);
        this.spriteSet = spriteSet;
        this.gravity = -0.07f;
        this.scale(1 + (level.getRandom().nextFloat() * 2));
        this.lifetime = level.getRandom().nextInt(4, 8);

        this.xd = (double) level.getRandom().nextInt(-10, 10) / 30;
        this.yd = (double) level.getRandom().nextInt(-10, 10) / 10;
        this.zd = (double) level.getRandom().nextInt(-10, 10) / 30;

        this.gravity = 0.4f;

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
        var a = new Vec3(this.xd, this.yd, this.zd);
        a = a.yRot(0.7f);
        this.xd = a.x * 0.9f;
        this.zd = a.z * 0.9f;
    }

    @Override
    public @NotNull ParticleRenderType getRenderType() {
        return ParticleRenderType.PARTICLE_SHEET_LIT;
    }
}

