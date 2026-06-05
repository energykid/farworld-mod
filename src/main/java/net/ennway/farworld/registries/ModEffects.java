package net.ennway.farworld.registries;

import net.ennway.farworld.Farworld;
import net.ennway.farworld.effect.BaseEffect;
import net.ennway.farworld.effect.ParalysisEffect;
import net.ennway.farworld.effect.SludgedEffect;
import net.ennway.farworld.effect.SparklingEffect;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.Objects;

public class ModEffects {
    public static final DeferredRegister<MobEffect> MOB_EFFECTS = DeferredRegister.create(
            BuiltInRegistries.MOB_EFFECT, Farworld.MOD_ID
    );

    public static final Holder<MobEffect> PARALYSIS = MOB_EFFECTS.register("paralysis", ParalysisEffect::new);

    public static final Holder<MobEffect> SPARKLING = MOB_EFFECTS.register("sparkling", SparklingEffect::new);

    public static final Holder<MobEffect> SLUDGED = MOB_EFFECTS.register("sludged", SludgedEffect::new);

    private static Holder<MobEffect> registerBasic(String name, MobEffect effect) {
        return Registry.registerForHolder(BuiltInRegistries.MOB_EFFECT, ResourceLocation.withDefaultNamespace(name), effect);
    }

}
