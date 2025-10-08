package net.ennway.farworld.feature;

import com.mojang.serialization.Codec;
import net.ennway.farworld.registries.ModBlocks;
import net.ennway.farworld.utils.MathUtils;
import net.ennway.farworld.utils.curve.InCirc;
import net.ennway.farworld.utils.curve.OutCirc;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Vec3i;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.BlockStateConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;

import java.util.Iterator;

public class FlowstoneColumnFeature extends Feature<NoneFeatureConfiguration> {
    public FlowstoneColumnFeature(Codec<NoneFeatureConfiguration> codec) {
        super(codec);
    }

    @Override
    public boolean place(FeaturePlaceContext<NoneFeatureConfiguration> featurePlaceContext) {
        BlockPos origin = featurePlaceContext.origin();

        BlockPos.MutableBlockPos pos = new BlockPos(origin.getX(), -94, origin.getZ()).mutable();

        float size2 = Mth.randomBetweenInclusive(featurePlaceContext.random(), 2, 6);
        float size3 = Mth.randomBetweenInclusive(featurePlaceContext.random(), 2, 6);

        float factor = 0.6f + (featurePlaceContext.random().nextFloat() % 0.4f);

        size2 *= factor;
        size3 *= factor;

        while (pos.getY() < 126)
        {
            pos.move(0, 1, 0);

            float size = Mth.lerp(((float)pos.getY() + 96f) / 224f, size2, size3);

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
