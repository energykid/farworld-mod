package net.ennway.farworld.block;

import com.mojang.serialization.MapCodec;
import net.ennway.farworld.registries.ModBlocks;
import net.ennway.farworld.registries.ModItems;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.CropBlock;
import net.minecraft.world.level.block.PotatoBlock;
import net.minecraft.world.level.block.SnowLayerBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.neoforged.neoforge.common.CommonHooks;

public class MilkBerryCropBlock extends CropBlock {
    public static final MapCodec<MilkBerryCropBlock> CODEC = simpleCodec(MilkBerryCropBlock::new);
    private static final VoxelShape[] SHAPE_BY_AGE = new VoxelShape[]{Block.box(0.0, 0.0, 0.0, 16.0, 2.0, 16.0), Block.box(0.0, 0.0, 0.0, 16.0, 3.0, 16.0), Block.box(0.0, 0.0, 0.0, 16.0, 4.0, 16.0), Block.box(0.0, 0.0, 0.0, 16.0, 5.0, 16.0), Block.box(0.0, 0.0, 0.0, 16.0, 6.0, 16.0), Block.box(0.0, 0.0, 0.0, 16.0, 7.0, 16.0), Block.box(0.0, 0.0, 0.0, 16.0, 8.0, 16.0), Block.box(0.0, 0.0, 0.0, 16.0, 9.0, 16.0)};

    public MilkBerryCropBlock(Properties properties) {
        super(properties);
    }

    public static boolean canGrowOn(BlockState state)
    {
        return state.is(ModBlocks.DUST_BLOCK) || (state.is(ModBlocks.DUST_SHEET) && state.getValue(SnowLayerBlock.LAYERS) == 8);
    }

    @Override
    protected void randomTick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
        if (level.isAreaLoaded(pos, 1)) {
            int i = this.getAge(state);
            if (i < this.getMaxAge()) {
                this.growCrops(level, pos, state);
                CommonHooks.fireCropGrowPost(level, pos, state);
            }
        }
    }

    @Override
    public MapCodec<MilkBerryCropBlock> codec() {
        return CODEC;
    }

    @Override
    protected ItemLike getBaseSeedId() {
        return ModItems.MILK_BERRIES;
    }

    @Override
    protected VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return SHAPE_BY_AGE[this.getAge(state)];
    }

    @Override
    protected boolean canSurvive(BlockState state, LevelReader level, BlockPos pos) {
        return canGrowOn(level.getBlockState(new BlockPos(pos.getX(), pos.getY() - 1, pos.getZ())));
    }

    @Override
    protected boolean mayPlaceOn(BlockState state, BlockGetter level, BlockPos pos) {
        boolean b = state.is(ModBlocks.DUST_SHEET);
        if (b && state.getValue(SnowLayerBlock.LAYERS) <= 7) b = false;
        return state.is(ModBlocks.DUST_BLOCK) || b;
    }
}
