package net.ennway.farworld;

import net.ennway.farworld.entity.client.bloomed.BloomedRenderer;
import net.ennway.farworld.entity.client.soulgolem.SoulGolemRenderer;
import net.ennway.farworld.registries.*;
import net.minecraft.client.renderer.entity.EntityRenderers;
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

        // Register the item to a creative tab
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
            event.insertAfter(Items.NETHERITE_BLOCK.getDefaultInstance(), ModItems.SOUL_STEEL_BLOCK.toStack(), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
        }
        if (event.getTabKey() == CreativeModeTabs.FOOD_AND_DRINKS)
        {
            event.insertAfter(Items.ENCHANTED_GOLDEN_APPLE.getDefaultInstance(), ModItems.PEAR.toStack(), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
            event.insertAfter(ModItems.PEAR.toStack(), ModItems.MYSTIC_PEAR.toStack(), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
            event.insertAfter(ModItems.MYSTIC_PEAR.toStack(), ModItems.ENCHANTED_MYSTIC_PEAR.toStack(), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
            event.insertBefore(Items.SWEET_BERRIES.getDefaultInstance(), ModItems.CHERRIES.toStack(), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
        }
        if (event.getTabKey() == CreativeModeTabs.NATURAL_BLOCKS)
        {
            event.insertAfter(Items.WARPED_FUNGUS.getDefaultInstance(), ModItems.GLOOMCAP_BLOCK.toStack(), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
            event.insertAfter(Items.NETHER_QUARTZ_ORE.getDefaultInstance(), ModItems.NETHER_IRON_ORE.toStack(), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);

            event.insertAfter(Items.DEEPSLATE_COAL_ORE.getDefaultInstance(), ModItems.DENSE_COAL_ORE.toStack(), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
            event.insertAfter(Items.DEEPSLATE_IRON_ORE.getDefaultInstance(), ModItems.DENSE_IRON_ORE.toStack(), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
            event.insertAfter(Items.DEEPSLATE_GOLD_ORE.getDefaultInstance(), ModItems.DENSE_GOLD_ORE.toStack(), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
            event.insertAfter(ModItems.DENSE_GOLD_ORE.toStack(), ModItems.IRIDIUM_ORE.toStack(), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);

            event.insertAfter(Items.PRISMARINE.getDefaultInstance(), ModItems.FLOWSTONE.toStack(), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
            event.insertAfter(ModItems.FLOWSTONE.toStack(), ModItems.FLOWSTONE_CACHE.toStack(), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
        }
        if (event.getTabKey() == CreativeModeTabs.INGREDIENTS)
        {
            event.insertAfter(Items.BLAZE_POWDER.getDefaultInstance(), ModItems.GLOOMSPORES.toStack(), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
            event.insertAfter(Items.RAW_GOLD.getDefaultInstance(), ModItems.RAW_IRIDIUM.toStack(), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
            event.insertAfter(Items.GOLD_INGOT.getDefaultInstance(), ModItems.IRIDIUM_INGOT.toStack(), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
            event.insertBefore(Items.NETHERITE_INGOT.getDefaultInstance(), ModItems.SOUL_STEEL.toStack(), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
            event.insertBefore(Items.NETHERITE_UPGRADE_SMITHING_TEMPLATE.getDefaultInstance(), ModItems.SOUL_STEEL_UPGRADE_SMITHING_TEMPLATE.toStack(), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
        }
        if (event.getTabKey() == CreativeModeTabs.TOOLS_AND_UTILITIES)
        {
            event.insertAfter(Items.COMPASS.getDefaultInstance(), ModItems.WISHBONE.toStack(), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);

            event.insertAfter(Items.NETHERITE_HOE.getDefaultInstance(), ModItems.ALLSAW.toStack(), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);

            event.insertAfter(Items.MUSIC_DISC_WAIT.getDefaultInstance(), ModItems.WHIRLING_WORLD_DISC.toStack(), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);

            event.insertAfter(Items.DIAMOND_HOE.getDefaultInstance(), ModItems.SOUL_STEEL_SHOVEL.toStack(), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
            event.insertAfter(ModItems.SOUL_STEEL_SHOVEL.toStack(), ModItems.SOUL_STEEL_PICKAXE.toStack(), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
            event.insertAfter(ModItems.SOUL_STEEL_PICKAXE.toStack(), ModItems.SOUL_STEEL_AXE.toStack(), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
            event.insertAfter(ModItems.SOUL_STEEL_AXE.toStack(), ModItems.SOUL_STEEL_HOE.toStack(), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);

            event.insertAfter(Items.GOLDEN_HOE.getDefaultInstance(), ModItems.IRIDIUM_SHOVEL.toStack(), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
            event.insertAfter(ModItems.IRIDIUM_SHOVEL.toStack(), ModItems.IRIDIUM_PICKAXE.toStack(), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
            event.insertAfter(ModItems.IRIDIUM_PICKAXE.toStack(), ModItems.IRIDIUM_AXE.toStack(), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
            event.insertAfter(ModItems.IRIDIUM_AXE.toStack(), ModItems.IRIDIUM_HOE.toStack(), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
        }
        if (event.getTabKey() == CreativeModeTabs.COMBAT)
        {
            event.insertAfter(Items.DIAMOND_SWORD.getDefaultInstance(), ModItems.SOUL_STEEL_SWORD.toStack(), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
            event.insertAfter(Items.DIAMOND_AXE.getDefaultInstance(), ModItems.SOUL_STEEL_AXE.toStack(), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);

            event.insertAfter(Items.GOLDEN_SWORD.getDefaultInstance(), ModItems.IRIDIUM_SWORD.toStack(), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
            event.insertAfter(Items.GOLDEN_AXE.getDefaultInstance(), ModItems.IRIDIUM_AXE.toStack(), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);

            event.insertAfter(Items.TURTLE_HELMET.getDefaultInstance(), ModItems.BREEZE_BOOTS.toStack(), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);

            event.insertAfter(Items.DIAMOND_BOOTS.getDefaultInstance(), ModItems.SOUL_STEEL_HELMET.toStack(), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
            event.insertAfter(ModItems.SOUL_STEEL_HELMET.toStack(), ModItems.SOUL_STEEL_CHESTPLATE.toStack(), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
            event.insertAfter(ModItems.SOUL_STEEL_CHESTPLATE.toStack(), ModItems.SOUL_STEEL_LEGGINGS.toStack(), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
            event.insertAfter(ModItems.SOUL_STEEL_LEGGINGS.toStack(), ModItems.SOUL_STEEL_BOOTS.toStack(), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);

            event.insertAfter(Items.GOLDEN_BOOTS.getDefaultInstance(), ModItems.IRIDIUM_HELMET.toStack(), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
            event.insertAfter(ModItems.IRIDIUM_HELMET.toStack(), ModItems.IRIDIUM_CHESTPLATE.toStack(), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
            event.insertAfter(ModItems.IRIDIUM_CHESTPLATE.toStack(), ModItems.IRIDIUM_LEGGINGS.toStack(), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
            event.insertAfter(ModItems.IRIDIUM_LEGGINGS.toStack(), ModItems.IRIDIUM_BOOTS.toStack(), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
        }
        if (event.getTabKey() == CreativeModeTabs.SPAWN_EGGS)
        {
            event.accept(ModItems.BLOOMED_SPAWN_EGG);
            event.accept(ModItems.SOUL_GOLEM_SPAWN_EGG);
        }
    }

    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event)
    {
        // Do something when the server starts
        LOGGER.info("HELLO from server starting");
    }

    @EventBusSubscriber(modid = MOD_ID, bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModEvents
    {
        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event)
        {
            EntityRenderers.register(ModEntities.BLOOMED.get(), BloomedRenderer::new);
            EntityRenderers.register(ModEntities.SOUL_GOLEM.get(), SoulGolemRenderer::new);
        }
    }
}
