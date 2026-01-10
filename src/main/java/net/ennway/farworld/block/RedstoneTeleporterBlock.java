package net.ennway.farworld.block;

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
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(BlockStateProperties.LIT);
    }

    @Override
    protected void tick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
        if (level.hasNeighborSignal(pos))
            runStuff(level, pos);
    }

    void runStuff(ServerLevel level, BlockPos pos)
    {
        if (level.getBlockEntity(pos) instanceof MyBlockEntity entity)
        {
            entity.teleport(level);
        }
    }

    @Override
    public @Nullable BlockState getStateForPlacement(BlockPlaceContext context) {
        return super.getStateForPlacement(context).setValue(BlockStateProperties.LIT, false);
    }

    @Override
    public @Nullable BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
        return new MyBlockEntity(blockPos, blockState);
    }

    @Override
    protected ItemInteractionResult useItemOn(ItemStack stack, BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hitResult) {
        BlockEntity ent = level.getBlockEntity(pos);

        if (ent instanceof MyBlockEntity teleporter)
        {
            if (teleporter.inventory.getStackInSlot(0).isEmpty() && !stack.isEmpty())
            {
                teleporter.insertItem(stack);
                player.swing(hand);
                player.playSound(ModSounds.REDSTONE_TELEPORTER_ARM.get());
                level.getBlockState(pos).setValue(BlockStateProperties.LIT, true);
                return ItemInteractionResult.SUCCESS;
            }
            if (!teleporter.inventory.getStackInSlot(0).isEmpty()) {
                teleporter.dropItem();
                player.swing(hand);
                player.playSound(ModSounds.REDSTONE_TELEPORTER_DISARM.get());
                level.getBlockState(pos).setValue(BlockStateProperties.LIT, false);
                return ItemInteractionResult.SUCCESS;
            }
        }

        return super.useItemOn(stack, state, level, pos, player, hand, hitResult);
    }

    @Override
    public void playerDestroy(Level level, Player player, BlockPos pos, BlockState state, @Nullable BlockEntity blockEntity, ItemStack tool) {
        if (level.getBlockEntity(pos) != null)
        {
            if (level.getBlockEntity(pos) instanceof MyBlockEntity ent)
            {
                ent.drops(false);
            }
        }
        super.playerDestroy(level, player, pos, state, blockEntity, tool);
    }

    public static class MyBlockEntity extends BlockEntity {

        public final ItemStackHandler inventory = new ItemStackHandler(1);

        public MyBlockEntity(BlockPos pos, BlockState state) {
            super(ModBlockEntities.REDSTONE_TELEPORTER_BE.get(), pos, state);
        }

        public void teleport(ServerLevel lvl) {
            List<LivingEntity> entities = lvl.getEntitiesOfClass(LivingEntity.class,
                    new AABB(
                            getBlockPos().getX(), getBlockPos().getY() - 5, getBlockPos().getZ(),
                            getBlockPos().getX() + 1, getBlockPos().getY(), getBlockPos().getZ() + 1));

            for (LivingEntity entity : entities) {
                entity.playSound(ModSounds.REDSTONE_TELEPORTER_WOOSH.get());

                BlockPos nearestTo = findNearestTo(lvl, getBlockPos());

                if (nearestTo != null) {
                    entity.teleportTo(nearestTo.getX(), nearestTo.getY() + 1, nearestTo.getZ());
                }
            }
        }

        @Nullable
        public BlockPos findNearestTo(ServerLevel lvl, BlockPos posFrom) {
            PoiManager manager = lvl.getPoiManager();
            manager.ensureLoadedAndValid(lvl, posFrom, 0);

            List<BlockPos> positions = manager.findAll(p -> p.is(ModPois.REDSTONE_TELEPORTER), p -> true, posFrom, 1500, PoiManager.Occupancy.ANY).toList();

            double dist = 1500;
            BlockPos targetPosition = null;

            for (BlockPos p : positions) {
                if (!p.equals(posFrom)) {
                    double d = p.distToCenterSqr(posFrom.getCenter().x, posFrom.getCenter().y, posFrom.getCenter().z);
                    if (d < dist) {
                        if (level.getBlockEntity(p) instanceof MyBlockEntity teleporter) {
                            if (level.getBlockEntity(posFrom) instanceof MyBlockEntity thisTeleporter) {
                                dist = d;
                                targetPosition = p;
                            }
                        }
                    }
                }
            }

            return targetPosition;
        }

        public void dropItem()
        {
            drops(true);
            inventory.setStackInSlot(0, ItemStack.EMPTY);
            getLevel().getBlockState(getBlockPos()).setValue(BlockStateProperties.LIT, false);
        }
        public void insertItem(ItemStack stack)
        {
            inventory.setStackInSlot(0, stack.split(1));
            getLevel().getBlockState(getBlockPos()).setValue(BlockStateProperties.LIT, true);
        }

        public void drops(boolean oneAbove)
        {
            SimpleContainer container = new SimpleContainer(inventory.getSlots());

            for (int i = 0; i < inventory.getSlots(); i++) {
                container.setItem(i, inventory.getStackInSlot(i));
            }

            if (this.getLevel() != null)
                Containers.dropContents(this.getLevel(), oneAbove ? this.worldPosition.above() : this.worldPosition, container);
        }

        @Override
        protected void saveAdditional(CompoundTag tag, HolderLookup.Provider registries) {
            super.saveAdditional(tag, registries);
            tag.put("inventory", this.inventory.serializeNBT(registries));
        }

        @Override
        protected void loadAdditional(CompoundTag tag, HolderLookup.Provider registries) {
            super.loadAdditional(tag, registries);
            this.inventory.deserializeNBT(registries, tag.getCompound("inventory"));
        }
    }
}
