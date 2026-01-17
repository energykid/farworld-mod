package net.ennway.farworld.item.accessory;

import net.ennway.farworld.entity.projectile.ApocalypseBreathProjectile;
import net.ennway.farworld.registries.ModAttachments;
import net.ennway.farworld.registries.ModEntities;
import net.ennway.farworld.registries.ModItems;
import net.ennway.farworld.registries.ModSounds;
import net.ennway.farworld.utils.AccessoryUtils;
import net.ennway.farworld.utils.ServerUtils;
import net.minecraft.commands.Commands;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientboundAddEntityPacket;
import net.minecraft.server.level.ServerEntity;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;
import net.neoforged.neoforge.event.tick.ServerTickEvent;

@EventBusSubscriber
public class ApocalypseEvents {
    @SubscribeEvent
    public static void breathe(PlayerTickEvent.Post evt) {
        evt.getEntity().setData(ModAttachments.APOCALYPSE_ABILITY, evt.getEntity().getData(ModAttachments.APOCALYPSE_ABILITY) - 1f);
        if (evt.getEntity().getData(ModAttachments.APOCALYPSE_ABILITY) == 5)
        {
            evt.getEntity().playSound(ModSounds.APOCALYPSE_COOLDOWN_OVER.get());
        }
    }
}
