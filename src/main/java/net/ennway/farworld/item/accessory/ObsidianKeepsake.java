package net.ennway.farworld.item.accessory;

import net.ennway.farworld.item.AccessoryItem;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

public class ObsidianKeepsake extends AccessoryItem {

    public ObsidianKeepsake(Properties properties) {
        super(properties);
    }

    @Override
    public void onDamagedByEnemy(Entity enemy, Entity player) {
        double distance = 3.0;

        AABB range = new AABB(player.getX() - distance, player.getY() - distance, player.getZ() - distance, player.getX() + distance, player.getY() + distance, player.getZ() + distance);

        for (LivingEntity mob : player.level().getEntitiesOfClass(LivingEntity.class, range))
        {
            if (!mob.is(player) && mob.canAttack((LivingEntity)player))
                mob.hurt(mob.damageSources().playerAttack((Player)player), 4f);
        }
    }
}
