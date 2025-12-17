//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package net.ennway.farworld.feature;

import com.mojang.serialization.Codec;
import java.util.Iterator;
import java.util.Optional;
import java.util.function.Consumer;

import net.ennway.farworld.registries.ModBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Direction.Plane;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.PointedDripstoneBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.DripstoneThickness;
import net.minecraft.world.level.levelgen.feature.DripstoneUtils;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.PointedDripstoneConfiguration;

public class PointedAmethystFeature extends Feature<PointedDripstoneConfiguration> {
    public PointedAmethystFeature(Codec<PointedDripstoneConfiguration> codec) {
        super(codec);
    }

    void buildBaseToTipColumn(Direction direction, int height, boolean mergeTip, Consumer<BlockState> blockSetter) {
        if (height >= 3) {
            blockSetter.accept(createPointedDripstone(direction, DripstoneThickness.BASE));

            for(int i = 0; i < height - 3; ++i) {
                blockSetter.accept(createPointedDripstone(direction, DripstoneThickness.MIDDLE));
            }
        }

        if (height >= 2) {
            blockSetter.accept(createPointedDripstone(direction, DripstoneThickness.FRUSTUM));
        }

        if (height >= 1) {
            blockSetter.accept(createPointedDripstone(direction, mergeTip ? DripstoneThickness.TIP_MERGE : DripstoneThickness.TIP));
        }

    }
    static BlockState createPointedDripstone(Direction direction, DripstoneThickness dripstoneThickness) {
        return (BlockState)((BlockState) ModBlocks.POINTED_AMETHYST.get().defaultBlockState().setValue(PointedDripstoneBlock.TIP_DIRECTION, direction)).setValue(PointedDripstoneBlock.THICKNESS, dripstoneThickness);
    }

    static boolean isDripstoneBase(BlockState state) {
        return state.is(Blocks.AMETHYST_BLOCK);
    }

    void growPointedDripstone(LevelAccessor level, BlockPos pos, Direction direction, int height, boolean mergeTip) {
        if (isDripstoneBase(level.getBlockState(pos.relative(direction.getOpposite())))) {
            BlockPos.MutableBlockPos blockpos$mutableblockpos = pos.mutable();
            buildBaseToTipColumn(direction, height, mergeTip, (p_313662_) -> {
                if (p_313662_.is(ModBlocks.POINTED_AMETHYST)) {
                    p_313662_ = (BlockState)p_313662_.setValue(PointedDripstoneBlock.WATERLOGGED, level.isWaterAt(blockpos$mutableblockpos));
                }

                level.setBlock(blockpos$mutableblockpos, p_313662_, 2);
                blockpos$mutableblockpos.move(direction);
            });
        }

    }

    static boolean placeDripstoneBlockIfPossible(LevelAccessor level, BlockPos pos) {
        BlockState blockstate = level.getBlockState(pos);
        if (blockstate.is(BlockTags.DRIPSTONE_REPLACEABLE)) {
            level.setBlock(pos, Blocks.DRIPSTONE_BLOCK.defaultBlockState(), 2);
            return true;
        } else {
            return false;
        }
    }

    public boolean place(FeaturePlaceContext<PointedDripstoneConfiguration> context) {
        LevelAccessor levelaccessor = context.level();
        BlockPos blockpos = context.origin();
        RandomSource randomsource = context.random();
        PointedDripstoneConfiguration pointeddripstoneconfiguration = (PointedDripstoneConfiguration)context.config();
        Optional<Direction> optional = getTipDirection(levelaccessor, blockpos, randomsource);
        if (optional.isEmpty()) {
            return false;
        } else {
            BlockPos blockpos1 = blockpos.relative(((Direction)optional.get()).getOpposite());
            createPatchOfDripstoneBlocks(levelaccessor, randomsource, blockpos1, pointeddripstoneconfiguration);
            int i = randomsource.nextFloat() < pointeddripstoneconfiguration.chanceOfTallerDripstone && DripstoneUtils.isEmptyOrWater(levelaccessor.getBlockState(blockpos.relative((Direction)optional.get()))) ? 2 : 1;
            growPointedDripstone(levelaccessor, blockpos, (Direction)optional.get(), i, false);
            return true;
        }
    }

    private static Optional<Direction> getTipDirection(LevelAccessor level, BlockPos pos, RandomSource random) {
        boolean flag = isDripstoneBase(level.getBlockState(pos.above()));
        boolean flag1 = isDripstoneBase(level.getBlockState(pos.below()));
        if (flag && flag1) {
            return Optional.of(random.nextBoolean() ? Direction.DOWN : Direction.UP);
        } else if (flag) {
            return Optional.of(Direction.DOWN);
        } else {
            return flag1 ? Optional.of(Direction.UP) : Optional.empty();
        }
    }

    private static void createPatchOfDripstoneBlocks(LevelAccessor level, RandomSource random, BlockPos pos, PointedDripstoneConfiguration config) {
        placeDripstoneBlockIfPossible(level, pos);
        Iterator var4 = Plane.HORIZONTAL.iterator();

        while(var4.hasNext()) {
            Direction direction = (Direction)var4.next();
            if (!(random.nextFloat() > config.chanceOfDirectionalSpread)) {
                BlockPos blockpos = pos.relative(direction);
                placeDripstoneBlockIfPossible(level, blockpos);
                if (!(random.nextFloat() > config.chanceOfSpreadRadius2)) {
                    BlockPos blockpos1 = blockpos.relative(Direction.getRandom(random));
                    placeDripstoneBlockIfPossible(level, blockpos1);
                    if (!(random.nextFloat() > config.chanceOfSpreadRadius3)) {
                        BlockPos blockpos2 = blockpos1.relative(Direction.getRandom(random));
                        placeDripstoneBlockIfPossible(level, blockpos2);
                    }
                }
            }
        }

    }
}
