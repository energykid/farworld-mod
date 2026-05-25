package net.ennway.farworld.block;

import com.mojang.serialization.MapCodec;
import net.ennway.farworld.registries.ModBlocks;
import net.ennway.farworld.registries.ModItems;
import net.ennway.farworld.utils.MathUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.Main;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.BushBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DripstoneThickness;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

public class CrystalliaBushBlock extends BushBlock {
    public CrystalliaBushBlock(Properties properties) {
        super(properties);
        this.registerDefaultState((BlockState)(this.stateDefinition.any()).setValue(BlockStateProperties.BERRIES, true));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(BlockStateProperties.BERRIES);
    }

    @Override
    protected MapCodec<? extends BushBlock> codec() {
        return simpleCodec(CrystalliaBushBlock::new);
    }

    protected static final VoxelShape SHAPE = Block.box(2.0, 0.0, 2.0, 14.0, 16.0, 14.0);

    @Override
    protected VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return SHAPE;
    }

    @Override
    public @Nullable BlockState getStateForPlacement(BlockPlaceContext context) {
        return super.getStateForPlacement(context).setValue(BlockStateProperties.BERRIES, false);
    }

    @Override
    protected ItemInteractionResult useItemOn(ItemStack stack, BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hitResult) {
        if (state.getValue(BlockStateProperties.BERRIES))
        {
            ItemStack st = ModItems.CRYSTALLIAS.toStack();
            st.setCount(level.getRandom().nextBoolean() ? 2 : 1);
            level.addFreshEntity(new ItemEntity(level, pos.getCenter().x, pos.getCenter().y, pos.getCenter().z, st));
            level.playLocalSound(pos, SoundEvents.CAVE_VINES_PICK_BERRIES, SoundSource.BLOCKS, 1f, 1f, false);
            level.setBlockAndUpdate(pos, state.setValue(BlockStateProperties.BERRIES, false));
            return ItemInteractionResult.SUCCESS;
        }
        else
        {
            if (stack.is(Items.BONE_MEAL))
            {
                level.playLocalSound(pos, SoundEvents.BONE_MEAL_USE, SoundSource.BLOCKS, 1f, 0f, false);
                for (int i = 0; i < 6; i++) {
                    double x = pos.getCenter().x + MathUtils.randomDouble(level.getRandom(), -0.5, 0.5);
                    double y = pos.getCenter().y + MathUtils.randomDouble(level.getRandom(), -0.5, 0.5);
                    double z = pos.getCenter().z + MathUtils.randomDouble(level.getRandom(), -0.5, 0.5);
                    level.addParticle(ParticleTypes.HAPPY_VILLAGER.getType(), x, y, z, 0, -0.1, 0);
                }
                level.setBlockAndUpdate(pos, state.setValue(BlockStateProperties.BERRIES, true));
                return ItemInteractionResult.SUCCESS;
            }
        }

        return super.useItemOn(stack, state, level, pos, player, hand, hitResult);
    }

    @Override
    protected boolean isRandomlyTicking(BlockState state) {
        return !state.getValue(BlockStateProperties.BERRIES);
    }

    @Override
    protected void randomTick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
        level.setBlockAndUpdate(pos, state.setValue(BlockStateProperties.BERRIES, true));
    }

    @Override
    protected boolean canSurvive(BlockState state, LevelReader level, BlockPos pos) {
        BlockState st = level.getBlockState(pos.below());
        return st.is(ModBlocks.ENCRUSTED_BASALT) || st.is(Blocks.SMOOTH_BASALT) || st.is(Blocks.BASALT);
    }
}
