package net.ennway.farworld.entity.base;

import net.ennway.farworld.entity.custom.GoliathEntity;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.level.Level;

public class DelayedAttackingMonster extends Monster {
    public static final EntityDataAccessor<Integer> ATTACK_TICKS = SynchedEntityData.defineId(DelayedAttackingMonster.class, EntityDataSerializers.INT);

    protected DelayedAttackingMonster(EntityType<? extends Monster> entityType, Level level) {
        super(entityType, level);
    }
}
