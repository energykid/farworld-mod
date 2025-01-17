package net.ennway.farworld.item;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;

public class Pear extends Item {
    public Pear() {
		super(new Item.Properties().food(
                new FoodProperties.Builder()
                .nutrition(6)
                .saturationModifier(0.4f)
                .build()));
    }
}
