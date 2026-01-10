package net.ennway.farworld.registries;

import net.ennway.farworld.Farworld;
import net.ennway.farworld.block.RedstoneTeleporterBlock;
import net.ennway.farworld.entity.custom.BloomedEntity;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class ModBlockEntities {
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITY_TYPES = DeferredRegister.create(
            BuiltInRegistries.BLOCK_ENTITY_TYPE,
            Farworld.MOD_ID);

    public static final Supplier<BlockEntityType<RedstoneTeleporterBlock.MyBlockEntity>> REDSTONE_TELEPORTER_BE = BLOCK_ENTITY_TYPES.register(
            "redstone_teleporter_be", () -> BlockEntityType.Builder.of(
                    RedstoneTeleporterBlock.MyBlockEntity::new, ModBlocks.REDSTONE_TELEPORTER_BLOCK.get()).build(null)
    );
}
