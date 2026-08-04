package net.ennway.farworld.particle;

import com.mojang.blaze3d.vertex.VertexConsumer;
import net.ennway.farworld.particle.base.StraightUpParticle;
import net.ennway.farworld.registries.ModParticles;
import net.minecraft.client.Camera;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.util.Mth;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class InfernalPillarParticle extends StraightUpParticle {

    public InfernalPillarParticle(ClientLevel level, double x, double y, double z, SpriteSet spriteSet) {
        super(level, x, y, z, spriteSet);

        this.quadSize = 1.5f;

        this.lifetime = 10;

        this.setSpriteFromAge(this.sprites);
    }

    @Override
    protected int getLightColor(float pPartialTick) {
        return 15728880;
    }

    @Override
    public void tick() {

        level.addParticle(
                ModParticles.INFERNAL_SMOKE.get(),
                x + Mth.nextDouble(random, -0.6, 0.6),
                y + Mth.nextDouble(random, 0.6, 1.2),
                z + Mth.nextDouble(random, -0.6, 0.6),
                0f,
                0f,
                0f
        );

        this.setSpriteFromAge(this.sprites);
        super.tick();
    }

    @Override
    public void render(VertexConsumer buffer, Camera camera, float ticks) {
        super.render(buffer, camera, ticks);
    }

    public static class Provider implements ParticleProvider<SimpleParticleType>
    {
        private final SpriteSet spriteSet;

        public Provider(SpriteSet spriteSet) {
            this.spriteSet = spriteSet;
        }

        @Override
        public @Nullable Particle createParticle(@NotNull SimpleParticleType particleType, @NotNull ClientLevel level, double x, double y, double z, double xsp, double ysp, double zsp) {
            return new InfernalPillarParticle(level, x, y, z, spriteSet);
        }
    }
}

