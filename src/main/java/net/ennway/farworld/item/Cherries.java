package net.ennway.farworld.item;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class Cherries extends Item {
    public Cherries() {
		super(new Properties().food(
                new FoodProperties.Builder()
                    .nutrition(2)
                    .saturationModifier(0.5f)
                    .fast()
                    .alwaysEdible()
                    .build())
                .stacksTo(16));
    }

    @Override
    public ItemStack finishUsingItem(ItemStack stack, Level level, LivingEntity livingEntity) {
        if (livingEntity.getRandom().nextFloat() < 0.6f) {
            livingEntity.heal(livingEntity.getRandom().nextFloat() * 3f);
        }
        return super.finishUsingItem(stack, level, livingEntity);
    }
}
