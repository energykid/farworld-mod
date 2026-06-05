package net.ennway.farworld.entity.projectile;

import net.ennway.farworld.registries.ModEffects;
import net.ennway.farworld.registries.ModEntities;
import net.ennway.farworld.registries.ModItems;
import net.ennway.farworld.registries.ModParticles;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;

import javax.annotation.Nullable;

public class SludgeArrowProjectile extends AbstractArrow {
    public static SludgeArrowProjectile getBase(EntityType<? extends SludgeArrowProjectile> entityType, Level level) {
        return new SludgeArrowProjectile(entityType, level);
    }

    public SludgeArrowProjectile(EntityType<? extends SludgeArrowProjectile> entityType, Level level) {
        super(entityType, level);
    }

    public SludgeArrowProjectile(Level level, double x, double y, double z, ItemStack pickupItemStack, @Nullable ItemStack firedFromWeapon) {
        super(ModEntities.SLUDGE_ARROW.get(), x, y, z, level, pickupItemStack, firedFromWeapon);
    }

    public SludgeArrowProjectile(Level level, LivingEntity owner, ItemStack pickupItemStack, @Nullable ItemStack firedFromWeapon) {
        super(ModEntities.SLUDGE_ARROW.get(), owner, level, pickupItemStack, firedFromWeapon);
    }

    @Override
    protected void onHitEntity(EntityHitResult result) {
        super.onHitEntity(result);
        if (result.getEntity() instanceof Mob mob)
        {
            mob.addEffect(new MobEffectInstance(ModEffects.SLUDGED, 60));
        }
    }

    @Override
    public void tick() {
        super.tick();
        level().addParticle(ModParticles.SLUDGE_DROP.get(), position().x, position().y, position().z, getDeltaMovement().x, getDeltaMovement().y, getDeltaMovement().z);
    }

    @Override
    protected ItemStack getDefaultPickupItem() {
        return new ItemStack(ModItems.SLUDGE_ARROW.get());
    }
}
