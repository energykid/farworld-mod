package net.ennway.farworld.utils;

import net.ennway.farworld.utils.curve.EasingCurve;
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

    public static int sign(int num)
    {
        return num < 0 ? -1 : 1;
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

    public static float key(float time, float start, float end, float pointA, float pointB, EasingCurve curve, float fallback) {
        if (time >= start && time <= end) {
            return Mth.lerp(pointA, pointB, curve.invoke((time - start) / (end - start)));
        }
        else
        {
            return fallback;
        }
    }
}
