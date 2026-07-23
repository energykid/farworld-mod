package net.ennway.farworld.datagen;

import com.ibm.icu.impl.data.ResourceReader;
import net.ennway.farworld.Farworld;
import net.ennway.farworld.registries.ModItems;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementHolder;
import net.minecraft.advancements.AdvancementRequirements;
import net.minecraft.advancements.AdvancementRewards;
import net.minecraft.advancements.critereon.InventoryChangeTrigger;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.resources.Resource;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Recipe;
import net.neoforged.neoforge.common.data.AdvancementProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.server.ServerLifecycleHooks;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

public class RecipeAdvancementGenerator extends AdvancementProvider {
    public RecipeAdvancementGenerator(PackOutput output,
                                      CompletableFuture<HolderLookup.Provider> lookupProvider, ExistingFileHelper existingFileHelper) {
        // Add an instance of our generator to the list parameter. This can be done as many times as you want.
        // Having multiple generators is purely for organization, all functionality can be achieved with a single generator.
        super(output, lookupProvider, existingFileHelper, List.of(new MyAdvancementGenerator()));
    }

    private static final class MyAdvancementGenerator implements AdvancementProvider.AdvancementGenerator {
        @Override
        public void generate(HolderLookup.Provider registries, Consumer<AdvancementHolder> saver, ExistingFileHelper helper) {

            /// Accessories
            {
                recipeAdvancement(saver, List.of(Items.COPPER_INGOT), List.of(
                        "goggles"));

                recipeAdvancement(saver, List.of(Items.IRON_INGOT), List.of(
                        "iron_cuff"));

                recipeAdvancement(saver, List.of(Items.GOLD_INGOT, Items.WHEAT), List.of(
                        "magic_spur"));

                recipeAdvancement(saver, List.of(Items.IRON_INGOT, Items.LEATHER), List.of(
                        "toolbelt"));

                recipeAdvancement(saver, List.of(Items.IRON_NUGGET, Items.BREEZE_ROD), List.of(
                        "breeze_ring"));

                recipeAdvancement(saver, List.of(Items.CHAIN, Items.BLAZE_ROD), List.of(
                        "blaze_locket"));

                recipeAdvancement(saver, List.of(Items.EMERALD, Items.REDSTONE, Items.DIAMOND), List.of(
                        "golem_heart"));

                recipeAdvancement(saver, List.of(Items.AMETHYST_BLOCK, Items.AMETHYST_SHARD, Items.DIAMOND), List.of(
                        "glittering_aspect"));

                recipeAdvancement(saver, List.of(ModItems.COBALT_INGOT.asItem()), List.of(
                        "cobalt_cuff"));

                recipeAdvancement(saver, List.of(ModItems.COBALT_INGOT.asItem(), Items.SLIME_BALL), List.of(
                        "slime_brace"));
            }

            /// Building blocks
            {
                /// Amethyst
                recipeAdvancement(saver, List.of(Items.AMETHYST_SHARD, Items.AMETHYST_BLOCK, Items.SMOOTH_BASALT), List.of(
                        "crystal_lamp"));

                /// Large Chain
                recipeAdvancement(saver, List.of(Items.IRON_INGOT, Items.IRON_NUGGET), List.of(
                        "large_chain"));

                /// Dust Clumps/Blocks
                recipeAdvancement(saver, List.of(ModItems.DUST_BLOCK.asItem(), ModItems.DUST_CLUMP.asItem()), List.of(
                        "dust_block", "dust_block_revert"));

                /// Dust Glass
                recipeAdvancement(saver, List.of(ModItems.DUST_GLASS.asItem()), List.of(
                        "shadow_dust_glass", "crystal_dust_glass"));

                /// Stonewood
                woodSet(saver, ModItems.STONEWOOD_LOG.asItem(), ModItems.STONEWOOD_PLANKS.asItem(),"stonewood");

                /// Flowstone blocksets
                {
                    genericStoneBlockset(saver, ModItems.COBBLED_FLOWSTONE.asItem(), "cobbled_flowstone");
                    genericStoneBlockset(saver, ModItems.FLOWSTONE_BRICKS.asItem(), "flowstone_brick");
                    genericStoneBlockset(saver, ModItems.LUSH_FLOWSTONE_BRICKS.asItem(), "lush_flowstone_brick");
                    genericStoneBlockset(saver, ModItems.DUSTY_FLOWSTONE_BRICKS.asItem(), "dusty_flowstone_brick");
                    genericStoneBlockset(saver, ModItems.COBBLED_SLEEKSTONE.asItem(), "cobbled_sleekstone");
                    genericStoneBlockset(saver, ModItems.SLEEKSTONE_BRICKS.asItem(), "sleekstone_brick");
                }
            }

            /// Redstone Curiosity
            recipeAdvancement(saver, List.of(ModItems.CURIOUS_CORE.asItem()), List.of(
                    "redstone_curiosity", "redstone_teleporter", "charge_bunker"));

            /// Mystic Pear
            recipeAdvancement(saver, List.of(ModItems.PEAR.asItem()), List.of(
                    "mystic_pear", "enchanted_mystic_pear"));

            /// Sludge Soup & Sludge Arrow
            recipeAdvancement(saver, List.of(ModItems.SLUDGE_BALL.asItem()), List.of(
                    "sludge_soup", "sludge_arrow"));

            /// Cobalt set & Gloom Plates
            recipeAdvancement(saver, List.of(ModItems.COBALT_INGOT.asItem()), List.of(
                    "cobalt_block", "cobalt_block_revert",
                    "cobalt_axe", "cobalt_pickaxe", "cobalt_sword", "cobalt_shovel", "cobalt_hoe",
                    "cobalt_helmet", "cobalt_chestplate", "cobalt_leggings", "cobalt_boots", "gloom_plate"));

            /// Soul Steel
            recipeAdvancement(saver, List.of(Items.SOUL_SAND, Items.QUARTZ), List.of(
                    "soul_steel", "soul_steel_block"));

            /// Smithing templates
            {
                /// From Smithstone Remnants
                recipeAdvancement(saver, List.of(ModItems.GLOOM_PLATE.asItem()),
                        List.of("gloomstone_template_from_smithstone"));
                recipeAdvancement(saver, List.of(ModItems.SOUL_STEEL.asItem(), ModItems.SOUL_STEEL_BLOCK.asItem()),
                        List.of("soul_steel_template_from_smithstone"));
                recipeAdvancement(saver, List.of(Items.NETHERITE_INGOT, Items.ANCIENT_DEBRIS, Items.NETHERITE_SCRAP, Items.NETHERITE_BLOCK),
                        List.of("netherite_template_from_smithstone"));
                recipeAdvancement(saver, List.of(ModItems.BLACK_ICE_GEM.asItem()),
                        List.of("black_ice_template_from_smithstone"));

                /// From duplication methods
                recipeAdvancement(saver, List.of(ModItems.GLOOMSTONE_UPGRADE_SMITHING_TEMPLATE.asItem()),
                        List.of("gloomstone_upgrade_smithing_template"));
                recipeAdvancement(saver, List.of(ModItems.SOUL_STEEL_UPGRADE_SMITHING_TEMPLATE.asItem()),
                        List.of("soul_steel_upgrade_smithing_template"));
                recipeAdvancement(saver, List.of(ModItems.BLACK_ICE_UPGRADE_SMITHING_TEMPLATE.asItem()),
                        List.of("black_ice_upgrade_smithing_template"));
            }
        }
    }

