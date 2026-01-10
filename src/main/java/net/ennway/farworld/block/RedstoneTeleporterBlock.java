package net.ennway.farworld.block;

import net.ennway.farworld.block.entity.RedstoneTeleporterBE;
import net.ennway.farworld.registries.*;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.*;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.village.poi.PoiManager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
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
    public @Nullable <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> blockEntityType) {
        return level.isClientSide ? null : (_1, _2, _3, tickingEntity) -> {
            ((RedstoneTeleporterBE)tickingEntity).tick();
        };
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
    public void playerDestroy(Level level, Player player, BlockPos pos, BlockState state, @Nullable BlockEntity blockEntity, ItemStack tool) {
        if (level.getBlockEntity(pos) != null)
        {
            if (level.getBlockEntity(pos) instanceof RedstoneTeleporterBE ent)
            {
                ent.drops(false);
            }
        }
        super.playerDestroy(level, player, pos, state, blockEntity, tool);
    }
}
