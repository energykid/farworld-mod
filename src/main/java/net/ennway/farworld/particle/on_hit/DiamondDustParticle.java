package net.ennway.farworld.particle.on_hit;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.ParticleRenderType;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.client.particle.TextureSheetParticle;
import org.jetbrains.annotations.NotNull;

public class DiamondDustParticle extends TextureSheetParticle {

    private final SpriteSet spriteSet;
    public DiamondDustParticle(ClientLevel level, double x, double y, double z, SpriteSet spriteSet) {
        super(level, x, y, z);
        this.spriteSet = spriteSet;
        this.gravity = 0;
        this.lifetime = 12;

        this.xd = (double) level.getRandom().nextInt(-10, 10) / 9;
        this.yd = level.getRandom().nextDouble() % 0.7;
        this.zd = (double) level.getRandom().nextInt(-10, 10) / 9;

        this.setSpriteFromAge(spriteSet);
    }

    @Override
    public void tick() {
        this.setSpriteFromAge(spriteSet);
        this.xd *= 0.7000000059604645;
        this.yd -= 0.175;
        this.zd *= 0.7000000059604645;
        super.tick();
    }

    @Override
    public @NotNull ParticleRenderType getRenderType() {
        return ParticleRenderType.PARTICLE_SHEET_OPAQUE;
    }
}

