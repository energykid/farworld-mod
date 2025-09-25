package net.ennway.farworld.item;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;

public class AccessoryItem extends Item {


    public AccessoryItem(Properties properties) {
        super(properties);
    }

    public void onDamageEnemy(Entity player, Entity enemy, LivingDamageEvent.Pre event)
    {
    }

    public void onDamagedByEnemy(Entity enemy, Entity player, LivingDamageEvent.Pre event)
    {
    }
}