    public static void woodSet(Consumer<AdvancementHolder> saver, Item log, Item planks, String name)
    {
        recipeAdvancement(saver, List.of(ModItems.STONEWOOD_LOG.asItem()), List.of(
                name,
                "stripped_"+name,
                name+"_planks_from_bark",
                name+"_planks_from_log",
                name+"_planks_from_stripped_bark",
                name+"_planks_from_stripped_log"));
        recipeAdvancement(saver, List.of(ModItems.STONEWOOD_PLANKS.asItem()), List.of(
                name+"_slab", name+"_stairs",
                name+"_door", name+"_trapdoor",
                name+"_fence", name+"_fence_gate",
                name+"_button", name+"_pressure_plate",
                name+"_sign", name+"_hanging_sign"));
    }

    public static void genericStoneBlockset(Consumer<AdvancementHolder> saver, Item block, String name)
    {
        recipeAdvancement(saver, List.of(block), List.of(
                name+"_slab", name+"_stairs", name+"_wall"));
    }

    public static void recipeAdvancement(Consumer<AdvancementHolder> saver, List<Item> items, List<String> recipes)
    {
        for (String recipe : recipes) {
            Advancement.Builder builder = Advancement.Builder.recipeAdvancement();
            int i = 0;
            List<String> reqs = new ArrayList<>(List.of());
            for (Item item : items) {
                reqs.add("pickup" + i);
                builder.addCriterion("pickup" + i, InventoryChangeTrigger.TriggerInstance.hasItems(item));
                i++;
            }
            builder.rewards(
                    AdvancementRewards.Builder.recipe(ResourceLocation.fromNamespaceAndPath(Farworld.MOD_ID, recipe))
            );
            builder.requirements(AdvancementRequirements.anyOf(reqs));
            builder.save(saver, recipe + "_unlock");
        }
    }
}