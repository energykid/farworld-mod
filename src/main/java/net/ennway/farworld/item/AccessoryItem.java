package net.ennway.farworld.item;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;

public class AccessoryItem extends Item {

    public AccessoryItem(Properties properties) {
        super(properties.stacksTo(1));
    }

    public void onDamageEnemy(Entity player, Entity enemy, Level level, LivingDamageEvent.Pre event)
    {
    }

    public void onDamagedByEnemy(Entity enemy, Entity player, Level level, LivingDamageEvent.Pre event)
    {
    }
}
