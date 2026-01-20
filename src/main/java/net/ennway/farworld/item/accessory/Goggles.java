package net.ennway.farworld.item.accessory;

import net.ennway.farworld.Farworld;
import net.ennway.farworld.entity.projectile.BlazeStanceProjectile;
import net.ennway.farworld.item.AccessoryItem;
import net.ennway.farworld.registries.ModAttachments;
import net.ennway.farworld.registries.ModEntities;
import net.ennway.farworld.registries.ModSounds;
import net.ennway.farworld.registries.ModTags;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.Event;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent;
import net.neoforged.neoforge.event.tick.EntityTickEvent;

public class Goggles extends AccessoryItem {

    public Goggles(Properties properties) {
        super(properties);
    }
}
