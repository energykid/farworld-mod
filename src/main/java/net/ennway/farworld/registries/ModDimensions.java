package net.ennway.farworld.registries;

import net.ennway.farworld.Farworld;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.dimension.DimensionType;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModDimensions {
    public static final ResourceLocation bystone = ResourceLocation.fromNamespaceAndPath(Farworld.MOD_ID, "bystone");
    public static final ResourceLocation bystone_type = ResourceLocation.fromNamespaceAndPath(Farworld.MOD_ID, "bystone");
    public static final ResourceKey<Level> BYSTONE = ResourceKey.create(Registries.DIMENSION, bystone);
    public static final ResourceKey<DimensionType> BYSTONE_TYPE = ResourceKey.create(Registries.DIMENSION_TYPE, bystone);
}