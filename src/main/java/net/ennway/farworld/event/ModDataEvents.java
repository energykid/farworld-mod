package net.ennway.farworld.event;

import net.ennway.farworld.Farworld;
import net.ennway.farworld.registries.ModEntities;
import net.ennway.farworld.registries.ModItems;
import net.ennway.farworld.registries.ModPotions;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.SpawnPlacementType;
import net.minecraft.world.entity.SpawnPlacementTypes;
import net.minecraft.world.item.alchemy.PotionBrewing;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.level.NaturalSpawner;
import net.minecraft.world.level.chunk.ChunkAccess;
import net.minecraft.world.level.levelgen.Heightmap;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.data.event.GatherDataEvent;
import net.neoforged.neoforge.event.brewing.RegisterBrewingRecipesEvent;
import net.neoforged.neoforge.event.entity.RegisterSpawnPlacementsEvent;

@EventBusSubscriber(modid = Farworld.MOD_ID, bus = EventBusSubscriber.Bus.MOD)
public class ModDataEvents {

    @SubscribeEvent
    public static void dataSpawn(GatherDataEvent event)
    {

    }

    @SubscribeEvent
    public static void registerSpawnConditions(RegisterSpawnPlacementsEvent event)
    {
        event.register(ModEntities.BLOOMED.get(), SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING, (a, b, c, d, e) -> true, RegisterSpawnPlacementsEvent.Operation.REPLACE);
    }
}
