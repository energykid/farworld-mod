//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package net.ennway.farworld.entity.goal;

import java.util.Collection;
import java.util.EnumSet;
import java.util.List;
import javax.annotation.Nullable;

import net.ennway.farworld.entity.custom.SoulGolemEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.ai.util.DefaultRandomPos;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.Items;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

public class FindFuelGoal extends Goal {
    private final PathfinderMob mob;
    @Nullable
    private ItemEntity target;
    private double wantedX;
    private double wantedY;
    private double wantedZ;
    private final float within = 18;

    public FindFuelGoal(PathfinderMob mob) {
        this.mob = mob;
        this.setFlags(EnumSet.of(Flag.MOVE));
    }

    public boolean canUse() {

        List<ItemEntity> items = this.mob.level().getEntitiesOfClass(ItemEntity.class,
                new AABB(
                        this.mob.getX() - 12, this.mob.getY() - 12, this.mob.getZ() - 12,
                        this.mob.getX() + 12, this.mob.getY() + 12, this.mob.getZ() + 12
                ));

        if (!items.isEmpty())
            items.removeIf(item -> !item.getItem().is(Items.COAL));

        items.sort((c1, c2) -> (int)c1.distanceToSqr(this.mob) - (int)c2.distanceToSqr(this.mob));

        if (!items.isEmpty())
        {
            this.target = items.getFirst();

            if (this.target == null) {
                return false;
            } else {
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
        if (this.target != null)
        {
            if (this.mob.distanceToSqr(this.target) < 1f)
            {
                ((SoulGolemEntity)this.mob).fuelGolem(200);
                this.target.remove(Entity.RemovalReason.DISCARDED);
                this.stop();
            }
        }
    }

    public boolean canContinueToUse() {
        return this.target != null;
    }

    public void stop() {
        this.target = null;
    }

    public void start() {
        this.mob.getNavigation().moveTo(this.wantedX, this.wantedY, this.wantedZ, 1f);
    }
}
