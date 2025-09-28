package net.ennway.farworld.item.accessory;

import net.ennway.farworld.item.AccessoryItem;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;

public class BlazeLocket extends AccessoryItem {

    public BlazeLocket(Properties properties) {
        super(properties);
    }

    @Override
    public void onDamageEnemy(Player player, Entity enemy, ItemStack stack, LivingDamageEvent.Pre event) {
        Player pl = (Player)player;
        if (pl.swingTime == 0)
        {
            if (player.getWeaponItem().is(ItemTags.AXES))
            {
                enemy.setRemainingFireTicks((int)Mth.absMax(50, enemy.getRemainingFireTicks()));
            }
        }
    }
}
