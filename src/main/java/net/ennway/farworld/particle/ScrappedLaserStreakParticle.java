package net.ennway.farworld.particle;

import com.mojang.blaze3d.vertex.VertexConsumer;
import net.ennway.farworld.particle.base.OrientedParticle;
import net.ennway.farworld.utils.QuaternionUtils;
import net.minecraft.client.Camera;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.ParticleRenderType;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.core.particles.*;
import net.minecraft.util.Mth;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class ScrappedLaserStreakParticle extends OrientedParticle {

    @Override
    public ParticleRenderType getRenderType() {
        return ParticleRenderType.PARTICLE_SHEET_TRANSLUCENT;
    }

    public ScrappedLaserStreakParticle(ClientLevel level, double x, double y, double z, Vec3 orientation, SpriteSet spriteSet) {
        super(level, x, y, z, spriteSet);

        this.quadSize = 1f;

        this.lifetime = 5;

        this.xo = 0;
        this.yo = 0;
        this.zo = 0;

        this.qu = QuaternionUtils.orientedQuaternion(orientation);

        this.setSpriteFromAge(this.sprites);
    }

    @Override
    public void tick() {
        this.setSpriteFromAge(this.sprites);
        super.tick();
    }

    @Override
    public void render(VertexConsumer buffer, Camera camera, float ticks) {
        this.quadSize = Mth.lerp(0.2f, this.quadSize, 2f);
        super.render(buffer, camera, ticks);
    }

    public static class Provider implements ParticleProvider<SimpleParticleType>
    {
        private final SpriteSet spriteSet;

        public Provider(SpriteSet spriteSet) {
            this.spriteSet = spriteSet;
        }

        @Override
        public @Nullable Particle createParticle(@NotNull SimpleParticleType simpleParticleType, @NotNull ClientLevel level, double x, double y, double z, double oX, double oY, double oZ) {
            return new ScrappedLaserStreakParticle(level, x, y, z, new Vec3(oX, oY, oZ), spriteSet);
        }
    }
}