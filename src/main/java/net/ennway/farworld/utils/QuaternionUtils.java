package net.ennway.farworld.utils;

import com.mojang.math.Axis;
import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.core.Direction;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.Vec3;
import org.joml.Quaternionf;
import org.joml.Vector3f;

public class QuaternionUtils {
    public static Quaternionf flatQuaternion()
    {
        return new Quaternionf().rotateX((float)Math.toRadians(90.0));
    }

    public static Quaternionf upQuaternion(Camera camera)
    {
        return Axis.YP.rotationDegrees(-camera.getYRot());
    }

    public static Quaternionf orientedQuaternion(Camera camera)
    {
        Quaternionf quaternionf = Axis.YP.rotationDegrees(-camera.getYRot());
        quaternionf.mul(Axis.XP.rotationDegrees(camera.getXRot()));
        quaternionf.mul(Axis.ZP.rotationDegrees(-camera.getXRot()));
        return quaternionf;
    }

    public static Quaternionf orientedQuaternion(Vec3 v3)
    {
        Vec3 d = v3.normalize();

        float yaw = (float)Math.atan2(d.z, d.x);

        Quaternionf quaternionf = Axis.YP.rotation(-yaw + (float)Math.toRadians(180));
        quaternionf = quaternionf.mul(Axis.ZP.rotationDegrees((float)(d.y * 60f)));
        return quaternionf;
    }

    public static Quaternionf orientedQuaternion(Entity ent)
    {
        Quaternionf quaternionf = Axis.YP.rotationDegrees(-ent.getYHeadRot());
        quaternionf.mul(Axis.XP.rotationDegrees(ent.getXRot()));
        return quaternionf;
    }
}
