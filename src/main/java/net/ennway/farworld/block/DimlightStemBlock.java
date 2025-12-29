package net.ennway.farworld.block;

import net.ennway.farworld.registries.ModBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SnowLayerBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.apache.logging.log4j.core.jmx.Server;
import org.jetbrains.annotations.Nullable;

public class DimlightStemBlock extends Block {

    @Override
    protected VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return Block.box(3.0, 0.0, 3.0, 14.0, 16.0, 14.0);
    }

    public DimlightStemBlock(Properties properties) {
        super(properties);
    }

    @Override
    protected void tick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
        if (state.getValue(BlockStateProperties.BLOOM)) {
            if (!shouldSurviveIfNatural(level, pos)) {
                level.destroyBlock(pos, true);

                BlockPos abovePos = new BlockPos(pos.getX(), pos.getY() + 1, pos.getZ());
                BlockState aboveState = level.getBlockState(abovePos);
                if (aboveState.is(ModBlocks.DIMLIGHT)) {
                    level.destroyBlock(abovePos, true);
                }
            }
        }
    }

    @Override
    public void destroy(LevelAccessor level, BlockPos pos, BlockState state) {
        super.destroy(level, pos, state);

        if (state.getValue(BlockStateProperties.BLOOM))
        {
            BlockPos abovePos = new BlockPos(pos.getX(), pos.getY() + 1, pos.getZ());
            BlockState aboveState = level.getBlockState(abovePos);
            if (aboveState.is(ModBlocks.DIMLIGHT))
            {
                level.destroyBlock(abovePos, true);
            }
        }
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(BlockStateProperties.BLOOM);
    }

    @Override
    protected BlockState updateShape(BlockState state, Direction facing, BlockState facingState, LevelAccessor level, BlockPos currentPos, BlockPos facingPos) {

        if (state.getValue(BlockStateProperties.BLOOM))
        {
            if (!shouldSurviveIfNatural(level, currentPos)) {
                level.scheduleTick(currentPos, this, 1);
            }
        }

        return super.updateShape(state, facing, facingState, level, currentPos, facingPos);
    }

    @Override
    public @Nullable BlockState getStateForPlacement(BlockPlaceContext context) {
        return super.getStateForPlacement(context).setValue(BlockStateProperties.BLOOM, false);
    }

    protected boolean shouldSurviveIfNatural(LevelReader level, BlockPos pos)
    {
        BlockState aboveState = level.getBlockState(new BlockPos(pos.getX(), pos.getY() + 1, pos.getZ()));
        BlockState belowState = level.getBlockState(new BlockPos(pos.getX(), pos.getY() - 1, pos.getZ()));

        boolean a = MilkBerryCropBlock.canGrowOn(aboveState) || belowState.is(ModBlocks.DIMLIGHT) || aboveState.is(ModBlocks.DIMLIGHT_STEM);
        boolean b = MilkBerryCropBlock.canGrowOn(belowState) || belowState.is(ModBlocks.DIMLIGHT) || belowState.is(ModBlocks.DIMLIGHT_STEM);

        return a && b;
    }
}
