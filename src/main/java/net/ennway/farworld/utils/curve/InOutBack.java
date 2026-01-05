package net.ennway.farworld.utils.curve;

import net.minecraft.util.Mth;

public class InOutBack extends EasingCurve {
    @Override
    public float invoke(float x) {
        double c1 = 1.70158;
        double c2 = c1 * 1.525;

        return (float)(x < 0.5
                ? (Math.pow(2 * x, 2) * ((c2 + 1) * 2 * x - c2)) / 2
                : (Math.pow(2 * x - 2, 2) * ((c2 + 1) * (x * 2 - 2) + c2) + 2) / 2);
    }
}
