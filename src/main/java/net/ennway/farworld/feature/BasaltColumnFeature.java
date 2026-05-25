package net.ennway.farworld.feature;

import com.mojang.serialization.Codec;
import net.ennway.farworld.block.StonewoodLogBlock;
import net.ennway.farworld.registries.ModBlocks;
import net.ennway.farworld.utils.MathUtils;
import net.ennway.farworld.utils.curve.Linear;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.Mth;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LeavesBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;

public class BasaltColumnFeature extends Feature<NoneFeatureConfiguration> {
    public BasaltColumnFeature(Codec<NoneFeatureConfiguration> codec) {
        super(codec);
    }

    @Override
    public boolean place(FeaturePlaceContext<NoneFeatureConfiguration> featurePlaceContext) {
        boolean branch = featurePlaceContext.random().nextBoolean();

        BlockPos origin = featurePlaceContext.origin();

        BlockPos.MutableBlockPos pos = new BlockPos(origin.getX(), -94, origin.getZ()).mutable();

        float sizeBig = featurePlaceContext.random().nextInt(7, 10);
        float sizeSmall = featurePlaceContext.random().nextInt(2, 5);
        float middle = featurePlaceContext.random().nextInt(-30, -20);

        while (pos.getY() < 126)
        {
            pos.move(0, 1, 0);

            float size = sizeBig;
            size = MathUtils.key(pos.getY(), -50, middle, sizeBig, sizeSmall, new Linear(), size);
            size = MathUtils.key(pos.getY(), middle, 0, sizeSmall, sizeBig, new Linear(), size);
            if (size == sizeSmall) size = MathUtils.key(middle - 1, -50, middle, sizeBig, sizeSmall, new Linear(), size);

            for (float x = -size; x <= size; x++)
            {
                for (float z = -size; z <= size; z++)
                {
                    BlockPos pos2 = new BlockPos(pos.getX() + Mth.floor(x), pos.getY(), pos.getZ() + Mth.floor(z));

                    int dist = (int)Math.sqrt(Math.pow((double)pos2.getX() - (double)pos.getX(), 2) + Math.pow((double)pos2.getZ() - (double)pos.getZ(), 2));

                    if (dist < size) {
                        if (featurePlaceContext.level().getBlockState(pos2).is(Blocks.AIR) || featurePlaceContext.level().getBlockState(pos2).is(ModBlocks.CRYSTALLIA_LEAVES) || featurePlaceContext.level().getBlockState(pos2).is(ModBlocks.FRUITLESS_CRYSTALLIA_LEAVES))
                            this.setBlock(featurePlaceContext.level(), pos2, Blocks.BASALT.defaultBlockState());
                    }
                }
            }

            if (branch && pos.getY() > -30 && pos.getY() < 30)
            {
                if (MathUtils.randomDouble(featurePlaceContext.random(), 0, 20) < 7)
                {
                    placeBranch(featurePlaceContext, pos.getY());
                }
            }
        }

        return true;
    }
    public boolean placeBranch(FeaturePlaceContext<NoneFeatureConfiguration> featurePlaceContext, int yy) {
        BlockPos origin = featurePlaceContext.origin();
        WorldGenLevel level = featurePlaceContext.level();

        int rand = Mth.randomBetweenInclusive(featurePlaceContext.random(), 2, 3);

        if (level.getBlockState(origin).is(Blocks.AIR) || level.getBlockState(origin).canBeReplaced())
        {
            return false;
        }

        BlockPos.MutableBlockPos posMid = new BlockPos(origin.getX() + Mth.floor(MathUtils.randomDouble(featurePlaceContext.random(), -5, 5)), yy, origin.getZ() + Mth.floor(MathUtils.randomDouble(featurePlaceContext.random(), -5, 5))).mutable();

        Direction dir = Direction.getRandom(featurePlaceContext.random());
        while (dir == Direction.UP || dir == Direction.DOWN)
        {
            dir = Direction.getRandom(featurePlaceContext.random());
        }

        for (int i = 0; i < 20; i++)
        {
            posMid.move(dir);
            if (i == 19) return false;
            if (level.getBlockState(posMid).is(Blocks.AIR) || level.getBlockState(posMid).canBeReplaced() || level.getBlockState(posMid).is(BlockTags.LEAVES)) break;
        }

        this.setBlock(featurePlaceContext.level(), posMid, ModBlocks.STONEWOOD_LOG.get().defaultBlockState().setValue(StonewoodLogBlock.AXIS, dir.getAxis()));
        posMid.move(dir);
        this.setBlock(featurePlaceContext.level(), posMid, ModBlocks.STONEWOOD_LOG.get().defaultBlockState().setValue(StonewoodLogBlock.AXIS, dir.getAxis()));
        if (featurePlaceContext.random().nextBoolean())
        {
            posMid.move(dir);
            this.setBlock(featurePlaceContext.level(), posMid, ModBlocks.STONEWOOD_LOG.get().defaultBlockState().setValue(StonewoodLogBlock.AXIS, dir.getAxis()));
        }
        posMid.move(dir);

        float size = 3 + (featurePlaceContext.random().nextFloat() * 2);

        for (float x = -size; x <= size; x++)
        {
            for (float y = -size; y <= size; y++)
            {
                for (float z = -size; z <= size; z++)
                {
                    BlockPos pos2 = new BlockPos(posMid.getX() + Mth.floor(x), posMid.getY() + Mth.floor(y), posMid.getZ() + Mth.floor(z));

                    int dist = (int)Math.sqrt(Math.pow((double)pos2.getX() - (double)posMid.getX(), 2) + Math.pow((double)pos2.getZ() - (double)posMid.getZ(), 2));

                    int dist2 = (int)Math.sqrt(Math.pow(dist, 2) + Math.pow((double)pos2.getY() - (double)posMid.getY(), 2));

                    if (dist < size && dist2 < (size / 2)) {
                        BlockState state = ModBlocks.FRUITLESS_CRYSTALLIA_LEAVES.get().defaultBlockState();
                        if (MathUtils.randomDouble(featurePlaceContext.random(), 0, 20) < 3) state = ModBlocks.CRYSTALLIA_LEAVES.get().defaultBlockState();

                        if (featurePlaceContext.level().getBlockState(pos2).is(Blocks.AIR))
                            this.setBlock(featurePlaceContext.level(), pos2, state);
                    }
                }
            }
        }

        return true;
    }
    float quad(float x)
    {
        return (float)-Math.pow(x * 10f, 2) + (x * 10);
    }
}
