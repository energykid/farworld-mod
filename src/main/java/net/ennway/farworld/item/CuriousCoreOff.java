package net.ennway.farworld.item;

import net.ennway.farworld.registries.ModDataComponents;
import net.ennway.farworld.registries.ModItems;
import net.ennway.farworld.registries.ModSounds;
import net.ennway.farworld.registries.ModTags;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.SlotAccess;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ClickAction;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;

import java.util.List;

public class CuriousCoreOff extends Item {
    public CuriousCoreOff(Properties properties) {
        super(properties.stacksTo(1)
                .component(ModDataComponents.EXP_SATURATION, 0));
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
        super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);
        tooltipComponents.add(Component.translatable("nonlore_tooltip.item.farworld.curious_core_off"));
    }

    @Override
    public boolean overrideOtherStackedOnMe(ItemStack stack, ItemStack other, Slot slot, ClickAction action, Player player, SlotAccess access) {
        if (other.is(ModTags.CURIOUS_CORE_FUEL))
        {
            int num = Math.min(other.getCount(), 5);

            other.setCount(other.getCount() - num);

            stack.set(ModDataComponents.EXP_SATURATION, stack.get(ModDataComponents.EXP_SATURATION) + num);

            if (stack.get(ModDataComponents.EXP_SATURATION) >= 5)
            {
                ItemStack s = new ItemStack(ModItems.CURIOUS_CORE.asItem());
                CuriousCoreItem.setModelStuff(s);
                slot.set(s);
                player.playNotifySound(ModSounds.CURIOUS_CORE_POWER_UP.get(), SoundSource.PLAYERS, 1, 1);
            }
            player.playNotifySound(SoundEvents.LEVER_CLICK, SoundSource.PLAYERS, 1, 0.5f + (stack.get(ModDataComponents.EXP_SATURATION) * 0.2f));

            return true;
        }
        return super.overrideOtherStackedOnMe(stack, other, slot, action, player, access);
    }

    public static final int MAX_SATURATION = 5;

    @Override
    public int getMaxDamage(ItemStack stack) {
        return MAX_SATURATION;
    }

    @Override
    public boolean isBarVisible(ItemStack stack) {
        return stack.get(ModDataComponents.EXP_SATURATION) > 0;
    }

    @Override
    public int getBarColor(ItemStack stack) {
        return 12728890;
    }

    @Override
    public void inventoryTick(ItemStack stack, Level level, Entity entity, int slotId, boolean isSelected) {
        if (stack.get(ModDataComponents.EXP_SATURATION) > 0)
            stack.setDamageValue(5 - stack.get(ModDataComponents.EXP_SATURATION));
    }
}
