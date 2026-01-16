package net.ennway.farworld.item;

import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;
import net.neoforged.neoforge.event.entity.living.LivingEntityUseItemEvent;
import net.neoforged.neoforge.event.entity.living.LivingHealEvent;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent;
import net.neoforged.neoforge.event.tick.EntityTickEvent;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;
import net.neoforged.neoforge.event.tick.ServerTickEvent;

public class AccessoryItem extends Item {

    public AccessoryItem(Properties properties) {
        super(properties.stacksTo(1));
    }

    public void onDamageEnemy(Player player, Entity enemy, ItemStack stack, LivingDamageEvent.Pre event)
    {
    }

    public void postDamageEnemy(Player player, Entity enemy, ItemStack stack, LivingDamageEvent.Post event)
    {
    }

    public void onDamagedByEnemy(Entity enemy, Player player, ItemStack stack, LivingDamageEvent.Pre event)
    {
    }

    public void preTick(Player player, ItemStack stack, EntityTickEvent.Pre event)
    {
    }

    public void postTick(Player player, ItemStack stack, EntityTickEvent.Post event)
    {
    }

    public void preTickServer(Player player, ItemStack stack, ServerLevel level)
    {
    }

    public void postTickServer(Player player, ItemStack stack, ServerLevel level)
    {
    }

    public void onRightClickUseItem(Player player, ItemStack stack, PlayerInteractEvent event) {
    }

    public void onLeftClickUseItem(Player player, ItemStack stack, PlayerInteractEvent event)
    {
    }
}
