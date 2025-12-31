package net.ennway.farworld.entity.base;

import net.ennway.farworld.entity.custom.GoliathEntity;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.level.Level;

public class DelayedAttackingMonster extends Monster {
    public int attackDelay;
    public int attackLength;
    public boolean attacking = false;
    public double attackRange = 8;
    public double speed = 1.5;

    public static final EntityDataAccessor<Integer> ATTACK_TICKS = SynchedEntityData.defineId(DelayedAttackingMonster.class, EntityDataSerializers.INT);

    public void performAttacking()
    {
        LivingEntity target = this.getTarget();
        if (target != null)
            this.getNavigation().moveTo(target, speed);
        if (canAttack(this) && target != null)
        {
            if (this.distanceToSqr(target) < this.attackRange) this.attacking = true;
            if (this.attacking)
            {
                this.getEntityData().set(DelayedAttackingMonster.ATTACK_TICKS, this.getEntityData().get(DelayedAttackingMonster.ATTACK_TICKS) + 1);

                if (this.getEntityData().get(DelayedAttackingMonster.ATTACK_TICKS) == 2)
                {
                    this.onBeginAttack(target);
                }

                if (this.getEntityData().get(DelayedAttackingMonster.ATTACK_TICKS) == this.attackDelay)
                {
                    this.onImpactAttack(target);
                    if (this.distanceToSqr(target) < this.attackRange)
                    {
                        this.doHurtTarget(target);
                    }
                }
                if (this.getEntityData().get(DelayedAttackingMonster.ATTACK_TICKS) > this.attackLength)
                {
                    this.getEntityData().set(DelayedAttackingMonster.ATTACK_TICKS, 0);
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
    
    protected DelayedAttackingMonster(EntityType<? extends Monster> entityType, Level level) {
        super(entityType, level);
    }

    public boolean isStartingAttack()
    {
        return getEntityData().get(ATTACK_TICKS) == 2;
    }
}
