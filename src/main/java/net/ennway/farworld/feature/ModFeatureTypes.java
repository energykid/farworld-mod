package net.ennway.farworld.feature;

import net.ennway.farworld.Farworld;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.PointedDripstoneConfiguration;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class ModFeatureTypes {

    public static final DeferredRegister<Feature<?>> FEATURES_ALL = DeferredRegister.create(Registries.FEATURE, Farworld.MOD_ID);

    public static final DeferredHolder<Feature<?>, Feature<NoneFeatureConfiguration>> FLOWSTONE_COLUMN = FEATURES_ALL.register("flowstone_column_feature", new Supplier<Feature<NoneFeatureConfiguration>>() {
        @Override
        public Feature<NoneFeatureConfiguration> get() {
            return new FlowstoneColumnFeature(NoneFeatureConfiguration.CODEC);
        }
    });

    public static final DeferredHolder<Feature<?>, Feature<NoneFeatureConfiguration>> FLOWSTONE_SPIKE = FEATURES_ALL.register("flowstone_spike", new Supplier<Feature<NoneFeatureConfiguration>>() {
        @Override
        public Feature<NoneFeatureConfiguration> get() {
            return new FlowstoneSpikeFeature(NoneFeatureConfiguration.CODEC);
        }
    });

    public static final DeferredHolder<Feature<?>, Feature<NoneFeatureConfiguration>> DUST_PILE = FEATURES_ALL.register("dust_pile", new Supplier<Feature<NoneFeatureConfiguration>>() {
        @Override
        public Feature<NoneFeatureConfiguration> get() {
            return new DustPileFeature(NoneFeatureConfiguration.CODEC);
        }
    });

    public static final DeferredHolder<Feature<?>, Feature<NoneFeatureConfiguration>> REDSTONE_PILLAR_FEATURE = FEATURES_ALL.register("redstone_pillar", new Supplier<Feature<NoneFeatureConfiguration>>() {
        @Override
        public Feature<NoneFeatureConfiguration> get() {
            return new RedstonePillarFeature(NoneFeatureConfiguration.CODEC);
        }
    });

    public static final DeferredHolder<Feature<?>, Feature<NoneFeatureConfiguration>> EMBEDDED_AMETHYST_GEODE = FEATURES_ALL.register("embedded_geode", new Supplier<Feature<NoneFeatureConfiguration>>() {
        @Override
        public Feature<NoneFeatureConfiguration> get() {
            return new EmbeddedGeodeFeature(NoneFeatureConfiguration.CODEC);
        }
    });

    public static final DeferredHolder<Feature<?>, Feature<PointedDripstoneConfiguration>> POINTED_AMETHYST = FEATURES_ALL.register("pointed_amethyst", new Supplier<Feature<PointedDripstoneConfiguration>>() {
        @Override
        public Feature<PointedDripstoneConfiguration> get() {
            return new PointedAmethystFeature(PointedDripstoneConfiguration.CODEC);
        }
    });
}
