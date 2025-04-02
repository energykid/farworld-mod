package net.ennway.farworld.utils;

import net.minecraft.resources.ResourceLocation;

import java.util.List;

public class SmithingUtils {
    public static List<ResourceLocation> singleIngotIcon() {
        return List.of(ResourceLocation.withDefaultNamespace("item/empty_slot_ingot"));
    }
    public static List<ResourceLocation> equipmentIconList() {
        return List.of(
                ResourceLocation.withDefaultNamespace("item/empty_armor_slot_helmet"),
                ResourceLocation.withDefaultNamespace("item/empty_armor_slot_chestplate"),
                ResourceLocation.withDefaultNamespace("item/empty_armor_slot_leggings"),
                ResourceLocation.withDefaultNamespace("item/empty_armor_slot_boots"),
                ResourceLocation.withDefaultNamespace("item/empty_armor_slot_hoe"),
                ResourceLocation.withDefaultNamespace("item/empty_armor_slot_axe"),
                ResourceLocation.withDefaultNamespace("item/empty_armor_slot_shovel"),
                ResourceLocation.withDefaultNamespace("item/empty_armor_slot_sword"),
                ResourceLocation.withDefaultNamespace("item/empty_armor_slot_pickaxe")
        );
    }
}
