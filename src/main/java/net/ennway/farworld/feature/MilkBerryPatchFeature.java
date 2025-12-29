package net.ennway.farworld.feature;

import com.mojang.serialization.Codec;
import net.ennway.farworld.block.MilkBerryCropBlock;
import net.ennway.farworld.registries.ModBlocks;
import net.ennway.farworld.registries.ModTags;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Vec3i;
import net.minecraft.util.Mth;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;

public class MilkBerryPatchFeature extends Feature<NoneFeatureConfiguration> {
    public MilkBerryPatchFeature(Codec<NoneFeatureConfiguration> codec) {
        super(codec);
    }

    @Override
    public boolean place(FeaturePlaceContext<NoneFeatureConfiguration> featurePlaceContext) {
        BlockPos origin = featurePlaceContext.origin();
        WorldGenLevel level = featurePlaceContext.level();

        int rand = Mth.randomBetweenInclusive(featurePlaceContext.random(), 2, 3);

        if (!level.getBlockState(origin).is(Blocks.AIR) && !level.getBlockState(origin).canBeReplaced())
        {
            return false;
        }

        BlockPos.MutableBlockPos posMid = new BlockPos(origin.getX(), origin.getY(), origin.getZ()).mutable();

        for (int k = 0; k < 40; k++) {
            if (level.getBlockState(posMid).is(Blocks.AIR) || level.getBlockState(posMid).canBeReplaced()) {
                posMid.move(0, -1, 0);
            }
            else
            {
                posMid.move(0, 1, 0);
                break;
            }
        }

        if (!MilkBerryCropBlock.canGrowOn(level.getBlockState(new BlockPos(posMid.getX(), posMid.getY() - 1, posMid.getZ()))))
            return false;

        this.setBlock(level, posMid, ModBlocks.DIMLIGHT_STEM.get().defaultBlockState().setValue(BlockStateProperties.BLOOM, true));
        for (int i = 0; i < featurePlaceContext.random().nextInt(3); i++) {
            posMid.move(0, 1, 0);
            this.setBlock(level, posMid, ModBlocks.DIMLIGHT_STEM.get().defaultBlockState().setValue(BlockStateProperties.BLOOM, true));
        }
        posMid.move(0, 1, 0);
        this.setBlock(level, posMid, ModBlocks.DIMLIGHT.get().defaultBlockState());

        for (int i = -rand; i < rand; i++) {
            for (int j = -rand; j < rand; j++) {

                if (i != 0 || j != 0)
                {
                    BlockPos.MutableBlockPos pos = new BlockPos(origin.getX() + i, origin.getY(), origin.getZ() + j).mutable();

                    for (int k = 0; k < 40; k++) {
                        if (level.getBlockState(pos).is(Blocks.AIR) || level.getBlockState(pos).canBeReplaced()) {
                            pos.move(0, -1, 0);
                        }
                        else
                        {
                            pos.move(0, 1, 0);
                            break;
                        }
                    }

                    Vec3i pos1 = new Vec3i(origin.getX(), origin.getY(), origin.getZ());
                    Vec3i pos2 = new Vec3i(pos.getX(), origin.getY(), pos.getZ());

                    double dist = (pos1.distSqr(pos2));

                    float r = rand * rand;

                    if (dist < r)
                    {
                        if (MilkBerryCropBlock.canGrowOn(level.getBlockState(new BlockPos(pos.getX(), pos.getY() - 1, pos.getZ()))))
                        {
                            if (featurePlaceContext.random().nextInt(10) < 4) {
                                this.setBlock(level, pos, ModBlocks.MILK_BERRIES.get().defaultBlockState().setValue(MilkBerryCropBlock.AGE, Mth.randomBetweenInclusive(featurePlaceContext.random(), 3, 7)));
                            }
                        }
                    }
                }
            }
        }

        return true;
    }
}
