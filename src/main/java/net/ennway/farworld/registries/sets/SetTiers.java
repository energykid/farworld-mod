package net.ennway.farworld.registries.sets;

import net.ennway.farworld.Farworld;
import net.ennway.farworld.registries.ModItems;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.common.SimpleTier;
import net.neoforged.neoforge.common.Tags;

public class SetTiers {
    public static final Tier SOUL_STEEL_TIER = new SimpleTier(
            BlockTags.NEEDS_DIAMOND_TOOL,
            450,
            8.0F,
            3.0F,
            12,
            () -> Ingredient.of(ModItems.SOUL_STEEL)
    );

    public static final TagKey<Block> NEEDS_COBALT_TOOL = TagKey.create(
            BuiltInRegistries.BLOCK.key(),
            ResourceLocation.fromNamespaceAndPath(Farworld.MOD_ID, "needs_cobalt_tool"));

    public static final Tier COBALT_TIER = new SimpleTier(
            NEEDS_COBALT_TOOL,
            450,
            8.0F,
            3.0F,
            12,
            () -> Ingredient.of(ModItems.COBALT_INGOT)
    );
}
