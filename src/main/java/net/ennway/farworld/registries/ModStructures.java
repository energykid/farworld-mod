package net.ennway.farworld.registries;

import com.mojang.serialization.MapCodec;
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

    private static <T extends Structure> StructureType<T> explicitStructureTypeTyping(MapCodec<T> structureCodec) {
        return () -> structureCodec;
    }
}