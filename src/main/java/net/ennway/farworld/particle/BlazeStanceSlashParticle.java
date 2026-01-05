package net.ennway.farworld.particle;

import com.mojang.blaze3d.vertex.VertexConsumer;
import net.ennway.farworld.particle.base.FlatParticle;
import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.core.particles.SimpleParticleType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class BlazeStanceSlashParticle extends FlatParticle {

    float rollSpeed = 0f;

    public BlazeStanceSlashParticle(ClientLevel level, double x, double y, double z, SpriteSet spriteSet) {
        super(level, x, y, z, spriteSet);

        this.rollSpeed = 0.25f;

        this.quadSize = 1.5f;

        this.lifetime = level.getRandom().nextInt(5, 7);

        this.roll = level.getRandom().nextFloat() * 4f;
        this.oRoll = roll;

        this.setSpriteFromAge(this.sprites);
    }

    @Override
    public void render(VertexConsumer buffer, Camera camera, float ticks) {
        super.render(buffer, camera, ticks);
        if (!Minecraft.getInstance().isPaused())
        {
            this.roll += this.rollSpeed;
            this.oRoll += this.rollSpeed;
            this.rollSpeed *= 0.75f;
            this.quadSize += 0.1f;
        }
    }

    @Override
    public void tick() {
        this.setSpriteFromAge(this.sprites);
        super.tick();
    }

    public static class Provider implements ParticleProvider<SimpleParticleType>
    {
        private final SpriteSet spriteSet;

        public Provider(SpriteSet spriteSet) {
            this.spriteSet = spriteSet;
        }

        @Override
        public @Nullable Particle createParticle(@NotNull SimpleParticleType particleType, @NotNull ClientLevel level, double x, double y, double z, double xsp, double ysp, double zsp) {
            return new BlazeStanceSlashParticle(level, x, y, z, spriteSet);
        }
    }
}

