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
        AABB range = new AABB(player.getX() - 2.0, player.getY() - 1.0, player.getZ() - 2.0, player.getX() + 2.0, player.getY() + 2.0, player.getZ() + 2.0);

        for (LivingEntity mob : player.level().getEntitiesOfClass(LivingEntity.class, range))
        {
            mob.hurt(mob.damageSources().playerAttack((Player)player), 4f);
        }
    }
}
