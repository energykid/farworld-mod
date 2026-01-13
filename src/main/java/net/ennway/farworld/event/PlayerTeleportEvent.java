package net.ennway.farworld.event;

import net.ennway.farworld.Farworld;
import net.ennway.farworld.registries.ModAttachments;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.tick.EntityTickEvent;

@EventBusSubscriber(modid = Farworld.MOD_ID)
public class PlayerTeleportEvent {
    @SubscribeEvent
    public static void perTick(EntityTickEvent.Post evt)
    {
        if (evt.getEntity() instanceof Player plr)
        {
            BlockPos pos = plr.getData(ModAttachments.TP_LOCATION);
            if (!pos.equals(new BlockPos(0, 500, 0)))
            {
                plr.moveTo(pos.getCenter().x, pos.above().getBottomCenter().y, pos.getCenter().z);
                plr.setData(ModAttachments.TP_LOCATION, new BlockPos(0, 500, 0));
            }
        }
    }
}
