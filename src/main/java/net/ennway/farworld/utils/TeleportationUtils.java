package net.ennway.farworld.utils;

import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.phys.Vec3;

import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;

public class TeleportationUtils {
    public static void TeleportPlayer(ServerPlayer player, ServerLevel world, Vec3 coords) {
        // check if the player is currently flying
        boolean flying = player.getAbilities().flying;

        // teleport!
        player.teleportTo(world, coords.x, coords.y, coords.z, player.getYRot(), player.getXRot());

        // Restore flying when teleporting trough dimensions
        if (flying) {
            player.getAbilities().flying = true;
            player.onUpdateAbilities();
        }

        // delay visual effects so the player can see it when switching dimensions
        Timer timer = new Timer();
        timer.schedule(
                new TimerTask() {
                    @Override
                    public void run() {
                        world.sendParticles(ParticleTypes.SNOWFLAKE, player.getX(), player.getY() , player.getZ(), 20, 0.0D, 1.0D, 0.0D, 0.01);
                        world.sendParticles(ParticleTypes.WHITE_SMOKE, player.getX(), player.getY(), player.getZ(), 15, 0.0D, 0.0D, 0.0D, 0.03);
                    }
                }, 100 // hopefully a good delay, ~ 2 ticks
        );
    }
}
