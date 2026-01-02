package net.ennway.farworld.event;

import net.ennway.farworld.Farworld;
import net.ennway.farworld.registries.ModItems;
import net.ennway.farworld.registries.ModPotions;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.alchemy.PotionBrewing;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.level.ItemLike;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.ModifyDefaultComponentsEvent;
import net.neoforged.neoforge.event.brewing.RegisterBrewingRecipesEvent;

import java.util.function.Predicate;

@EventBusSubscriber(modid = Farworld.MOD_ID)
public class ModRecipeEvents {

    @SubscribeEvent
    public static void brewRecipe(RegisterBrewingRecipesEvent event)
    {
        PotionBrewing.Builder builder = event.getBuilder();

        builder.addMix(Potions.AWKWARD, ModItems.GLOOMSPORES.value(), ModPotions.PARALYSIS);
    }
}
