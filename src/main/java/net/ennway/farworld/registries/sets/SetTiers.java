package net.ennway.farworld.registries.sets;

import net.ennway.farworld.Farworld;
import net.ennway.farworld.registries.ModItems;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.common.SimpleTier;
import net.neoforged.neoforge.common.Tags;

public class SetTiers {
    public static final Tier GLOOMSTONE_TIER = new SimpleTier(
            BlockTags.NEEDS_IRON_TOOL,
            375,
            8.0F,
            2.5F,
            7,
            () -> Ingredient.of(ModItems.GLOOMSPORES)
    );

    public static final Tier SOUL_STEEL_TIER = new SimpleTier(
            BlockTags.INCORRECT_FOR_NETHERITE_TOOL,
            450,
            8.0F,
            3.0F,
            12,
            () -> Ingredient.of(ModItems.SOUL_STEEL)
    );

    public static final Tier COBALT_TIER = new SimpleTier(
            BlockTags.INCORRECT_FOR_DIAMOND_TOOL,
            450,
            8.0F,
            3.0F,
            12,
            () -> Ingredient.of(ModItems.COBALT_INGOT)
    );

    public static final TagKey<Block> INCORRECT_FOR_BLACK_ICE_TOOL = TagKey.create(
            BuiltInRegistries.BLOCK.key(),
            ResourceLocation.fromNamespaceAndPath(Farworld.MOD_ID, "incorrect_for__black_ice_tool"));

    public static final Tier BLACK_ICE_TIER = new SimpleTier(
            INCORRECT_FOR_BLACK_ICE_TOOL,
            1500,
            8.0F,
            3.5F,
            10,
            () -> Ingredient.of(ModItems.BLACK_ICE_GEM)
    );
}
