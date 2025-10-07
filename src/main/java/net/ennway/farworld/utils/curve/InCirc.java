package net.ennway.farworld.utils.curve;

import net.minecraft.util.Mth;

public class InCirc extends EasingCurve {
    @Override
    public float invoke(float input) {
        return -((float) Mth.sqrt(1 - input * input) - 1);
    }
}
