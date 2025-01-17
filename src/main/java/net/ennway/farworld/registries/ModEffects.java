package net.ennway.farworld.registries;

import net.ennway.farworld.Farworld;
import net.ennway.farworld.effect.ParalysisEffect;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.effect.MobEffect;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModEffects {
    public static final DeferredRegister<MobEffect> MOB_EFFECTS = DeferredRegister.create(
            BuiltInRegistries.MOB_EFFECT, Farworld.MOD_ID
    );

    public static final Holder<MobEffect> PARALYSIS = MOB_EFFECTS.register("paralysis",
            ParalysisEffect::new);
}
