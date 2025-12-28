package net.ennway.farworld.registries;

import net.ennway.farworld.Farworld;
import net.ennway.farworld.item.*;
import net.ennway.farworld.item.AccessoryItem;
import net.ennway.farworld.item.accessory.*;
import net.ennway.farworld.item.tool.*;
import net.ennway.farworld.item.tool.black_ice.*;
import net.ennway.farworld.item.tool.cobalt.*;
import net.ennway.farworld.item.tool.gloomstone.*;
import net.ennway.farworld.item.tool.soul_steel.*;
import net.ennway.farworld.utils.SmithingUtils;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.*;
import net.minecraft.world.item.component.ItemAttributeModifiers;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModItems
{
    public static final DeferredRegister.Items ITEMS_ALL = DeferredRegister.createItems(Farworld.MOD_ID);

    public static final DeferredItem<Item> CHERRIES = ITEMS_ALL.register("cherries",
            Cherries::new);
    public static final DeferredItem<Item> PEAR = ITEMS_ALL.register("pear",
            Pear::new);
    public static final DeferredItem<Item> MILK_BERRIES = ITEMS_ALL.register("milk_berries",
            MilkBerries::new);
    public static final DeferredItem<Item> GEODE_FRUIT = ITEMS_ALL.register("geode_fruit",
            GeodeFruit::new);
    public static final DeferredItem<Item> MYSTIC_PEAR = ITEMS_ALL.register("mystic_pear",
            MysticPear::new);
    public static final DeferredItem<Item> ENCHANTED_MYSTIC_PEAR = ITEMS_ALL.register("enchanted_mystic_pear",
            EnchantedMysticPear::new);
    public static final DeferredItem<Item> ALLSAW = ITEMS_ALL.register("allsaw",
            Allsaw::new);

    public static final DeferredItem<Item> WISHBONE = ITEMS_ALL.register("wishbone",
            Wishbone::new);

    public static final DeferredItem<BrittlePowder> BRITTLE_POWDER = ITEMS_ALL.register("brittle_powder",
            () -> new BrittlePowder(new BrittlePowder.Properties()));

    public static final DeferredItem<Item> GLOOMSPORES = ITEMS_ALL.register("gloomspores",
            () -> new Item(new Item.Properties()));
    public static final DeferredItem<Item> SOUL_STEEL = ITEMS_ALL.register("soul_steel",
            () -> new Item(new Item.Properties()));
    public static final DeferredItem<Item> RAW_COBALT = ITEMS_ALL.register("raw_cobalt",
            () -> new Item(new Item.Properties()));
    public static final DeferredItem<Item> COBALT_INGOT = ITEMS_ALL.register("cobalt_ingot",
            () -> new Item(new Item.Properties()));
    public static final DeferredItem<Item> BLACK_ICE_SHARD = ITEMS_ALL.register("black_ice_shard",
            () -> new Item(new Item.Properties()));
    public static final DeferredItem<Item> BLACK_ICE_GEM = ITEMS_ALL.register("black_ice_gem",
            () -> new Item(new Item.Properties()));
    public static final DeferredItem<Item> SOUL_STEEL_UPGRADE_SMITHING_TEMPLATE = ITEMS_ALL.register("soul_steel_upgrade_smithing_template",
            ModItems::createSoulSteelUpgradeTemplate);
    public static final DeferredItem<Item> GLOOMSTONE_UPGRADE_SMITHING_TEMPLATE = ITEMS_ALL.register("gloomstone_upgrade_smithing_template",
            ModItems::createGloomstoneUpgradeTemplate);
    public static final DeferredItem<Item> BLACK_ICE_UPGRADE_SMITHING_TEMPLATE = ITEMS_ALL.register("black_ice_upgrade_smithing_template",
            ModItems::createBlackIceUpgradeTemplate);

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
    public static final DeferredItem<Item> SOUL_STEEL_BOW = ITEMS_ALL.register(
            "soul_steel_bow",
            () -> new SoulSteelBow()
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

    //region Cobalt Gear
    public static final DeferredItem<Item> COBALT_SWORD = ITEMS_ALL.register(
            "cobalt_sword",
            () -> new CobaltSword(new Item.Properties())
    );
    public static final DeferredItem<Item> COBALT_AXE = ITEMS_ALL.register(
            "cobalt_axe",
            () -> new CobaltAxe(new Item.Properties())
    );
    public static final DeferredItem<Item> COBALT_PICKAXE = ITEMS_ALL.register(
            "cobalt_pickaxe",
            () -> new CobaltPickaxe(new Item.Properties())
    );
    public static final DeferredItem<Item> COBALT_SHOVEL = ITEMS_ALL.register(
            "cobalt_shovel",
            () -> new CobaltShovel(new Item.Properties())
    );
    public static final DeferredItem<Item> COBALT_HOE = ITEMS_ALL.register(
            "cobalt_hoe",
            () -> new CobaltHoe(new Item.Properties())
    );

    public static final DeferredItem<ArmorItem> COBALT_HELMET = ITEMS_ALL.register(
            "cobalt_helmet",
            () -> new ArmorItem(ModArmorMaterials.COBALT_ARMOR_MATERIAL,
                    ArmorItem.Type.HELMET,
                    new Item.Properties().durability(312))
    );

    public static final DeferredItem<ArmorItem> COBALT_CHESTPLATE = ITEMS_ALL.register(
            "cobalt_chestplate",
            () -> new ArmorItem(ModArmorMaterials.COBALT_ARMOR_MATERIAL,
                    ArmorItem.Type.CHESTPLATE,
                    new Item.Properties().durability(428))
    );

    public static final DeferredItem<ArmorItem> COBALT_LEGGINGS = ITEMS_ALL.register(
            "cobalt_leggings",
            () -> new ArmorItem(ModArmorMaterials.COBALT_ARMOR_MATERIAL,
                    ArmorItem.Type.LEGGINGS,
                    new Item.Properties().durability(350))
    );

    public static final DeferredItem<ArmorItem> COBALT_BOOTS = ITEMS_ALL.register(
            "cobalt_boots",
            () -> new ArmorItem(ModArmorMaterials.COBALT_ARMOR_MATERIAL,
                    ArmorItem.Type.BOOTS,
                    new Item.Properties().durability(290))
    );
    //endregion

    //region Gloomstone Gear
    public static final DeferredItem<Item> GLOOMSTONE_SWORD = ITEMS_ALL.register(
            "gloomstone_sword",
            () -> new GloomstoneSword(new Item.Properties())
    );
    public static final DeferredItem<Item> GLOOMSTONE_AXE = ITEMS_ALL.register(
            "gloomstone_axe",
            () -> new GloomstoneAxe(new Item.Properties())
    );
    public static final DeferredItem<Item> GLOOMSTONE_PICKAXE = ITEMS_ALL.register(
            "gloomstone_pickaxe",
            () -> new GloomstonePickaxe(new Item.Properties())
    );
    public static final DeferredItem<Item> GLOOMSTONE_SHOVEL = ITEMS_ALL.register(
            "gloomstone_shovel",
            () -> new GloomstoneShovel(new Item.Properties())
    );
    public static final DeferredItem<Item> GLOOMSTONE_HOE = ITEMS_ALL.register(
            "gloomstone_hoe",
            () -> new GloomstoneHoe(new Item.Properties())
    );
    //endregion

    //region Black Ice Gear
    public static final DeferredItem<Item> BLACK_ICE_SWORD = ITEMS_ALL.register(
            "black_ice_sword",
            () -> new BlackIceSword(new Item.Properties())
    );
    public static final DeferredItem<Item> BLACK_ICE_AXE = ITEMS_ALL.register(
            "black_ice_axe",
            () -> new BlackIceAxe(new Item.Properties())
    );
    public static final DeferredItem<Item> BLACK_ICE_PICKAXE = ITEMS_ALL.register(
            "black_ice_pickaxe",
            () -> new BlackIcePickaxe(new Item.Properties())
    );
    public static final DeferredItem<Item> BLACK_ICE_SHOVEL = ITEMS_ALL.register(
            "black_ice_shovel",
            () -> new BlackIceShovel(new Item.Properties())
    );
    public static final DeferredItem<Item> BLACK_ICE_HOE = ITEMS_ALL.register(
            "black_ice_hoe",
            () -> new BlackIceHoe(new Item.Properties())
    );
    public static final DeferredItem<Item> BLACK_ICE_BOW = ITEMS_ALL.register(
            "black_ice_bow",
            () -> new BlackIceBow(new Item.Properties())
    );
    public static final DeferredItem<Item> BLACK_ICE_CROSSBOW = ITEMS_ALL.register(
            "black_ice_crossbow",
            () -> new BlackIceCrossbow(new Item.Properties())
    );

    public static final DeferredItem<ArmorItem> BLACK_ICE_HELMET = ITEMS_ALL.register(
            "black_ice_helmet",
            () -> new ArmorItem(ModArmorMaterials.BLACK_ICE_ARMOR_MATERIAL,
                    ArmorItem.Type.HELMET,
                    new Item.Properties().durability(712))
    );

    public static final DeferredItem<ArmorItem> BLACK_ICE_CHESTPLATE = ITEMS_ALL.register(
            "black_ice_chestplate",
            () -> new ArmorItem(ModArmorMaterials.BLACK_ICE_ARMOR_MATERIAL,
                    ArmorItem.Type.CHESTPLATE,
                    new Item.Properties().durability(828))
    );

    public static final DeferredItem<ArmorItem> BLACK_ICE_LEGGINGS = ITEMS_ALL.register(
            "black_ice_leggings",
            () -> new ArmorItem(ModArmorMaterials.BLACK_ICE_ARMOR_MATERIAL,
                    ArmorItem.Type.LEGGINGS,
                    new Item.Properties().durability(750))
    );

    public static final DeferredItem<ArmorItem> BLACK_ICE_BOOTS = ITEMS_ALL.register(
            "black_ice_boots",
            () -> new ArmorItem(ModArmorMaterials.BLACK_ICE_ARMOR_MATERIAL,
                    ArmorItem.Type.BOOTS,
                    new Item.Properties().durability(690))
    );
    //endregion

    public static final DeferredItem<Item> NETHERITE_CROSSBOW = ITEMS_ALL.register(
            "netherite_crossbow",
            () -> new NetheriteCrossbow()
    );

    public static final DeferredItem<Item> OBSIDIAN_KEEPSAKE = ITEMS_ALL.register(
            "obsidian_keepsake",
            () -> new ObsidianKeepsake(new Item.Properties().attributes(ItemAttributeModifiers.builder().add(
                    Attributes.KNOCKBACK_RESISTANCE,
                    new AttributeModifier(Attributes.KNOCKBACK_RESISTANCE.getKey().location(), 0.2, AttributeModifier.Operation.ADD_VALUE),
                    EquipmentSlotGroup.ANY).build()))
    );

    public static final DeferredItem<Item> IRON_CUFF = ITEMS_ALL.register(
            "iron_cuff",
            () -> new AccessoryItem(new Item.Properties().attributes(ItemAttributeModifiers.builder().add(
                    Attributes.ARMOR,
                    new AttributeModifier(Attributes.ARMOR.getKey().location(), 2, AttributeModifier.Operation.ADD_VALUE),
                    EquipmentSlotGroup.ANY).build()))
    );

    public static final DeferredItem<Item> COBALT_CUFF = ITEMS_ALL.register(
            "cobalt_cuff",
            () -> new AccessoryItem(new Item.Properties().attributes(ItemAttributeModifiers.builder().add(
                    Attributes.ARMOR_TOUGHNESS,
                    new AttributeModifier(Attributes.ARMOR_TOUGHNESS.getKey().location(), 2, AttributeModifier.Operation.ADD_VALUE),
                    EquipmentSlotGroup.ANY).add(
                    Attributes.ARMOR,
                    new AttributeModifier(Attributes.ARMOR.getKey().location(), 3, AttributeModifier.Operation.ADD_VALUE),
                    EquipmentSlotGroup.ANY).build()))
    );

    public static final DeferredItem<Item> BLAZE_LOCKET = ITEMS_ALL.register(
            "blaze_locket",
            () -> new BlazeLocket(new Item.Properties().attributes(ItemAttributeModifiers.builder().add(
                    Attributes.ATTACK_DAMAGE,
                    new AttributeModifier(Attributes.ATTACK_DAMAGE.getKey().location(), 1, AttributeModifier.Operation.ADD_VALUE),
                    EquipmentSlotGroup.ANY
            ).build()))
    );

    public static final DeferredItem<Item> BREEZE_RING = ITEMS_ALL.register(
            "breeze_ring",
            () -> new BreezeRing(new Item.Properties().attributes(ItemAttributeModifiers.builder().add(
                    Attributes.ATTACK_SPEED,
                    new AttributeModifier(Attributes.ATTACK_SPEED.getKey().location(), 0.7, AttributeModifier.Operation.ADD_VALUE),
                    EquipmentSlotGroup.ANY
            ).build()))
    );

    public static final DeferredItem<Item> SKELETON_ARM = ITEMS_ALL.register(
            "skeleton_arm",
            () -> new AccessoryItem(new Item.Properties().attributes(ItemAttributeModifiers.builder().add(
                    Attributes.BLOCK_INTERACTION_RANGE,
                    new AttributeModifier(Attributes.BLOCK_INTERACTION_RANGE.getKey().location(), 1.5, AttributeModifier.Operation.ADD_VALUE),
                    EquipmentSlotGroup.ANY
            ).build()))
    );

    public static final DeferredItem<Item> GLITTERING_ASPECT = ITEMS_ALL.register(
            "glittering_aspect",
            () -> new GlitteringAspect(new Item.Properties())
    );

    public static final DeferredItem<Item> APOCALYPSE_CORE = ITEMS_ALL.register(
            "apocalypse_core",
            () -> new ApocalypseCore(new Item.Properties().attributes(ItemAttributeModifiers.builder().add(
                    Attributes.ATTACK_SPEED,
                    new AttributeModifier(Attributes.ATTACK_SPEED.getKey().location(), 0.5, AttributeModifier.Operation.ADD_VALUE),
                    EquipmentSlotGroup.ANY
            ).add(
                    Attributes.ARMOR,
                    new AttributeModifier(Attributes.ARMOR.getKey().location(), 3, AttributeModifier.Operation.ADD_VALUE),
                    EquipmentSlotGroup.ANY
            ).add(
                    Attributes.ARMOR_TOUGHNESS,
                    new AttributeModifier(Attributes.ARMOR_TOUGHNESS.getKey().location(), 3, AttributeModifier.Operation.ADD_VALUE),
                    EquipmentSlotGroup.ANY
            ).build()))
    );

    public static final DeferredItem<Item> GOLEM_HEART = ITEMS_ALL.register(
            "golem_heart",
            () -> new GolemHeart(new Item.Properties().attributes(ItemAttributeModifiers.builder().build()))
    );

    public static final DeferredItem<Item> MAGIC_SPUR = ITEMS_ALL.register(
            "magic_spur",
            () -> new MagicSpur(new Item.Properties().attributes(ItemAttributeModifiers.builder().build()))
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
    public static SmithingTemplateItem createGloomstoneUpgradeTemplate() {
        return new SmithingTemplateItem(
                Component.translatable("item.farworld.gloomstone_upgrade_smithing_template.applies_to"),
                Component.translatable("item.farworld.gloomstone_upgrade_smithing_template.ingredient"),
                Component.translatable("item.farworld.gloomstone_upgrade_smithing_template.type"),
                Component.translatable("item.farworld.gloomstone_upgrade_smithing_template.base_slot_description"),
                Component.translatable("item.farworld.gloomstone_upgrade_smithing_template.additions_slot_description"),
                SmithingUtils.equipmentIconListNoArmor(),
                SmithingUtils.gloomsporeIcon());
    }
    public static SmithingTemplateItem createBlackIceUpgradeTemplate() {
        return new SmithingTemplateItem(
                Component.translatable("item.farworld.black_ice_upgrade_smithing_template.applies_to"),
                Component.translatable("item.farworld.black_ice_upgrade_smithing_template.ingredient"),
                Component.translatable("item.farworld.black_ice_upgrade_smithing_template.type"),
                Component.translatable("item.farworld.black_ice_upgrade_smithing_template.base_slot_description"),
                Component.translatable("item.farworld.black_ice_upgrade_smithing_template.additions_slot_description"),
                SmithingUtils.equipmentIconListNoArmor(),
                SmithingUtils.blackIceGemIcon());
    }

    public static final DeferredItem<Item> GLOOMCAP = ITEMS_ALL.register("gloomcap",
            () -> new BlockItem(ModBlocks.GLOOMCAP.get(), new Item.Properties()).asItem());
    public static final DeferredItem<Item> LUSH_FOLIAGE = ITEMS_ALL.register("lush_foliage",
            () -> new BlockItem(ModBlocks.LUSH_FOLIAGE.get(), new Item.Properties()).asItem());
    public static final DeferredItem<Item> FLOWERING_LUSH_FOLIAGE = ITEMS_ALL.register("flowering_lush_foliage",
            () -> new BlockItem(ModBlocks.FLOWERING_LUSH_FOLIAGE.get(), new Item.Properties()).asItem());
    public static final DeferredItem<Item> GEODE_NUT = ITEMS_ALL.register("geode_nut",
            () -> new BlockItem(ModBlocks.GEODE_NUT.get(), new Item.Properties()).asItem());
    public static final DeferredItem<Item> NETHER_IRON_ORE = ITEMS_ALL.register("nether_iron_ore",
            () -> new BlockItem(ModBlocks.NETHER_IRON_ORE.get(), new Item.Properties()).asItem());
    public static final DeferredItem<Item> SOUL_STEEL_BLOCK = ITEMS_ALL.register("soul_steel_block",
            () -> new BlockItem(ModBlocks.SOUL_STEEL_BLOCK.get(), new Item.Properties()).asItem());
    public static final DeferredItem<Item> COBALT_BLOCK = ITEMS_ALL.register("cobalt_block",
            () -> new BlockItem(ModBlocks.COBALT_BLOCK.get(), new Item.Properties()).asItem());
    public static final DeferredItem<Item> FLOWSTONE = ITEMS_ALL.register("flowstone",
            () -> new BlockItem(ModBlocks.FLOWSTONE.get(), new Item.Properties()).asItem());
    public static final DeferredItem<Item> ENCRUSTED_BASALT = ITEMS_ALL.register("encrusted_basalt",
            () -> new BlockItem(ModBlocks.ENCRUSTED_BASALT.get(), new Item.Properties()).asItem());
    public static final DeferredItem<Item> DUST_BLOCK = ITEMS_ALL.register("dust_block",
            () -> new BlockItem(ModBlocks.DUST_BLOCK.get(), new Item.Properties()).asItem());
    public static final DeferredItem<Item> DUST_CLUMP = ITEMS_ALL.register("dust_clump",
            () -> new BlockItem(ModBlocks.DUST_SHEET.get(), new Item.Properties()).asItem());
    public static final DeferredItem<Item> STONEWOOD_LOG = ITEMS_ALL.register("stonewood_log",
            () -> new BlockItem(ModBlocks.STONEWOOD_LOG.get(), new Item.Properties()).asItem());
    public static final DeferredItem<Item> STONEWOOD = ITEMS_ALL.register("stonewood",
            () -> new BlockItem(ModBlocks.STONEWOOD.get(), new Item.Properties()).asItem());
    public static final DeferredItem<Item> STRIPPED_STONEWOOD = ITEMS_ALL.register("stripped_stonewood",
            () -> new BlockItem(ModBlocks.STRIPPED_STONEWOOD.get(), new Item.Properties()).asItem());
    public static final DeferredItem<Item> STRIPPED_STONEWOOD_LOG = ITEMS_ALL.register("stripped_stonewood_log",
            () -> new BlockItem(ModBlocks.STRIPPED_STONEWOOD_LOG.get(), new Item.Properties()).asItem());
    public static final DeferredItem<Item> STONEWOOD_PLANKS = ITEMS_ALL.register("stonewood_planks",
            () -> new BlockItem(ModBlocks.STONEWOOD_PLANKS.get(), new Item.Properties()).asItem());
    public static final DeferredItem<Item> STONEWOOD_STAIRS = ITEMS_ALL.register("stonewood_stairs",
            () -> new BlockItem(ModBlocks.STONEWOOD_STAIRS.get(), new Item.Properties()).asItem());
    public static final DeferredItem<Item> STONEWOOD_SLAB = ITEMS_ALL.register("stonewood_slab",
            () -> new BlockItem(ModBlocks.STONEWOOD_SLAB.get(), new Item.Properties()).asItem());
    public static final DeferredItem<Item> STONEWOOD_DOOR = ITEMS_ALL.register("stonewood_door",
            () -> new BlockItem(ModBlocks.STONEWOOD_DOOR.get(), new Item.Properties()).asItem());
    public static final DeferredItem<Item> STONEWOOD_TRAPDOOR = ITEMS_ALL.register("stonewood_trapdoor",
            () -> new BlockItem(ModBlocks.STONEWOOD_TRAPDOOR.get(), new Item.Properties()).asItem());
    public static final DeferredItem<Item> STONEWOOD_FENCE = ITEMS_ALL.register("stonewood_fence",
            () -> new BlockItem(ModBlocks.STONEWOOD_FENCE.get(), new Item.Properties()).asItem());
    public static final DeferredItem<Item> STONEWOOD_FENCE_GATE = ITEMS_ALL.register("stonewood_fence_gate",
            () -> new BlockItem(ModBlocks.STONEWOOD_FENCE_GATE.get(), new Item.Properties()).asItem());
    public static final DeferredItem<Item> STONEWOOD_BUTTON = ITEMS_ALL.register("stonewood_button",
            () -> new BlockItem(ModBlocks.STONEWOOD_BUTTON.get(), new Item.Properties()).asItem());
    public static final DeferredItem<Item> STONEWOOD_PRESSURE_PLATE = ITEMS_ALL.register("stonewood_pressure_plate",
            () -> new BlockItem(ModBlocks.STONEWOOD_PRESSURE_PLATE.get(), new Item.Properties()).asItem());
    public static final DeferredItem<Item> STONEWOOD_SIGN = ITEMS_ALL.register("stonewood_sign",
            () -> new SignItem(new Item.Properties(), ModBlocks.STONEWOOD_SIGN.get(), ModBlocks.STONEWOOD_SIGN_WALL.get()).asItem());
    public static final DeferredItem<Item> STONEWOOD_HANGING_SIGN = ITEMS_ALL.register("stonewood_hanging_sign",
            () -> new HangingSignItem(ModBlocks.STONEWOOD_SIGN_HANGING.get(), ModBlocks.STONEWOOD_SIGN_WALL_HANGING.get(), new Item.Properties()).asItem());
    public static final DeferredItem<Item> COBBLED_FLOWSTONE = ITEMS_ALL.register("cobbled_flowstone",
            () -> new BlockItem(ModBlocks.COBBLED_FLOWSTONE.get(), new Item.Properties()).asItem());
    public static final DeferredItem<Item> STONEWOOD_LEAVES = ITEMS_ALL.register("stonewood_leaves",
            () -> new BlockItem(ModBlocks.STONEWOOD_LEAVES.get(), new Item.Properties()).asItem());
    public static final DeferredItem<Item> STONEWOOD_LEAVES_FLOWERED = ITEMS_ALL.register("stonewood_leaves_flowered",
            () -> new BlockItem(ModBlocks.STONEWOOD_LEAVES_FLOWERED.get(), new Item.Properties()).asItem());
    public static final DeferredItem<Item> POINTED_AMETHYST = ITEMS_ALL.register("pointed_amethyst",
            () -> new BlockItem(ModBlocks.POINTED_AMETHYST.get(), new Item.Properties()).asItem());
    public static final DeferredItem<Item> HANGING_FLORA = ITEMS_ALL.register("hanging_flora",
            () -> new HangingFloraItem(ModBlocks.HANGING_VINES_END.get(), new Item.Properties()).asItem());
    public static final DeferredItem<Item> HANGING_VINES = ITEMS_ALL.register("hanging_vines",
            () -> new BlockItem(ModBlocks.HANGING_VINES_END.get(), new Item.Properties()).asItem());
    public static final DeferredItem<Item> STONEWOOD_SAPLING = ITEMS_ALL.register("stonewood_sapling",
            () -> new BlockItem(ModBlocks.STONEWOOD_SAPLING.get(), new Item.Properties()).asItem());
    public static final DeferredItem<Item> LUSH_FLOWSTONE = ITEMS_ALL.register("lush_flowstone",
            () -> new BlockItem(ModBlocks.LUSH_FLOWSTONE.get(), new Item.Properties()).asItem());
    public static final DeferredItem<Item> DENSE_COAL_ORE = ITEMS_ALL.register("dense_coal_ore",
            () -> new BlockItem(ModBlocks.DENSE_COAL_ORE.get(), new Item.Properties()).asItem());
    public static final DeferredItem<Item> DENSE_IRON_ORE = ITEMS_ALL.register("dense_iron_ore",
            () -> new BlockItem(ModBlocks.DENSE_IRON_ORE.get(), new Item.Properties()).asItem());
    public static final DeferredItem<Item> DENSE_GOLD_ORE = ITEMS_ALL.register("dense_gold_ore",
            () -> new BlockItem(ModBlocks.DENSE_GOLD_ORE.get(), new Item.Properties()).asItem());
    public static final DeferredItem<Item> DENSE_REDSTONE_ORE = ITEMS_ALL.register("dense_redstone_ore",
            () -> new BlockItem(ModBlocks.DENSE_REDSTONE_ORE.get(), new Item.Properties()).asItem());
    public static final DeferredItem<Item> COBALT_ORE = ITEMS_ALL.register("cobalt_ore",
            () -> new BlockItem(ModBlocks.COBALT_ORE.get(), new Item.Properties()).asItem());
    public static final DeferredItem<Item> FLOWSTONE_CACHE = ITEMS_ALL.register("flowstone_cache",
            () -> new BlockItem(ModBlocks.FLOWSTONE_CACHE.get(), new Item.Properties()).asItem());
    public static final DeferredItem<Item> BLACK_ICE = ITEMS_ALL.register("black_ice",
            () -> new BlockItem(ModBlocks.BLACK_ICE.get(), new Item.Properties()).asItem());
    public static final DeferredItem<Item> REDSTONE_PILLAR_BLOCK = ITEMS_ALL.register("redstone_pillar_block",
            () -> new BlockItem(ModBlocks.REDSTONE_PILLAR_BLOCK.get(), new Item.Properties()).asItem());

    public static final DeferredItem<Item> BLOOMED_SPAWN_EGG = ITEMS_ALL.register("bloomed_spawn_egg",
            () -> new SpawnEggItem(ModEntities.BLOOMED.get(), 0x9B691F, 0x9BFC1F, new Item.Properties()));
    public static final DeferredItem<Item> SOUL_GOLEM_SPAWN_EGG = ITEMS_ALL.register("soul_golem_spawn_egg",
            () -> new SpawnEggItem(ModEntities.SOUL_GOLEM.get(), 0x494358, 0xFE8738, new Item.Properties()));
    public static final DeferredItem<Item> BRITTLE_SPAWN_EGG = ITEMS_ALL.register("brittle_spawn_egg",
            () -> new SpawnEggItem(ModEntities.BRITTLE.get(), 0x59555D, 0xFFE31B, new Item.Properties()));
    public static final DeferredItem<Item> DUSTBUG_SPAWN_EGG = ITEMS_ALL.register("dustbug_spawn_egg",
            () -> new SpawnEggItem(ModEntities.DUSTBUG.get(), 0x282828, 0x666666, new Item.Properties()));
    public static final DeferredItem<Item> GOLIATH_SPAWN_EGG = ITEMS_ALL.register("goliath_spawn_egg",
            () -> new SpawnEggItem(ModEntities.GOLIATH.get(), 0x3A7190, 0x4A4848, new Item.Properties()));

    public static final DeferredItem<Item> WHIRLING_WORLD_DISC = ITEMS_ALL.register("disc_whirlingworld",
            () -> new Item(new Item.Properties()
                    .jukeboxPlayable(ModSounds.WHIRLING_WORLD_KEY)
                    .rarity(Rarity.RARE)));
}