package net.ennway.farworld.registries;

import com.mojang.serialization.MapCodec;
import net.ennway.farworld.Farworld;
import net.ennway.farworld.block.*;
import net.ennway.farworld.registries.sets.WoodTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.features.FeatureUtils;
import net.minecraft.data.worldgen.features.TreeFeatures;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.grower.TreeGrower;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.predicate.BlockStatePredicate;
import net.minecraft.world.level.block.state.properties.BlockSetType;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.block.state.properties.WoodType;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.Optional;

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

    public static final DeferredBlock<Block> MILK_BERRIES = BLOCKS_ALL.register(
            "milk_berries",
            registryName -> new MilkBerryCropBlock(BlockBehaviour.Properties.of()
                    .sound(SoundType.GRASS)
                    .ignitedByLava()
                    .isViewBlocking((a,b,c) -> false)
                    .noCollission()
                    .noOcclusion()));

    public static final DeferredBlock<HalfTransparentBlock> BLACK_ICE = BLOCKS_ALL.register(
            "black_ice",
            registryName -> new HalfTransparentBlock(BlockBehaviour.Properties.of()
                    .sound(SoundType.GLASS)
                    .strength(1.25F, 4.2F)
                    .forceSolidOn()
                    .requiresCorrectToolForDrops()
                    .noOcclusion()));

    public static final DeferredBlock<Block> NETHER_IRON_ORE = BLOCKS_ALL.register(
            "nether_iron_ore",
            registryName -> new RotatedPillarBlock(BlockBehaviour.Properties.of()
                    .sound(SoundType.BASALT)
                    .strength(1.25F, 4.2F)
                    .forceSolidOn()
                    .requiresCorrectToolForDrops()));

    public static final DeferredBlock<Block> SOUL_STEEL_BLOCK = BLOCKS_ALL.register(
            "soul_steel_block",
            registryName -> new Block(BlockBehaviour.Properties.of()
                    .mapColor(MapColor.COLOR_GRAY).requiresCorrectToolForDrops()
                    .strength(2.0F, 10.0F)
                    .sound(SoundType.NETHERITE_BLOCK)));

    public static final DeferredBlock<Block> COBALT_BLOCK = BLOCKS_ALL.register(
            "cobalt_block",
            registryName -> new Block(BlockBehaviour.Properties.of()
                    .mapColor(MapColor.COLOR_BLUE).requiresCorrectToolForDrops()
                    .strength(1.6F, 12.0F)
                    .sound(SoundType.METAL)));

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

    public static final DeferredBlock<Block> DIMLIGHT = BLOCKS_ALL.register(
            "dimlight",
            registryName -> new Block(BlockBehaviour.Properties.of()
                    .mapColor(MapColor.COLOR_BLUE)
                    .strength(0.9F, 1.0F)
                    .sound(SoundType.SHROOMLIGHT)
                    .lightLevel(lamb -> 14)));

    public static final DeferredBlock<Block> DIMLIGHT_STEM = BLOCKS_ALL.register(
            "dimlight_stem",
            registryName -> new DimlightStemBlock(BlockBehaviour.Properties.of()
                    .mapColor(MapColor.COLOR_LIGHT_GRAY)
                    .strength(0.5F, 1.0F)
                    .sound(SoundType.STEM)
                    .noOcclusion()
                    .isViewBlocking((a, b, c) -> false)));

    public static final DeferredBlock<Block> DENSE_GOLD_ORE = BLOCKS_ALL.register(
            "dense_gold_ore",
            registryName -> new Block(BlockBehaviour.Properties.of()
                    .mapColor(MapColor.COLOR_YELLOW).requiresCorrectToolForDrops()
                    .strength(1.8F, 6.0F)
                    .sound(SoundType.NETHERRACK)));

    public static final DeferredBlock<Block> DENSE_REDSTONE_ORE = BLOCKS_ALL.register(
            "dense_redstone_ore",
            registryName -> new RedStoneOreBlock(BlockBehaviour.Properties.of()
                    .mapColor(MapColor.COLOR_RED).requiresCorrectToolForDrops()
                    .strength(1.8F, 6.0F)
                    .sound(SoundType.NETHERRACK)));

    public static final DeferredBlock<Block> COBALT_ORE = BLOCKS_ALL.register(
            "cobalt_ore",
            registryName -> new Block(BlockBehaviour.Properties.of()
                    .mapColor(MapColor.COLOR_YELLOW).requiresCorrectToolForDrops()
                    .strength(1.8F, 6.0F)
                    .sound(SoundType.NETHERRACK)));

    public static final DeferredBlock<Block> REDSTONE_PILLAR_BLOCK = BLOCKS_ALL.register(
            "redstone_pillar_block",
            registryName -> new RedstonePillarBlock(BlockBehaviour.Properties.of()
                    .emissiveRendering(new BlockBehaviour.StatePredicate() {
                        @Override
                        public boolean test(BlockState blockState, BlockGetter blockGetter, BlockPos blockPos) {
                            return true;
                        }
                    })
                    .mapColor(MapColor.COLOR_RED).requiresCorrectToolForDrops()
                    .strength(1F, 3.0F)
                    .sound(SoundType.STONE)));

    public static final DeferredBlock<Block> FLOWSTONE = BLOCKS_ALL.register(
            "flowstone",
            registryName -> new Block(BlockBehaviour.Properties.of()
                    .mapColor(MapColor.COLOR_GRAY).requiresCorrectToolForDrops()
                    .strength(1.1F, 6.0F)
                    .sound(SoundType.NETHERRACK)));

    public static final DeferredBlock<Block> SLEEKSTONE = BLOCKS_ALL.register(
            "sleekstone",
            registryName -> new Block(BlockBehaviour.Properties.of()
                    .mapColor(MapColor.COLOR_LIGHT_GRAY).requiresCorrectToolForDrops()
                    .strength(1.1F, 6.0F)
                    .sound(SoundType.STONE)));

    public static final DeferredBlock<Block> CHARGED_SLEEKSTONE = BLOCKS_ALL.register(
            "charged_sleekstone",
            registryName -> new Block(BlockBehaviour.Properties.of()
                    .mapColor(MapColor.COLOR_RED).requiresCorrectToolForDrops()
                    .strength(1.1F, 6.0F)
                    .sound(SoundType.STONE)));

    public static final DeferredBlock<Block> COBBLED_SLEEKSTONE = BLOCKS_ALL.register(
            "cobbled_sleekstone",
            registryName -> new Block(BlockBehaviour.Properties.of()
                    .mapColor(MapColor.COLOR_LIGHT_GRAY).requiresCorrectToolForDrops()
                    .strength(1.2F, 5.0F)
                    .sound(SoundType.STONE)));

    public static final DeferredBlock<Block> COBBLED_SLEEKSTONE_SLAB = BLOCKS_ALL.register(
            "cobbled_sleekstone_slab",
            registryName -> new SlabBlock(BlockBehaviour.Properties.of()
                    .mapColor(MapColor.COLOR_LIGHT_GRAY).requiresCorrectToolForDrops()
                    .strength(1.2F, 5.0F)
                    .sound(SoundType.STONE)));

    public static final DeferredBlock<Block> COBBLED_SLEEKSTONE_STAIRS = BLOCKS_ALL.register(
            "cobbled_sleekstone_stairs",
            registryName -> new StairBlock(COBBLED_SLEEKSTONE.get().defaultBlockState(), BlockBehaviour.Properties.of()
                    .mapColor(MapColor.COLOR_LIGHT_GRAY).requiresCorrectToolForDrops()
                    .strength(1.2F, 5.0F)
                    .sound(SoundType.STONE)));

    public static final DeferredBlock<Block> SLEEKSTONE_BRICKS = BLOCKS_ALL.register(
            "sleekstone_bricks",
            registryName -> new Block(BlockBehaviour.Properties.of()
                    .mapColor(MapColor.COLOR_LIGHT_GRAY).requiresCorrectToolForDrops()
                    .strength(1.2F, 5.0F)
                    .sound(SoundType.STONE)));

    public static final DeferredBlock<Block> SLEEKSTONE_BRICK_SLAB = BLOCKS_ALL.register(
            "sleekstone_brick_slab",
            registryName -> new SlabBlock(BlockBehaviour.Properties.of()
                    .mapColor(MapColor.COLOR_LIGHT_GRAY).requiresCorrectToolForDrops()
                    .strength(1.2F, 5.0F)
                    .sound(SoundType.STONE)));

    public static final DeferredBlock<Block> SLEEKSTONE_BRICK_STAIRS = BLOCKS_ALL.register(
            "sleekstone_brick_stairs",
            registryName -> new StairBlock(COBBLED_SLEEKSTONE.get().defaultBlockState(), BlockBehaviour.Properties.of()
                    .mapColor(MapColor.COLOR_LIGHT_GRAY).requiresCorrectToolForDrops()
                    .strength(1.2F, 5.0F)
                    .sound(SoundType.STONE)));

    public static final DeferredBlock<Block> SLEEKSTONE_BRICK_WALL = BLOCKS_ALL.register(
            "sleekstone_brick_wall",
            registryName -> new WallBlock(BlockBehaviour.Properties.of()
                    .mapColor(MapColor.COLOR_LIGHT_GRAY).requiresCorrectToolForDrops()
                    .strength(1.2F, 5.0F)
                    .sound(SoundType.STONE)));

    public static final DeferredBlock<Block> POINTED_AMETHYST = BLOCKS_ALL.register(
            "pointed_amethyst",
            registryName -> new PointedAmethystBlock(BlockBehaviour.Properties.of()
                    .mapColor(MapColor.COLOR_GRAY).requiresCorrectToolForDrops()
                    .strength(1.1F, 6.0F)
                    .sound(SoundType.AMETHYST_CLUSTER)));

    public static final DeferredBlock<Block> DUST_BLOCK = BLOCKS_ALL.register(
            "dust_block",
            registryName -> new Block(BlockBehaviour.Properties.of()
                    .mapColor(MapColor.COLOR_LIGHT_GRAY)
                    .strength(0.5F, 1.0F)
                    .sound(SoundType.SAND)));

    public static final DeferredBlock<Block> DUST_SHEET = BLOCKS_ALL.register(
            "dust_sheet",
            registryName -> new SnowLayerBlock(BlockBehaviour.Properties.of()
                    .mapColor(MapColor.COLOR_LIGHT_GRAY)
                    .strength(0.5F, 1.0F)
                    .sound(SoundType.SAND)));

    public static final DeferredBlock<Block> STRIPPED_STONEWOOD_LOG = BLOCKS_ALL.register(
            "stripped_stonewood_log",
            registryName -> new RotatedPillarBlock(BlockBehaviour.Properties.of()
                    .mapColor(MapColor.COLOR_GRAY)
                    .strength(1.2F, 3.0F)
                    .sound(SoundType.NETHER_WOOD)));

    public static final DeferredBlock<Block> STRIPPED_STONEWOOD = BLOCKS_ALL.register(
            "stripped_stonewood",
            registryName -> new RotatedPillarBlock(BlockBehaviour.Properties.of()
                    .mapColor(MapColor.COLOR_GRAY)
                    .strength(1.2F, 3.0F)
                    .sound(SoundType.NETHER_WOOD)));

    public static final DeferredBlock<Block> STONEWOOD_LOG = BLOCKS_ALL.register(
            "stonewood_log",
            registryName -> new StonewoodLogBlock(STRIPPED_STONEWOOD_LOG.get(), BlockBehaviour.Properties.of()
                    .mapColor(MapColor.COLOR_GRAY)
                    .strength(1.2F, 3.0F)
                    .sound(SoundType.NETHER_WOOD)));

    public static final DeferredBlock<Block> STONEWOOD = BLOCKS_ALL.register(
            "stonewood",
            registryName -> new StonewoodLogBlock(STRIPPED_STONEWOOD.get(), BlockBehaviour.Properties.of()
                    .mapColor(MapColor.COLOR_GRAY)
                    .strength(1.2F, 3.0F)
                    .sound(SoundType.NETHER_WOOD)));

    public static final DeferredBlock<Block> STONEWOOD_PLANKS = BLOCKS_ALL.register(
            "stonewood_planks",
            registryName -> new Block(BlockBehaviour.Properties.of()
                    .mapColor(MapColor.COLOR_GRAY)
                    .strength(1.2F, 3.0F)
                    .sound(SoundType.NETHER_WOOD)));

    public static final DeferredBlock<Block> STONEWOOD_STAIRS = BLOCKS_ALL.register(
            "stonewood_stairs",
            registryName -> new StairBlock(STONEWOOD_PLANKS.get().defaultBlockState(), BlockBehaviour.Properties.of()
                    .mapColor(MapColor.COLOR_GRAY)
                    .strength(1.2F, 3.0F)
                    .sound(SoundType.NETHER_WOOD)));

    public static final DeferredBlock<Block> STONEWOOD_SLAB = BLOCKS_ALL.register(
            "stonewood_slab",
            registryName -> new SlabBlock(BlockBehaviour.Properties.of()
                    .mapColor(MapColor.COLOR_GRAY)
                    .strength(1.2F, 3.0F)
                    .sound(SoundType.NETHER_WOOD)));

    public static final DeferredBlock<Block> STONEWOOD_DOOR = BLOCKS_ALL.register(
            "stonewood_door",
            registryName -> new DoorBlock(BlockSetType.STONE, BlockBehaviour.Properties.of()
                    .mapColor(MapColor.COLOR_GRAY)
                    .strength(1.2F, 3.0F)
                    .sound(SoundType.NETHER_WOOD)));

    public static final DeferredBlock<Block> STONEWOOD_TRAPDOOR = BLOCKS_ALL.register(
            "stonewood_trapdoor",
            registryName -> new TrapDoorBlock(BlockSetType.STONE, BlockBehaviour.Properties.of()
                    .mapColor(MapColor.COLOR_GRAY)
                    .strength(1.2F, 3.0F)
                    .sound(SoundType.NETHER_WOOD)));

    public static final DeferredBlock<Block> STONEWOOD_FENCE = BLOCKS_ALL.register(
            "stonewood_fence",
            registryName -> new FenceBlock(BlockBehaviour.Properties.of()
                    .mapColor(MapColor.COLOR_GRAY)
                    .strength(1.2F, 3.0F)
                    .sound(SoundType.NETHER_WOOD)));

    public static final DeferredBlock<Block> STONEWOOD_FENCE_GATE = BLOCKS_ALL.register(
            "stonewood_fence_gate",
            registryName -> new FenceGateBlock(WoodTypes.STONEWOOD_TYPE, BlockBehaviour.Properties.of()
                    .mapColor(MapColor.COLOR_GRAY)
                    .strength(1.2F, 3.0F)
                    .sound(SoundType.NETHER_WOOD)));

    public static final DeferredBlock<Block> STONEWOOD_PRESSURE_PLATE = BLOCKS_ALL.register(
            "stonewood_pressure_plate",
            registryName -> new PressurePlateBlock(BlockSetType.STONE, BlockBehaviour.Properties.of()
                    .mapColor(MapColor.COLOR_GRAY)
                    .strength(1.2F, 3.0F)
                    .sound(SoundType.NETHER_WOOD)));

    public static final DeferredBlock<Block> STONEWOOD_BUTTON = BLOCKS_ALL.register(
            "stonewood_button",
            registryName -> new ButtonBlock(BlockSetType.STONE, 10, BlockBehaviour.Properties.of()
                    .mapColor(MapColor.COLOR_GRAY)
                    .strength(1.2F, 3.0F)
                    .sound(SoundType.NETHER_WOOD)));

    public static final DeferredBlock<StandingSignBlock> STONEWOOD_SIGN = BLOCKS_ALL.register(
            "stonewood_sign",
            registryName -> new StandingSignBlock(WoodTypes.STONEWOOD_TYPE, BlockBehaviour.Properties.of()
                    .mapColor(MapColor.COLOR_GRAY)
                    .strength(1.2F, 3.0F)
                    .sound(SoundType.NETHER_WOOD)
                    .noCollission()));

    public static final DeferredBlock<WallSignBlock> STONEWOOD_SIGN_WALL = BLOCKS_ALL.register(
            "stonewood_wall_sign",
            registryName -> new WallSignBlock(WoodTypes.STONEWOOD_TYPE, BlockBehaviour.Properties.of()
                    .mapColor(MapColor.COLOR_GRAY)
                    .strength(1.2F, 3.0F)
                    .sound(SoundType.NETHER_WOOD)
                    .noCollission()
                    .dropsLike(STONEWOOD_SIGN.get())));

    public static final DeferredBlock<CeilingHangingSignBlock> STONEWOOD_SIGN_HANGING = BLOCKS_ALL.register(
            "stonewood_hanging_sign",
            registryName -> new CeilingHangingSignBlock(WoodTypes.STONEWOOD_TYPE, BlockBehaviour.Properties.of()
                    .mapColor(MapColor.COLOR_GRAY)
                    .strength(1.2F, 3.0F)
                    .sound(SoundType.NETHER_WOOD)
                    .noCollission()));

    public static final DeferredBlock<WallHangingSignBlock> STONEWOOD_SIGN_WALL_HANGING = BLOCKS_ALL.register(
            "stonewood_wall_hanging_sign",
            registryName -> new WallHangingSignBlock(WoodTypes.STONEWOOD_TYPE, BlockBehaviour.Properties.of()
                    .mapColor(MapColor.COLOR_GRAY)
                    .strength(1.2F, 3.0F)
                    .sound(SoundType.NETHER_WOOD)
                    .noCollission()
                    .dropsLike(STONEWOOD_SIGN_HANGING.get())));

    public static final DeferredBlock<Block> STONEWOOD_LEAVES = BLOCKS_ALL.register(
            "stonewood_leaves",
            registryName -> new LeavesBlock(BlockBehaviour.Properties.of()
                    .mapColor(MapColor.COLOR_LIGHT_GREEN)
                    .strength(0.4F, 0F)
                    .sound(SoundType.AZALEA_LEAVES)
                    .noOcclusion()
                    .isViewBlocking((a,b,c) -> false)));

    public static final DeferredBlock<Block> STONEWOOD_LEAVES_FLOWERED = BLOCKS_ALL.register(
            "stonewood_leaves_flowered",
            registryName -> new LeavesBlock(BlockBehaviour.Properties.of()
                    .mapColor(MapColor.COLOR_YELLOW)
                    .strength(0.4F, 0F)
                    .noOcclusion()
                    .sound(SoundType.AZALEA_LEAVES)
                    .isViewBlocking((a,b,c) -> false)));

    public static final DeferredBlock<Block> HANGING_VINES = BLOCKS_ALL.register(
            "hanging_vines",
            registryName -> new HangingVinesBodyBlock(BlockBehaviour.Properties.of()
                    .mapColor(MapColor.COLOR_GREEN)
                    .strength(0F, 0F)
                    .noOcclusion()
                    .noCollission()
                    .sound(SoundType.AZALEA_LEAVES)
                    .isViewBlocking((a,b,c) -> false)));

    public static final DeferredBlock<GrowingPlantHeadBlock> HANGING_VINES_END = BLOCKS_ALL.register(
            "hanging_vines_end",
            registryName -> new HangingVinesBlock(BlockBehaviour.Properties.of()
                    .mapColor(MapColor.COLOR_GREEN)
                    .strength(0F, 0F)
                    .noOcclusion()
                    .noCollission()
                    .sound(SoundType.AZALEA_LEAVES)
                    .isViewBlocking((a,b,c) -> false)));


    public static final DeferredBlock<SaplingBlock> STONEWOOD_SAPLING = BLOCKS_ALL.register(
            "stonewood_sapling",
            registryName -> new StonewoodSaplingBlock(
                    BlockBehaviour.Properties.of()
                    .mapColor(MapColor.COLOR_GRAY)
                    .strength(0F, 0F)
                    .noOcclusion()
                    .noCollission()
                    .sound(SoundType.AZALEA_LEAVES)
                    .isViewBlocking((a,b,c) -> false)));

    public static final DeferredBlock<Block> COBBLED_FLOWSTONE = BLOCKS_ALL.register(
            "cobbled_flowstone",
            registryName -> new Block(BlockBehaviour.Properties.of()
                    .mapColor(MapColor.COLOR_GRAY).requiresCorrectToolForDrops()
                    .strength(1.2F, 5.0F)
                    .sound(SoundType.NETHERRACK)));

    public static final DeferredBlock<Block> COBBLED_FLOWSTONE_SLAB = BLOCKS_ALL.register(
            "cobbled_flowstone_slab",
            registryName -> new SlabBlock(BlockBehaviour.Properties.of()
                    .mapColor(MapColor.COLOR_GRAY).requiresCorrectToolForDrops()
                    .strength(1.2F, 5.0F)
                    .sound(SoundType.NETHERRACK)));

    public static final DeferredBlock<Block> COBBLED_FLOWSTONE_STAIRS = BLOCKS_ALL.register(
            "cobbled_flowstone_stairs",
            registryName -> new StairBlock(COBBLED_FLOWSTONE.get().defaultBlockState(), BlockBehaviour.Properties.of()
                    .mapColor(MapColor.COLOR_GRAY).requiresCorrectToolForDrops()
                    .strength(1.2F, 5.0F)
                    .sound(SoundType.NETHERRACK)));


    public static final DeferredBlock<Block> FLOWSTONE_BRICKS = BLOCKS_ALL.register(
            "flowstone_bricks",
            registryName -> new Block(BlockBehaviour.Properties.of()
                    .mapColor(MapColor.COLOR_GRAY).requiresCorrectToolForDrops()
                    .strength(1.2F, 5.0F)
                    .sound(SoundType.NETHERRACK)));

    public static final DeferredBlock<Block> FLOWSTONE_BRICK_SLAB = BLOCKS_ALL.register(
            "flowstone_brick_slab",
            registryName -> new SlabBlock(BlockBehaviour.Properties.of()
                    .mapColor(MapColor.COLOR_GRAY).requiresCorrectToolForDrops()
                    .strength(1.2F, 5.0F)
                    .sound(SoundType.NETHERRACK)));

    public static final DeferredBlock<Block> FLOWSTONE_BRICK_WALL = BLOCKS_ALL.register(
            "flowstone_brick_wall",
            registryName -> new WallBlock(BlockBehaviour.Properties.of()
                    .mapColor(MapColor.COLOR_GRAY).requiresCorrectToolForDrops()
                    .strength(1.2F, 5.0F)
                    .sound(SoundType.NETHERRACK)));

    public static final DeferredBlock<Block> FLOWSTONE_BRICK_STAIRS = BLOCKS_ALL.register(
            "flowstone_brick_stairs",
            registryName -> new StairBlock(COBBLED_FLOWSTONE.get().defaultBlockState(), BlockBehaviour.Properties.of()
                    .mapColor(MapColor.COLOR_GRAY).requiresCorrectToolForDrops()
                    .strength(1.2F, 5.0F)
                    .sound(SoundType.NETHERRACK)));

    public static final DeferredBlock<Block> LUSH_FLOWSTONE = BLOCKS_ALL.register(
            "lush_flowstone",
            registryName -> new LushFlowstoneBlock(BlockBehaviour.Properties.of()
                    .mapColor(MapColor.COLOR_GRAY).requiresCorrectToolForDrops()
                    .strength(1.1F, 6.0F)
                    .sound(SoundType.NYLIUM)));

    public static final DeferredBlock<Block> ENCRUSTED_BASALT = BLOCKS_ALL.register(
            "encrusted_basalt",
            registryName -> new Block(BlockBehaviour.Properties.of()
                    .mapColor(MapColor.COLOR_PURPLE).requiresCorrectToolForDrops()
                    .strength(1.2F, 4.0F)
                    .sound(SoundType.BASALT)));

    public static final DeferredBlock<Block> LUSH_FOLIAGE = BLOCKS_ALL.register(
            "lush_foliage",
            registryName -> new LushFoliage(BlockBehaviour.Properties.of()
                    .sound(SoundType.GRASS)
                    .ignitedByLava()
                    .isViewBlocking((a,b,c) -> false)
                    .replaceable()
                    .noCollission()
                    .noOcclusion()));

    public static final DeferredBlock<Block> FLOWERING_LUSH_FOLIAGE = BLOCKS_ALL.register(
            "flowering_lush_foliage",
            registryName -> new LushFoliage(BlockBehaviour.Properties.of()
                    .sound(SoundType.GRASS)
                    .ignitedByLava()
                    .isViewBlocking((a,b,c) -> false)
                    .replaceable()
                    .noCollission()
                    .noOcclusion()));

    public static final DeferredBlock<Block> FLOWSTONE_CACHE = BLOCKS_ALL.register(
            "flowstone_cache",
            registryName -> new Block(BlockBehaviour.Properties.of()
                    .mapColor(MapColor.COLOR_LIGHT_GRAY).requiresCorrectToolForDrops()
                    .strength(1.3F, 8.0F)
                    .sound(SoundType.NETHER_GOLD_ORE)));

    public static final DeferredBlock<Block> ECHO_LANTERN = BLOCKS_ALL.register(
            "echo_lantern",
            registryName -> new EchoLantern(BlockBehaviour.Properties.of()
                    .mapColor(MapColor.COLOR_LIGHT_GRAY).requiresCorrectToolForDrops()
                    .strength(1.3F, 8.0F)
                    .sound(SoundType.LANTERN)
                    .lightLevel(lamb -> {return 7;})
                    .pushReaction(PushReaction.BLOCK)));

    public static final DeferredBlock<Block> GEODE_NUT = BLOCKS_ALL.register(
            "geode_nut",
            registryName -> new GeodeNut(BlockBehaviour.Properties.of()
                    .mapColor(MapColor.COLOR_LIGHT_GRAY).requiresCorrectToolForDrops()
                    .strength(0.8F, 6.0F)
                    .requiresCorrectToolForDrops()
                    .sound(SoundType.TUFF_BRICKS)
                    .pushReaction(PushReaction.DESTROY)));

    public static final DeferredBlock<Block> BYSTONE_PORTAL = BLOCKS_ALL.register(
            "bystone_portal",
            registryName -> new BystonePortalBlock(BlockBehaviour.Properties.of()
                    .mapColor(MapColor.COLOR_LIGHT_GRAY)
                    .noCollission()
                    .randomTicks()
                    .strength(-1.0F).sound(SoundType.GLASS).lightLevel((p_50870_) -> {
                        return 11;
                    })
                    .pushReaction(PushReaction.BLOCK)));

    public static final DeferredBlock<Block> REDSTONE_CURIOSITY_BLOCK = BLOCKS_ALL.register(
            "redstone_curiosity_block",
            registryName -> new RedstoneCuriosityBlock(BlockBehaviour.Properties.of()
                    .mapColor(MapColor.COLOR_LIGHT_GRAY).requiresCorrectToolForDrops()
                    .strength(2F, 100.0F)
                    .sound(SoundType.STONE)));
}
