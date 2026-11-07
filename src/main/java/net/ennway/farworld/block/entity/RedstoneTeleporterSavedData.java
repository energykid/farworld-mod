package net.ennway.farworld.block.entity;

import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.saveddata.SavedData;
import net.minecraft.world.level.storage.DimensionDataStorage;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

// note: this data is per dimension
public class RedstoneTeleporterSavedData extends SavedData {
    private final List<BlockPos> posList = new ArrayList<>();

    public static RedstoneTeleporterSavedData create() {
        return new RedstoneTeleporterSavedData();
    }

    public static RedstoneTeleporterSavedData load(CompoundTag tag, HolderLookup.Provider lookupProvider) {
        RedstoneTeleporterSavedData data = RedstoneTeleporterSavedData.create();
        if (tag.contains("positions")) {
            long[] positions = tag.getLongArray("positions");
            for (long pos : positions) data.posList.add(BlockPos.of(pos));
        }
        return data;
    }

    @Override
    public CompoundTag save(CompoundTag tag, HolderLookup.Provider registries) {
        long[] positions = posList.stream().mapToLong(BlockPos::asLong).toArray();
        tag.putLongArray("positions", positions);
        return tag;
    }

    public static RedstoneTeleporterSavedData getTeleportersData(MinecraftServer server, ResourceKey<Level> dimension) {
        return server.getLevel(dimension).getDataStorage().computeIfAbsent(new Factory<>(RedstoneTeleporterSavedData::create, RedstoneTeleporterSavedData::load), "redstone_teleporters");
    }

    public List<BlockPos> getPositions() { return this.posList; }
    public void addTeleporterPos(BlockPos pos) { posList.add(pos); }
    public void removeTeleporterPos(BlockPos pos) { posList.remove(pos); }
}
