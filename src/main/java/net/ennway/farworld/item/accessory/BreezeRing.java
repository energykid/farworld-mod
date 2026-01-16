package net.ennway.farworld.item.accessory;

import net.ennway.farworld.item.AccessoryItem;
import net.ennway.farworld.registries.*;
import net.ennway.farworld.utils.MathUtils;
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
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;
import net.neoforged.neoforge.event.entity.living.LivingEntityUseItemEvent;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent;
import net.neoforged.neoforge.event.tick.EntityTickEvent;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;

import java.util.Objects;

public class BreezeRing extends AccessoryItem {

    public BreezeRing(Properties properties) {
        super(properties);
    }

    public boolean lunging = false;

    @Override
    public void onDamageEnemy(Player player, Entity enemy, ItemStack stack, LivingDamageEvent.Pre event) {
        if (lunging)
        {
            if (!stack.is(Items.MACE))
            {
                float fD = player.fallDistance;
                player.playSound(SoundEvents.BREEZE_CHARGE);
                event.setNewDamage(event.getOriginalDamage() + fD);
                player.fallDistance = 0;
            }
            lunging = false;
        }
    }

    @Override
    public void onDamagedByEnemy(Entity enemy, Player player, ItemStack stack, LivingDamageEvent.Pre event) {
        lunging = false;
    }

    @Override
    public void preTick(Player player, ItemStack stack, EntityTickEvent.Pre event) {
        if (player.getWeaponItem().is(ModTags.BREEZE_STANCEABLE_WEAPONS))
        {
            if (player.onGround() || player.isSwimming())
            {
                lunging = false;
            }

            player.setData(ModAttachments.BATTLE_STANCE.get(), lunging);
        }

        if (!player.getWeaponItem().is(ModTags.BLAZE_STANCEABLE_WEAPONS) && !player.getWeaponItem().is(ModTags.BREEZE_STANCEABLE_WEAPONS))
        {
            player.setData(ModAttachments.BATTLE_STANCE.get(), false);
            lunging = false;
        }

        if (!player.getWeaponItem().is(ModTags.BREEZE_STANCEABLE_WEAPONS) && lunging)
        {
            player.setData(ModAttachments.BATTLE_STANCE.get(), false);
            lunging = false;
        }
    }

    public void runStuff(Player player)
    {
        if (player.getWeaponItem().is(ModTags.BREEZE_STANCEABLE_WEAPONS) && !lunging) {
            if (!player.getData(ModAttachments.BATTLE_STANCE.get())) {
                player.setOnGround(false);
                if (player.getServer() != null)
                    if (player.getServer().getPlayerList().getPlayer(player.getUUID()) != null)
                        player.getServer().getPlayerList().getPlayer(player.getUUID()).setData(ModAttachments.BATTLE_STANCE, true);
                player.playSound(ModSounds.BATTLE_STANCE.get());
                player.playSound(SoundEvents.WIND_CHARGE_BURST.value(), 0.4f, 1);
                player.setDeltaMovement(player.getDeltaMovement().multiply(2, 0, 2));
                player.setDeltaMovement(player.getDeltaMovement().x, 0.5, player.getDeltaMovement().z);
                player.level().addParticle(ModParticles.BREEZE_STANCE_BURST.get(), player.getX(), player.getY() + 0.05, player.getZ(), 0, 0, 0);
                for (int i = 0; i < 7; i++) {
                    double spd = MathUtils.randomDouble(player.getRandom(), 0.5f, 1f);
                    player.level().addParticle(ModParticles.BREEZE_STANCE_PUFF.get(), player.getX() + MathUtils.randomDouble(player.getRandom(), 0, 2) - 1, player.getY(), player.getZ() + MathUtils.randomDouble(player.getRandom(), 0, 2) - 1, player.getDeltaMovement().x * spd, player.getDeltaMovement().y * spd, player.getDeltaMovement().z * spd);
                }
                player.fallDistance = 1;
                lunging = true;
            }
        }
    }

    @Override
    public void onRightClickUseItem(Player player, ItemStack stack, PlayerInteractEvent event) {
        if (!player.isSwimming())
        {
            if (event instanceof PlayerInteractEvent.RightClickBlock bl)
            {
                if (bl.getUseItem().isTrue()) runStuff(player);
            }
            else runStuff(player);
        }
    }

    @Override
    public void onLeftClickUseItem(Player player, ItemStack stack, PlayerInteractEvent event) {
        if (event instanceof PlayerInteractEvent.LeftClickEmpty)
            lunging = false;
    }
}
