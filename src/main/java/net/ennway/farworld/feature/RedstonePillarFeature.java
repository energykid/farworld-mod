package net.ennway.farworld.feature;

import com.mojang.serialization.Codec;
import net.ennway.farworld.registries.ModBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Vec3i;
import net.minecraft.util.Mth;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;

public class RedstonePillarFeature extends Feature<NoneFeatureConfiguration> {
    public RedstonePillarFeature(Codec<NoneFeatureConfiguration> codec) {
        super(codec);
    }

    @Override
    public boolean place(FeaturePlaceContext<NoneFeatureConfiguration> featurePlaceContext) {
        BlockPos origin = featurePlaceContext.origin();

        int size = Mth.randomBetweenInclusive(featurePlaceContext.random(), 2, 3);

        BlockPos.MutableBlockPos pos = origin.mutable().move(0, -2, 0);

        float y = pos.getY() + Mth.randomBetweenInclusive(featurePlaceContext.random(), 8, 20);

        for (float x = -size / 2f; x <= size / 2f; x++)
        {
            for (float z = -size / 2f; z <= size / 2f; z++)
            {
                BlockPos.MutableBlockPos posclone = new BlockPos(pos.getX(), pos.getY(), pos.getZ()).mutable();
                float y2 = y + Mth.randomBetweenInclusive(featurePlaceContext.random(), -1, 2);

                while (posclone.getY() < y2) {
                    BlockPos pos2 = new BlockPos(pos.getX() + (int) x, posclone.getY(), pos.getZ() + (int) z);

                    int dist = (int) Math.sqrt(Math.pow((double) pos2.getX() - (double) pos.getX(), 2) + Math.pow((double) pos2.getZ() - (double) pos.getZ(), 2));

                    if (dist < size) {
                        this.setBlock(featurePlaceContext.level(), pos2, ModBlocks.REDSTONE_PILLAR_BLOCK.get().defaultBlockState());
                    }

                    posclone.move(0, 1, 0);
                }
            }
        }

        return true;
    }
}
