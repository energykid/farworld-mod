package net.ennway.farworld.registries;

import com.mojang.serialization.MapCodec;
import net.ennway.farworld.Farworld;
import net.ennway.farworld.loot.BasicLootModifier;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.common.loot.IGlobalLootModifier;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

import java.util.function.Supplier;

public class ModLootModifiers {
    public static final DeferredRegister<MapCodec<? extends IGlobalLootModifier>> GLOBAL_LOOT_MODIFIERS_ALL =
        DeferredRegister.create(NeoForgeRegistries.Keys.GLOBAL_LOOT_MODIFIER_SERIALIZERS, Farworld.MOD_ID);

    public static final Supplier<MapCodec<BasicLootModifier>> BASIC_LOOT_MODIFIER =
            GLOBAL_LOOT_MODIFIERS_ALL.register("basic_loot_modifier", () -> BasicLootModifier.CODEC);
}
