package net.ennway.farworld.utils;


import net.ennway.farworld.entity.custom.redstone_curiosity.RedstoneCuriosityEntity;
import net.minecraft.client.Minecraft;

import java.util.Objects;

public class BossMusicHandling {

    public static void playMyMusic(String music)
    {
        if (Objects.equals(music, "")) {
            if (Minecraft.getInstance().getMusicManager().isPlayingMusic(RedstoneCuriosityEntity.BATTLE_THEME))
                Minecraft.getInstance().getMusicManager().stopPlaying(RedstoneCuriosityEntity.BATTLE_THEME);
        }

        if (Objects.equals(music, "redstone_curiosity")) {
            Minecraft.getInstance().getMusicManager().stopPlaying();
            Minecraft.getInstance().getMusicManager().startPlaying(RedstoneCuriosityEntity.BATTLE_THEME);
        }
    }
}