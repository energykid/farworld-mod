package net.ennway.farworld.particle;

import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import net.ennway.farworld.particle.base.OrientedParticle;
import net.ennway.farworld.particle.base.VelRotatedParticle;
import net.ennway.farworld.utils.MathUtils;
import net.ennway.farworld.utils.QuaternionUtils;
import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.*;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class DirectionalRedstoneChargeParticle extends OrientedParticle {

    private final SpriteSet spriteSet;
    public DirectionalRedstoneChargeParticle(ClientLevel level, double x, double y, double z, double xv, double yv, double zv, SpriteSet spriteSet) {
        super(level, x, y, z, spriteSet);
        this.xd = xv;
        this.yd = yv;
        this.zd = zv;
        this.spriteSet = spriteSet;
        this.lifetime = 3;
        this.setSpriteFromAge(spriteSet);
        this.quadSize = 0.5f;
        this.qu = QuaternionUtils.orientedQuaternion(new Vec3(this.xd, this.yd, this.zd));
    }

    @Override
    public int getLightColor(float partialTick) {
        return 15728880;
    }

    @Override
    public void tick() {
        this.setSpriteFromAge(spriteSet);
        this.xd *= 0.6;
        this.yd *= 0.6;
        this.zd *= 0.6;
        super.tick();
    }

    @Override
    public void render(VertexConsumer buffer, Camera camera, float ticks) {
        super.render(buffer, camera, ticks);
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
            return new DirectionalRedstoneChargeParticle(level, x, y, z, xsp, ysp, zsp, spriteSet);
        }
    }
}

