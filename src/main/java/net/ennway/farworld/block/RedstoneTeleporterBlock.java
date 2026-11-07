package net.ennway.farworld.block;

import net.ennway.farworld.block.entity.RedstoneTeleporterBE;
import net.ennway.farworld.block.entity.RedstoneTeleporterSavedData;
import net.ennway.farworld.registries.*;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.RandomSource;
import net.minecraft.world.*;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.village.poi.PoiManager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.neoforged.neoforge.items.ItemStackHandler;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class RedstoneTeleporterBlock extends Block implements EntityBlock {
    public RedstoneTeleporterBlock(Properties properties) {
        super(properties);
        this.registerDefaultState((this.stateDefinition.any()).setValue(BlockStateProperties.LIT, false));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(BlockStateProperties.LIT);
    }

    @Override
    public @Nullable BlockState getStateForPlacement(BlockPlaceContext context) {
        return super.getStateForPlacement(context).setValue(BlockStateProperties.LIT, false);
    }

    @Override
    public @Nullable BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
        return new RedstoneTeleporterBE(blockPos, blockState);
    }

    @Override
    protected void tick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
        super.tick(state, level, pos, random);
        ((RedstoneTeleporterBE)level.getBlockEntity(pos)).tick();
    }

    @Override
    public void animateTick(BlockState state, Level level, BlockPos pos, RandomSource random) {
        super.animateTick(state, level, pos, random);
        ((RedstoneTeleporterBE)level.getBlockEntity(pos)).tick();
    }

    @Override
    protected ItemInteractionResult useItemOn(ItemStack stack, BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hitResult) {
        BlockEntity ent = level.getBlockEntity(pos);

        if (ent instanceof RedstoneTeleporterBE teleporter)
        {
            if (teleporter.inventory.getStackInSlot(0).isEmpty() && !stack.isEmpty())
            {
                teleporter.insertItem(player, stack);
                player.swing(hand);
                player.playSound(ModSounds.REDSTONE_TELEPORTER_ARM.get());
                level.setBlockAndUpdate(pos, state.setValue(BlockStateProperties.LIT, true));
                return ItemInteractionResult.SUCCESS;
            }
            if (!teleporter.inventory.getStackInSlot(0).isEmpty()) {
                teleporter.dropItem();
                player.swing(hand);
                player.playSound(ModSounds.REDSTONE_TELEPORTER_DISARM.get());
                level.setBlockAndUpdate(pos, state.setValue(BlockStateProperties.LIT, false));
                return ItemInteractionResult.SUCCESS;
            }
        }

        return super.useItemOn(stack, state, level, pos, player, hand, hitResult);
    }

    @Override
    protected void onPlace(BlockState state, Level level, BlockPos pos, BlockState oldState, boolean movedByPiston) {
        // add to teleporter list
        if (!level.isClientSide()) {
            RedstoneTeleporterSavedData rtData = RedstoneTeleporterSavedData.getTeleportersData(level.getServer(), level.dimension());
            rtData.addTeleporterPos(pos);
            rtData.setDirty();
        }
        super.onPlace(state, level, pos, oldState, movedByPiston);
    }

    @Override
    public boolean onDestroyedByPlayer(BlockState state, Level level, BlockPos pos, Player player, boolean willHarvest, FluidState fluid) {
        // remove from teleporter list
        if (!level.isClientSide()) {
            RedstoneTeleporterSavedData rtData = RedstoneTeleporterSavedData.getTeleportersData(player.getServer(), player.level().dimension());
            rtData.removeTeleporterPos(pos);
            rtData.setDirty();
        }

        if (level.getBlockEntity(pos) != null)
        {
            if (level.getBlockEntity(pos) instanceof RedstoneTeleporterBE ent)
            {
                ent.drops(false);
            }
        }
        return super.onDestroyedByPlayer(state, level, pos, player, willHarvest, fluid);
    }
}
