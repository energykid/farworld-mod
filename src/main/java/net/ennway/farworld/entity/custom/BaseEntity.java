package net.ennway.farworld.entity.custom;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.level.Level;

public class BaseEntity extends Mob {
    protected BaseEntity(EntityType<? extends Mob> entityType, Level level) {
        super(entityType, level);
    }
}
