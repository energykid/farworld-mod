package net.ennway.farworld;

import net.ennway.farworld.feature.ModFeatureTypes;
import net.ennway.farworld.registries.*;
import net.ennway.farworld.registries.entity_definitions.GeoEntityRendererDefinition;
import net.ennway.farworld.registries.entity_definitions.NonGeoEntityLayerDefinition;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraft.world.entity.*;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Items;
import org.slf4j.Logger;

import com.mojang.logging.LogUtils;

import net.minecraft.world.item.CreativeModeTabs;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import net.neoforged.neoforge.event.server.ServerStartingEvent;

// The value here should match an entry in the META-INF/neoforge.mods.toml file
@Mod(Farworld.MOD_ID)
public class Farworld
{
    public static final String MOD_ID = "farworld";

    private static final Logger LOGGER = LogUtils.getLogger();

    public Farworld(IEventBus modEventBus, ModContainer modContainer)
    {
        modEventBus.addListener(this::commonSetup);

        // Register ourselves for server and other game events we are interested in.
        // Note that this is necessary if and only if we want *this* class (ExampleMod) to respond directly to events.
        // Do not add this line if there are no @SubscribeEvent-annotated functions in this class, like onServerStarting() below.
        NeoForge.EVENT_BUS.register(this);

        ModAttachments.ATTACHMENT_TYPES.register(modEventBus);
        ModPois.POIS.register(modEventBus);
        ModItems.ITEMS_ALL.register(modEventBus);
        ModDataComponents.DATA_COMPONENT_TYPES.register(modEventBus);
        ModBlocks.BLOCKS_ALL.register(modEventBus);
        ModLootModifiers.GLOBAL_LOOT_MODIFIERS_ALL.register(modEventBus);
        ModEntities.ENTITY_TYPES.register(modEventBus);
        ModSounds.SOUND_EVENTS.register(modEventBus);
        ModEffects.MOB_EFFECTS.register(modEventBus);
        ModParticles.PARTICLE_TYPES.register(modEventBus);
        ModPotions.POTIONS.register(modEventBus);
        ModStructures.STRUCTURES_ALL.register(modEventBus);
        ModFeatureTypes.FEATURES_ALL.register(modEventBus);

        // Register items to a creative tab
        modEventBus.addListener(this::addCreative);

        // Register our mod's ModConfigSpec so that FML can create and load the config file for us
        modContainer.registerConfig(ModConfig.Type.COMMON, Config.SPEC);
    }

    private void commonSetup(final FMLCommonSetupEvent event)
    {
    }

