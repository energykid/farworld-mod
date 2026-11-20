package net.ennway.farworld.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

public class NeighborCognisantBlock extends Block {

    public NeighborCognisantBlock(Properties properties) {
        super(properties);
    }

    @Override
    protected void onPlace(BlockState state, Level level, BlockPos pos, BlockState oldState, boolean movedByPiston) {
        level.setBlockAndUpdate(pos, findState(level, state, pos));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(BlockStateProperties.NORTH);
        builder.add(BlockStateProperties.SOUTH);
        builder.add(BlockStateProperties.EAST);
        builder.add(BlockStateProperties.WEST);
        builder.add(BlockStateProperties.UP);
        builder.add(BlockStateProperties.DOWN);
    }

    public static BlockState findState(Level level, BlockState state, BlockPos pos)
    {
        BlockState st = state;

        st = st.setValue(BlockStateProperties.NORTH, false);
        st = st.setValue(BlockStateProperties.SOUTH, false);
        st = st.setValue(BlockStateProperties.EAST, false);
        st = st.setValue(BlockStateProperties.WEST, false);
        st = st.setValue(BlockStateProperties.UP, false);
        st = st.setValue(BlockStateProperties.DOWN, false);

        if (level.getBlockState(pos.mutable().move(Direction.NORTH)).is(state.getBlock()))
            st = st.setValue(BlockStateProperties.NORTH, true);
        if (level.getBlockState(pos.mutable().move(Direction.SOUTH)).is(state.getBlock()))
            st = st.setValue(BlockStateProperties.SOUTH, true);
        if (level.getBlockState(pos.mutable().move(Direction.EAST)).is(state.getBlock()))
            st = st.setValue(BlockStateProperties.EAST, true);
        if (level.getBlockState(pos.mutable().move(Direction.WEST)).is(state.getBlock()))
            st = st.setValue(BlockStateProperties.WEST, true);
        if (level.getBlockState(pos.mutable().move(Direction.UP)).is(state.getBlock()))
            st = st.setValue(BlockStateProperties.UP, true);
        if (level.getBlockState(pos.mutable().move(Direction.DOWN)).is(state.getBlock()))
            st = st.setValue(BlockStateProperties.DOWN, true);

        return st;
    }

    public static BlockState findState(WorldGenLevel level, BlockState state, BlockPos pos)
    {
        BlockState st = state;

        if (st.hasProperty(BlockStateProperties.NORTH) && st.hasProperty(BlockStateProperties.SOUTH)
        && st.hasProperty(BlockStateProperties.EAST) && st.hasProperty(BlockStateProperties.WEST)
        && st.hasProperty(BlockStateProperties.UP) && st.hasProperty(BlockStateProperties.DOWN))
        {
            st = st.setValue(BlockStateProperties.NORTH, false);
            st = st.setValue(BlockStateProperties.SOUTH, false);
            st = st.setValue(BlockStateProperties.EAST, false);
            st = st.setValue(BlockStateProperties.WEST, false);
            st = st.setValue(BlockStateProperties.UP, false);
            st = st.setValue(BlockStateProperties.DOWN, false);

            if (level.getBlockState(pos.mutable().move(Direction.NORTH)).is(state.getBlock()))
                st = st.setValue(BlockStateProperties.NORTH, true);
            if (level.getBlockState(pos.mutable().move(Direction.SOUTH)).is(state.getBlock()))
                st = st.setValue(BlockStateProperties.SOUTH, true);
            if (level.getBlockState(pos.mutable().move(Direction.EAST)).is(state.getBlock()))
                st = st.setValue(BlockStateProperties.EAST, true);
            if (level.getBlockState(pos.mutable().move(Direction.WEST)).is(state.getBlock()))
                st = st.setValue(BlockStateProperties.WEST, true);
            if (level.getBlockState(pos.mutable().move(Direction.UP)).is(state.getBlock()))
                st = st.setValue(BlockStateProperties.UP, true);
            if (level.getBlockState(pos.mutable().move(Direction.DOWN)).is(state.getBlock()))
                st = st.setValue(BlockStateProperties.DOWN, true);
        }

        return st;
    }

    @Override
    public @Nullable BlockState getStateForPlacement(BlockPlaceContext context) {
        BlockState st = super.getStateForPlacement(context);
        return findState(context.getLevel(), st, context.getClickedPos());
    }

    @Override
    protected void neighborChanged(BlockState state, Level level, BlockPos pos, Block neighborBlock, BlockPos neighborPos, boolean movedByPiston) {
        level.setBlockAndUpdate(pos, findState(level, state, pos));
    }
}
