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

    @Override
    public boolean canUse() {

        return false;
    }

    @Override
    public boolean requiresUpdateEveryTick() {
        return true;
    }

    @Override
    public void tick() {
    }

    @Override
    public boolean canContinueToUse() {
        return true;
    }

    @Override
    public void stop() {
        this.target = null;
        super.stop();
    }

    @Override
    public void start() {
        this.mob.getNavigation().moveTo(this.wantedX, this.wantedY, this.wantedZ, 1f);
    }
}