    // Add the example block item to the building blocks tab
    private void addCreative(BuildCreativeModeTabContentsEvent event)
    {
        if (event.getTabKey() == CreativeModeTabs.BUILDING_BLOCKS)
        {
            event.insertBefore(Items.NETHERRACK.getDefaultInstance(), ModItems.FLOWSTONE.toStack(), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
            event.insertAfter(ModItems.FLOWSTONE.toStack(), ModItems.COBBLED_FLOWSTONE.toStack(), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
            event.insertAfter(ModItems.COBBLED_FLOWSTONE.toStack(), ModItems.COBBLED_FLOWSTONE_SLAB.toStack(), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
            event.insertAfter(ModItems.COBBLED_FLOWSTONE_SLAB.toStack(), ModItems.COBBLED_FLOWSTONE_STAIRS.toStack(), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
            event.insertAfter(ModItems.COBBLED_FLOWSTONE_STAIRS.toStack(), ModItems.FLOWSTONE_BRICKS.toStack(), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
            event.insertAfter(ModItems.FLOWSTONE_BRICKS.toStack(), ModItems.FLOWSTONE_BRICK_SLAB.toStack(), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
            event.insertAfter(ModItems.FLOWSTONE_BRICK_SLAB.toStack(), ModItems.FLOWSTONE_BRICK_STAIRS.toStack(), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
            event.insertAfter(ModItems.FLOWSTONE_BRICK_STAIRS.toStack(), ModItems.FLOWSTONE_BRICK_WALL.toStack(), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);

            event.insertAfter(Items.IRON_BLOCK.getDefaultInstance(), ModItems.COBALT_BLOCK.toStack(), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
            event.insertBefore(Items.NETHERITE_BLOCK.getDefaultInstance(), ModItems.SOUL_STEEL_BLOCK.toStack(), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);

            event.insertAfter(Items.CHERRY_BUTTON.getDefaultInstance(), ModItems.STONEWOOD_LOG.toStack(), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
            event.insertAfter(ModItems.STONEWOOD_LOG.toStack(), ModItems.STONEWOOD.toStack(), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
            event.insertAfter(ModItems.STONEWOOD.toStack(), ModItems.STRIPPED_STONEWOOD_LOG.toStack(), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
            event.insertAfter(ModItems.STRIPPED_STONEWOOD_LOG.toStack(), ModItems.STRIPPED_STONEWOOD.toStack(), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
            event.insertAfter(ModItems.STRIPPED_STONEWOOD.toStack(), ModItems.STONEWOOD_PLANKS.toStack(), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
            event.insertAfter(ModItems.STONEWOOD_PLANKS.toStack(), ModItems.STONEWOOD_STAIRS.toStack(), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
            event.insertAfter(ModItems.STONEWOOD_STAIRS.toStack(), ModItems.STONEWOOD_SLAB.toStack(), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
            event.insertAfter(ModItems.STONEWOOD_SLAB.toStack(), ModItems.STONEWOOD_FENCE.toStack(), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
            event.insertAfter(ModItems.STONEWOOD_FENCE.toStack(), ModItems.STONEWOOD_FENCE_GATE.toStack(), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
            event.insertAfter(ModItems.STONEWOOD_FENCE_GATE.toStack(), ModItems.STONEWOOD_DOOR.toStack(), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
            event.insertAfter(ModItems.STONEWOOD_DOOR.toStack(), ModItems.STONEWOOD_TRAPDOOR.toStack(), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
            event.insertAfter(ModItems.STONEWOOD_TRAPDOOR.toStack(), ModItems.STONEWOOD_PRESSURE_PLATE.toStack(), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
            event.insertAfter(ModItems.STONEWOOD_PRESSURE_PLATE.toStack(), ModItems.STONEWOOD_BUTTON.toStack(), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
        }
        if (event.getTabKey() == CreativeModeTabs.REDSTONE_BLOCKS)
        {
            event.insertAfter(Items.REDSTONE_BLOCK.getDefaultInstance(), ModItems.REDSTONE_PILLAR_BLOCK.toStack(), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
        }
        if (event.getTabKey() == CreativeModeTabs.FUNCTIONAL_BLOCKS)
        {
            event.insertAfter(Items.BAMBOO_HANGING_SIGN.getDefaultInstance(), ModItems.STONEWOOD_SIGN.toStack(), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
            event.insertAfter(ModItems.STONEWOOD_SIGN.toStack(), ModItems.STONEWOOD_HANGING_SIGN.toStack(), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
        }
        if (event.getTabKey() == CreativeModeTabs.FOOD_AND_DRINKS)
        {
            event.insertAfter(Items.ENCHANTED_GOLDEN_APPLE.getDefaultInstance(), ModItems.PEAR.toStack(), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
            event.insertAfter(ModItems.PEAR.toStack(), ModItems.MYSTIC_PEAR.toStack(), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
            event.insertAfter(ModItems.MYSTIC_PEAR.toStack(), ModItems.ENCHANTED_MYSTIC_PEAR.toStack(), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
            event.insertBefore(Items.SWEET_BERRIES.getDefaultInstance(), ModItems.CHERRIES.toStack(), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
            event.insertAfter(ModItems.CHERRIES.toStack(), ModItems.GEODE_NUT.toStack(), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
            event.insertAfter(ModItems.GEODE_NUT.toStack(), ModItems.GEODE_FRUIT.toStack(), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
            event.insertAfter(ModItems.GEODE_FRUIT.toStack(), ModItems.MILK_BERRIES.toStack(), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
        }
        if (event.getTabKey() == CreativeModeTabs.NATURAL_BLOCKS)
        {
            event.insertBefore(Items.SHROOMLIGHT.getDefaultInstance(), ModItems.DIMLIGHT_STEM.toStack(), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
            event.insertBefore(ModItems.DIMLIGHT_STEM.toStack(), ModItems.DIMLIGHT.toStack(), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);

            event.insertAfter(Items.SNOW.getDefaultInstance(), ModItems.DUST_BLOCK.toStack(), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
            event.insertAfter(ModItems.DUST_BLOCK.toStack(), ModItems.DUST_CLUMP.toStack(), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);

            event.insertAfter(Items.WARPED_FUNGUS.getDefaultInstance(), ModItems.GLOOMCAP.toStack(), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
            event.insertAfter(Items.NETHER_QUARTZ_ORE.getDefaultInstance(), ModItems.NETHER_IRON_ORE.toStack(), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);

            event.insertAfter(Items.DEEPSLATE_COAL_ORE.getDefaultInstance(), ModItems.DENSE_COAL_ORE.toStack(), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
            event.insertAfter(Items.DEEPSLATE_IRON_ORE.getDefaultInstance(), ModItems.DENSE_IRON_ORE.toStack(), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
            event.insertAfter(Items.DEEPSLATE_GOLD_ORE.getDefaultInstance(), ModItems.DENSE_GOLD_ORE.toStack(), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
            event.insertAfter(Items.DEEPSLATE_REDSTONE_ORE.getDefaultInstance(), ModItems.DENSE_REDSTONE_ORE.toStack(), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
            event.insertAfter(ModItems.DENSE_GOLD_ORE.toStack(), ModItems.COBALT_ORE.toStack(), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);

            event.insertAfter(Items.PRISMARINE.getDefaultInstance(), ModItems.FLOWSTONE.toStack(), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
            event.insertAfter(ModItems.FLOWSTONE.toStack(), ModItems.FLOWSTONE_CACHE.toStack(), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
            event.insertAfter(ModItems.FLOWSTONE_CACHE.toStack(), ModItems.COBBLED_FLOWSTONE.toStack(), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
            event.insertAfter(ModItems.COBBLED_FLOWSTONE.toStack(), ModItems.LUSH_FLOWSTONE.toStack(), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
            event.insertAfter(ModItems.LUSH_FLOWSTONE.toStack(), ModItems.LUSH_FOLIAGE.toStack(), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
            event.insertAfter(ModItems.LUSH_FOLIAGE.toStack(), ModItems.FLOWERING_LUSH_FOLIAGE.toStack(), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);

            event.insertAfter(Items.AMETHYST_CLUSTER.getDefaultInstance(), ModItems.POINTED_AMETHYST.toStack(), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
            event.insertBefore(Items.AMETHYST_BLOCK.getDefaultInstance(), ModItems.ENCRUSTED_BASALT.toStack(), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);

            event.insertAfter(Items.FLOWERING_AZALEA.getDefaultInstance(), ModItems.STONEWOOD_LEAVES.toStack(), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
            event.insertAfter(ModItems.STONEWOOD_LEAVES.toStack(), ModItems.STONEWOOD_LEAVES_FLOWERED.toStack(), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
            event.insertAfter(ModItems.STONEWOOD_LEAVES_FLOWERED.toStack(), ModItems.HANGING_VINES.toStack(), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
            event.insertAfter(ModItems.HANGING_VINES.toStack(), ModItems.HANGING_FLORA.toStack(), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
            event.insertAfter(Items.CHERRY_SAPLING.getDefaultInstance(), ModItems.STONEWOOD_SAPLING.toStack(), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
            event.insertAfter(ModItems.STONEWOOD_SAPLING.toStack(), ModItems.GEODE_NUT.toStack(), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
        }
        if (event.getTabKey() == CreativeModeTabs.INGREDIENTS)
        {
            event.insertAfter(Items.BLAZE_POWDER.getDefaultInstance(), ModItems.BRITTLE_POWDER.toStack(), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
            event.insertAfter(ModItems.BRITTLE_POWDER.toStack(), ModItems.GLOOMSPORES.toStack(), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
            event.insertAfter(Items.RAW_GOLD.getDefaultInstance(), ModItems.RAW_COBALT.toStack(), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
            event.insertAfter(Items.GOLD_INGOT.getDefaultInstance(), ModItems.COBALT_INGOT.toStack(), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
            event.insertBefore(Items.NETHERITE_INGOT.getDefaultInstance(), ModItems.SOUL_STEEL.toStack(), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
            event.insertAfter(Items.NETHERITE_INGOT.getDefaultInstance(), ModItems.BLACK_ICE_SHARD.toStack(), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
            event.insertAfter(ModItems.BLACK_ICE_SHARD.toStack(), ModItems.BLACK_ICE_GEM.toStack(), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);

            event.insertAfter(Items.ANCIENT_DEBRIS.getDefaultInstance(), ModItems.BLACK_ICE.toStack(), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);

            // Pre-Netherite smithing templates
            event.insertBefore(Items.NETHERITE_UPGRADE_SMITHING_TEMPLATE.getDefaultInstance(), ModItems.SOUL_STEEL_UPGRADE_SMITHING_TEMPLATE.toStack(), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
            event.insertBefore(ModItems.SOUL_STEEL_UPGRADE_SMITHING_TEMPLATE.toStack(), ModItems.GLOOMSTONE_UPGRADE_SMITHING_TEMPLATE.toStack(), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
            // Post-Netherite smithing templates
            event.insertAfter(Items.NETHERITE_UPGRADE_SMITHING_TEMPLATE.getDefaultInstance(), ModItems.BLACK_ICE_UPGRADE_SMITHING_TEMPLATE.toStack(), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
        }
        if (event.getTabKey() == CreativeModeTabs.TOOLS_AND_UTILITIES)
        {
            event.insertAfter(Items.RECOVERY_COMPASS.getDefaultInstance(), ModItems.WISHBONE.toStack(), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);

            event.insertAfter(Items.NETHERITE_HOE.getDefaultInstance(), ModItems.ALLSAW.toStack(), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);

            event.insertAfter(Items.MUSIC_DISC_WAIT.getDefaultInstance(), ModItems.WHIRLING_WORLD_DISC.toStack(), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);

            event.insertAfter(Items.DIAMOND_HOE.getDefaultInstance(), ModItems.SOUL_STEEL_SHOVEL.toStack(), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
            event.insertAfter(ModItems.SOUL_STEEL_SHOVEL.toStack(), ModItems.SOUL_STEEL_PICKAXE.toStack(), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
            event.insertAfter(ModItems.SOUL_STEEL_PICKAXE.toStack(), ModItems.SOUL_STEEL_AXE.toStack(), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
            event.insertAfter(ModItems.SOUL_STEEL_AXE.toStack(), ModItems.SOUL_STEEL_HOE.toStack(), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);

            event.insertAfter(Items.GOLDEN_HOE.getDefaultInstance(), ModItems.GLOOMSTONE_SHOVEL.toStack(), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
            event.insertAfter(ModItems.GLOOMSTONE_SHOVEL.toStack(), ModItems.GLOOMSTONE_PICKAXE.toStack(), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
            event.insertAfter(ModItems.GLOOMSTONE_PICKAXE.toStack(), ModItems.GLOOMSTONE_AXE.toStack(), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
            event.insertAfter(ModItems.GLOOMSTONE_AXE.toStack(), ModItems.GLOOMSTONE_HOE.toStack(), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);

            event.insertAfter(ModItems.GLOOMSTONE_HOE.toStack(), ModItems.COBALT_SHOVEL.toStack(), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
            event.insertAfter(ModItems.COBALT_SHOVEL.toStack(), ModItems.COBALT_PICKAXE.toStack(), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
            event.insertAfter(ModItems.COBALT_PICKAXE.toStack(), ModItems.COBALT_AXE.toStack(), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
            event.insertAfter(ModItems.COBALT_AXE.toStack(), ModItems.COBALT_HOE.toStack(), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);

            event.insertAfter(Items.NETHERITE_HOE.getDefaultInstance(), ModItems.BLACK_ICE_SHOVEL.toStack(), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
            event.insertAfter(ModItems.BLACK_ICE_SHOVEL.toStack(), ModItems.BLACK_ICE_PICKAXE.toStack(), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
            event.insertAfter(ModItems.BLACK_ICE_PICKAXE.toStack(), ModItems.BLACK_ICE_AXE.toStack(), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
            event.insertAfter(ModItems.BLACK_ICE_AXE.toStack(), ModItems.BLACK_ICE_HOE.toStack(), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);

            event.insertAfter(Items.ELYTRA.getDefaultInstance(), ModItems.MAGIC_SPUR.toStack(), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
            event.insertAfter(ModItems.MAGIC_SPUR.toStack(), ModItems.SKELETON_ARM.toStack(), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
            event.insertAfter(ModItems.SKELETON_ARM.toStack(), ModItems.GLITTERING_ASPECT.toStack(), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
        }
        if (event.getTabKey() == CreativeModeTabs.COMBAT)
        {
            event.insertAfter(Items.DIAMOND_SWORD.getDefaultInstance(), ModItems.SOUL_STEEL_SWORD.toStack(), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
            event.insertAfter(Items.DIAMOND_AXE.getDefaultInstance(), ModItems.SOUL_STEEL_AXE.toStack(), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);

            event.insertAfter(Items.GOLDEN_SWORD.getDefaultInstance(), ModItems.GLOOMSTONE_SWORD.toStack(), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
            event.insertAfter(Items.GOLDEN_AXE.getDefaultInstance(), ModItems.GLOOMSTONE_AXE.toStack(), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);

            event.insertAfter(ModItems.GLOOMSTONE_SWORD.toStack(), ModItems.COBALT_SWORD.toStack(), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
            event.insertAfter(ModItems.GLOOMSTONE_AXE.toStack(), ModItems.COBALT_AXE.toStack(), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);

            event.insertAfter(Items.NETHERITE_SWORD.getDefaultInstance(), ModItems.BLACK_ICE_SWORD.toStack(), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
            event.insertAfter(Items.NETHERITE_AXE.getDefaultInstance(), ModItems.BLACK_ICE_AXE.toStack(), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);

            event.insertAfter(Items.BOW.getDefaultInstance(), ModItems.SOUL_STEEL_BOW.toStack(), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
            event.insertAfter(ModItems.SOUL_STEEL_BOW.toStack(), ModItems.BLACK_ICE_BOW.toStack(), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);

            event.insertAfter(Items.CROSSBOW.getDefaultInstance(), ModItems.NETHERITE_CROSSBOW.toStack(), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
            event.insertAfter(ModItems.NETHERITE_CROSSBOW.toStack(), ModItems.BLACK_ICE_CROSSBOW.toStack(), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);

            event.insertAfter(Items.TURTLE_HELMET.getDefaultInstance(), ModItems.BREEZE_BOOTS.toStack(), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);

            event.insertAfter(Items.DIAMOND_BOOTS.getDefaultInstance(), ModItems.SOUL_STEEL_HELMET.toStack(), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
            event.insertAfter(ModItems.SOUL_STEEL_HELMET.toStack(), ModItems.SOUL_STEEL_CHESTPLATE.toStack(), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
            event.insertAfter(ModItems.SOUL_STEEL_CHESTPLATE.toStack(), ModItems.SOUL_STEEL_LEGGINGS.toStack(), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
            event.insertAfter(ModItems.SOUL_STEEL_LEGGINGS.toStack(), ModItems.SOUL_STEEL_BOOTS.toStack(), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);

            event.insertAfter(Items.NETHERITE_BOOTS.getDefaultInstance(), ModItems.BLACK_ICE_HELMET.toStack(), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
            event.insertAfter(ModItems.BLACK_ICE_HELMET.toStack(), ModItems.BLACK_ICE_CHESTPLATE.toStack(), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
            event.insertAfter(ModItems.BLACK_ICE_CHESTPLATE.toStack(), ModItems.BLACK_ICE_LEGGINGS.toStack(), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
            event.insertAfter(ModItems.BLACK_ICE_LEGGINGS.toStack(), ModItems.BLACK_ICE_BOOTS.toStack(), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);

            event.insertAfter(Items.GOLDEN_BOOTS.getDefaultInstance(), ModItems.COBALT_HELMET.toStack(), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
            event.insertAfter(ModItems.COBALT_HELMET.toStack(), ModItems.COBALT_CHESTPLATE.toStack(), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
            event.insertAfter(ModItems.COBALT_CHESTPLATE.toStack(), ModItems.COBALT_LEGGINGS.toStack(), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
            event.insertAfter(ModItems.COBALT_LEGGINGS.toStack(), ModItems.COBALT_BOOTS.toStack(), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);

            event.insertAfter(ModItems.BREEZE_BOOTS.toStack(), ModItems.IRON_CUFF.toStack(), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
            event.insertAfter(ModItems.IRON_CUFF.toStack(), ModItems.COBALT_CUFF.toStack(), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
            event.insertAfter(ModItems.COBALT_CUFF.toStack(), ModItems.GOLEM_HEART.toStack(), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
            event.insertAfter(ModItems.GOLEM_HEART.toStack(), ModItems.OBSIDIAN_KEEPSAKE.toStack(), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
            event.insertAfter(ModItems.OBSIDIAN_KEEPSAKE.toStack(), ModItems.BLAZE_LOCKET.toStack(), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
            event.insertAfter(ModItems.BLAZE_LOCKET.toStack(), ModItems.BREEZE_RING.toStack(), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
            event.insertAfter(ModItems.BREEZE_RING.toStack(), ModItems.APOCALYPSE_CORE.toStack(), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
        }
        if (event.getTabKey() == CreativeModeTabs.SPAWN_EGGS)
        {
            event.accept(ModItems.BLOOMED_SPAWN_EGG);
            event.accept(ModItems.SOUL_GOLEM_SPAWN_EGG);
            event.accept(ModItems.BRITTLE_SPAWN_EGG);
            event.accept(ModItems.DUSTBUG_SPAWN_EGG);
            event.accept(ModItems.GOLIATH_SPAWN_EGG);
            event.accept(ModItems.AMETHYST_CONSTRUCT_SPAWN_EGG);
        }
    }

    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event)
    {
        // Do something when the server starts
        LOGGER.info("HELLO from server starting");
    }

    @EventBusSubscriber(modid = MOD_ID, value = Dist.CLIENT)
    public static class ClientModEvents
    {
        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event)
        {
            for (NonGeoEntityLayerDefinition<Entity> def : ModEntities.mobDefinitions) {
                EntityRenderers.register(def.type.get(), def.renderer);
            }

            for (GeoEntityRendererDefinition<Entity> def : ModEntities.geoMobDefinitions) {
                EntityRenderers.register(def.type.get(), def.renderer);
            }

            ModItemProperties.addCustomItemProperties();
        }
    }
}