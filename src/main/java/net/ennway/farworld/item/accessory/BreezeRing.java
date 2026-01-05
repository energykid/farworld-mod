package net.ennway.farworld.item.accessory;

import net.ennway.farworld.item.AccessoryItem;
import net.ennway.farworld.registries.ModAttachments;
import net.ennway.farworld.registries.ModDataComponents;
import net.ennway.farworld.registries.ModSounds;
import net.ennway.farworld.registries.ModTags;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.Mth;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;
import net.neoforged.neoforge.event.entity.living.LivingEntityUseItemEvent;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;

import java.util.Objects;

public class BreezeRing extends AccessoryItem {

    public BreezeRing(Properties properties) {
        super(properties);
    }

    public boolean lunging = false;

    @Override
    public void postDamageEnemy(Player player, Entity enemy, ItemStack stack, LivingDamageEvent.Post event) {
        player.setData(ModAttachments.BREEZE_STANCE.get(), false);
        player.fallDistance = 0;
    }

    @Override
    public void onDamagedByEnemy(Entity enemy, Player player, ItemStack stack, LivingDamageEvent.Pre event) {
        player.setData(ModAttachments.BREEZE_STANCE.get(), false);
    }

    @Override
    public void preTick(Player player, ItemStack stack, PlayerTickEvent.Pre event) {
        if (player.onGround())
        {
            lunging = false;
            player.setData(ModAttachments.BREEZE_STANCE.get(), false);
        }
        if (!player.getWeaponItem().is(ModTags.STANCEABLE_WEAPONS))
        {
            player.setData(ModAttachments.BREEZE_STANCE.get(), false);
        }

        player.setData(ModAttachments.BREEZE_STANCE.get(), lunging);
    }

    @Override
    public void onRightClickUseItem(Player player, ItemStack stack, PlayerInteractEvent event) {
        if (player.getWeaponItem().is(ModTags.STANCEABLE_WEAPONS) && !lunging) {
            if (!player.getData(ModAttachments.BREEZE_STANCE.get())) {
                player.setOnGround(false);
                if (player.getServer() != null)
                    if (player.getServer().getPlayerList().getPlayer(player.getUUID()) != null)
                        player.getServer().getPlayerList().getPlayer(player.getUUID()).setData(ModAttachments.BREEZE_STANCE, true);
                player.setData(ModAttachments.BREEZE_STANCE.get(), true);
                player.playSound(ModSounds.BATTLE_STANCE.get());
                player.playSound(SoundEvents.BREEZE_SHOOT);
                player.setDeltaMovement(player.getDeltaMovement().multiply(2, 0, 2));
                player.setDeltaMovement(player.getDeltaMovement().x, 0.5, player.getDeltaMovement().z);
                player.fallDistance = 1;
                lunging = true;
            }
        }
    }

    @Override
    public void onLeftClickUseItem(Player player, ItemStack stack, PlayerInteractEvent event) {
        player.setData(ModAttachments.BREEZE_STANCE, false);
    }
}
