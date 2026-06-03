package net.ennway.farworld.item;

import net.ennway.farworld.registries.ModItems;
import net.ennway.farworld.utils.AccessoryUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.player.Player;
import net.neoforged.bus.api.Event;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.common.damagesource.DamageContainer;
import net.neoforged.neoforge.event.entity.EntityEvent;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;
import net.neoforged.neoforge.event.entity.living.LivingFallEvent;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;
import net.neoforged.neoforge.event.entity.player.PlayerFlyableFallEvent;
import net.neoforged.neoforge.event.tick.EntityTickEvent;

import java.util.Objects;

@EventBusSubscriber
public class SlimeBraceEvents {
    @SubscribeEvent
    public static void fallDmg(LivingIncomingDamageEvent evt)
    {
        if (evt.getEntity() instanceof Player plr)
        {
            if (AccessoryUtils.playerHasAccessory(plr, ModItems.SLIME_BRACE.get()))
            {
                if (evt.getSource().is(DamageTypes.FALL))
                {
                    if (evt.getAmount() <= 4)
                    {
                        evt.setCanceled(true);
                    }
                    evt.setAmount(evt.getAmount() - 4);
                }
            }
        }
    }
}
