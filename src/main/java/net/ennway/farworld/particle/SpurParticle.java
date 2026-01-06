package net.ennway.farworld.particle;

import com.mojang.blaze3d.vertex.VertexConsumer;
import net.ennway.farworld.particle.base.FlatParticle;
import net.minecraft.client.Camera;
import net.minecraft.client.main.GameConfig;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.SpriteSet;
import net.neoforged.neoforge.common.CommonHooks;

public class SpurParticle extends FlatParticle {

    @Override
    protected int getLightColor(float pPartialTick) {
        return 15728880;
    }

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
    public void render(VertexConsumer buffer, Camera camera, float ticks) {
        super.render(buffer, camera, ticks);
        this.roll += 0.05f;
        this.oRoll += 0.05f;
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

