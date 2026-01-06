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

public class BreezeStanceBurstParticle extends FlatParticle {

    public BreezeStanceBurstParticle(ClientLevel level, double x, double y, double z, SpriteSet spriteSet) {
        super(level, x, y, z, spriteSet);

        this.quadSize = 1.5f;

        this.lifetime = level.getRandom().nextInt(6, 8);

        this.roll = level.getRandom().nextFloat() * 4f;
        this.oRoll = roll;

        this.setSpriteFromAge(this.sprites);
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
            return new BreezeStanceBurstParticle(level, x, y, z, spriteSet);
        }
    }
}

