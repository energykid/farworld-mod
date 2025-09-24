package net.ennway.farworld.block;

import net.ennway.farworld.registries.ModBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LanternBlock;
import net.minecraft.world.level.block.TallGrassBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class GeodeNut extends LanternBlock {

    public GeodeNut(Properties properties) {
        super(properties);
    }

    protected static final VoxelShape SHAPE = Block.box(3.5, 4.0, 3.5, 12.5, 13, 12.5);
    protected static final VoxelShape SHAPE_GROUNDED = Block.box(3.5, 0.0, 3.5, 12.5, 9.0, 12.5);

    @Override
    protected VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return state.getValue(BlockStateProperties.HANGING) ? SHAPE : SHAPE_GROUNDED;
    }

    @Override
    protected boolean canSurvive(BlockState state, LevelReader level, BlockPos pos) {
        Direction direction = getConnectedDirection(state).getOpposite();
        return level.getBlockState(pos.relative(direction)).is(ModBlocks.STONEWOOD_LEAVES_FLOWERED) || level.getBlockState(pos.relative(direction)).is(ModBlocks.STONEWOOD_LEAVES);
    }
}
