package net.ennway.farworld.utils;

import net.ennway.farworld.Farworld;
import net.minecraft.resources.ResourceLocation;

import java.util.List;

public class SmithingUtils {
    public static List<ResourceLocation> singleIngotIcon() {
        return List.of(ResourceLocation.withDefaultNamespace("item/empty_slot_ingot"));
    }
    public static List<ResourceLocation> gloomsporeIcon() {
        return List.of(ResourceLocation.fromNamespaceAndPath(Farworld.MOD_ID, "item/empty_slot_gloomspores"));
    }
    public static List<ResourceLocation> equipmentIconList() {
        return List.of(
                ResourceLocation.withDefaultNamespace("item/empty_armor_slot_helmet"),
                ResourceLocation.withDefaultNamespace("item/empty_armor_slot_chestplate"),
                ResourceLocation.withDefaultNamespace("item/empty_armor_slot_leggings"),
                ResourceLocation.withDefaultNamespace("item/empty_armor_slot_boots"),
                ResourceLocation.withDefaultNamespace("item/empty_slot_hoe"),
                ResourceLocation.withDefaultNamespace("item/empty_slot_axe"),
                ResourceLocation.withDefaultNamespace("item/empty_slot_shovel"),
                ResourceLocation.withDefaultNamespace("item/empty_slot_sword"),
                ResourceLocation.withDefaultNamespace("item/empty_slot_pickaxe")
        );
    }
    public static List<ResourceLocation> equipmentIconListNoArmor() {
        return List.of(
                ResourceLocation.withDefaultNamespace("item/empty_slot_hoe"),
                ResourceLocation.withDefaultNamespace("item/empty_slot_axe"),
                ResourceLocation.withDefaultNamespace("item/empty_slot_shovel"),
                ResourceLocation.withDefaultNamespace("item/empty_slot_sword"),
                ResourceLocation.withDefaultNamespace("item/empty_slot_pickaxe")
        );
    }
}
