package net.ennway.farworld.block;

import com.mojang.serialization.MapCodec;
import net.ennway.farworld.registries.ModBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.BushBlock;
import net.minecraft.world.level.block.RootsBlock;
import net.minecraft.world.level.block.TallGrassBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class Gloomcap extends BushBlock {
    public Gloomcap(Properties properties) {
        super(properties);
    }

    @Override
    protected MapCodec<? extends BushBlock> codec() {
        return simpleCodec(Gloomcap::new);
    }

    protected static final VoxelShape SHAPE = Block.box(4.0, 0.0, 4.0, 12.0, 10.0, 12.0);

    @Override
    protected VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return SHAPE;
    }

    @Override
    protected boolean canSurvive(BlockState state, LevelReader level, BlockPos pos) {
        BlockState st = level.getBlockState(pos.below());
        return super.canSurvive(state, level, pos) || st.is(ModBlocks.LUSH_FLOWSTONE) || st.is(ModBlocks.DUST_BLOCK);
    }

    @Override
    protected boolean mayPlaceOn(BlockState state, BlockGetter level, BlockPos pos) {
        BlockState st = level.getBlockState(pos.below());
        return super.mayPlaceOn(state, level, pos) || state.is(ModBlocks.LUSH_FLOWSTONE) || state.is(ModBlocks.DUST_BLOCK);
    }
}
