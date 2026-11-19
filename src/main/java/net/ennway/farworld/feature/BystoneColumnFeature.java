package net.ennway.farworld.feature;

import com.mojang.serialization.Codec;
import net.ennway.farworld.feature.configuration.BystoneColumnFeatureConfiguration;
import net.ennway.farworld.registries.ModBlocks;
import net.ennway.farworld.utils.MathUtils;
import net.ennway.farworld.utils.curve.Linear;
import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import net.minecraft.world.phys.Vec2;

public class BystoneColumnFeature extends Feature<BystoneColumnFeatureConfiguration> {
    public BystoneColumnFeature(Codec<BystoneColumnFeatureConfiguration> codec) {
        super(codec);
    }

    @Override
    public boolean place(FeaturePlaceContext<BystoneColumnFeatureConfiguration> featurePlaceContext) {
        String orientation = "normal";
        if (featurePlaceContext.config().orientation().isPresent()) orientation = featurePlaceContext.config().orientation().get();

        BlockPos origin = featurePlaceContext.origin();

        BlockPos.MutableBlockPos pos = new BlockPos(origin.getX(), -94, origin.getZ()).mutable();

        double sizeBig = MathUtils.randomDouble(featurePlaceContext.random(), featurePlaceContext.config().sizeBig().getFirst(), featurePlaceContext.config().sizeBig().get(1));
        double sizeSmall = MathUtils.randomDouble(featurePlaceContext.random(), featurePlaceContext.config().sizeSmall().getFirst(), featurePlaceContext.config().sizeSmall().get(1));
        double middle = MathUtils.randomDouble(featurePlaceContext.random(), featurePlaceContext.config().middleRange().getFirst(), featurePlaceContext.config().middleRange().get(1));

        Vec2 offset = new Vec2((float)MathUtils.randomDouble(featurePlaceContext.random(), -12, 12), (float)MathUtils.randomDouble(featurePlaceContext.random(), -12, 12));

        int spikeDir = 0;
        if (orientation.contains("spike"))
        {
            spikeDir = Math.random() > 0.5 ? 1 : -1;
        }

        while (pos.getY() < 126)
        {
            pos.move(0, 1, 0);

            double size = 0;
            if (orientation.equals("normal"))
            {
                size = 0;
                size = MathUtils.key(pos.getY(), -50f, (float)middle, (float)sizeBig, (float)sizeSmall, new Linear(), (float)size);
                size = MathUtils.key(pos.getY(), (float)middle, 20f, (float)sizeSmall, (float)sizeBig, new Linear(), (float)size);
                if (size == sizeSmall) size = MathUtils.key((float)middle - 1f, -50f, (float)middle, (float)sizeBig, (float)sizeSmall, new Linear(), (float)size);
            }
            else {
                if (spikeDir == -1)
                    size = MathUtils.key(pos.getY(), -50f, (float)middle, (float)sizeBig, 0, new Linear(), 0);
                else
                    size = MathUtils.key(pos.getY(), (float)middle, 20f, 0, (float)sizeBig, new Linear(), 0);
            }
            if (size != 0)
            {
                for (double x = -size; x <= size; x++)
                {
                    for (double z = -size; z <= size; z+=0.5)
                    {
                        BlockPos pos1 = new BlockPos(pos.getX(), pos.getY(), pos.getZ());
                        if (orientation.contains("crazy"))
                        {
                            Vec2 p = new Vec2(
                                    MathUtils.key(pos.getY(), -50, 20, offset.x, -offset.x, new Linear(), 0),
                                    MathUtils.key(pos.getY(), -50, 20, offset.y, -offset.y, new Linear(), 0)
                            );
                            pos1 = pos1.mutable().move((int)p.x, 0, (int)p.y);
                        }
                        BlockPos pos2 = new BlockPos(pos1.getX() + Mth.floor(x), pos1.getY(), pos1.getZ() + Mth.floor(z));

                        float dist = new Vec2(pos2.getX(), pos2.getZ()).distanceToSqr(new Vec2(pos1.getX(), pos1.getZ()));

                        if (dist < size) {
                            if (featurePlaceContext.level().getBlockState(pos2).is(Blocks.AIR))
                            {
                                this.setBlock(featurePlaceContext.level(), pos2, featurePlaceContext.config().toPlace());
                                if (orientation.contains("crazy"))
                                {
                                    this.setBlock(featurePlaceContext.level(), pos2.mutable().move(0, -1, 0), featurePlaceContext.config().toPlace());
                                    this.setBlock(featurePlaceContext.level(), pos2.mutable().move(0, 1, 0), featurePlaceContext.config().toPlace());
                                }
                            }
                        }
                    }
                }
            }
        }

        return true;
    }
}
