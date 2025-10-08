package net.ennway.farworld.particle;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.*;
import net.minecraft.core.particles.SimpleParticleType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class RedstoneChargeParticle extends TextureSheetParticle {

    private final SpriteSet spriteSet;
    public RedstoneChargeParticle(ClientLevel level, double x, double y, double z, SpriteSet spriteSet) {
        super(level, x, y, z);
        this.spriteSet = spriteSet;
        this.gravity = 0;
        this.lifetime = 6;

        this.scale(1f + (level.random.nextFloat() % 1f));

        this.xd = (double) level.getRandom().nextInt(-10, 10) / 20;
        this.yd = (double) level.getRandom().nextInt(-10, 10) / 20;
        this.zd = (double) level.getRandom().nextInt(-10, 10) / 20;

        float thisRoll = level.random.nextFloat();

        this.roll = thisRoll;
        this.oRoll = thisRoll;

        this.setSpriteFromAge(spriteSet);
    }

    @Override
    public int getLightColor(float partialTick) {
        return 15728880;
    }

    @Override
    public void tick() {
        this.setSpriteFromAge(spriteSet);
        this.xd *= 0.8;
        this.yd *= 0.8;
        this.zd *= 0.8;
        super.tick();
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
            return new RedstoneChargeParticle(level, x, y, z, spriteSet);
        }
    }
}

