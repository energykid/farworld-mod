package net.ennway.farworld.particle;

import com.mojang.blaze3d.vertex.VertexConsumer;
import net.ennway.farworld.utils.MathUtils;
import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.*;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.util.Mth;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class FallingDustParticle extends TextureSheetParticle {

    private final SpriteSet spriteSet;
    float v = 0f;
    public FallingDustParticle(ClientLevel level, double x, double y, double z, double xsp, double ysp, double zsp, SpriteSet spriteSet) {
        super(level, x, y, z);
        this.spriteSet = spriteSet;
        this.gravity = 0;
        this.lifetime = 64;

        this.xd = 0;
        this.yd = -0.1;
        this.zd = 0;

        v = (float)MathUtils.randomDouble(level.getRandom(), 0, 1.5);

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
        v += 0.07f;
        if (!this.onGround) {
            this.xd -= (0.004 * Mth.sin(v));
            this.yd -= 0.005;
            this.zd -= (0.004 * Mth.sin(v));
        }
    }

    @Override
    public void render(VertexConsumer buffer, Camera renderInfo, float partialTicks) {
        if (!this.onGround && !Minecraft.getInstance().isPaused())
        {
            this.roll += 0.06f;
            this.oRoll += 0.06f;
        }
        super.render(buffer, renderInfo, partialTicks);
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
            return new FallingDustParticle(level, x, y, z, xsp, ysp, zsp, spriteSet);
        }
    }
}

