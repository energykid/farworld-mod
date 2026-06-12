package net.ennway.farworld.feature;

import com.mojang.serialization.Codec;
import net.ennway.farworld.registries.ModBlocks;
import net.ennway.farworld.utils.MathUtils;
import net.ennway.farworld.utils.curve.InCirc;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.Mth;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.DirectionalBlock;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;

public class LargeDustPileFeature extends Feature<NoneFeatureConfiguration> {
    public LargeDustPileFeature(Codec<NoneFeatureConfiguration> codec) {
        super(codec);
    }

    @Override
    public boolean place(FeaturePlaceContext<NoneFeatureConfiguration> featurePlaceContext) {
        boolean branch = featurePlaceContext.random().nextBoolean();

        BlockPos origin = featurePlaceContext.origin();

        BlockPos.MutableBlockPos pos = new BlockPos(origin.getX(), -94, origin.getZ()).mutable();

        float middle = featurePlaceContext.random().nextInt(-50, -40);
        float sizeBig = featurePlaceContext.random().nextInt(5, 8);

        while (pos.getY() < 126)
        {
            pos.move(0, 1, 0);

            float size = sizeBig;
            if (pos.getY() >= middle) break;
            size = MathUtils.key(pos.getY(), -50, middle + 1, sizeBig, 0, new InCirc(), size);

            for (float x = -size; x <= size; x+=0.5f)
            {
                for (float z = -size; z <= size; z+=0.5f)
                {
                    BlockPos pos2 = new BlockPos(pos.getX() + Mth.floor(x), pos.getY(), pos.getZ() + Mth.floor(z));

                    int dist = (int)Math.sqrt(Math.pow((double)pos2.getX() - (double)pos.getX(), 2) + Math.pow((double)pos2.getZ() - (double)pos.getZ(), 2));

                    if (dist < size) {
                        if (featurePlaceContext.level().getBlockState(pos2).is(Blocks.AIR) || featurePlaceContext.level().getBlockState(pos2).is(ModBlocks.DUSTY_FLOWSTONE))
                            this.setBlock(featurePlaceContext.level(), pos2, ModBlocks.DUST_BLOCK.get().defaultBlockState());
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
