package net.ennway.farworld.item;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;

public class GeodeFruit extends Item {
    public GeodeFruit() {
		super(new Properties().food(
                new FoodProperties.Builder()
                .nutrition(8)
                .saturationModifier(0.2f)
                .build()));
    }
}
