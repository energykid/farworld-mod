package net.ennway.farworld.block;

import com.mojang.serialization.MapCodec;
import net.ennway.farworld.registries.ModParticles;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.DirectionalBlock;
import net.minecraft.world.level.block.ObserverBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

public class RedstonePillarBlock extends DirectionalBlock {

    public static final MapCodec<RedstonePillarBlock> CODEC = simpleCodec(RedstonePillarBlock::new);

    public static StatePredicate predicate = new StatePredicate() {
        @Override
        public boolean test(BlockState blockState, BlockGetter blockGetter, BlockPos blockPos) {
            return blockState.getValue(FACING) == Direction.UP;
        }
    };

    @Override
    protected boolean isSignalSource(BlockState state) {
        return true;
    }

    @Override
    protected int getDirectSignal(BlockState blockState, BlockGetter blockAccess, BlockPos pos, Direction side) {
        return blockState.getSignal(blockAccess, pos, side);
    }

    @Override
    protected int getSignal(BlockState blockState, BlockGetter blockAccess, BlockPos pos, Direction side) {
        return blockState.getValue(FACING) == side.getOpposite() ? 15 : 0;
    }

    public RedstonePillarBlock(Properties props) {
        super(props.emissiveRendering(predicate));
        this.registerDefaultState((BlockState)((BlockState)((BlockState)this.stateDefinition.any()).setValue(FACING, Direction.UP).setValue(BlockStateProperties.CRACKED, false)));
    }

    @Override
    public void animateTick(BlockState state, Level level, BlockPos pos, RandomSource random) {
        super.animateTick(state, level, pos, random);
        if (state.getValue(BlockStateProperties.CRACKED)) {
            Vec3 pos2 = pos.getCenter().add(new Vec3(-0.6 + (random.nextDouble() * 1.2), -0.6 + (random.nextDouble() * 1.2), -0.6 + (random.nextDouble() * 1.2)));
            level.addParticle(ModParticles.REDSTONE_CHARGE_PARTICLE.get(), pos2.x, pos2.y, pos2.z, 0, 0, 0);
        }
    }

    @Override
    public @Nullable BlockState getStateForPlacement(BlockPlaceContext context) {
        Direction face = context.getClickedFace();
        if (context.getPlayer() != null)
        {
            if (context.getPlayer().isCrouching()) face = face.getOpposite();
        }
        return super.getStateForPlacement(context).setValue(FACING, face).setValue(BlockStateProperties.CRACKED, false);
    }

    @Override
    protected MapCodec<? extends RedstonePillarBlock> codec() {
        return CODEC;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(new Property[]{FACING, BlockStateProperties.CRACKED});
    }
}
