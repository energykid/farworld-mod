package net.ennway.farworld.particle;

import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import net.ennway.farworld.particle.base.FlatParticle;
import net.ennway.farworld.particle.base.OrientedParticle;
import net.ennway.farworld.utils.MathUtils;
import net.ennway.farworld.utils.QuaternionUtils;
import net.minecraft.client.Camera;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.ParticleRenderType;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class ApocalypseRoarParticle extends OrientedParticle {

    @Override
    public ParticleRenderType getRenderType() {
        return ParticleRenderType.PARTICLE_SHEET_TRANSLUCENT;
    }

    public ApocalypseRoarParticle(ClientLevel level, double x, double y, double z, SpriteSet spriteSet) {
        super(level, x, y, z, spriteSet);

        this.quadSize = 1f;

        this.lifetime = level.getRandom().nextInt(2, 4);

        this.roll = level.getRandom().nextFloat() * 4f;
        this.oRoll = roll;

        if (level.getNearestPlayer(x, y, z, 3, false) instanceof Player player)
        {
            this.qu = QuaternionUtils.orientedQuaternion(player).mul(Axis.ZP.rotationDegrees((float)MathUtils.randomDouble(player.getRandom(), 0, 360)));
        }

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
        public @Nullable Particle createParticle(@NotNull SimpleParticleType particleType, @NotNull ClientLevel level, double x, double y, double z, double xsp, double ysp, double zsp) {
            return new ApocalypseRoarParticle(level, x, y, z, spriteSet);
        }
    }
}

