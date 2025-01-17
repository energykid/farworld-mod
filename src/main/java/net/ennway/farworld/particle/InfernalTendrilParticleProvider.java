package net.ennway.farworld.particle;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.core.particles.SimpleParticleType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class InfernalTendrilParticleProvider implements ParticleProvider<SimpleParticleType> {

    private final SpriteSet spriteSet;

    public InfernalTendrilParticleProvider(SpriteSet spriteSet) {
        this.spriteSet = spriteSet;
    }

    @Override
    public @Nullable Particle createParticle(@NotNull SimpleParticleType particleType, @NotNull ClientLevel level, double x, double y, double z, double xsp, double ysp, double zsp) {
        return new InfernalTendrilParticle(level, x, y, z, spriteSet, level.getRandom().nextFloat() * 360);
    }
}