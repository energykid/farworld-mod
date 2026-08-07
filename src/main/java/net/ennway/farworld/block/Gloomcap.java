package net.ennway.farworld.block;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.ennway.farworld.registries.ModBlocks;
import net.ennway.farworld.registries.ModItems;
import net.ennway.farworld.registries.ModTags;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

public class Gloomcap extends BushBlock {
    public Gloomcap(Properties properties) {
        super(properties);
    }

    @Override
    protected MapCodec<? extends BushBlock> codec() {
        return RecordCodecBuilder.mapCodec((p_304392_) -> {
            return p_304392_.group(propertiesCodec()).apply(p_304392_, Gloomcap::new);
        });
    }

    protected static final VoxelShape SHAPE = Block.box(4.0, 0.0, 4.0, 12.0, 10.0, 12.0);

    @Override
    protected VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return SHAPE;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(BlockStateProperties.DUSTED);
    }

    @Override
    public @Nullable BlockState getStateForPlacement(BlockPlaceContext context) {
        return super.getStateForPlacement(context).setValue(BlockStateProperties.DUSTED, 0);
    }

    @Override
    protected ItemInteractionResult useItemOn(ItemStack stack, BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hitResult) {
        if (stack.is(ModItems.DUST_CLUMP) && state.getValue(BlockStateProperties.DUSTED) != 1)
        {
            level.setBlock(pos, state.setValue(BlockStateProperties.DUSTED, 1), 0);
            level.playLocalSound(pos.getCenter().x, pos.getCenter().y, pos.getCenter().z,
                    SoundEvents.BONE_MEAL_USE, SoundSource.BLOCKS, 1f, 1f, false);
            player.swing(hand);
            return ItemInteractionResult.CONSUME;
        }

        return super.useItemOn(stack, state, level, pos, player, hand, hitResult);
    }

    @Override
    protected boolean canSurvive(BlockState state, LevelReader level, BlockPos pos) {
        BlockState st = level.getBlockState(pos.below());
        return super.canSurvive(state, level, pos) || st.is(ModBlocks.LUSH_FLOWSTONE) || st.is(ModTags.MILK_BERRY_SURVIVABLE);
    }

    @Override
    protected boolean mayPlaceOn(BlockState state, BlockGetter level, BlockPos pos) {
        BlockState st = level.getBlockState(pos.below());
        return super.mayPlaceOn(state, level, pos) || state.is(ModBlocks.LUSH_FLOWSTONE) || state.is(ModBlocks.DUST_BLOCK);
    }
}
