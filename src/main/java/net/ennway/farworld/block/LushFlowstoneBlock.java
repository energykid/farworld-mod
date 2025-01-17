package net.ennway.farworld.block;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.DirectionalBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.block.state.properties.Property;

import java.util.function.Function;

public class LushFlowstoneBlock extends DirectionalBlock {

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING);
    }

    public LushFlowstoneBlock(Properties p_52591_) {
        super(p_52591_);
    }

    @Override
    protected MapCodec<? extends DirectionalBlock> codec() {
        return RecordCodecBuilder.mapCodec((p_304392_) -> {
            return p_304392_.group(propertiesCodec()).apply(p_304392_, LushFlowstoneBlock::new);
        });
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        return (BlockState)this.defaultBlockState().setValue(FACING, context.getNearestLookingDirection().getOpposite());
    }
}