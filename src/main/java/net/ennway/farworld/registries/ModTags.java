package net.ennway.farworld.registries;

import net.ennway.farworld.Farworld;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

public class ModTags {

    public static final TagKey<Item> TWO_ACCESSORY_ARMOR = TagKey.create(
            BuiltInRegistries.ITEM.key(),
            ResourceLocation.fromNamespaceAndPath(Farworld.MOD_ID, "two_accessory_armor"));
    public static final TagKey<Item> THREE_ACCESSORY_ARMOR = TagKey.create(
            BuiltInRegistries.ITEM.key(),
            ResourceLocation.fromNamespaceAndPath(Farworld.MOD_ID, "three_accessory_armor"));
    public static final TagKey<Item> ACCESSORY_INCOMPATIBLE = TagKey.create(
            BuiltInRegistries.ITEM.key(),
            ResourceLocation.fromNamespaceAndPath(Farworld.MOD_ID, "accessory_incompatible_armor"));

    public static final TagKey<Item> HAS_NETHERITE_EFFECT = TagKey.create(
            BuiltInRegistries.ITEM.key(),
            ResourceLocation.fromNamespaceAndPath(Farworld.MOD_ID, "has_netherite_effect"));
    public static final TagKey<Item> HAS_SOUL_STEEL_EFFECT = TagKey.create(
            BuiltInRegistries.ITEM.key(),
            ResourceLocation.fromNamespaceAndPath(Farworld.MOD_ID, "has_soul_steel_effect"));
    public static final TagKey<Item> HAS_NECROMIUM_EFFECT = TagKey.create(
            BuiltInRegistries.ITEM.key(),
            ResourceLocation.fromNamespaceAndPath(Farworld.MOD_ID, "has_necromium_effect"));
    public static final TagKey<Item> HAS_GLOOMSTONE_EFFECT = TagKey.create(
            BuiltInRegistries.ITEM.key(),
            ResourceLocation.fromNamespaceAndPath(Farworld.MOD_ID, "has_gloomstone_effect"));
    public static final TagKey<Item> HAS_BLACK_ICE_EFFECT = TagKey.create(
            BuiltInRegistries.ITEM.key(),
            ResourceLocation.fromNamespaceAndPath(Farworld.MOD_ID, "has_black_ice_effect"));

    public static final TagKey<Item> HELMET_ACCESSORIES = TagKey.create(
            BuiltInRegistries.ITEM.key(),
            ResourceLocation.fromNamespaceAndPath(Farworld.MOD_ID, "helmet_accessories"));
    public static final TagKey<Item> CHESTPLATE_ACCESSORIES = TagKey.create(
            BuiltInRegistries.ITEM.key(),
            ResourceLocation.fromNamespaceAndPath(Farworld.MOD_ID, "chestplate_accessories"));
    public static final TagKey<Item> LEGGINGS_ACCESSORIES = TagKey.create(
            BuiltInRegistries.ITEM.key(),
            ResourceLocation.fromNamespaceAndPath(Farworld.MOD_ID, "legging_accessories"));
    public static final TagKey<Item> BOOTS_ACCESSORIES = TagKey.create(
            BuiltInRegistries.ITEM.key(),
            ResourceLocation.fromNamespaceAndPath(Farworld.MOD_ID, "boot_accessories"));

    public static final TagKey<Item> BREEZE_STANCEABLE_WEAPONS = TagKey.create(
            BuiltInRegistries.ITEM.key(),
            ResourceLocation.fromNamespaceAndPath(Farworld.MOD_ID, "breeze_stanceable_weapons"));
    public static final TagKey<Item> BLAZE_STANCEABLE_WEAPONS = TagKey.create(
            BuiltInRegistries.ITEM.key(),
            ResourceLocation.fromNamespaceAndPath(Farworld.MOD_ID, "blaze_stanceable_weapons"));

    public static final TagKey<Item> BYSTONE_PORTAL_ITEMS = TagKey.create(
            BuiltInRegistries.ITEM.key(),
            ResourceLocation.fromNamespaceAndPath(Farworld.MOD_ID, "can_light_bystone_portal"));

    public static final TagKey<Block> MILK_BERRY_SURVIVABLE = TagKey.create(
            BuiltInRegistries.BLOCK.key(),
            ResourceLocation.fromNamespaceAndPath(Farworld.MOD_ID, "milk_berry_survivable"));
}
