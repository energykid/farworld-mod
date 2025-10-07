package net.ennway.farworld.feature;

import net.ennway.farworld.Farworld;
import net.ennway.farworld.structure.SurfacedStructures;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.BlockStateConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import net.minecraft.world.level.levelgen.structure.StructureType;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class ModFeatureTypes {

    public static final DeferredRegister<Feature<?>> FEATURES_ALL = DeferredRegister.create(Registries.FEATURE, Farworld.MOD_ID);

    public static final DeferredHolder<Feature<?>, Feature<NoneFeatureConfiguration>> LARGE_BLOCK_COLUMN = FEATURES_ALL.register("flowstone_column_feature", new Supplier<Feature<NoneFeatureConfiguration>>() {
        @Override
        public Feature<NoneFeatureConfiguration> get() {
            return new FlowstoneColumnFeature(NoneFeatureConfiguration.CODEC);
        }
    });


}
