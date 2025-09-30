package net.ennway.farworld.utils;

import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import org.joml.Quaternionf;
import org.joml.Vector3f;

public class QuaternionUtils {
    public static Quaternionf flatQuaternion()
    {
        Quaternionf quaternionf = new Quaternionf();
        quaternionf.rotateX((float)Math.toRadians(90.0));
        return quaternionf;
    }
}
