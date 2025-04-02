package net.ennway.farworld.event;

import net.ennway.farworld.Farworld;
import net.ennway.farworld.entity.custom.SoulGolemEntity;
import net.ennway.farworld.registries.ModBlocks;
import net.ennway.farworld.registries.ModEntities;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.pattern.BlockInWorld;
import net.minecraft.world.level.block.state.pattern.BlockPattern;
import net.minecraft.world.level.block.state.pattern.BlockPatternBuilder;
import net.minecraft.world.level.block.state.predicate.BlockStatePredicate;
import net.neoforged.bus.api.Event;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.player.UseItemOnBlockEvent;
import net.neoforged.neoforge.event.level.BlockEvent;
import net.neoforged.neoforge.event.tick.LevelTickEvent;

@EventBusSubscriber(modid = Farworld.MOD_ID, bus = EventBusSubscriber.Bus.GAME)
public class PortalEvents {

    public static BlockPattern bystonePortal = null;

    public static BlockPattern getOrCreateSoulGolemBase() {

        if (bystonePortal == null) {
            bystonePortal = BlockPatternBuilder.start()
                    .aisle("###", "~~~", "~~~", "~~~")
                    .where('#', BlockInWorld.hasState(BlockStatePredicate.forBlock(Blocks.LANTERN)))
                    .where('~', BlockInWorld.hasState(BlockStatePredicate.ANY))
                    .build();
        }

        return bystonePortal;
    }

    @SubscribeEvent
    public static void lightPortal(UseItemOnBlockEvent event)
    {
        BlockPattern.BlockPatternMatch blockpatternmatch = getOrCreateSoulGolemBase().find(event.getLevel(), event.getPos());

        if (blockpatternmatch != null) {
            if (event.getPos().getCenter().distanceTo(blockpatternmatch.getFrontTopLeft().getCenter()) < 3) {
                if (event.getItemStack().is(Items.ECHO_SHARD)) {
                    event.getLevel().playSound(event.getPlayer(), event.getPos(), SoundEvents.FIRECHARGE_USE, SoundSource.PLAYERS);
                }
            }
        }
    }
}
