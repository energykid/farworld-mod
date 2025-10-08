package net.ennway.farworld.feature;

import com.mojang.serialization.Codec;
import net.ennway.farworld.registries.ModBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;

public class FlowstoneSpikeFeature extends Feature<NoneFeatureConfiguration> {
    public FlowstoneSpikeFeature(Codec<NoneFeatureConfiguration> codec) {
        super(codec);
    }

    @Override
    public boolean place(FeaturePlaceContext<NoneFeatureConfiguration> featurePlaceContext) {
        BlockPos origin = featurePlaceContext.origin();

        int y = featurePlaceContext.random().nextInt(10, 20);

        BlockPos.MutableBlockPos pos = new BlockPos(origin.getX(), origin.getY(), origin.getZ()).mutable();

        int baseY = pos.getY();
        int maxY = pos.getY() + y;

        float size2 = Mth.randomBetweenInclusive(featurePlaceContext.random(), 2, 6);
        float size3 = 0;

        float factor = 0.6f + (featurePlaceContext.random().nextFloat() % 0.4f);

        size2 *= factor;
        size3 *= factor;

        while (pos.getY() < maxY)
        {
            pos.move(0, 1, 0);

            float size = Mth.lerp(((float)pos.getY() - baseY) / (maxY - baseY), size2, size3);

            for (float x = -size; x <= size; x++)
            {
                for (float z = -size; z <= size; z++)
                {
                    BlockPos pos2 = new BlockPos(pos.getX() + (int) x, pos.getY(), pos.getZ() + (int) z);

                    int dist = (int)Math.sqrt(Math.pow((double)pos2.getX() - (double)pos.getX(), 2) + Math.pow((double)pos2.getZ() - (double)pos.getZ(), 2));

                    if (dist < size) {
                        if (featurePlaceContext.level().getBlockState(pos2).is(Blocks.AIR))
                            this.setBlock(featurePlaceContext.level(), pos2, ModBlocks.FLOWSTONE.get().defaultBlockState());
                    }
                }
            }
        }

        return true;
    }
}
