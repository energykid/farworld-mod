package net.ennway.farworld.feature;

import com.mojang.serialization.Codec;
import net.ennway.farworld.registries.ModBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Vec3i;
import net.minecraft.util.Mth;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SnowLayerBlock;
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
        Level level = featurePlaceContext.level().getLevel();

        int rand = 5;

        for (int i = -rand; i < rand; i++) {
            for (int j = -rand; j < rand; j++) {

                BlockPos p = new BlockPos(origin.getX(), origin.getY(), origin.getZ());
                BlockPos.MutableBlockPos pos = p.mutable().move(i, 0, j);

                for (int k = 0; k < 10; k++) {
                    if (!level.getBlockState(pos).is(Blocks.AIR)) {
                        pos.move(0, 1, 0);
                    }
                    else break;
                }
                for (int k = 0; k < 20; k++) {
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

                if (dist < rand)
                {
                    double bal = Mth.lerp(dist / rand, 1f, 0f);

                    this.setBlock(featurePlaceContext.level(), pos, ModBlocks.DUST_SHEET.get().defaultBlockState()
                            .setValue(SnowLayerBlock.LAYERS, Mth.floor(bal * 7) + 1));
                }
            }
        }

        return true;
    }
}
