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

    public void tick() {
        super.tick();
        LivingEntity target = this.mob.getTarget();
        if (target != null)
        {
            if (this.mob.distanceToSqr(target) < this.attackRange) this.attacking = true;
            if (this.attacking)
            {
                this.mob.getEntityData().set(DelayedAttackingMonster.ATTACK_TICKS, this.mob.getEntityData().get(DelayedAttackingMonster.ATTACK_TICKS) + 1);

                if (this.mob.getEntityData().get(DelayedAttackingMonster.ATTACK_TICKS) == 1)
                {
                    onBeginAttack(target);
                }

                if (this.mob.getEntityData().get(DelayedAttackingMonster.ATTACK_TICKS) == this.attackDelay)
                {
                    onImpactAttack(target);
                    if (this.mob.distanceToSqr(target) < this.attackRange)
                    {
                        this.mob.doHurtTarget(target);
                    }
                }
                if (this.mob.getEntityData().get(DelayedAttackingMonster.ATTACK_TICKS) > this.attackLength)
                {
                    this.mob.getEntityData().set(DelayedAttackingMonster.ATTACK_TICKS, 0);
                    this.attacking = false;
                }
            }
        }
    }

    public void onBeginAttack(LivingEntity target)
    {

    }

    public void onImpactAttack(LivingEntity target)
    {

    }

    @Override
    protected void checkAndPerformAttack(LivingEntity target) {
    }
}
