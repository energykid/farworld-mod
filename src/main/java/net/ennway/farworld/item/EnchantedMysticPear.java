package net.ennway.farworld.item;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;

public class EnchantedMysticPear extends Item {
    public EnchantedMysticPear() {
		super(new Properties().food(
                new FoodProperties.Builder()
                        .nutrition(10)
                        .saturationModifier(0.7f)
                        .effect(new MobEffectInstance(MobEffects.JUMP, 20 * 60, 1), 1f)
                        .effect(new MobEffectInstance(MobEffects.DIG_SPEED, 20 * 60, 0), 1f)
                        .effect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 20 * 135, 1), 1f)
                        .alwaysEdible()
                        .build())
                .rarity(Rarity.EPIC)
                .component(DataComponents.ENCHANTMENT_GLINT_OVERRIDE, true));
    }
}