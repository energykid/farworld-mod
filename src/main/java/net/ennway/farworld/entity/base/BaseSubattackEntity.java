package net.ennway.farworld.entity.base;

import net.minecraft.client.Minecraft;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.TraceableEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.entity.EntityTypeTest;
import net.minecraft.world.phys.AABB;
import org.jetbrains.annotations.Nullable;

import java.util.Comparator;
import java.util.List;

public class BaseSubattackEntity extends Entity implements TraceableEntity
{
    public int delay;
    public int duration;
    public int animationTime = 100;
    public double distance;
    public Entity owner;
    public float damage = 1f;
    public BaseSubattackEntity(EntityType<?> entityType, Level level, float dam, int del, int dur, double dist) {
        super(entityType, level);
        damage = dam;
        delay = del;
        duration = dur;
        distance = dist;
    }

    @Override
    public void tick() {
        getEntityData().set(ATTACK_TICKS, getEntityData().get(ATTACK_TICKS) + 1);
        if (this.owner != null)
        {
            if (getEntityData().get(ATTACK_TICKS) > delay && getEntityData().get(ATTACK_TICKS) < delay + duration)
            {
                List<LivingEntity> entities = this.level().getEntitiesOfClass(LivingEntity.class,
                        new AABB(
                                this.getX() - distance, this.getY() - distance, this.getZ() - distance,
                                this.getX() + distance, this.getY() + distance, this.getZ() + distance
                        ));
                for (LivingEntity entity : entities)
                {
                    if (entity instanceof Player plr)
                    {
                        if (canHit(entity))
                        {
                            if (!plr.isInvulnerable() && !plr.isCreative())
                                plr.hurt(owner.damageSources().generic(), damage);
                        }
                    }
                    else if (canHit(entity))
                    {
                        if (!entity.isInvulnerable())
                            entity.hurt(owner.damageSources().generic(), damage);
                    }
                }
            }
        }
        if (getEntityData().get(ATTACK_TICKS) > animationTime)
        {
            remove(RemovalReason.DISCARDED);
        }
    }

    public boolean canHit(Entity potentialTarget)
    {
        return potentialTarget instanceof Player;
    }

    public static final EntityDataAccessor<Integer> ATTACK_TICKS = SynchedEntityData.defineId(BaseSubattackEntity.class, EntityDataSerializers.INT);
    public static final EntityDataAccessor<Float> ROTATION = SynchedEntityData.defineId(BaseSubattackEntity.class, EntityDataSerializers.FLOAT);

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        builder.define(ATTACK_TICKS, 0);
        builder.define(ROTATION, 0f);
    }

    @Override
    protected void readAdditionalSaveData(CompoundTag compoundTag) {
        //this.owner = getServer().getLevel(level().dimension()).getEntity(compoundTag.getUUID("OwnerUUID"));
    }

    @Override
    protected void addAdditionalSaveData(CompoundTag compoundTag) {
        //compoundTag.putUUID("OwnerUUID", owner.getUUID());
    }

    @Override
    public @Nullable Entity getOwner() {
        return owner;
    }
}
