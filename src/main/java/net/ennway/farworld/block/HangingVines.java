package net.ennway.farworld.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.TagKey;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.CaveVinesBlock;
import net.minecraft.world.level.block.TallGrassBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class HangingVines extends Block implements BonemealableBlock {

    public static final BooleanProperty SHORT;

    static {
        SHORT = BlockStateProperties.SHORT;
    }

    public static final TagKey<Block> LUSH_SHALLOWS_HANGING_VINES = TagKey.create(
            BuiltInRegistries.BLOCK.key(),
            ResourceLocation.withDefaultNamespace("lush_shallows_hanging_vines"));

    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(new Property[]{SHORT});
    }

    public HangingVines(Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any().setValue(SHORT, false));
    }

    @Override
    protected void neighborChanged(BlockState state, Level level, BlockPos pos, Block neighborBlock, BlockPos neighborPos, boolean movedByPiston) {
        super.neighborChanged(state, level, pos, neighborBlock, neighborPos, movedByPiston);
        if (level.getBlockState(new BlockPos(pos.getX(), pos.getY() + 1, pos.getZ())).is(LUSH_SHALLOWS_HANGING_VINES) && !level.getBlockState(pos).is(LUSH_SHALLOWS_HANGING_VINES))
        {
            state.setValue(SHORT, true);
        }
        else
        {
            state.setValue(SHORT, false);
        }
    }

    @Override
    protected void onPlace(BlockState state, Level level, BlockPos pos, BlockState oldState, boolean movedByPiston) {
        super.onPlace(state, level, pos, oldState, movedByPiston);
        if (level.getBlockState(new BlockPos(pos.getX(), pos.getY() + 1, pos.getZ())).is(LUSH_SHALLOWS_HANGING_VINES) && !level.getBlockState(pos).is(LUSH_SHALLOWS_HANGING_VINES))
        {
            state.setValue(SHORT, true);
        }
        else
        {
            state.setValue(SHORT, false);
        }
    }

    protected static final VoxelShape SHAPE = Block.box(4.0, 0.0, 4.0, 12.0, 16.0, 12.0);

    @Override
    protected VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return SHAPE;
    }

    @Override
    public boolean isValidBonemealTarget(LevelReader levelReader, BlockPos blockPos, BlockState blockState) {
        return false;
    }

    @Override
    public boolean isBonemealSuccess(Level level, RandomSource randomSource, BlockPos blockPos, BlockState blockState) {
        return false;
    }

    @Override
    public void performBonemeal(ServerLevel serverLevel, RandomSource randomSource, BlockPos blockPos, BlockState blockState) {

    }
}
