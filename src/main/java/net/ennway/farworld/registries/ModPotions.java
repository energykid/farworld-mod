package net.ennway.farworld.registries;

import net.ennway.farworld.Farworld;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.item.alchemy.Potion;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModPotions {
    public static final DeferredRegister<Potion> POTIONS = DeferredRegister.create(BuiltInRegistries.POTION, Farworld.MOD_ID);

    public static final Holder<Potion> PARALYSIS = POTIONS.register("paralysis_potion",
        () -> new Potion(new MobEffectInstance(ModEffects.PARALYSIS, 60)));
}
