package net.ennway.farworld.feature;

import net.ennway.farworld.Farworld;
import net.ennway.farworld.feature.configuration.TemplateFeatureConfiguration;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class ModFeatureTypes {

    public static final DeferredRegister<Feature<?>> FEATURES_ALL = DeferredRegister.create(Registries.FEATURE, Farworld.MOD_ID);

    public static final DeferredHolder<Feature<?>, Feature<NoneFeatureConfiguration>> BASALT_COLUMN = FEATURES_ALL.register("basalt_column", new Supplier<Feature<NoneFeatureConfiguration>>() {
        @Override
        public Feature<NoneFeatureConfiguration> get() {
            return new BasaltColumnFeature(NoneFeatureConfiguration.CODEC);
        }
    });

    public static final DeferredHolder<Feature<?>, Feature<NoneFeatureConfiguration>> MILK_BERRY_PATCH = FEATURES_ALL.register("milk_berry_patch", new Supplier<Feature<NoneFeatureConfiguration>>() {
        @Override
        public Feature<NoneFeatureConfiguration> get() {
            return new MilkBerryPatchFeature(NoneFeatureConfiguration.CODEC);
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

    public static final DeferredHolder<Feature<?>, Feature<NoneFeatureConfiguration>> EMBEDDED_AMETHYST_GEODE = FEATURES_ALL.register("embedded_geode", new Supplier<Feature<NoneFeatureConfiguration>>() {
        @Override
        public Feature<NoneFeatureConfiguration> get() {
            return new EmbeddedGeodeFeature(NoneFeatureConfiguration.CODEC);
        }
    });

    public static final DeferredHolder<Feature<?>, Feature<NoneFeatureConfiguration>> POINTED_AMETHYST = FEATURES_ALL.register("pointed_amethyst", new Supplier<Feature<NoneFeatureConfiguration>>() {
        @Override
        public Feature<NoneFeatureConfiguration> get() {
            return new PointedAmethystFeature(NoneFeatureConfiguration.CODEC);
        }
    });

    public static final DeferredHolder<Feature<?>, Feature<TemplateFeatureConfiguration>> SIMPLE_TEMPLATE_FEATURE = FEATURES_ALL.register("simple_template", new Supplier<Feature<TemplateFeatureConfiguration>>() {
        @Override
        public Feature<TemplateFeatureConfiguration> get() {
            return new SimpleTemplateFeature(TemplateFeatureConfiguration.CODEC);
        }
    });
}
