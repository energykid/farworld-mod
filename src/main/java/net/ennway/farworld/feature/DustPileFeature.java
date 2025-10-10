package net.ennway.farworld.feature;

import com.mojang.serialization.Codec;
import net.ennway.farworld.registries.ModBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Vec3i;
import net.minecraft.util.Mth;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SnowLayerBlock;
import net.minecraft.world.level.levelgen.Column;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;

public class DustPileFeature extends Feature<NoneFeatureConfiguration> {
    public DustPileFeature(Codec<NoneFeatureConfiguration> codec) {
        super(codec);
    }

    @Override
    public boolean place(FeaturePlaceContext<NoneFeatureConfiguration> featurePlaceContext) {
        BlockPos origin = featurePlaceContext.origin();
        WorldGenLevel level = featurePlaceContext.level();

        int rand = Mth.randomBetweenInclusive(featurePlaceContext.random(), 4, 7);

        if (!level.getBlockState(origin).is(Blocks.AIR) && !level.getBlockState(origin).canBeReplaced())
        {
            return false;
        }

        for (int i = -rand; i < rand; i++) {
            for (int j = -rand; j < rand; j++) {

                BlockPos.MutableBlockPos pos = new BlockPos(origin.getX() + i, origin.getY(), origin.getZ() + j).mutable();

                for (int k = 0; k < 40; k++) {
                    if (level.getBlockState(pos).is(Blocks.AIR) || level.getBlockState(pos).canBeReplaced() || level.getBlockState(pos).is(ModBlocks.DUST_SHEET.get())) {
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
                    double bal = Mth.lerp(dist / r, 1f, 0f);

                    float height = Mth.floor(bal * Mth.randomBetweenInclusive(featurePlaceContext.random(), 10, 20));

                    while (height > 0) {
                        this.setBlock(featurePlaceContext.level(), pos, ModBlocks.DUST_SHEET.get().defaultBlockState()
                                .setValue(SnowLayerBlock.LAYERS, (int)Math.min(height, 8)));
                        pos.move(0,1, 0);
                        height -= 8;
                    }
                }
            }
        }


        return true;
    }
}
