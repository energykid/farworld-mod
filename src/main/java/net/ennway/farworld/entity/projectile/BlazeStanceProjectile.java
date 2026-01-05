package net.ennway.farworld.entity.projectile;

import net.ennway.farworld.registries.ModParticles;
import net.ennway.farworld.registries.ModSounds;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.OwnableEntity;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

public class BlazeStanceProjectile extends Projectile {
    public BlazeStanceProjectile(EntityType<? extends Projectile> entityType, Level level) {
        super(entityType, level);
    }
    private static final EntityDataAccessor<Integer> TIMER = SynchedEntityData.defineId(BlazeStanceProjectile.class, EntityDataSerializers.INT);

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        builder.define(TIMER, 0)
                .build();
    }

    @Override
    public void tick() {
        super.tick();
        int timer = this.getEntityData().get(TIMER);

        if (timer < 6 && timer % 2 == 1)
        {
            AABB mobs = new AABB(
                    this.position().add(new Vec3(-4, -1, -4)),
                    this.position().add(new Vec3(4, 1, 4))
            );

            for (Entity mob : level().getEntitiesOfClass(Mob.class, mobs))
            {
                if (mob instanceof OwnableEntity ownable)
                {
                    if (ownable.getOwner() == null)
                    {
                        mob.invulnerableTime = 0;
                        mob.hurt(mob.damageSources().magic(), 1.5f);
                    }
                }
                else
                {
                    mob.invulnerableTime = 0;
                    mob.hurt(mob.damageSources().magic(), 1.5f);
                }
            }

            this.playSound(ModSounds.BLAZE_STANCE_SLASH.get(), 1, 1f + (timer * 0.15f));
            this.level().addParticle(ModParticles.BLAZE_STANCE_SLASH.get(), this.position().x, this.position().y - 0.5, this.position().z, 0,0,0);
        }

        if (timer == 10)
        {
            this.remove(RemovalReason.DISCARDED);
        }

        this.getEntityData().set(TIMER, timer + 1);
    }
}
