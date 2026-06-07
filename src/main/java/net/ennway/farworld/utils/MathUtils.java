package net.ennway.farworld.utils;

import com.mojang.blaze3d.vertex.PoseStack;
import net.ennway.farworld.entity.custom.redstone_curiosity.RedstoneCuriosityEntity;
import net.ennway.farworld.utils.curve.EasingCurve;
import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.phys.Vec3;
import org.joml.Quaternionf;
import org.joml.Vector2f;
import org.joml.Vector3f;

public class MathUtils {

    public static Vector2f worldToScreen(Vector3f position, PoseStack viewPose)
    {
        Vector3f v = position.mulProject(Minecraft.getInstance().gameRenderer.getProjectionMatrix(Minecraft.getInstance().options.fov().get()));

        v = v.mulProject(viewPose.last().pose());

        return new Vector2f(v.x / v.z, v.y / v.z);
    }

    public static Vector2f worldToScreenBackup(Vector3f position, Camera camera)
    {
        Vector3f ps = position;

        Vec3 cPos = camera.getPosition();
        float fov = 1f;

        ps.sub((float)cPos.x, (float)cPos.y, (float)cPos.z);

        Quaternionf qu = camera.rotation();
        ps.rotate(qu.invert());

        Vector2f v = new Vector2f(ps.x * (70 * fov / 2), -ps.y * (70 * fov / 2));

        return v;
    }

    public static double randomDouble(RandomSource randomSource, double min, double max)
    {
        return min + (randomSource.nextDouble() * max);
    }

    public static int sign(int num)
    {
        return num < 0 ? -1 : 1;
    }

    public static Vector2f flatVec2FromRotation(double rotation)
    {
        return rotateVector2f(new Vector2f(1,0), rotation);
    }

    public static Vector2f rotateVector2f(Vector2f vec2f, double n)
    {
        float rx = (float)((vec2f.x * Math.cos(n)) - (vec2f.y * Math.sin(n)));
        float ry = (float)((vec2f.x * Math.sin(n)) + (vec2f.y * Math.cos(n)));
        return new Vector2f(rx, ry);
    }

    public static double entityLookAngle(Vec3 target) {
        double d0 = target.x;
        double d2 = target.z;
        return Mth.wrapDegrees((float)(Mth.atan2(d2, d0) * 180.0 / 3.1415927410125732) - 90.0F);
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
            return Mth.lerp(curve.invoke((time - start) / (end - start)), pointA, pointB);
        }
        else
        {
            return fallback;
        }
    }
}
