package net.ennway.farworld.utils.curve;

import net.minecraft.util.Mth;

public class OutCirc extends EasingCurve {
    @Override
    public float invoke(float input) {
        float input2 = 1 - input;
        float inCirc = -((float) Mth.sqrt(1 - input2 * input2) - 1);

        return 1 - inCirc;
    }
}
