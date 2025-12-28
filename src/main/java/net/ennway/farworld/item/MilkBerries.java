package net.ennway.farworld.item;
import net.ennway.farworld.registries.ModBlocks;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;

public class MilkBerries extends BlockItem {
    public MilkBerries() {
		super(ModBlocks.MILK_BERRIES.get(), new Properties().food(
                new FoodProperties.Builder()
                .nutrition(2)
                .fast()
                .alwaysEdible()
                .saturationModifier(1.5f)
                .build()));
    }
}
