package net.ennway.farworld.item;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.Item;

public class AccessoryItem extends Item {
    public AccessoryItem(Properties properties) {
        super(properties);
    }

    public void onDamageEnemy(Entity player, Entity enemy)
    {
    }

    public void onDamagedByEnemy(Entity enemy, Entity player)
    {
    }
}
