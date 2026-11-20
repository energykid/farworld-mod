package net.ennway.farworld.feature;

import com.mojang.serialization.Codec;
import net.ennway.farworld.block.NeighborCognisantBlock;
import net.ennway.farworld.registries.ModBlocks;
import net.ennway.farworld.utils.MathUtils;
import net.ennway.farworld.utils.curve.InCirc;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.Mth;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import net.minecraft.world.phys.Vec2;

public class LargeGloomcapFeature extends Feature<NoneFeatureConfiguration> {
    public LargeGloomcapFeature(Codec<NoneFeatureConfiguration> codec) {
        super(codec);
    }

    @Override
    public boolean place(FeaturePlaceContext<NoneFeatureConfiguration> featurePlaceContext) {
        BlockPos.MutableBlockPos pos = featurePlaceContext.origin().mutable();

        int height = Mth.randomBetweenInclusive(featurePlaceContext.random(), 4, 6);

        for (int i = 0; i <= height; i++) {
            featurePlaceContext.level().setBlock(pos, ModBlocks.GLOOMCAP_STEM.get().defaultBlockState(), 1);

            if (i == height - 2)
            {
                Direction[] dirs = {Direction.EAST, Direction.WEST, Direction.NORTH, Direction.SOUTH};
                for (int j = 0; j < 4; j++) {
                    BlockPos.MutableBlockPos p2 = new BlockPos(pos.getX(), pos.getY() + 1, pos.getZ()).mutable().move(dirs[j]);
                    featurePlaceContext.level().setBlock(p2, ModBlocks.GLOOMCAP_SHELF.get().defaultBlockState()
                            .setValue(BlockStateProperties.NORTH, false)
                            .setValue(BlockStateProperties.SOUTH, false)
                            .setValue(BlockStateProperties.EAST, false)
                            .setValue(BlockStateProperties.WEST, false), 1);
                }
            }
            if (i == height)
            {
                for (int j = -12; j <= 12; j++) {
                    for (int k = -12; k <= 12; k++) {
                        if (new Vec2(j, k).distanceToSqr(Vec2.ZERO) < 12)
                        {
                            BlockPos.MutableBlockPos p2 = new BlockPos(pos.getX() + j, pos.getY() + 1, pos.getZ() + k).mutable();
                            featurePlaceContext.level().setBlock(p2, NeighborCognisantBlock.findState(featurePlaceContext.level(), ModBlocks.GLOOMCAP_SHELF.get().defaultBlockState(), p2), 1);
                        }
                    }
                }
                for (int j = -12; j <= 12; j++) {
                    for (int k = -12; k <= 12; k++) {
                        if (new Vec2(j, k).distanceToSqr(Vec2.ZERO) < 12)
                        {
                            BlockPos.MutableBlockPos p2 = new BlockPos(pos.getX() + j, pos.getY() + 1, pos.getZ() + k).mutable();
                            featurePlaceContext.level().setBlock(p2, NeighborCognisantBlock.findState(featurePlaceContext.level(), featurePlaceContext.level().getBlockState(p2), p2), 1);
                        }
                    }
                }
            }

            pos = pos.move(0, 1, 0);
        }

        return true;
    }
}
