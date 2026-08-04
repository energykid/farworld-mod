package net.ennway.farworld.particle.base;

import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.*;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

@OnlyIn(Dist.CLIENT)
public class VelRotatedParticle extends TextureSheetParticle {

    private final SpriteSet spriteSet;
    private final int variant;
    private final float baseQuadSize;
    private final float brightness;
    private double xoD, yoD, zoD;

    private static final int TOTAL_TEXTURES = 3;

    protected VelRotatedParticle(ClientLevel world, double x, double y, double z, double vx, double vy, double vz, SpriteSet spriteSet) {
        super(world, x, y, z, vx, vy, vz);
        this.spriteSet = spriteSet;

        this.variant = this.random.nextInt(3);

        float sizeMultiplier = 1.0f;
        if (this.variant == 0) sizeMultiplier = 2.1f;
        else if (this.variant == 1) sizeMultiplier = 2f;
        else if (this.variant == 2) sizeMultiplier = 2.2f;

        this.baseQuadSize = 0.20f * sizeMultiplier;
        this.quadSize = this.baseQuadSize;

        this.lifetime = 124 + this.random.nextInt(8);

        this.gravity = 0.8f;
        this.hasPhysics = true;

        this.xd = vx;
        this.yd = vy;
        this.zd = vz;

        this.xoD = vx;
        this.yoD = vy;
        this.zoD = vz;

        this.alpha = 1.0f;

        this.brightness = 0.3f + 0.7f * this.random.nextFloat();
        this.rCol *= this.brightness;
        this.gCol *= this.brightness;
        this.bCol *= this.brightness;

        this.setSprite(this.spriteSet.get(this.variant, TOTAL_TEXTURES - 1));
    }

    @Override
    public @NotNull ParticleRenderType getRenderType() {
        return ParticleRenderType.PARTICLE_SHEET_TRANSLUCENT;
    }

    @Override
    public float getQuadSize(float partialTicks) {
        float ageProgress = ((float) this.age + partialTicks) / (float) this.lifetime;
        if (ageProgress >= 0.9f) {
            float t = (ageProgress - 0.9f) / 0.1f;
            t = Mth.clamp(t, 0.0f, 1.0f);
            float smooth = t * t * (3.0f - 2.0f * t);
            return this.baseQuadSize * (1.0f - smooth);
        }
        return this.baseQuadSize;
    }

    @Override
    public void tick() {
        this.gravity = 0f;
        super.tick();
    }

    @Override
    public void render(VertexConsumer buffer, Camera camera, float partialTicks) {
        float vx = (float) Mth.lerp(partialTicks, this.xoD, this.xd);
        float vy = (float) Mth.lerp(partialTicks, this.yoD, this.yd);
        float vz = (float) Mth.lerp(partialTicks, this.zoD, this.zd);

        float upProj = vx * camera.getUpVector().x() + vy * camera.getUpVector().y() + vz * camera.getUpVector().z();
        float leftProj = vx * camera.getLeftVector().x() + vy * camera.getLeftVector().y() + vz * camera.getLeftVector().z();

        this.roll = (float) (Math.atan2(leftProj, upProj));
        this.oRoll = this.roll;

        super.render(buffer, camera, partialTicks);
    }
}