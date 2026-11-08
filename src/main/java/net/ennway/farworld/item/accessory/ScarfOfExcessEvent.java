package net.ennway.farworld.item.accessory;

import net.ennway.farworld.registries.ModItems;
import net.ennway.farworld.utils.AccessoryUtils;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.player.PlayerXpEvent;

@EventBusSubscriber
public class ScarfOfExcessEvent {
    @SubscribeEvent
    public static void extraExp(PlayerXpEvent.PickupXp evt)
    {
        if (AccessoryUtils.playerHasAccessory(evt.getEntity(), ModItems.SCARF_OF_EXCESS.asItem()))
        {
            evt.getEntity().giveExperiencePoints(evt.getOrb().value / 4);
        }
    }
}
