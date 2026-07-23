package net.ennway.farworld.item;

import net.ennway.farworld.registries.ModItems;
import net.ennway.farworld.registries.ModTags;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.SlotAccess;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ClickAction;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;

import java.util.List;

public class CuriousCoreOff extends Item {
    public CuriousCoreOff(Properties properties) {
        super(properties.stacksTo(1));
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
            other.setCount(other.getCount() - 1);
            ItemStack s = new ItemStack(ModItems.CURIOUS_CORE.asItem());
            CuriousCoreItem.setModelStuff(s);
            slot.set(s);
            return true;
        }
        return super.overrideOtherStackedOnMe(stack, other, slot, action, player, access);
    }
}
