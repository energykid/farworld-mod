package net.ennway.farworld.particle;

import com.mojang.blaze3d.vertex.VertexConsumer;
import net.ennway.farworld.particle.base.StraightUpParticle;
import net.minecraft.client.Camera;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.util.Mth;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class SlimeStreaks extends StraightUpParticle {

    public SlimeStreaks(ClientLevel level, double x, double y, double z, SpriteSet spriteSet) {
        super(level, x, y, z, spriteSet);

        this.heightScale = 0.2f;
        this.quadSize = 0.6f;

        this.lifetime = level.getRandom().nextInt(5, 7);

        this.setSpriteFromAge(this.sprites);
    }

    @Override
    public void tick() {
        this.setSpriteFromAge(this.sprites);
        super.tick();
    }

    @Override
    public void render(VertexConsumer buffer, Camera camera, float ticks) {
        this.heightScale = Mth.lerp(0.2f, heightScale, 2f);
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
            return new SlimeStreaks(level, x, y, z, spriteSet);
        }
    }
}

