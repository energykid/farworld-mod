package net.ennway.farworld.registries;

import net.ennway.farworld.Farworld;
import net.ennway.farworld.block.Gloomcap;
import net.ennway.farworld.block.LushFlowstoneBlock;
import net.ennway.farworld.block.NetherIronOreBlock;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.predicate.BlockStatePredicate;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.material.MapColor;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModBlocks {
    public static final DeferredRegister.Blocks BLOCKS_ALL = DeferredRegister.createBlocks(Farworld.MOD_ID);

    public static final DeferredBlock<Block> GLOOMCAP = BLOCKS_ALL.register(
            "gloomcap",
            registryName -> new Gloomcap(BlockBehaviour.Properties.of()
                    .sound(SoundType.GRASS)
                    .ignitedByLava()
                    .isViewBlocking((a,b,c) -> false)
                    .noCollission()
                    .noOcclusion()));

    public static final DeferredBlock<Block> NETHER_IRON_ORE = BLOCKS_ALL.register(
            "nether_iron_ore",
            registryName -> new NetherIronOreBlock(BlockBehaviour.Properties.of()
                    .sound(SoundType.BASALT)
                    .strength(1.25F, 4.2F)
                    .forceSolidOn()
                    .requiresCorrectToolForDrops()));

    public static final DeferredBlock<Block> SOUL_STEEL_BLOCK = BLOCKS_ALL.register(
            "soul_steel_block",
            registryName -> new Block(BlockBehaviour.Properties.of()
                    .mapColor(MapColor.COLOR_GRAY).requiresCorrectToolForDrops()
                    .strength(30.0F, 800.0F)
                    .sound(SoundType.NETHERITE_BLOCK)));

    public static final DeferredBlock<Block> DENSE_COAL_ORE = BLOCKS_ALL.register(
            "dense_coal_ore",
            registryName -> new Block(BlockBehaviour.Properties.of()
                    .mapColor(MapColor.COLOR_BLACK).requiresCorrectToolForDrops()
                    .strength(1.8F, 6.0F)
                    .sound(SoundType.NETHERRACK)));


    public static final DeferredBlock<Block> DENSE_IRON_ORE = BLOCKS_ALL.register(
            "dense_iron_ore",
            registryName -> new Block(BlockBehaviour.Properties.of()
                    .mapColor(MapColor.COLOR_BROWN).requiresCorrectToolForDrops()
                    .strength(1.8F, 6.0F)
                    .sound(SoundType.NETHERRACK)));

    public static final DeferredBlock<Block> FLOWSTONE = BLOCKS_ALL.register(
            "flowstone",
            registryName -> new Block(BlockBehaviour.Properties.of()
                    .mapColor(MapColor.COLOR_GRAY).requiresCorrectToolForDrops()
                    .strength(1.1F, 6.0F)
                    .sound(SoundType.NETHERRACK)));

    public static final DeferredBlock<Block> LUSH_FLOWSTONE = BLOCKS_ALL.register(
            "lush_flowstone",
            registryName -> new LushFlowstoneBlock(BlockBehaviour.Properties.of()
                    .mapColor(MapColor.COLOR_GRAY).requiresCorrectToolForDrops()
                    .strength(1.1F, 6.0F)
                    .sound(SoundType.NYLIUM)));

    public static final DeferredBlock<Block> FLOWSTONE_CACHE = BLOCKS_ALL.register(
            "flowstone_cache",
            registryName -> new Block(BlockBehaviour.Properties.of()
                    .mapColor(MapColor.COLOR_GRAY).requiresCorrectToolForDrops()
                    .strength(1.3F, 8.0F)
                    .sound(SoundType.NETHER_GOLD_ORE)));
}
