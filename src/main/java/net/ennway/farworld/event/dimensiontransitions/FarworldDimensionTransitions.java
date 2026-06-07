package net.ennway.farworld.event.dimensiontransitions;

import net.ennway.farworld.Farworld;
import net.ennway.farworld.registries.ModAttachments;
import net.ennway.farworld.registries.ModSounds;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;

import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.village.poi.PoiManager;
import net.minecraft.world.entity.ai.village.poi.PoiType;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.tick.EntityTickEvent;

import java.util.*;

@EventBusSubscriber(modid = Farworld.MOD_ID)
public class FarworldDimensionTransitions {

    public static BlockPos findNearestPortalTo(ServerLevel level, BlockPos posFrom, Holder<PoiType> poitype)
    {
        PoiManager manager = level.getPoiManager();
        manager.ensureLoadedAndValid(level, posFrom, 0);
        Optional<BlockPos> opt = manager.findClosest(p -> { return p.is(poitype); },
                posFrom,
                1000,
                PoiManager.Occupancy.ANY
        );

        return opt.orElse(null);
    }

    @SubscribeEvent
    public static void runDimensionTransitions(EntityTickEvent.Post event) {
        Entity entity = event.getEntity();

        // dimension transitions for players
        if (entity instanceof ServerPlayer player) {
            if (!player.isPassenger())
            {
                boolean shouldBeMoving = false;
                BlockPos blockPos = BlockPos.containing(entity.getEyePosition());
                BlockState currentBlockState = entity.level().getBlockState(blockPos);
                for (int i = 0; i < AllDimensionLinks.links.length; i++) {
                    DimensionLink link = AllDimensionLinks.links[i];
                    if (currentBlockState.is(link.portalBlock)) {

                        if (entity.getData(ModAttachments.DIMENSION_TRANSITION) == 0) {
                            ((ServerPlayer) entity).playNotifySound(ModSounds.BYSTONE_PORTAL_ENTER.get(), SoundSource.AMBIENT, 1f, 1f);
                        }

                        entity.setData(ModAttachments.DIMENSION_TRANSITION, entity.getData(ModAttachments.DIMENSION_TRANSITION) + 1f);
                        entity.setData(ModAttachments.DIMENSION_TRANSITION_VISUAL, entity.getData(ModAttachments.DIMENSION_TRANSITION));
                        entity.setData(ModAttachments.DIMENSION_TRANSITION_RESOURCE, link.resourceLocation);
                        shouldBeMoving = true;
                        if (entity.getData(ModAttachments.DIMENSION_TRANSITION) == link.transitionTime) {
                            try {
                                double xx = entity.getX();
                                double yy = Math.clamp(entity.getY(), link.minY, link.maxY);
                                double zz = entity.getZ();

                                BlockPos pos = new BlockPos((int) xx, (int) yy, (int) zz);
                                ServerLevel levelTo = entity.getServer().getLevel(link.level2);
                                if (player.level().dimension() == link.level2) {
                                    levelTo = entity.getServer().getLevel(link.level1);
                                }
                                assert levelTo != null;
                                BlockPos pos2 = findNearestPortalTo(levelTo, pos, link.poiType);
                                if (pos2 != null) {
                                    player.teleportTo(levelTo, pos2.getX(), pos2.getY(), pos2.getZ(), Set.of(), 0f, 0f);
                                } else {
                                    int dir = 1;
                                    int bedrocks = 0;
                                    for (int j = 0; j < 1000; j++) {
                                        pos = new BlockPos(pos.getX(), pos.getY() + dir, pos.getZ());
                                        if (levelTo.getBlockState(pos).is(Blocks.BEDROCK)) {
                                            bedrocks++;
                                            dir = -dir;
                                        }
                                        if (levelTo.getBlockState(pos).is(Blocks.AIR) && levelTo.getBlockState(new BlockPos(pos.getX(), pos.getY() + 1, pos.getZ())).is(Blocks.AIR) && !levelTo.getBlockState(new BlockPos(pos.getX(), pos.getY() - 1, pos.getZ())).canBeReplaced())
                                            break;
                                    }
                                    player.teleportTo(levelTo, pos.getX(), pos.getY(), pos.getZ(), Set.of(), 0f, 0f);
                                    link.setPortal(pos, levelTo);
                                }
                            } finally {
                                ((ServerPlayer) entity).playNotifySound(ModSounds.BYSTONE_PORTAL_EXIT.get(), SoundSource.AMBIENT, 1f, 1f);
                            }
                        }
                    }
                }
                if (!shouldBeMoving) {
                    entity.setData(ModAttachments.DIMENSION_TRANSITION, 0f);
                    entity.setData(ModAttachments.DIMENSION_TRANSITION_VISUAL, 0f);
                }
            }
        }
        else if (!(entity instanceof Player)) // dimension transitions for anything else!
        {
            if (entity.getServer() != null)
            {
                BlockPos blockPos = BlockPos.containing(entity.getEyePosition());
                BlockState currentBlockState = entity.level().getBlockState(blockPos);
                for (int i = 0; i < AllDimensionLinks.links.length + 1; i++) {
                    if (i < AllDimensionLinks.links.length)
                    {
                        DimensionLink link = AllDimensionLinks.links[i];
                        if (currentBlockState.is(link.portalBlock)) {
                            float transTime = 1f;

                            if (!entity.getPassengers().isEmpty()) transTime = link.transitionTime;

                            if (entity.getData(ModAttachments.DIMENSION_TRANSITION_COOLDOWN) > transTime) {
                                double xx = entity.getX();
                                double yy = Math.clamp(entity.getY(), link.minY, link.maxY);
                                double zz = entity.getZ();

                                BlockPos pos = new BlockPos((int) xx, (int) yy, (int) zz);

                                ServerLevel levelTo = entity.getServer().getLevel(link.level2);
                                if (entity.level().dimension() == link.level2) {
                                    levelTo = entity.getServer().getLevel(link.level1);
                                }
                                assert levelTo != null;
                                BlockPos pos2 = findNearestPortalTo(levelTo, pos, link.poiType);
                                if (pos2 != null) {

                                    if (!entity.getPassengers().isEmpty())
                                    {
                                        for (int j = 0; j < entity.getPassengers().size(); j++)
                                        {
                                            if (entity.getPassengers().get(j) instanceof ServerPlayer plr)
                                            {
                                                plr.teleportTo(levelTo, pos.getCenter().x, pos.getCenter().y, pos.getCenter().z, Set.of(), entity.getXRot(), entity.getYRot());
                                            }
                                            else if (entity.getPassengers().get(j) instanceof LivingEntity mob1)
                                            {
                                                mob1.teleportTo(levelTo, pos.getCenter().x, pos.getCenter().y, pos.getCenter().z, Set.of(), mob1.getXRot(), mob1.getYRot());
                                            }
                                        }
                                    }

                                    entity.teleportTo(levelTo, pos.getCenter().x, pos.getCenter().y, pos.getCenter().z, Set.of(), entity.getXRot(), entity.getYRot());
                                } else {
                                    int dir = 1;
                                    int bedrocks = 0;
                                    for (int j = 0; j < 1000; j++) {
                                        pos = new BlockPos(pos.getX(), pos.getY() + dir, pos.getZ());
                                        if (levelTo.getBlockState(pos).is(Blocks.BEDROCK)) {
                                            bedrocks++;
                                            dir = -dir;
                                        }
                                        if (levelTo.getBlockState(pos).is(Blocks.AIR) && levelTo.getBlockState(new BlockPos(pos.getX(), pos.getY() + 1, pos.getZ())).is(Blocks.AIR) && !levelTo.getBlockState(new BlockPos(pos.getX(), pos.getY() - 1, pos.getZ())).canBeReplaced())
                                            break;
                                    }

                                    if (!entity.getPassengers().isEmpty())
                                    {
                                        for (int j = 0; j < entity.getPassengers().size(); j++)
                                        {
                                            if (entity.getPassengers().get(j) instanceof ServerPlayer plr)
                                            {
                                                plr.teleportTo(levelTo, pos.getCenter().x, pos.getCenter().y, pos.getCenter().z, Set.of(), entity.getXRot(), entity.getYRot());
                                            }
                                            else if (entity.getPassengers().get(j) instanceof LivingEntity mob1)
                                            {
                                                mob1.teleportTo(levelTo, pos.getCenter().x, pos.getCenter().y, pos.getCenter().z, Set.of(), mob1.getXRot(), mob1.getYRot());
                                            }
                                        }
                                    }

                                    entity.teleportTo(levelTo, pos.getCenter().x, pos.getCenter().y, pos.getCenter().z, Set.of(), entity.getXRot(), entity.getYRot());

                                    link.setPortal(pos, levelTo);
                                }
                            }

                            entity.setData(ModAttachments.DIMENSION_TRANSITION_COOLDOWN, -20f);

                            break;
                        }
                    }
                    else
                    {
                        entity.setData(ModAttachments.DIMENSION_TRANSITION_COOLDOWN, entity.getData(ModAttachments.DIMENSION_TRANSITION_COOLDOWN) + 1f);
                    }
                }
            }
        }
    }
}