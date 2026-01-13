package net.ennway.farworld.block.entity;

import net.ennway.farworld.Farworld;
import net.ennway.farworld.registries.*;
import net.ennway.farworld.utils.BehaviorUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.Mth;
import net.minecraft.world.Containers;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.ai.village.poi.PoiManager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.items.ItemStackHandler;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Set;

public class RedstoneTeleporterBE extends BlockEntity {

    public float sc = 0.5f;
    public int cooldown = 0;

    public void tick()
    {
        List<LivingEntity> entities = getLevel().getEntitiesOfClass(LivingEntity.class,
                new AABB(
                        getBlockPos().getX(), getBlockPos().getY() + 3, getBlockPos().getZ(),
                        getBlockPos().getX() + 1, getBlockPos().getY(), getBlockPos().getZ() + 1));

        if (entities.isEmpty()) cooldown--;

        if (!inventory.getStackInSlot(0).isEmpty())
        {
            if (cooldown <= 0)
            {
                if (getLevel().hasNeighborSignal(getBlockPos()))
                {
                    teleport(getLevel());
                }
            }
        }
    }

    public final ItemStackHandler inventory = new ItemStackHandler(1);

    public RedstoneTeleporterBE(BlockPos pos, BlockState state) {
        super(ModBlockEntities.REDSTONE_TELEPORTER_BE.get(), pos, state);
    }

    public void teleport(Level lvl) {
        List<LivingEntity> entities = lvl.getEntitiesOfClass(LivingEntity.class,
                new AABB(
                        getBlockPos().getX(), getBlockPos().getY() + 3, getBlockPos().getZ(),
                        getBlockPos().getX() + 1, getBlockPos().getY(), getBlockPos().getZ() + 1));

        for (LivingEntity entity : entities) {
            BlockPos nearestTo = findNearestTo(lvl, getBlockPos());

            if (nearestTo != null) {
                tpFX(nearestTo, entity);
                entity.moveTo(nearestTo.getCenter().x, nearestTo.above().getBottomCenter().y, nearestTo.getCenter().z);
            }
        }
    }

    public void tpFX(BlockPos nearestTo, Entity ent) {

        if (getLevel().getBlockEntity(nearestTo) instanceof RedstoneTeleporterBE otherTP)
        {
            getLevel().playLocalSound(getBlockPos(), ModSounds.REDSTONE_TELEPORTER_WOOSH.get(), SoundSource.BLOCKS, 1, 1, false);
            ent.playSound(ModSounds.REDSTONE_TELEPORTER_WOOSH.get());

            otherTP.cooldown = 1;
            cooldown = 1;
            level.playLocalSound(getBlockPos(), ModSounds.REDSTONE_TELEPORTER_WOOSH.get(), SoundSource.BLOCKS, 1, 1, false);
            level.playLocalSound(nearestTo, ModSounds.REDSTONE_TELEPORTER_WOOSH.get(), SoundSource.BLOCKS, 1, 1, false);

            level.addParticle(ModParticles.REDSTONE_TELEPORT_SHOCKWAVE.get(), true, nearestTo.getCenter().x, nearestTo.getY() + 1.1, nearestTo.getCenter().z, 0, 0, 0);
            level.addParticle(ModParticles.REDSTONE_TELEPORT_UP.get(), true, nearestTo.getCenter().x, nearestTo.getY() + 1, nearestTo.getCenter().z, 0, 0, 0);

            level.addParticle(ModParticles.REDSTONE_TELEPORT_SHOCKWAVE.get(), true, getBlockPos().getCenter().x, getBlockPos().getY() + 1.1, getBlockPos().getCenter().z, 0, 0, 0);
            level.addParticle(ModParticles.REDSTONE_TELEPORT_UP.get(), true, getBlockPos().getCenter().x, getBlockPos().getY() + 1, getBlockPos().getCenter().z, 0, 0, 0);
        }
    }

    MinecraftServer getServerFrom(Level lvl)
    {
        if (lvl.getServer() == null)
        {
            return Minecraft.getInstance().getSingleplayerServer();
        }
        return lvl.getServer();
    }

    @Nullable
    public BlockPos findNearestTo(Level lvl, BlockPos posFrom) {
        BlockPos targetPosition = null;

        {
            ServerLevel lev = getServerFrom(lvl).getLevel(lvl.dimension());

            PoiManager manager = lev.getPoiManager();
            manager.ensureLoadedAndValid(lev, posFrom, 0);

            List<BlockPos> positions = manager.findAll(p -> p.is(ModPois.REDSTONE_TELEPORTER), x -> true, posFrom, 500, PoiManager.Occupancy.ANY).toList();

            double dist = 1500000;

            for (BlockPos p : positions) {
                if (!p.equals(posFrom)) {
                    double d = p.distToCenterSqr(posFrom.getCenter().x, posFrom.getCenter().y, posFrom.getCenter().z);
                    if (d < dist) {
                        if (level.getBlockEntity(p) instanceof RedstoneTeleporterBE teleporter) {
                            if (level.getBlockEntity(posFrom) instanceof RedstoneTeleporterBE thisTeleporter) {
                                if (!teleporter.equals(thisTeleporter))
                                {
                                    if (teleporter.inventory.getStackInSlot(0).is(thisTeleporter.inventory.getStackInSlot(0).getItem()))
                                    {
                                        if (teleporter.inventory.getStackInSlot(0).getDisplayName().equals(thisTeleporter.inventory.getStackInSlot(0).getDisplayName())) {
                                            dist = d;
                                            targetPosition = p;
                                        }
                                    }
                                }
                            }
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
    }
    public void insertItem(Player plr, ItemStack stack)
    {
        if (plr.isCreative())
        {
            inventory.setStackInSlot(0, stack.copy().split(1));
        }
        else
        {
            inventory.setStackInSlot(0, stack.split(1));
        }
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
        tag.put("inventory", this.inventory.serializeNBT(registries));
    }

    @Override
    public void handleUpdateTag(CompoundTag tag, HolderLookup.Provider lookupProvider) {
        loadAdditional(tag, lookupProvider);
    }

    @Override
    public CompoundTag getUpdateTag(HolderLookup.Provider registries) {
        CompoundTag t = new CompoundTag();
        saveAdditional(t, registries);

        return t;
    }

    @Override
    protected void loadAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        this.inventory.deserializeNBT(registries, tag.getCompound("inventory"));
    }
}