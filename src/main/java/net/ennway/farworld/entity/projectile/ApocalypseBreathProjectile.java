package net.ennway.farworld.entity.projectile;

import net.ennway.farworld.entity.custom.redstone_curiosity.RedstoneCuriosityBlastEntity;
import net.ennway.farworld.registries.ModEntities;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.OwnableEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractHurtingProjectile;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import org.jetbrains.annotations.Nullable;

public class ApocalypseBreathProjectile extends AbstractHurtingProjectile {
    public ApocalypseBreathProjectile(EntityType<? extends AbstractHurtingProjectile> entityType, Level level) {
        super(entityType, level);
    }

    public int frame = 0;
    public float scale = 0f;

    @Override
    public boolean isOnFire() {
        return false;
    }

    @Override
    protected void onHitEntity(EntityHitResult result) {
        if (getOwner() != null)
            result.getEntity().hurt(getOwner().damageSources().mobProjectile(this, (LivingEntity)getOwner()), 20);
    }

    @Override
    protected boolean canHitEntity(Entity target) {
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

    @Override
    public void tick() {
        super.tick();
        setDeltaMovement(getDeltaMovement().multiply(0.9f, 0.9f, 0.9f));
        this.frame++;
        if (this.frame >= 14)
        {
            this.remove(RemovalReason.DISCARDED);
        }
    }
}
