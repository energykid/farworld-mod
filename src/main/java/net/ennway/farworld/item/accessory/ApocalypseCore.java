package net.ennway.farworld.item.accessory;

import net.ennway.farworld.item.AccessoryItem;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;

public class ApocalypseCore extends AccessoryItem {

    public ApocalypseCore(Properties properties) {
        super(properties);
    }

    @Override
    public void onDamageEnemy(Player player, Entity enemy, ItemStack stack, LivingDamageEvent.Pre event) {
        event.setNewDamage(event.getOriginalDamage() * 1.1f);
    }
}
