package net.ennway.farworld.registries.sets;

import net.ennway.farworld.registries.ModItems;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.crafting.Ingredient;
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

    public static final Tier COBALT_TIER = new SimpleTier(
            BlockTags.NEEDS_DIAMOND_TOOL,
            450,
            8.0F,
            3.0F,
            12,
            () -> Ingredient.of(ModItems.COBALT_INGOT)
    );
}
