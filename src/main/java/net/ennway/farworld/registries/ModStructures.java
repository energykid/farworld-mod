package net.ennway.farworld.registries;

import com.mojang.serialization.MapCodec;
import net.ennway.farworld.structure.BystoneStructures;
import net.ennway.farworld.structure.RedstoneRuin;
import net.ennway.farworld.structure.SurfacedStructures;
import net.ennway.farworld.Farworld;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.levelgen.structure.StructureType;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModStructures {

    public static final DeferredRegister<StructureType<?>> STRUCTURES_ALL = DeferredRegister.create(Registries.STRUCTURE_TYPE, Farworld.MOD_ID);

    public static final DeferredHolder<StructureType<?>, StructureType<SurfacedStructures>> SURFACE_STRUCTURES = STRUCTURES_ALL.register("surfaced_structures", () -> explicitStructureTypeTyping(SurfacedStructures.CODEC));

    public static final DeferredHolder<StructureType<?>, StructureType<BystoneStructures>> BYSTONE_STRUCTURES = STRUCTURES_ALL.register("bystone_structure", () -> explicitStructureTypeTyping(BystoneStructures.CODEC));

    public static final DeferredHolder<StructureType<?>, StructureType<RedstoneRuin>> REDSTONE_RUIN = STRUCTURES_ALL.register("redstone_ruin", () -> explicitStructureTypeTyping(RedstoneRuin.CODEC));

    private static <T extends Structure> StructureType<T> explicitStructureTypeTyping(MapCodec<T> structureCodec) {
        return () -> structureCodec;
    }
}