package net.ennway.farworld.particle;

import com.mojang.blaze3d.vertex.VertexConsumer;
import net.ennway.farworld.particle.base.OrientedParticle;
import net.ennway.farworld.utils.QuaternionUtils;
import net.minecraft.client.Camera;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.*;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.util.Mth;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class ThirtyTwoPixelExplosionParticle extends TextureSheetParticle {

    private final SpriteSet spriteSet;
    @Override
    public ParticleRenderType getRenderType() {
        return ParticleRenderType.PARTICLE_SHEET_LIT;
    }

    public ThirtyTwoPixelExplosionParticle(ClientLevel level, double x, double y, double z, SpriteSet spriteSet) {
        super(level, x, y, z);

        this.spriteSet = spriteSet;

        this.quadSize = 1f;

        this.lifetime = 5;

        this.xo = 0;
        this.yo = 0;
        this.zo = 0;

        this.setSpriteFromAge(spriteSet);
    }

    @Override
    public void tick() {
        super.tick();

        this.setSpriteFromAge(spriteSet);
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
            return new ThirtyTwoPixelExplosionParticle(level, x, y, z, spriteSet);
        }
    }

    public static class Emissive extends ThirtyTwoPixelExplosionParticle {

        public Emissive(ClientLevel level, double x, double y, double z, SpriteSet spriteSet) {
            super(level, x, y, z, spriteSet);
        }

        @Override
        protected int getLightColor(float partialTick) {
            return 16777215;
        }

        public static class Provider implements ParticleProvider<SimpleParticleType>
        {
            private final SpriteSet spriteSet;
            public Provider(SpriteSet spriteSet) {
                this.spriteSet = spriteSet;
            }
            @Override
            public @Nullable Particle createParticle(@NotNull SimpleParticleType particleType, @NotNull ClientLevel level, double x, double y, double z, double xsp, double ysp, double zsp) {
                return new ThirtyTwoPixelExplosionParticle.Emissive(level, x, y, z, spriteSet);
            }
        }
    }
}

