package net.ennway.farworld.feature.configuration;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;

import java.util.List;
import java.util.Optional;

public record BystoneColumnFeatureConfiguration(BlockState toPlace, List<Double> sizeBig, List<Double> sizeSmall, List<Double> middleRange, Optional<String> orientation) implements FeatureConfiguration {
    public static final Codec<BystoneColumnFeatureConfiguration> CODEC = RecordCodecBuilder.create((p_191331_) -> {
        return p_191331_.group(BlockState.CODEC.fieldOf("to_place").forGetter((p_161168_) -> {
                    return p_161168_.toPlace;
                }),
                Codec.DOUBLE.listOf().fieldOf("size_big").forGetter((p_161168_) -> {
                    return p_161168_.sizeBig;
                }),
                Codec.DOUBLE.listOf().fieldOf("size_small").forGetter((p_161168_) -> {
                    return p_161168_.sizeSmall;
                }),
                Codec.DOUBLE.listOf().fieldOf("middle_range").forGetter((p_161168_) -> {
                    return p_161168_.middleRange;
                }),
                Codec.STRING.optionalFieldOf("orientation").forGetter((p_161168_) -> {
                    return p_161168_.orientation;
                })).apply(p_191331_, BystoneColumnFeatureConfiguration::new);
    });

    public BystoneColumnFeatureConfiguration(BlockState toPlace, List<Double> sizeBig, List<Double> sizeSmall, List<Double> middleRange, Optional<String> orientation) {
        this.toPlace = toPlace;
        this.sizeBig = sizeBig;
        this.sizeSmall = sizeSmall;
        this.middleRange = middleRange;
        this.orientation = orientation;
    }

    public BlockState toPlace() {
        return this.toPlace;
    }
}
