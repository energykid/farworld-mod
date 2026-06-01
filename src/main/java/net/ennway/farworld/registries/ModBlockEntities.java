package net.ennway.farworld.registries;

import net.ennway.farworld.Farworld;
import net.ennway.farworld.block.entity.CustomRenderBlockBE;
import net.ennway.farworld.block.entity.RedstoneTeleporterBE;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class ModBlockEntities {
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITY_TYPES = DeferredRegister.create(
            BuiltInRegistries.BLOCK_ENTITY_TYPE,
            Farworld.MOD_ID);

    public static final Supplier<BlockEntityType<RedstoneTeleporterBE>> REDSTONE_TELEPORTER_BE = BLOCK_ENTITY_TYPES.register(
            "redstone_teleporter_be", () -> BlockEntityType.Builder.of(
                    RedstoneTeleporterBE::new, ModBlocks.REDSTONE_TELEPORTER_BLOCK.get()).build(null)
    );

    public static final Supplier<BlockEntityType<CustomRenderBlockBE>> CUSTOM_RENDER_BE = BLOCK_ENTITY_TYPES.register(
            "custom_render_be", () -> BlockEntityType.Builder.of(
                    CustomRenderBlockBE::new,
                    ModBlocks.DUST_GLASS.get(),
                    ModBlocks.SHADOW_DUST_GLASS.get(),
                    ModBlocks.CRYSTAL_DUST_GLASS.get()
            ).build(null)
    );
}
