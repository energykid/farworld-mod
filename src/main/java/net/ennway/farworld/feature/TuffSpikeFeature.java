package net.ennway.farworld.feature;

import com.mojang.serialization.Codec;
import net.ennway.farworld.block.StonewoodLogBlock;
import net.ennway.farworld.registries.ModBlocks;
import net.ennway.farworld.utils.MathUtils;
import net.ennway.farworld.utils.curve.InCirc;
import net.ennway.farworld.utils.curve.Linear;
import net.ennway.farworld.utils.curve.OutCirc;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.Mth;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;

public class TuffSpikeFeature extends Feature<NoneFeatureConfiguration> {
    public TuffSpikeFeature(Codec<NoneFeatureConfiguration> codec) {
        super(codec);
    }

    @Override
    public boolean place(FeaturePlaceContext<NoneFeatureConfiguration> featurePlaceContext) {
        boolean branch = featurePlaceContext.random().nextBoolean();

        BlockPos origin = featurePlaceContext.origin();

        BlockPos.MutableBlockPos pos = new BlockPos(origin.getX(), -94, origin.getZ()).mutable();

        boolean top = featurePlaceContext.random().nextBoolean();

        float middle = featurePlaceContext.random().nextInt(-30, -20);
        float sizeBig = featurePlaceContext.random().nextInt(2, 4);

        while (pos.getY() < 126)
        {
            pos.move(0, 1, 0);

            float size = sizeBig;
            if (!top)
            {
                if (pos.getY() >= middle) break;
                size = MathUtils.key(pos.getY(), -50, middle + 1, sizeBig, 0, new Linear(), size);
            }
            else
            {
                if (pos.getY() <= middle) pos.setY((int)middle + 2);
                size = MathUtils.key(pos.getY(), middle - 1, 0, 0, sizeBig, new Linear(), size);
            }

            for (float x = -size; x <= size; x++)
            {
                for (float z = -size; z <= size; z++)
                {
                    BlockPos pos2 = new BlockPos(pos.getX() + Mth.floor(x), pos.getY(), pos.getZ() + Mth.floor(z));

                    int dist = (int)Math.sqrt(Math.pow((double)pos2.getX() - (double)pos.getX(), 2) + Math.pow((double)pos2.getZ() - (double)pos.getZ(), 2));

                    if (dist < size) {
                        if (featurePlaceContext.level().getBlockState(pos2).is(Blocks.AIR) || featurePlaceContext.level().getBlockState(pos2).is(ModBlocks.DUST_BLOCK) || featurePlaceContext.level().getBlockState(pos2).is(ModBlocks.DUST_SHEET))
                            this.setBlock(featurePlaceContext.level(), pos2, Blocks.TUFF.defaultBlockState());
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
