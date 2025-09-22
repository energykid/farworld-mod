package net.ennway.farworld.event;

import net.ennway.farworld.Farworld;
import net.ennway.farworld.registries.ModSounds;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.level.BlockEvent;

@EventBusSubscriber(modid = Farworld.MOD_ID, bus = EventBusSubscriber.Bus.GAME)
public class BlockEvents {

    @SubscribeEvent
    public static void breakVines(BlockEvent.BreakEvent event)
    {
        event.getPlayer().playSound(ModSounds.SOUL_GOLEM_ACTIVATE.get());
    }
}