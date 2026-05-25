package net.ennway.farworld.registries;

import net.ennway.farworld.Farworld;
import net.ennway.farworld.feature.*;
import net.ennway.farworld.feature.configuration.TemplateFeatureConfiguration;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.dimension.DimensionType;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class ModDimensions {
    public static final ResourceLocation bystone = ResourceLocation.fromNamespaceAndPath(Farworld.MOD_ID, "bystone");
    public static final ResourceLocation bystone_type = ResourceLocation.fromNamespaceAndPath(Farworld.MOD_ID, "bystone");
    public static final ResourceKey<Level> BYSTONE = ResourceKey.create(Registries.DIMENSION, bystone);
    public static final ResourceKey<DimensionType> BYSTONE_TYPE = ResourceKey.create(Registries.DIMENSION_TYPE, bystone);

}