package net.ennway.farworld.entity.goal;

import net.ennway.farworld.entity.custom.GoliathEntity;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.player.Player;

public class GoliathMeleeHurtGoal extends MeleeAttackGoal {
    private int raiseArmTicks;

    public GoliathMeleeHurtGoal(GoliathEntity spider, double speedModifier, boolean followingTargetEvenIfNotSeen) {
        super(spider, speedModifier, followingTargetEvenIfNotSeen);
    }

    @Override
    public boolean canUse() {
        if (this.mob instanceof GoliathEntity goliath) {
            boolean b = true;
            if (goliath.isTame()) b = false;
            if (goliath.getTarget() instanceof Player plr)
            {
                if (goliath.isFood(plr.getItemInHand(InteractionHand.MAIN_HAND))) b = false;
                if (goliath.isFood(plr.getItemInHand(InteractionHand.OFF_HAND))) b = false;
            }
            return b && super.canUse();
        }
        return false;
    }

    @Override
    public boolean canContinueToUse() {
        return this.canUse();
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
