package net.ennway.farworld.utils;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import org.joml.Vector3f;

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

    public static Vec3 groundedVec3(Level lvl, Vector3f pos)
    {
        double yy = pos.y;

        AABB box = new AABB(pos.x - 0.2, pos.y - 0.2, pos.z - 0.2, pos.x + 0.2, pos.y, pos.z + 0.2);
        for (int i = 0; i < 20; i++)
        {
            if (lvl.noCollision(box))
            {
                box.move(0, -0.25, 0);
                yy -= 0.25;
            }
            else break;
        }

        return new Vec3(pos.x, yy, pos.z);
    }

    public static Vec3 groundedVec3(Level lvl, Vector3f pos, int tries)
    {
        double yy = pos.y;

        AABB box = new AABB(pos.x - 0.2, pos.y - 0.2, pos.z - 0.2, pos.x + 0.2, pos.y, pos.z + 0.2);
        for (int i = 0; i < tries; i++)
        {
            if (lvl.noCollision(box))
            {
                box.move(0, -0.25, 0);
                yy -= 0.25;
            }
            else break;
        }

        return new Vec3(pos.x, yy, pos.z);
    }

    public static void groundEntity(Entity entity)
    {
        int tries = 100;
        while (!entity.level().noCollision(entity))
        {
            entity.moveTo(entity.getX(), entity.getY() + 1, entity.getZ());
            tries--;
            if (tries < 0) break;
        }
        while (entity.level().noCollision(entity))
        {
            entity.moveTo(entity.getX(), entity.getY() - 1, entity.getZ());
            tries--;
            if (tries < 0) break;
        }
        entity.moveTo(entity.getX(), entity.getY() + 1, entity.getZ());
    }
}
