package net.ennway.farworld.block.dimensiontransitions;

import net.ennway.farworld.block.BystonePortalBlock;
import net.ennway.farworld.registries.ModBlocks;
import net.ennway.farworld.registries.ModSounds;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.packs.resources.Resource;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.ai.village.poi.PoiType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec2;

public class DimensionLink {

    public DimensionLink(ResourceKey<Level> lv1, ResourceKey<Level> lv2, Block portal, Holder<PoiType> poi, int min, int max, ResourceLocation textureLoc)
    {
        level1 = lv1;
        level2 = lv2;
        poiType = poi;
        portalBlock = portal;
        minY = min;
        maxY = max;
        resourceLocation = textureLoc;
    }

    ResourceKey<Level> level1;
    ResourceKey<Level> level2;
    Block portalBlock;
    public float transitionTime = 40;
    public ResourceLocation resourceLocation;
    int minY;
    int maxY;
    float travelMultiplier;
    Holder<PoiType> poiType;
    SoundEvent idleSound = ModSounds.BYSTONE_PORTAL_IDLE.get();
    SoundEvent enterSound = ModSounds.BYSTONE_PORTAL_ENTER.get();
    SoundEvent exitSound = ModSounds.BYSTONE_PORTAL_EXIT.get();

    public void setPortal(BlockPos pos, Level levelTo)
    {
        levelTo.setBlock(pos, ModBlocks.BYSTONE_PORTAL.get().defaultBlockState(),
                Block.UPDATE_ALL);
        levelTo.setBlock(new BlockPos(pos.getX(), pos.getY() + 1, pos.getZ()), ModBlocks.BYSTONE_PORTAL.get().defaultBlockState().setValue(BystonePortalBlock.SHORT, true),
                Block.UPDATE_ALL);
        levelTo.setBlock(new BlockPos(pos.getX(), pos.getY() + 2, pos.getZ()), ModBlocks.ECHO_LANTERN.get().defaultBlockState(),
                Block.UPDATE_ALL);
        levelTo.setBlock(new BlockPos(pos.getX(), pos.getY() + 3, pos.getZ()), Blocks.STONE_BRICKS.defaultBlockState(),
                Block.UPDATE_ALL);
    }
}
