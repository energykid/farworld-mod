package net.ennway.farworld.entity.projectile;

import net.ennway.farworld.registries.ModSounds;
import net.ennway.farworld.utils.MathUtils;
import net.minecraft.core.Direction;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.entity.projectile.ThrowableProjectile;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec3;

public class ThornBerryProjectile extends ThrowableProjectile {

    public static ThornBerryProjectile get(EntityType<? extends ThrowableProjectile> entityType, Level level) {
        return new ThornBerryProjectile(entityType, level);
    }

    public ThornBerryProjectile(EntityType<? extends ThrowableProjectile> entityType, Level level) {
        super(entityType, level);
    }

    public ThornBerryProjectile(EntityType<? extends ThrowableProjectile> entityType, double d, double e, double f, Level level) {
        super(entityType, d, e, f, level);
    }

    public ThornBerryProjectile(EntityType<? extends ThrowableProjectile> entityType, LivingEntity livingEntity, Level level) {
        super(entityType, livingEntity, level);
    }

    public float visualRoll = 0f;

    public static final EntityDataAccessor<Integer> TIMER = SynchedEntityData.defineId(ThornBerryProjectile.class, EntityDataSerializers.INT);
    public static final EntityDataAccessor<Integer> BOUNCES = SynchedEntityData.defineId(ThornBerryProjectile.class, EntityDataSerializers.INT);
    public static final EntityDataAccessor<Float> ROLL = SynchedEntityData.defineId(ThornBerryProjectile.class, EntityDataSerializers.FLOAT);

    @Override
    public Vec3 getMovementToShoot(double x, double y, double z, float velocity, float inaccuracy) {
        return new Vec3(MathUtils.randomDouble(random, -0.5d, 0.5d), MathUtils.randomDouble(random, -0.5d, 0.5d), MathUtils.randomDouble(random, -0.5d, 0.5d));
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        builder.define(TIMER, 0);
        builder.define(BOUNCES, 3);
        builder.define(ROLL, 0f);
    }

    @Override
    protected void onHitEntity(EntityHitResult result) {
        super.onHitEntity(result);
        if (result.getEntity() instanceof LivingEntity entity)
        {
            explode();
        }
    }

    public void explode()
    {
        playSound(ModSounds.THORN_BERRY_IMPACT.get());
        remove(RemovalReason.KILLED);
    }

    @Override
    public void tick() {
        if (entityData.get(TIMER) == 0)
        {
            setDeltaMovement(new Vec3(MathUtils.randomDouble(random, -0.5, 0.5), MathUtils.randomDouble(random, -0.5, 0.5), MathUtils.randomDouble(random, -0.5, 0.5)));
        }

        setYRot(lerpRotation(getYRot(), (float)(Mth.atan2(getDeltaMovement().x, getDeltaMovement().z) * 180.0F / (float) Math.PI)));

        super.tick();

        this.setOnGround(false);

        this.addDeltaMovement(new Vec3(0, -0.1, 0));

        this.setDeltaMovement(this.getDeltaMovement().multiply(0.9, 1, 0.9));

        entityData.set(ROLL, entityData.get(ROLL) + (float)(this.getDeltaMovement().length() * 52f));
        entityData.set(TIMER, entityData.get(TIMER) + 1);
    }

    @Override
    protected void onHitBlock(BlockHitResult result) {
        Vec3 movement = getDeltaMovement();

        if (result.getDirection().getAxis() == Direction.Axis.X) movement = movement.multiply(-0.5, 1, 1);
        if (result.getDirection().getAxis() == Direction.Axis.Y) movement = movement.multiply(1, -0.5, 1);
        if (result.getDirection().getAxis() == Direction.Axis.Z) movement = movement.multiply(1, 1, -0.5);

        entityData.set(BOUNCES, entityData.get(BOUNCES) - 1);

        if (entityData.get(BOUNCES) < 0)
        {
            explode();
        }

        setDeltaMovement(movement);
    }
}
