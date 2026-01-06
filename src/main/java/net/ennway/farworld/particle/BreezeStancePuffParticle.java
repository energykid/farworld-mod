package net.ennway.farworld.particle;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.*;
import net.minecraft.core.particles.SimpleParticleType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class BreezeStancePuffParticle extends TextureSheetParticle {

    private final SpriteSet spriteSet;
    public BreezeStancePuffParticle(ClientLevel level, double x, double y, double z, double xsp, double ysp, double zsp, SpriteSet spriteSet) {
        super(level, x, y, z);
        this.spriteSet = spriteSet;
        this.gravity = 0;
        this.lifetime = 6;

        this.xd = xsp;
        this.yd = ysp;
        this.zd = zsp;

        this.scale(1f + (level.random.nextFloat() % 1f));

        float thisRoll = level.random.nextFloat();

        this.roll = thisRoll;
        this.oRoll = thisRoll;

        this.setSpriteFromAge(spriteSet);
    }

    @Override
    public void tick() {
        this.setSpriteFromAge(spriteSet);
        super.tick();
        this.xd *= 0.6;
        this.yd *= 0.6;
        this.zd *= 0.6;
    }

    @Override
    public @NotNull ParticleRenderType getRenderType() {
        return ParticleRenderType.PARTICLE_SHEET_LIT;
    }

    public static class Provider implements ParticleProvider<SimpleParticleType> {

        private final SpriteSet spriteSet;

        public Provider(SpriteSet spriteSet) {
            this.spriteSet = spriteSet;
        }

        @Override
        public @Nullable Particle createParticle(@NotNull SimpleParticleType particleType, @NotNull ClientLevel level, double x, double y, double z, double xsp, double ysp, double zsp) {
            return new BreezeStancePuffParticle(level, x, y, z, xsp, ysp, zsp, spriteSet);
        }
    }
}

