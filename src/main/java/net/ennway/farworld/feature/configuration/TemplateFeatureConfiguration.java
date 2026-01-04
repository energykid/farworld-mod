package net.ennway.farworld.feature.configuration;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;

public record TemplateFeatureConfiguration(String toPlace) implements FeatureConfiguration {
    public static final Codec<TemplateFeatureConfiguration> CODEC = RecordCodecBuilder.create((p_191331_) -> {
        return p_191331_.group(Codec.STRING.fieldOf("to_place").forGetter((p_161168_) -> {
            return p_161168_.toPlace;
        })).apply(p_191331_, TemplateFeatureConfiguration::new);
    });

    public TemplateFeatureConfiguration(String toPlace) {
        this.toPlace = toPlace;
    }

    public String toPlace() {
        return this.toPlace;
    }
}
