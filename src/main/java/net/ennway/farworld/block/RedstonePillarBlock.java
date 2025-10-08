package net.ennway.farworld.block;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.DirectionalBlock;
import net.minecraft.world.level.block.ObserverBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.Property;
import org.jetbrains.annotations.Nullable;

public class RedstonePillarBlock extends DirectionalBlock {

    public static final MapCodec<RedstonePillarBlock> CODEC = simpleCodec(RedstonePillarBlock::new);

    public static StatePredicate predicate = new StatePredicate() {
        @Override
        public boolean test(BlockState blockState, BlockGetter blockGetter, BlockPos blockPos) {
            return blockState.getValue(FACING) == Direction.UP;
        }
    };

    public RedstonePillarBlock(Properties props) {
        super(props.emissiveRendering(predicate));
        this.registerDefaultState((BlockState)((BlockState)((BlockState)this.stateDefinition.any()).setValue(FACING, Direction.UP)));
    }

    @Override
    public @Nullable BlockState getStateForPlacement(BlockPlaceContext context) {
        return super.getStateForPlacement(context).setValue(FACING, context.getClickedFace());
    }

    @Override
    protected MapCodec<? extends RedstonePillarBlock> codec() {
        return CODEC;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(new Property[]{FACING});
    }
}
