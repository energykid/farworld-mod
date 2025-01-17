package net.ennway.farworld.event;

import net.ennway.farworld.Farworld;
import net.ennway.farworld.entity.custom.BloomedEntity;
import net.ennway.farworld.entity.custom.SoulGolemEntity;
import net.ennway.farworld.registries.*;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.tags.EntityTypeTags;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.alchemy.PotionBrewing;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.pattern.BlockInWorld;
import net.minecraft.world.level.block.state.pattern.BlockPattern;
import net.minecraft.world.level.block.state.pattern.BlockPatternBuilder;
import net.minecraft.world.level.block.state.predicate.BlockStatePredicate;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.event.brewing.RegisterBrewingRecipesEvent;
import net.neoforged.neoforge.event.entity.living.LivingChangeTargetEvent;
import net.neoforged.neoforge.event.entity.player.AttackEntityEvent;
import net.neoforged.neoforge.event.level.BlockEvent;
import net.neoforged.neoforge.event.tick.EntityTickEvent;

import java.util.Objects;

@EventBusSubscriber(modid = Farworld.MOD_ID, bus = EventBusSubscriber.Bus.GAME)
public class ModEvents {

    public static BlockPattern soulGolemBase = null;

    public static BlockPattern getOrCreateSoulGolemBase() {

        if (soulGolemBase == null)
        {
            soulGolemBase = BlockPatternBuilder.start()
                .aisle("X~", "##", "#~")
                .where('#', BlockInWorld.hasState(BlockStatePredicate.forBlock(ModBlocks.SOUL_STEEL_BLOCK.get())))
                .where('~', BlockInWorld.hasState(BlockStatePredicate.ANY))
                .where('X', BlockInWorld.hasState(BlockStatePredicate.forBlock(Blocks.SHROOMLIGHT)))
                .build();
        }

        return soulGolemBase;
    }

    @SubscribeEvent
    public static void spawnSoulGolems(BlockEvent.EntityPlaceEvent event)
    {
        if (getOrCreateSoulGolemBase().find(event.getLevel(), event.getPos()) != null)
        {
            BlockPattern.BlockPatternMatch blockpattern$blockpatternmatch = getOrCreateSoulGolemBase().find(event.getLevel(), event.getPos());
            spawnGolemInWorld(event.getEntity().level(), blockpattern$blockpatternmatch, new SoulGolemEntity(ModEntities.SOUL_GOLEM.get(), event.getEntity().level()), blockpattern$blockpatternmatch.getBlock(0, 2, 0).getPos());
        }
    }

    private static void spawnGolemInWorld(Level level, BlockPattern.BlockPatternMatch patternMatch, Entity golem, BlockPos pos) {
        clearPatternBlocks(level, patternMatch);
        golem.moveTo((double)pos.getX() + 0.5, (double)pos.getY() + 0.05, (double)pos.getZ() + 0.5, 0.0F, 0.0F);
        level.addFreshEntity(golem);

        for (ServerPlayer serverplayer : level.getEntitiesOfClass(ServerPlayer.class, golem.getBoundingBox().inflate(5.0))) {
            CriteriaTriggers.SUMMONED_ENTITY.trigger(serverplayer, golem);
        }

        updatePatternBlocks(level, patternMatch);
    }

    public static void clearPatternBlocks(Level level, BlockPattern.BlockPatternMatch patternMatch) {
        for (int i = 0; i < patternMatch.getWidth(); i++) {
            for (int j = 0; j < patternMatch.getHeight(); j++) {
                BlockInWorld blockinworld = patternMatch.getBlock(i, j, 0);
                level.setBlock(blockinworld.getPos(), Blocks.AIR.defaultBlockState(), 2);
                level.levelEvent(2001, blockinworld.getPos(), Block.getId(blockinworld.getState()));
            }
        }
    }

    public static void updatePatternBlocks(Level level, BlockPattern.BlockPatternMatch patternMatch) {
        for (int i = 0; i < patternMatch.getWidth(); i++) {
            for (int j = 0; j < patternMatch.getHeight(); j++) {
                BlockInWorld blockinworld = patternMatch.getBlock(i, j, 0);
                level.blockUpdated(blockinworld.getPos(), Blocks.AIR);
            }
        }
    }

    @SubscribeEvent
    public static void moveStop(EntityTickEvent.Post event)
    {
        Entity entity = event.getEntity();
        if (entity instanceof LivingEntity livEnt) {
            if (livEnt.hasEffect(ModEffects.PARALYSIS)) {

                entity.setDeltaMovement(0.0, 0.0, 0.0);
                entity.teleportTo(entity.getX() - entity.getDeltaMovement().x(), entity.getY(), entity.getZ() - entity.getDeltaMovement().z());
            }
        }
    }

    @SubscribeEvent
    public static void changeTarget(LivingChangeTargetEvent event)
    {
        if (event.getEntity().getType().is(EntityTypeTags.ARTHROPOD) && event.getEntity().getType() != EntityType.ENDERMITE)
        {
            Entity ent = event.getNewAboutToBeSetTarget();

            if (ent != null)
                {if (ent.getType().equals(EntityType.PLAYER))
                {
                    Player player = (Player)ent;

                    player.level().getEntitiesOfClass(BloomedEntity.class, new AABB(
                            player.position().x - 15,
                            player.position().y - 15,
                            player.position().z - 15,
                            player.position().x + 15,
                            player.position().y + 15,
                            player.position().z + 15
                    )).forEach(
                            entity -> {
                                if (Objects.equals(entity.getOwner(), player))
                                {
                                    event.setCanceled(true);
                                }
                            }
                    );
                }
            }
        }
    }
}