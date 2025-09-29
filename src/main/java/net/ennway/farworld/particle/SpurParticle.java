package net.ennway.farworld.particle;

import net.ennway.farworld.particle.base.FlatParticle;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.SpriteSet;

public class SpurParticle extends FlatParticle {

    public SpurParticle(ClientLevel level, double x, double y, double z, SpriteSet spriteSet) {
        super(level, x, y, z, spriteSet);

        this.quadSize = 0.7f;

        this.yd = 0.4;

        this.lifetime = level.getRandom().nextInt(7, 12);

        float roll = level.getRandom().nextFloat();

        this.roll = roll;
        this.oRoll = roll;

        this.setSpriteFromAge(this.sprites);
    }

    @Override
    public void tick() {
        this.setSpriteFromAge(this.sprites);
        this.xd *= 0.5;
        this.yd *= 0.7;
        this.zd *= 0.5;
        super.tick();
    }
}

