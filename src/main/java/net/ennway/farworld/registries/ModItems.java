package net.ennway.farworld.registries;

import net.ennway.farworld.Farworld;
import net.ennway.farworld.item.*;
import net.ennway.farworld.item.tool.*;
import net.ennway.farworld.item.tool.iridium.*;
import net.ennway.farworld.item.tool.soul_steel.*;
import net.ennway.farworld.utils.SmithingUtils;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.*;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModItems
{
    public static final DeferredRegister.Items ITEMS_ALL = DeferredRegister.createItems(Farworld.MOD_ID);

    public static final DeferredItem<Item> CHERRIES = ITEMS_ALL.register("cherries",
            Cherries::new);
    public static final DeferredItem<Item> PEAR = ITEMS_ALL.register("pear",
            Pear::new);
    public static final DeferredItem<Item> MYSTIC_PEAR = ITEMS_ALL.register("mystic_pear",
            MysticPear::new);
    public static final DeferredItem<Item> ENCHANTED_MYSTIC_PEAR = ITEMS_ALL.register("enchanted_mystic_pear",
            EnchantedMysticPear::new);
    public static final DeferredItem<Item> ALLSAW = ITEMS_ALL.register("allsaw",
            Allsaw::new);

    public static final DeferredItem<Item> WISHBONE = ITEMS_ALL.register("wishbone",
            Wishbone::new);

    public static final DeferredItem<Item> GLOOMSPORES = ITEMS_ALL.register("gloomspores",
            () -> new Item(new Item.Properties()));
    public static final DeferredItem<Item> SOUL_STEEL = ITEMS_ALL.register("soul_steel",
            () -> new Item(new Item.Properties()));
    public static final DeferredItem<Item> RAW_IRIDIUM = ITEMS_ALL.register("raw_iridium",
            () -> new Item(new Item.Properties()));
    public static final DeferredItem<Item> IRIDIUM_INGOT = ITEMS_ALL.register("iridium_ingot",
            () -> new Item(new Item.Properties()));
    public static final DeferredItem<Item> SOUL_STEEL_UPGRADE_SMITHING_TEMPLATE = ITEMS_ALL.register("soul_steel_upgrade_smithing_template",
            ModItems::createSoulSteelUpgradeTemplate);

    //region Soul Steel Gear
    public static final DeferredItem<Item> SOUL_STEEL_SWORD = ITEMS_ALL.register(
            "soul_steel_sword",
            () -> new SoulSteelSword(new Item.Properties())
    );
    public static final DeferredItem<Item> SOUL_STEEL_AXE = ITEMS_ALL.register(
            "soul_steel_axe",
            () -> new SoulSteelAxe(new Item.Properties())
    );
    public static final DeferredItem<Item> SOUL_STEEL_PICKAXE = ITEMS_ALL.register(
            "soul_steel_pickaxe",
            () -> new SoulSteelPickaxe(new Item.Properties())
    );
    public static final DeferredItem<Item> SOUL_STEEL_SHOVEL = ITEMS_ALL.register(
            "soul_steel_shovel",
            () -> new SoulSteelShovel(new Item.Properties())
    );
    public static final DeferredItem<Item> SOUL_STEEL_HOE = ITEMS_ALL.register(
            "soul_steel_hoe",
            () -> new SoulSteelHoe(new Item.Properties())
    );

    public static final DeferredItem<ArmorItem> SOUL_STEEL_HELMET = ITEMS_ALL.register(
            "soul_steel_helmet",
            () -> new ArmorItem(ModArmorMaterials.SOUL_STEEL_ARMOR_MATERIAL,
                    ArmorItem.Type.HELMET,
                    new Item.Properties().durability(312))
    );

    public static final DeferredItem<ArmorItem> SOUL_STEEL_CHESTPLATE = ITEMS_ALL.register(
            "soul_steel_chestplate",
            () -> new ArmorItem(ModArmorMaterials.SOUL_STEEL_ARMOR_MATERIAL,
                    ArmorItem.Type.CHESTPLATE,
                    new Item.Properties().durability(428))
    );

    public static final DeferredItem<ArmorItem> SOUL_STEEL_LEGGINGS = ITEMS_ALL.register(
            "soul_steel_leggings",
            () -> new ArmorItem(ModArmorMaterials.SOUL_STEEL_ARMOR_MATERIAL,
                    ArmorItem.Type.LEGGINGS,
                    new Item.Properties().durability(350))
    );

    public static final DeferredItem<ArmorItem> SOUL_STEEL_BOOTS = ITEMS_ALL.register(
            "soul_steel_boots",
            () -> new ArmorItem(ModArmorMaterials.SOUL_STEEL_ARMOR_MATERIAL,
                    ArmorItem.Type.BOOTS,
                    new Item.Properties().durability(290))
    );
    //endregion

    //region Iridium Gear
    public static final DeferredItem<Item> IRIDIUM_SWORD = ITEMS_ALL.register(
            "iridium_sword",
            () -> new IridiumSword(new Item.Properties())
    );
    public static final DeferredItem<Item> IRIDIUM_AXE = ITEMS_ALL.register(
            "iridium_axe",
            () -> new IridiumAxe(new Item.Properties())
    );
    public static final DeferredItem<Item> IRIDIUM_PICKAXE = ITEMS_ALL.register(
            "iridium_pickaxe",
            () -> new IridiumPickaxe(new Item.Properties())
    );
    public static final DeferredItem<Item> IRIDIUM_SHOVEL = ITEMS_ALL.register(
            "iridium_shovel",
            () -> new IridiumShovel(new Item.Properties())
    );
    public static final DeferredItem<Item> IRIDIUM_HOE = ITEMS_ALL.register(
            "iridium_hoe",
            () -> new IridiumHoe(new Item.Properties())
    );

    public static final DeferredItem<ArmorItem> IRIDIUM_HELMET = ITEMS_ALL.register(
            "iridium_helmet",
            () -> new ArmorItem(ModArmorMaterials.IRIDIUM_ARMOR_MATERIAL,
                    ArmorItem.Type.HELMET,
                    new Item.Properties().durability(312))
    );

    public static final DeferredItem<ArmorItem> IRIDIUM_CHESTPLATE = ITEMS_ALL.register(
            "iridium_chestplate",
            () -> new ArmorItem(ModArmorMaterials.IRIDIUM_ARMOR_MATERIAL,
                    ArmorItem.Type.CHESTPLATE,
                    new Item.Properties().durability(428))
    );

    public static final DeferredItem<ArmorItem> IRIDIUM_LEGGINGS = ITEMS_ALL.register(
            "iridium_leggings",
            () -> new ArmorItem(ModArmorMaterials.IRIDIUM_ARMOR_MATERIAL,
                    ArmorItem.Type.LEGGINGS,
                    new Item.Properties().durability(350))
    );

    public static final DeferredItem<ArmorItem> IRIDIUM_BOOTS = ITEMS_ALL.register(
            "iridium_boots",
            () -> new ArmorItem(ModArmorMaterials.IRIDIUM_ARMOR_MATERIAL,
                    ArmorItem.Type.BOOTS,
                    new Item.Properties().durability(290))
    );
    //endregion

    public static final DeferredItem<ArmorItem> BREEZE_BOOTS = ITEMS_ALL.register(
            "breeze_boots", BreezeBoots::new
    );

    public static SmithingTemplateItem createSoulSteelUpgradeTemplate() {
        return new SmithingTemplateItem(
                Component.translatable("item.farworld.soul_steel_upgrade_smithing_template.applies_to"),
                Component.translatable("item.farworld.soul_steel_upgrade_smithing_template.ingredient"),
                Component.translatable("item.farworld.soul_steel_upgrade_smithing_template.type"),
                Component.translatable("item.farworld.soul_steel_upgrade_smithing_template.base_slot_description"),
                Component.translatable("item.farworld.soul_steel_upgrade_smithing_template.additions_slot_description"),
                SmithingUtils.equipmentIconList(),
                SmithingUtils.singleIngotIcon());
    }

    public static final DeferredItem<Item> GLOOMCAP_BLOCK = ITEMS_ALL.register("gloomcap",
            () -> new BlockItem(ModBlocks.GLOOMCAP.get(), new Item.Properties()).asItem());
    public static final DeferredItem<Item> NETHER_IRON_ORE = ITEMS_ALL.register("nether_iron_ore",
            () -> new BlockItem(ModBlocks.NETHER_IRON_ORE.get(), new Item.Properties()).asItem());
    public static final DeferredItem<Item> SOUL_STEEL_BLOCK = ITEMS_ALL.register("soul_steel_block",
            () -> new BlockItem(ModBlocks.SOUL_STEEL_BLOCK.get(), new Item.Properties()).asItem());
    public static final DeferredItem<Item> FLOWSTONE = ITEMS_ALL.register("flowstone",
            () -> new BlockItem(ModBlocks.FLOWSTONE.get(), new Item.Properties()).asItem());
    public static final DeferredItem<Item> COBBLED_FLOWSTONE = ITEMS_ALL.register("cobbled_flowstone",
            () -> new BlockItem(ModBlocks.COBBLED_FLOWSTONE.get(), new Item.Properties()).asItem());
    public static final DeferredItem<Item> LUSH_FLOWSTONE = ITEMS_ALL.register("lush_flowstone",
            () -> new BlockItem(ModBlocks.LUSH_FLOWSTONE.get(), new Item.Properties()).asItem());
    public static final DeferredItem<Item> DENSE_COAL_ORE = ITEMS_ALL.register("dense_coal_ore",
            () -> new BlockItem(ModBlocks.DENSE_COAL_ORE.get(), new Item.Properties()).asItem());
    public static final DeferredItem<Item> DENSE_IRON_ORE = ITEMS_ALL.register("dense_iron_ore",
            () -> new BlockItem(ModBlocks.DENSE_IRON_ORE.get(), new Item.Properties()).asItem());
    public static final DeferredItem<Item> DENSE_GOLD_ORE = ITEMS_ALL.register("dense_gold_ore",
            () -> new BlockItem(ModBlocks.DENSE_GOLD_ORE.get(), new Item.Properties()).asItem());
    public static final DeferredItem<Item> IRIDIUM_ORE = ITEMS_ALL.register("iridium_ore",
            () -> new BlockItem(ModBlocks.IRIDIUM_ORE.get(), new Item.Properties()).asItem());
    public static final DeferredItem<Item> FLOWSTONE_CACHE = ITEMS_ALL.register("flowstone_cache",
            () -> new BlockItem(ModBlocks.FLOWSTONE_CACHE.get(), new Item.Properties()).asItem());

    public static final DeferredItem<Item> BLOOMED_SPAWN_EGG = ITEMS_ALL.register("bloomed_spawn_egg",
            () -> new SpawnEggItem(ModEntities.BLOOMED.get(), 0x9B691F, 0x9BFC1F, new Item.Properties()));
    public static final DeferredItem<Item> SOUL_GOLEM_SPAWN_EGG = ITEMS_ALL.register("soul_golem_spawn_egg",
            () -> new SpawnEggItem(ModEntities.SOUL_GOLEM.get(), 0x494358, 0xFE8738, new Item.Properties()));

    public static final DeferredItem<Item> WHIRLING_WORLD_DISC = ITEMS_ALL.register("disc_whirlingworld",
            () -> new Item(new Item.Properties()
                    .jukeboxPlayable(ModSounds.WHIRLING_WORLD_KEY)
                    .rarity(Rarity.RARE)));
}