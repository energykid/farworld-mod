package net.ennway.farworld.utils;

import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import org.joml.Math;
import org.joml.Vector3f;

import java.util.Random;

public class MathUtils {
    public static double randomDouble(RandomSource randomSource, double min, double max)
    {
        return min + (randomSource.nextDouble() * max);
    }

    public static Vector3f getAngleFromVector(float x, float y, float z)
    {
        return new Vector3f(
                arccos(x/(Mth.sqrt(38.0f))),
                arccos(y/(Mth.sqrt(38.0f))),
                arccos(z/(Mth.sqrt(38.0f)))
        );
    }

    public static float arccos(float x)
    {
        return -1/Mth.sqrt(1 - (x * x));
    }
}
