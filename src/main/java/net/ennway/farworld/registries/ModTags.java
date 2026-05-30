package net.ennway.farworld.registries;

import net.ennway.farworld.Farworld;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

public class ModTags {

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

    public static final TagKey<Item> TWO_ACCESSORY_ARMOR = TagKey.create(
            BuiltInRegistries.ITEM.key(),
            ResourceLocation.withDefaultNamespace("two_accessory_armor"));
    public static final TagKey<Item> THREE_ACCESSORY_ARMOR = TagKey.create(
            BuiltInRegistries.ITEM.key(),
            ResourceLocation.withDefaultNamespace("three_accessory_armor"));

    public static final TagKey<Item> BYSTONE_PORTAL_ITEMS = TagKey.create(
            BuiltInRegistries.ITEM.key(),
            ResourceLocation.fromNamespaceAndPath(Farworld.MOD_ID, "can_light_bystone_portal"));

    public static final TagKey<Block> MILK_BERRY_SURVIVABLE = TagKey.create(
            BuiltInRegistries.BLOCK.key(),
            ResourceLocation.fromNamespaceAndPath(Farworld.MOD_ID, "milk_berry_survivable"));
}
