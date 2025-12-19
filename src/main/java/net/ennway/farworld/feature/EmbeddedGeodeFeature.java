package net.ennway.farworld.feature;

import com.mojang.serialization.Codec;
import net.ennway.farworld.Farworld;
import net.ennway.farworld.registries.ModBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Vec3i;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.TagKey;
import net.minecraft.util.Mth;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;

public class EmbeddedGeodeFeature extends Feature<NoneFeatureConfiguration> {
    public EmbeddedGeodeFeature(Codec<NoneFeatureConfiguration> codec) {
        super(codec);
    }

    public static final TagKey<Block> GEODE_EMBEDDABLE = TagKey.create(
            BuiltInRegistries.BLOCK.key(),
            ResourceLocation.fromNamespaceAndPath(Farworld.MOD_ID, "geode_embeddable"));

    @Override
    public boolean place(FeaturePlaceContext<NoneFeatureConfiguration> featurePlaceContext) {
        BlockPos origin = featurePlaceContext.origin();

        int size = Mth.randomBetweenInclusive(featurePlaceContext.random(), 34, 44);

        for (int i = -size / 2; i < size / 2; i++) {
            for (int j = -size / 2; j < size / 2; j++) {
                for (int k = -size / 2; k < size / 2; k++) {
                    Vec3i pos = new Vec3i(origin.getX(), origin.getY(), origin.getZ());
                    Vec3i pos2 = new Vec3i(origin.getX() + i, origin.getY() + j, origin.getZ() + k);

                    int s1 = (int)(size * 0.3f);
                    int s2 = (int)(size * 0.6f);

                    double dist = pos.distSqr(pos2);

                    if (featurePlaceContext.level().getBlockState(new BlockPos(pos2)).is(GEODE_EMBEDDABLE)) {
                        if (dist < s1){
                            this.setBlock(featurePlaceContext.level(), new BlockPos(pos2), Blocks.AIR.defaultBlockState());
                        } else if (dist < s2) {
                            this.setBlock(featurePlaceContext.level(), new BlockPos(pos2), Blocks.AMETHYST_BLOCK.defaultBlockState());
                        } else if (dist < size) {
                            this.setBlock(featurePlaceContext.level(), new BlockPos(pos2), Blocks.CALCITE.defaultBlockState());
                        }
                    }
                }
            }
        }

        return true;
    }
}
