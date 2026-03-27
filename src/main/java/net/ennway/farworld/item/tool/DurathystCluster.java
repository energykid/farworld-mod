package net.ennway.farworld.item.tool;

import net.ennway.farworld.registries.ModDataComponents;
import net.ennway.farworld.registries.ModSounds;
import net.minecraft.core.component.DataComponentMap;
import net.minecraft.core.component.DataComponents;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ClickAction;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.CustomModelData;
import net.minecraft.world.item.component.ItemAttributeModifiers;
import net.minecraft.world.level.Level;

public class DurathystCluster extends Item {
    public static final int MAX_SATURATION = 1000;

    public DurathystCluster() {
        super(new Properties().stacksTo(1)
                .component(ModDataComponents.EXP_SATURATION, 0)
                .component(ModDataComponents.FRAME, 0)
                .component(ModDataComponents.EFFECT_INTENSITY, 0f));
    }

    @Override
    public int getMaxDamage(ItemStack stack) {
        return MAX_SATURATION;
    }

    @Override
    public void inventoryTick(ItemStack stack, Level level, Entity entity, int slotId, boolean isSelected) {
        super.inventoryTick(stack, level, entity, slotId, isSelected);
        stack.set(ModDataComponents.EFFECT_INTENSITY, 0f);
        setDamage(stack, Mth.clamp(MAX_SATURATION - stack.get(ModDataComponents.EXP_SATURATION), 1, MAX_SATURATION - 1));
    }

    @Override
    public int getBarColor(ItemStack stack) {
        return 0x9B9BDC;
    }

    @Override
    public boolean isBarVisible(ItemStack stack) {
        return stack.get(ModDataComponents.EXP_SATURATION) > 0;
    }

    @Override
    public boolean overrideStackedOnOther(ItemStack stack, Slot slot, ClickAction action, Player player) {
        if (action == ClickAction.SECONDARY)
        {
            if (slot.hasItem() && slot.getItem().isDamaged())
            {
                int amt = stack.get(ModDataComponents.EXP_SATURATION);
                amt = Math.min(amt, slot.getItem().getDamageValue());

                slot.getItem().setDamageValue(slot.getItem().getDamageValue() - amt);

                stack.set(ModDataComponents.EXP_SATURATION, stack.get(ModDataComponents.EXP_SATURATION) - amt);

                stack.set(ModDataComponents.FRAME, stack.get(ModDataComponents.FRAME) + 1);
                stack.set(ModDataComponents.EFFECT_INTENSITY, 1f);
                stack.set(DataComponents.CUSTOM_MODEL_DATA, new CustomModelData(stack.get(ModDataComponents.FRAME)));
                player.playSound(SoundEvents.AMETHYST_BLOCK_BREAK, 1, Mth.randomBetween(player.getRandom(), 1.2f, 1.5f));
                player.playSound(SoundEvents.BASALT_BREAK, 1, 0.5f);
                if (stack.get(ModDataComponents.FRAME) >= 3)
                {
                    stack.consume(1, player);
                    player.playSound(SoundEvents.BASALT_BREAK, 1, -0.5f);
                }
            }
            return true;
        }
        return super.overrideStackedOnOther(stack, slot, action, player);
    }
}
