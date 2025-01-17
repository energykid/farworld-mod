package net.ennway.farworld.block;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RotatedPillarBlock;
import org.jetbrains.annotations.NotNull;

import java.util.function.Function;

public class NetherIronOreBlock extends RotatedPillarBlock {
    public NetherIronOreBlock(Properties p_52591_) {
        super(p_52591_);
    }

    public static <B extends Block> MapCodec<B> simpleCodec(@NotNull Function<Properties, B> factory) {
    return RecordCodecBuilder.mapCodec((p_304392_) -> {
        return p_304392_.group(propertiesCodec()).apply(p_304392_, factory);
    });
    }
}