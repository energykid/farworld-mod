package net.ennway.farworld.feature;

import com.mojang.serialization.Codec;
import net.ennway.farworld.registries.ModBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Vec3i;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;

public class EmbeddedGeodeFeature extends Feature<NoneFeatureConfiguration> {
    public EmbeddedGeodeFeature(Codec<NoneFeatureConfiguration> codec) {
        super(codec);
    }

    @Override
    public boolean place(FeaturePlaceContext<NoneFeatureConfiguration> featurePlaceContext) {
        BlockPos origin = featurePlaceContext.origin();

        int size = Mth.randomBetweenInclusive(featurePlaceContext.random(), 34, 44);

        for (int i = -size / 2; i < size / 2; i++) {
            for (int j = -size / 2; j < size / 2; j++) {
                for (int k = -size / 2; k < size / 2; k++) {
                    Vec3i pos = new Vec3i(origin.getX(), origin.getY(), origin.getZ());
                    Vec3i pos2 = new Vec3i(origin.getX() + i, origin.getY() + j, origin.getZ() + k);

                    int s1 = (int)(size * 0.45f);
                    int s2 = (int)(size * 0.7f);
                    int s3 = (int)(size * 0.85f);

                    double dist = pos.distSqr(pos2);

                    if (dist < s1)
                    {
                        this.setBlock(featurePlaceContext.level(), new BlockPos(pos2), Blocks.AIR.defaultBlockState());
                    }
                    else if (!featurePlaceContext.level().getBlockState(new BlockPos(pos2)).isAir()) {
                        if (dist < s2) {
                            this.setBlock(featurePlaceContext.level(), new BlockPos(pos2), Blocks.AMETHYST_BLOCK.defaultBlockState());
                        } else if (dist < s3) {
                            this.setBlock(featurePlaceContext.level(), new BlockPos(pos2), Blocks.CALCITE.defaultBlockState());
                        } else if (dist < size) {
                            this.setBlock(featurePlaceContext.level(), new BlockPos(pos2), Blocks.SMOOTH_BASALT.defaultBlockState());
                        }
                    }
                }
            }
        }

        return true;
    }
}
