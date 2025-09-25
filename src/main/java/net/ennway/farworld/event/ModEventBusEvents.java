package net.ennway.farworld.event;

import net.ennway.farworld.Farworld;
import net.ennway.farworld.entity.custom.BloomedEntity;
import net.ennway.farworld.entity.custom.BrittleEntity;
import net.ennway.farworld.entity.custom.DustbugEntity;
import net.ennway.farworld.entity.custom.SoulGolemEntity;
import net.ennway.farworld.registries.ModBlocks;
import net.ennway.farworld.registries.ModEntities;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.BlockEntityTypeAddBlocksEvent;
import net.neoforged.neoforge.event.entity.EntityAttributeCreationEvent;

@EventBusSubscriber(modid = Farworld.MOD_ID, bus = EventBusSubscriber.Bus.MOD)
public class ModEventBusEvents {
    @SubscribeEvent
    public static void registerAttributes(EntityAttributeCreationEvent event)
    {
        event.put(ModEntities.BLOOMED.get(), BloomedEntity.createAttributes().build());
        event.put(ModEntities.SOUL_GOLEM.get(), SoulGolemEntity.createAttributes().build());
        event.put(ModEntities.BRITTLE.get(), BrittleEntity.createAttributes().build());
        event.put(ModEntities.DUSTBUG.get(), DustbugEntity.createAttributes().build());
    }
    @SubscribeEvent
    public static void registerBlockEntities(BlockEntityTypeAddBlocksEvent event)
    {
        event.modify(BlockEntityType.SIGN,
                ModBlocks.STONEWOOD_SIGN.get(), ModBlocks.STONEWOOD_SIGN_WALL.get());
        event.modify(BlockEntityType.HANGING_SIGN,
                ModBlocks.STONEWOOD_SIGN_HANGING.get(), ModBlocks.STONEWOOD_SIGN_WALL_HANGING.get());
    }
}
