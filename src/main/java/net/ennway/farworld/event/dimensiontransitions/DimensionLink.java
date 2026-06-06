package net.ennway.farworld.event.dimensiontransitions;

import net.ennway.farworld.block.BystonePortalBlock;
import net.ennway.farworld.registries.ModBlocks;
import net.ennway.farworld.registries.ModSounds;
import net.ennway.farworld.utils.WorldUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.ai.village.poi.PoiType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;

import java.util.Timer;
import java.util.TimerTask;

public class DimensionLink {

    public DimensionLink(ResourceKey<Level> lv1, ResourceKey<Level> lv2, Block portal, Holder<PoiType> poi, int min, int max, String textureLoc, ResourceLocation featureLoc)
    {
        level1 = lv1;
        level2 = lv2;
        poiType = poi;
        portalBlock = portal;
        minY = min;
        maxY = max;
        portalTemplate = featureLoc;
        resourceLocation = textureLoc;
    }

    ResourceKey<Level> level1;
    ResourceLocation portalTemplate;
    ResourceKey<Level> level2;
    Block portalBlock;
    public float transitionTime = 40;
    public String resourceLocation;
    int minY;
    int maxY;
    float travelMultiplier;
    Holder<PoiType> poiType;
    SoundEvent idleSound = ModSounds.BYSTONE_PORTAL_IDLE.get();
    SoundEvent enterSound = ModSounds.BYSTONE_PORTAL_ENTER.get();
    SoundEvent exitSound = ModSounds.BYSTONE_PORTAL_EXIT.get();

    public void setPortal(BlockPos pos, ServerLevel levelTo)
    {
        levelTo.setChunkForced(levelTo.getChunk(pos).getPos().x, levelTo.getChunk(pos).getPos().z, true);
        // delay visual effects so the player can see it when switching dimensions
        Timer timer = new Timer();
        timer.schedule(
                new TimerTask() {
                    @Override
                    public void run() {
                        if (WorldUtils.placeTemplateFloorCenteredXZ(levelTo, portalTemplate, pos, Rotation.NONE, Mirror.NONE, levelTo.getSeed()))
                        {
                            cancel();
                        }
                    }
                }, 10, // start after 10 frames
                10 // run again again again after each consecutive 10 frames
        );
    }
}
