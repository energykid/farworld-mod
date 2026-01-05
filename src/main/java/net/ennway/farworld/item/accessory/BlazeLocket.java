package net.ennway.farworld.item.accessory;

import net.ennway.farworld.entity.projectile.BlazeStanceProjectile;
import net.ennway.farworld.item.AccessoryItem;
import net.ennway.farworld.registries.ModAttachments;
import net.ennway.farworld.registries.ModEntities;
import net.ennway.farworld.registries.ModSounds;
import net.ennway.farworld.registries.ModTags;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.Mth;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;

public class BlazeLocket extends AccessoryItem {

    public BlazeLocket(Properties properties) {
        super(properties);
    }

    public boolean charging = false;

    @Override
    public void onRightClickUseItem(Player player, ItemStack stack, PlayerInteractEvent event) {
        if (!charging && player.getWeaponItem().is(ModTags.BLAZE_STANCEABLE_WEAPONS))
        {
            charging = true;
            player.playSound(ModSounds.BATTLE_STANCE.get());
        }
    }

    @Override
    public void onLeftClickUseItem(Player player, ItemStack stack, PlayerInteractEvent event) {
        charging = false;
    }

    @Override
    public void postDamageEnemy(Player player, Entity enemy, ItemStack stack, LivingDamageEvent.Post event) {

        if (charging)
        {
            BlazeStanceProjectile proj = new BlazeStanceProjectile(ModEntities.BLAZE_STANCE_SLASH.get(), player.level());
            proj.setPos(player.position().add(0, 1, 0));
            player.level().addFreshEntity(proj);
        }

        charging = false;
    }

    @Override
    public void preTick(Player player, ItemStack stack, PlayerTickEvent.Pre event) {
        if (!player.getWeaponItem().is(ModTags.BLAZE_STANCEABLE_WEAPONS))
        {
            charging = false;
        }
        else
        {
            if (charging)
            {
                player.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 2, 3, true, false));
            }
            player.setData(ModAttachments.BATTLE_STANCE.get(), charging);
        }
        player.setData(ModAttachments.BATTLE_STANCE.get(), charging);
    }
}
