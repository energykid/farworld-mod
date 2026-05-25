package net.ennway.farworld.block;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.ennway.farworld.registries.ModParticles;
import net.ennway.farworld.utils.MathUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.DirectionalBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;

public class DustyFlowstoneBlock extends DirectionalBlock {

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(BlockStateProperties.INVERTED);
    }

    public DustyFlowstoneBlock(Properties p_52591_) {
        super(p_52591_);
    }

    @Override
    protected MapCodec<? extends DirectionalBlock> codec() {
        return RecordCodecBuilder.mapCodec((p_304392_) -> {
            return p_304392_.group(propertiesCodec()).apply(p_304392_, DustyFlowstoneBlock::new);
        });
    }

    @Override
    public void animateTick(BlockState state, Level level, BlockPos pos, RandomSource random) {
        if (state.getValue(BlockStateProperties.INVERTED) && MathUtils.randomDouble(level.getRandom(), 0, 10) > 6)
        {
            level.addParticle(ModParticles.FALLING_DUST.get(), pos.getBottomCenter().x + MathUtils.randomDouble(level.getRandom(), -0.6, 0.6), pos.getBottomCenter().y - 0.15, pos.getBottomCenter().z + MathUtils.randomDouble(level.getRandom(), -0.6, 0.6), 0, -0.1, 0);
        }
        super.animateTick(state, level, pos, random);
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        return (BlockState)this.defaultBlockState().setValue(BlockStateProperties.INVERTED, context.getNearestLookingVerticalDirection() == Direction.UP);
    }
}