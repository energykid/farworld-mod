package net.ennway.farworld.item;
import net.ennway.farworld.registries.ModItems;
import net.minecraft.client.gui.font.glyphs.BakedGlyph;
import net.minecraft.client.renderer.EffectInstance;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;

public class MysticPear extends Item {
    public MysticPear() {
		super(new Properties().food(
                new FoodProperties.Builder()
                        .nutrition(8)
                        .saturationModifier(0.6f)
                        .effect(new MobEffectInstance(MobEffects.JUMP, 20 * 45, 1), 1f)
                        .effect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 20 * 105, 1), 1f)
                        .alwaysEdible()
                        .build())
                .rarity(Rarity.RARE));
    }
}