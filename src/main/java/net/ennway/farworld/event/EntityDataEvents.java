package net.ennway.farworld.event;

import net.ennway.farworld.Farworld;
import net.ennway.farworld.entity.custom.BloomedEntity;
import net.ennway.farworld.entity.custom.BrittleEntity;
import net.ennway.farworld.entity.custom.DustbugEntity;
import net.ennway.farworld.entity.custom.SoulGolemEntity;
import net.ennway.farworld.registries.ModEntities;
import net.minecraft.world.entity.SpawnPlacementTypes;
import net.minecraft.world.level.levelgen.Heightmap;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
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
}