package net.ennway.farworld.particle;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.*;
import net.minecraft.core.particles.SimpleParticleType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class SludgeDropParticle extends TextureSheetParticle {

    private final SpriteSet spriteSet;
    public SludgeDropParticle(ClientLevel level, double x, double y, double z, SpriteSet spriteSet) {
        super(level, x, y, z);
        this.spriteSet = spriteSet;
        this.gravity = 0;
        this.lifetime = 10;

        this.scale(0.5f + (level.random.nextFloat() % 0.6f));

        this.xd = (double) level.getRandom().nextInt(-10, 10) / 20;
        this.yd = (double) level.getRandom().nextInt(-10, 10) / 20;
        this.zd = (double) level.getRandom().nextInt(-10, 10) / 20;

        this.setSpriteFromAge(spriteSet);
    }

    @Override
    public void tick() {
        this.setSpriteFromAge(spriteSet);
        this.xd *= 0.4;
        this.yd *= 0.4;
        this.yd -= 0.1;
        this.zd *= 0.4;
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
            return new SludgeDropParticle(level, x, y, z, spriteSet);
        }
    }
}

