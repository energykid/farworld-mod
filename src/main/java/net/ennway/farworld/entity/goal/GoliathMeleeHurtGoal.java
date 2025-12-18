package net.ennway.farworld.entity.goal;

import net.ennway.farworld.entity.custom.GoliathEntity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;

public class GoliathMeleeHurtGoal extends MeleeAttackGoal {
    private int raiseArmTicks;

    public GoliathMeleeHurtGoal(GoliathEntity spider, double speedModifier, boolean followingTargetEvenIfNotSeen) {
        super(spider, speedModifier, followingTargetEvenIfNotSeen);
    }

    @Override
    public boolean canUse() {
        if (this.mob instanceof GoliathEntity goliath) {
            return !goliath.isTame() && super.canUse();
        }
        return false;
    }

    public void tick() {
        super.tick();
        if (this.mob.getEntityData().get(GoliathEntity.ATTACK_TICKS) == 12) {
            this.mob.setAggressive(true);
        }
        else {
            this.mob.setAggressive(false);
        }
    }

    @Override
    protected void checkAndPerformAttack(LivingEntity target) {
        if (this.mob.isWithinMeleeAttackRange(target))
        {
            if (this.mob.getEntityData().get(GoliathEntity.ATTACK_TICKS) == 12)
            {
                this.mob.doHurtTarget(target);
            }
            if (this.mob.getEntityData().get(GoliathEntity.ATTACK_TICKS) > 40)
            {
                this.mob.getEntityData().set(GoliathEntity.ATTACK_TICKS, 0);
            }
        }
    }
}
