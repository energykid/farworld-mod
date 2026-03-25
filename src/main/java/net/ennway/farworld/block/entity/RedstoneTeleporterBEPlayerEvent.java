package net.ennway.farworld.block.entity;

import net.ennway.farworld.Farworld;
import net.ennway.farworld.registries.ModAttachments;
import net.ennway.farworld.registries.ModParticles;
import net.ennway.farworld.registries.ModSounds;
import net.ennway.farworld.utils.BehaviorUtils;
import net.ennway.farworld.utils.ServerUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Holder;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.phys.AABB;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;
import net.neoforged.neoforge.event.tick.EntityTickEvent;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;

import java.util.List;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;

@EventBusSubscriber(modid = Farworld.MOD_ID)
public class RedstoneTeleporterBEPlayerEvent {

    @SubscribeEvent
    public static void TeleportPlayer(PlayerTickEvent.Pre evt)
    {
        Player plr = evt.getEntity();

        BlockEntity ent = plr.level().getBlockEntity(plr.getOnPos());
        if (ent != null && plr.getData(ModAttachments.TP_COOLDOWN) <= 0)
        {
            if (ent instanceof RedstoneTeleporterBE teleporter)
            {
                if (teleporter.getLevel().hasNeighborSignal(teleporter.getBlockPos()))
                {
                    if (plr.level() instanceof ServerLevel srvl)
                    {
                        teleport(plr, srvl, teleporter);
                        plr.setData(ModAttachments.TP_COOLDOWN, 20);
                    }
                }
            }
        }
        else {
            plr.setData(ModAttachments.TP_COOLDOWN, plr.getData(ModAttachments.TP_COOLDOWN) - 1);
        }
    }

    public static BlockPos teleport(Player plr, ServerLevel lvl, RedstoneTeleporterBE ent) {
        BlockPos nearestTo = ent.findNearestTo(plr, ent.getBlockPos());

        if (nearestTo != null) {
            tpFX(nearestTo, plr, lvl, plr.getOnPos());
            plr.teleportTo(lvl,
                    nearestTo.getCenter().x, nearestTo.above().getBottomCenter().y, nearestTo.getCenter().z,
                    Set.of(), plr.getYRot(), plr.getXRot());
            plr.setData(ModAttachments.TP_COOLDOWN, 20);

            return nearestTo;
        }
        return null;
    }


    public static void tpFX(BlockPos nearestTo, Entity ent, ServerLevel lvl, BlockPos onPos) {
        Timer timer = new Timer();
        timer.schedule(
                new TimerTask() {
                    @Override
                    public void run() {
                        lvl.sendParticles(ModParticles.REDSTONE_TELEPORT_SHOCKWAVE.get(), nearestTo.getCenter().x, nearestTo.getY() + 1.1, nearestTo.getCenter().z, 1, 0, 0, 0, 0);
                        lvl.sendParticles(ModParticles.REDSTONE_TELEPORT_UP.get(), nearestTo.getCenter().x, nearestTo.getY() + 1, nearestTo.getCenter().z, 1, 0, 0, 0, 0);
                    }
                }, 100 // hopefully a good delay, ~ 2 ticks
        );

        lvl.sendParticles(ModParticles.REDSTONE_TELEPORT_SHOCKWAVE.get(), onPos.getCenter().x, onPos.getY() + 1.1, onPos.getCenter().z, 1, 0, 0, 0, 0);
        lvl.sendParticles(ModParticles.REDSTONE_TELEPORT_UP.get(), onPos.getCenter().x, onPos.getY() + 1, onPos.getCenter().z, 1, 0, 0, 0, 0);
    }
}
