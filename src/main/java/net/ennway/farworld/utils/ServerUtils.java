package net.ennway.farworld.utils;

import net.minecraft.client.Minecraft;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.player.Player;

public class ServerUtils {
    public static MinecraftServer getServerFromPlayer(Player player)
    {
        if (player.getServer() != null)
        {
            return player.getServer();
        }
        return Minecraft.getInstance().getSingleplayerServer();
    }
    public static ServerLevel getServerLevelFromPlayer(Player player)
    {
        return getServerFromPlayer(player).getLevel(player.level().dimension());
    }
}
