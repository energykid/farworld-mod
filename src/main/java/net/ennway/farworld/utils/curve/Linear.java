package net.ennway.farworld.utils.curve;

import net.minecraft.util.Mth;

public class Linear extends EasingCurve {
    @Override
    public float invoke(float input) {
        return input;
    }
}
