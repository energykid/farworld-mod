package net.ennway.farworld.utils;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.AABB;

import java.util.Comparator;
import java.util.List;

public class BehaviorUtils {
    public static Player getNearestPlayer(LivingEntity ent, double range)
    {
        List<Player> items = ent.level().getEntitiesOfClass(Player.class,
                new AABB(
                        ent.getX() - range, ent.getY() - range, ent.getZ() - range,
                        ent.getX() + range, ent.getY() + range, ent.getZ() + range
                ));

        items.sort(Comparator.comparingInt(c -> (int) c.distanceToSqr(ent)));

        if (!items.isEmpty())
            return items.getFirst();
        else
            return null;
    }
}
