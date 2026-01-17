package net.ennway.farworld.entity.projectile;

import net.ennway.farworld.entity.custom.redstone_curiosity.RedstoneCuriosityBlastEntity;
import net.ennway.farworld.registries.ModEntities;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractHurtingProjectile;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class ApocalypseBreathProjectile extends AbstractHurtingProjectile {
    public ApocalypseBreathProjectile(EntityType<? extends AbstractHurtingProjectile> entityType, Level level) {
        super(entityType, level);
        this.noPhysics = false;
    }

    public int frame = 0;
    public float scale = 0f;

    @Override
    public boolean isOnFire() {
        return false;
    }

    @Override
    protected boolean canHitEntity(@NotNull Entity target) {
        if (target instanceof OwnableEntity ownable)
        {
            if (ownable.getOwner() instanceof Player) return false;
        }
        return !(target instanceof Player);
    }

    @Override
    protected @Nullable ParticleOptions getTrailParticle() {
        return null;
    }

    int timer = 0;

    @Override
    public void tick() {
        setDeltaMovement(getDeltaMovement().multiply(0.9f, 0.85f, 0.9f));
        move(MoverType.SELF, getDeltaMovement());
        this.frame++;
        if (this.frame >= 14) {
            this.discard();
        }
        timer++;
        if (timer % 2 == 0)
        {
            AABB entityAABB = new AABB(position().x - 0.75, position().y - 0.75, position().z - 0.75, position().x + 0.75, position().y + 0.75, position().z + 0.75);
            for (LivingEntity i : level().getEntitiesOfClass(LivingEntity.class, entityAABB))
            {
                if (this.canHitEntity(i) && this.getOwner() != null)
                {
                    i.invulnerableTime = 0;
                    i.hurt(getOwner().damageSources().magic(), 2.5f);
                }
            }
        }
    }
}
