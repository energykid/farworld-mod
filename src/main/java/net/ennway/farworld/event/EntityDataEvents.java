package net.ennway.farworld.event;

import net.ennway.farworld.Farworld;
import net.ennway.farworld.entity.custom.*;
import net.ennway.farworld.entity.custom.redstone_curiosity.RedstoneCuriosityEntity;
import net.ennway.farworld.registries.ModEntities;
import net.ennway.farworld.registries.entity_definitions.NonGeoEntityLayerDefinition;
import net.minecraft.world.entity.SpawnPlacementTypes;
import net.minecraft.world.level.levelgen.Heightmap;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.neoforge.event.entity.EntityAttributeCreationEvent;
import net.neoforged.neoforge.event.entity.RegisterSpawnPlacementsEvent;

@EventBusSubscriber(modid = Farworld.MOD_ID)
public class EntityDataEvents {
    @SubscribeEvent
    public static void registerSpawnConditions(RegisterSpawnPlacementsEvent event)
    {
        event.register(ModEntities.BLOOMED.get(), SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, (a, b, c, d, e) -> true, RegisterSpawnPlacementsEvent.Operation.REPLACE);
        event.register(ModEntities.BRITTLE.get(), SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, (a, b, c, d, e) -> true, RegisterSpawnPlacementsEvent.Operation.REPLACE);
        event.register(ModEntities.DUSTBUG.get(), SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, (a, b, c, d, e) -> true, RegisterSpawnPlacementsEvent.Operation.REPLACE);
        event.register(ModEntities.GOLIATH.get(), SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, (a, b, c, d, e) -> true, RegisterSpawnPlacementsEvent.Operation.REPLACE);
        event.register(ModEntities.AMETHYST_CONSTRUCT.get(), SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, (a, b, c, d, e) -> true, RegisterSpawnPlacementsEvent.Operation.REPLACE);
    }
    @SubscribeEvent
    public static void registerLayers(EntityRenderersEvent.RegisterLayerDefinitions event)
    {
        for (int i = 0; i < ModEntities.mobDefinitions.size(); i++) {
            NonGeoEntityLayerDefinition def = ModEntities.mobDefinitions.get(i);

            if (def.location != null && def.definition != null)
            {
                event.registerLayerDefinition(def.location, def.definition);
            }
        }
    }
    @SubscribeEvent
    public static void registerAttributes(EntityAttributeCreationEvent event)
    {
        event.put(ModEntities.BLOOMED.get(), BloomedEntity.createAttributes().build());
        event.put(ModEntities.SOUL_GOLEM.get(), SoulGolemEntity.createAttributes().build());
        event.put(ModEntities.BRITTLE.get(), BrittleEntity.createAttributes().build());
        event.put(ModEntities.DUSTBUG.get(), DustbugEntity.createAttributes().build());
        event.put(ModEntities.GOLIATH.get(), GoliathEntity.createAttributes().build());
        event.put(ModEntities.AMETHYST_CONSTRUCT.get(), AmethystConstructEntity.createAttributes().build());
        event.put(ModEntities.REDSTONE_CURIOSITY.get(), RedstoneCuriosityEntity.createAttributes().build());
    }
}