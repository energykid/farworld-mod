package net.ennway.farworld.entity.goal;

import net.ennway.farworld.entity.base.DelayedAttackingMonster;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;

/// meant for use with DelayedAttackingMonster
public abstract class DelayedMeleeHurtGoal extends MeleeAttackGoal {
    public int attackDelay;
    public int attackLength;
    public boolean attacking = false;
    public double attackRange = 8;
    public double speed = 1.5;

    public DelayedMeleeHurtGoal(PathfinderMob entity, double speedModifier, boolean followingTargetEvenIfNotSeen) {
        super(entity, speedModifier, followingTargetEvenIfNotSeen);
        this.attackDelay = 12;
        this.attackLength = 40;
    }
    public DelayedMeleeHurtGoal(PathfinderMob entity, double speedModifier, boolean followingTargetEvenIfNotSeen, int delay, int length) {
        super(entity, speedModifier, followingTargetEvenIfNotSeen);
        this.attackDelay = delay;
        this.attackLength = length;
    }
    public DelayedMeleeHurtGoal(PathfinderMob entity, double speedModifier, boolean followingTargetEvenIfNotSeen, int delay, int length, double range) {
        super(entity, speedModifier, followingTargetEvenIfNotSeen);
        this.attackDelay = delay;
        this.attackLength = length;
        this.attackRange = range;
    }

    @Override
    public boolean canUse() {
        return true;
    }

    @Override
    public boolean canContinueToUse() {
        return true;
    }

    public boolean canAttack(PathfinderMob monster)
    {
        return true;
    }

    @Override
    public void tick() {
    }

    @Override
    protected void checkAndPerformAttack(LivingEntity target) {
    }
}
