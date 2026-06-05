package net.ennway.farworld.item;
import net.ennway.farworld.registries.ModEffects;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.Rarity;

public class SludgeSoup extends Item {
    public SludgeSoup() {
		super(new Properties().stacksTo(1).food(
                new FoodProperties.Builder()
                        .nutrition(10)
                        .saturationModifier(0.5f)
                        .effect(new MobEffectInstance(MobEffects.DAMAGE_BOOST, 20 * 10, 1), 1f)
                        .effect(new MobEffectInstance(ModEffects.SLUDGED, 20 * 10, 0), 1f)
                        .usingConvertsTo(Items.BOWL)
                        .build())
                .rarity(Rarity.RARE));
    }
}