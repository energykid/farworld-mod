package net.ennway.farworld.item.tool;

import net.ennway.farworld.entity.projectile.SludgeArrowProjectile;
import net.ennway.farworld.registries.ModEntities;
import net.minecraft.core.Direction;
import net.minecraft.core.Position;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.Arrow;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.ArrowItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.ProjectileItem;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.common.Tags;

import javax.annotation.Nullable;

public class SludgeArrow extends ArrowItem implements ProjectileItem {
    public SludgeArrow() {
        super(new Properties());
    }

    @Override
    public AbstractArrow createArrow(Level level, ItemStack ammo, LivingEntity shooter, @Nullable ItemStack weapon) {
        return new SludgeArrowProjectile(level, shooter, ammo.copyWithCount(1), weapon);
    }

    @Override
    public Projectile asProjectile(Level level, Position pos, ItemStack stack, Direction direction) {
        SludgeArrowProjectile arrow = new SludgeArrowProjectile(level, pos.x(), pos.y(), pos.z(), stack.copyWithCount(1), (ItemStack)null);
        arrow.pickup = AbstractArrow.Pickup.ALLOWED;
        return arrow;
    }
}
