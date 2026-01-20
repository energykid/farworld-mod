package net.ennway.farworld.item.accessory;

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
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent;
import net.neoforged.neoforge.event.tick.EntityTickEvent;

public class Goggles extends AccessoryItem {

    public Goggles(Properties properties) {
        super(properties);
    }

    @Override
    public void preTick(Player player, ItemStack stack, EntityTickEvent.Pre event) {
        //player.addEffect(new MobEffectInstance(MobEffects.NIGHT_VISION, 2, 0, true, false));
    }
}
