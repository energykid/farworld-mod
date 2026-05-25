package net.ennway.farworld.item;
import net.ennway.farworld.registries.ModEffects;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;

public class Crystallias extends Item {
    public Crystallias() {
		super(new Properties().food(
                new FoodProperties.Builder()
                .nutrition(4)
                .saturationModifier(0.2f)
                        .effect(new MobEffectInstance(ModEffects.SPARKLING, 20 * 10, 1), 1f)
                        .build()));
    }
}
