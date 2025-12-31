//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package net.ennway.farworld.entity.goal;

import net.ennway.farworld.entity.custom.AmethystConstructEntity;
import net.ennway.farworld.entity.custom.GoliathEntity;
import net.ennway.farworld.entity.custom.SoulGolemEntity;
import net.ennway.farworld.registries.ModTags;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.phys.AABB;

import javax.annotation.Nullable;
import java.util.EnumSet;
import java.util.List;

public class AmethystConstructFindItemGoal extends Goal {
    private final PathfinderMob mob;
    @Nullable
    private ItemEntity target;
    private double wantedX;
    private double wantedY;
    private double wantedZ;
    private final float within = 18;

    public AmethystConstructFindItemGoal(PathfinderMob mob) {
        this.mob = mob;
        this.setFlags(EnumSet.of(Flag.MOVE));
    }

    AmethystConstructEntity ent()
    {
        return (AmethystConstructEntity)this.mob;
    }
    boolean itemSlotFree()
    {
        return ent().getEntityData().get(AmethystConstructEntity.ITEM_GRINDING) == ItemStack.EMPTY;
    }

    @Override
    public boolean canUse() {

        List<ItemEntity> items = this.mob.level().getEntitiesOfClass(ItemEntity.class,
                new AABB(
                        this.mob.getX() - 12, this.mob.getY() - 12, this.mob.getZ() - 12,
                        this.mob.getX() + 12, this.mob.getY() + 12, this.mob.getZ() + 12
                ));

        items.sort((c1, c2) -> (int)c1.distanceToSqr(this.mob) - (int)c2.distanceToSqr(this.mob));

        if (!items.isEmpty() && itemSlotFree())
        {
            this.target = items.getFirst();

            if (this.target != null)
            {
                this.wantedX = this.target.getX();
                this.wantedY = this.target.getY();
                this.wantedZ = this.target.getZ();
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean requiresUpdateEveryTick() {
        return true;
    }

    @Override
    public void tick() {
        if (this.target != null && itemSlotFree())
        {
            if (this.mob.distanceToSqr(this.target) < 1f)
            {
                this.mob.getEntityData().set(AmethystConstructEntity.ITEM_GRINDING, target.getItem());
                this.target.remove(Entity.RemovalReason.DISCARDED);
                this.stop();
            }
        }
    }

    @Override
    public boolean canContinueToUse() {
        return true;
    }

    @Override
    public void stop() {
        this.mob.goalSelector.removeGoal(this);
        super.stop();
        this.target = null;
    }

    @Override
    public void start() {
        this.mob.getNavigation().moveTo(this.wantedX, this.wantedY, this.wantedZ, 1f);
    }
}
