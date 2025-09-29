package net.ennway.farworld.item.accessory;

import net.ennway.farworld.item.AccessoryItem;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;

public class GolemHeart extends AccessoryItem {

    public GolemHeart(Properties properties) {
        super(properties);
    }

    @Override
    public void onDamageEnemy(Player player, Entity enemy, ItemStack stack, LivingDamageEvent.Pre event) {
        if (!enemy.isNoGravity())
        {
            if (enemy.onGround())
            {
                enemy.setOnGround(false);
                enemy.setDeltaMovement(enemy.getDeltaMovement().multiply(0.2, 1.0, 0.2).add(new Vec3(0, 0.7, 0)));
            }
            else
            {
                enemy.setOnGround(true);
                enemy.fallDistance += 4;
                enemy.setDeltaMovement(enemy.getDeltaMovement().multiply(0.2, 1.0, 0.2).add(new Vec3(0, -1.5, 0)));
            }
        }
    }
}
