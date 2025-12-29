package net.ennway.farworld.block;

import net.ennway.farworld.registries.ModBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SnowLayerBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import org.apache.logging.log4j.core.jmx.Server;

public class DimlightStemBlock extends Block {

    public DimlightStemBlock(Properties properties) {
        super(properties);
    }

    @Override
    protected void tick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
        if (!state.canSurvive(level, pos)) {
            level.destroyBlock(pos, false);
        }
    }

    @Override
    protected BlockState updateShape(BlockState state, Direction facing, BlockState facingState, LevelAccessor level, BlockPos currentPos, BlockPos facingPos) {
        if (!state.canSurvive(level, currentPos)) {
            level.scheduleTick(currentPos, this, 1);
        }

        return super.updateShape(state, facing, facingState, level, currentPos, facingPos);
    }

    @Override
    protected boolean canSurvive(BlockState state, LevelReader level, BlockPos pos) {
        BlockState aboveState = level.getBlockState(new BlockPos(pos.getX(), pos.getY() + 1, pos.getZ()));
        BlockState belowState = level.getBlockState(new BlockPos(pos.getX(), pos.getY() - 1, pos.getZ()));

        boolean a = aboveState.is(ModBlocks.DIMLIGHT) || aboveState.is(ModBlocks.DIMLIGHT_STEM);
        boolean b = belowState.is(ModBlocks.DUST_BLOCK) || belowState.is(ModBlocks.DIMLIGHT_STEM);

        return a && b;
    }
}
