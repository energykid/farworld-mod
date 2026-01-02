package net.ennway.farworld.block.dimensiontransitions;

import io.netty.buffer.ByteBuf;
import net.ennway.farworld.Farworld;
import net.ennway.farworld.block.BystonePortalBlock;
import net.ennway.farworld.event.PortalLayerEvents;
import net.ennway.farworld.item.tool.BreezeBoots;
import net.ennway.farworld.registries.ModAttachments;
import net.ennway.farworld.registries.ModBlocks;
import net.ennway.farworld.registries.ModPois;
import net.ennway.farworld.registries.ModSounds;
import net.ennway.farworld.utils.MathUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.chat.ChatListener;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.resources.sounds.Sound;
import net.minecraft.client.sounds.SoundEngine;
import net.minecraft.commands.Commands;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.Position;
import net.minecraft.core.Vec3i;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.ComponentContents;
import net.minecraft.network.chat.PlayerChatMessage;
import net.minecraft.network.chat.Style;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerEntity;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.FormattedCharSequence;
import net.minecraft.util.Mth;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;

import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.village.poi.PoiManager;
import net.minecraft.world.entity.ai.village.poi.PoiRecord;
import net.minecraft.world.entity.ai.village.poi.PoiType;
import net.minecraft.world.entity.ai.village.poi.PoiTypes;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.Portal;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.portal.DimensionTransition;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.attachment.AttachmentType;
import net.neoforged.neoforge.client.event.ClientPlayerNetworkEvent;
import net.neoforged.neoforge.client.event.CustomizeGuiOverlayEvent;
import net.neoforged.neoforge.common.SoundActions;
import net.neoforged.neoforge.event.tick.EntityTickEvent;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.handling.DirectionalPayloadHandler;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;

import java.awt.*;
import java.util.*;
import java.util.List;
import java.util.stream.Stream;

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

        BlockPos pos = opt.orElse(null);

        if (pos != null)
        {

        }

        return pos;
    }

    @SubscribeEvent
    public static void runDimensionTransitions(EntityTickEvent.Post event) {
        Entity entity = event.getEntity();

        if (entity instanceof Mob mob)
        {
            if (entity.getServer() != null)
            {
                boolean shouldBeMoving = false;
                BlockPos blockPos = BlockPos.containing(entity.getEyePosition());
                BlockState currentBlockState = entity.level().getBlockState(blockPos);
                for (int i = 0; i < AllDimensionLinks.links.length + 1; i++) {
                    if (i < AllDimensionLinks.links.length)
                    {
                        DimensionLink link = AllDimensionLinks.links[i];
                        if (currentBlockState.is(link.portalBlock)) {

                            shouldBeMoving = true;
                            float transTime = 30f;

                            if (!entity.getPassengers().isEmpty()) transTime = link.transitionTime;

                            if (entity.getData(ModAttachments.DIMENSION_TRANSITION_COOLDOWN) >= transTime) {
                                double xx = entity.getX();
                                double yy = Math.clamp(entity.getY(), link.minY, link.maxY);
                                double zz = entity.getZ();

                                BlockPos pos = new BlockPos((int) xx, (int) yy, (int) zz);

                                ServerLevel levelTo = entity.getServer().getLevel(link.level2);
                                if (mob.level().dimension() == link.level2) {
                                    levelTo = entity.getServer().getLevel(link.level1);
                                }
                                assert levelTo != null;
                                BlockPos pos2 = findNearestPortalTo(levelTo, pos, link.poiType);
                                if (pos2 != null) {
                                    entity.setData(ModAttachments.DIMENSION_TRANSITION_COOLDOWN, 0f);

                                    if (mob.getPassengers().getFirst() != null)
                                    {
                                        if (mob.getPassengers().getFirst() instanceof ServerPlayer plr)
                                        {
                                            plr.teleportTo(levelTo, pos2.getX(), pos2.getY(), pos2.getZ(), Set.of(), mob.getXRot(), mob.getYRot());
                                        }
                                    }

                                    mob.teleportTo(levelTo, pos2.getX(), pos2.getY(), pos2.getZ(), Set.of(), mob.getXRot(), mob.getYRot());
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
                                    entity.setData(ModAttachments.DIMENSION_TRANSITION_COOLDOWN, 0f);

                                    if (mob.getPassengers().getFirst() != null)
                                    {
                                        if (mob.getPassengers().getFirst() instanceof ServerPlayer plr)
                                        {
                                            plr.teleportTo(levelTo, pos.getCenter().x, pos.getCenter().y, pos.getCenter().z, Set.of(), mob.getXRot(), mob.getYRot());
                                        }
                                    }

                                    mob.teleportTo(levelTo, pos.getCenter().x, pos.getCenter().y, pos.getCenter().z, Set.of(), mob.getXRot(), mob.getYRot());

                                    link.setPortal(pos, levelTo);
                                }
                            }

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

        // dimension transitions for players.
        if (entity instanceof ServerPlayer servPlayer) {
            if (!servPlayer.isPassenger())
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
                        entity.setData(ModAttachments.DIMENSION_TRANSITION_RESOURCE, link.resourceLocation.getPath());
                        PortalLayerEvents.transitionOpacity = Mth.lerp(0.03f, PortalLayerEvents.transitionOpacity, 0.85f);
                        PortalLayerEvents.transitionResource = entity.getData(ModAttachments.DIMENSION_TRANSITION_RESOURCE);
                        shouldBeMoving = true;
                        if (entity.getData(ModAttachments.DIMENSION_TRANSITION) == link.transitionTime) {
                            try {
                                double xx = entity.getX();
                                double yy = Math.clamp(entity.getY(), link.minY, link.maxY);
                                double zz = entity.getZ();

                                BlockPos pos = new BlockPos((int) xx, (int) yy, (int) zz);
                                ServerLevel levelTo = entity.getServer().getLevel(link.level2);
                                if (servPlayer.level().dimension() == link.level2) {
                                    levelTo = entity.getServer().getLevel(link.level1);
                                }
                                assert levelTo != null;
                                BlockPos pos2 = findNearestPortalTo(levelTo, pos, link.poiType);
                                if (pos2 != null) {
                                    servPlayer.teleportTo(levelTo, pos2.getX(), pos2.getY(), pos2.getZ(), 0f, 0f);
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
                                    servPlayer.teleportTo(levelTo, pos.getX(), pos.getY(), pos.getZ(), 0f, 0f);
                                    link.setPortal(pos, levelTo);
                                }
                            } finally {
                                ((ServerPlayer) entity).playNotifySound(ModSounds.BYSTONE_PORTAL_EXIT.get(), SoundSource.AMBIENT, 1f, 1f);
                            }
                        }
                    }
                }
                if (!shouldBeMoving) {
                    PortalLayerEvents.transitionOpacity = Mth.lerp(0.06f, PortalLayerEvents.transitionOpacity, 0f);
                    entity.setData(ModAttachments.DIMENSION_TRANSITION, 0f);
                    entity.setData(ModAttachments.DIMENSION_TRANSITION_VISUAL, entity.getData(ModAttachments.DIMENSION_TRANSITION_VISUAL) * 0.95f);
                }
            }
        }
    }
}