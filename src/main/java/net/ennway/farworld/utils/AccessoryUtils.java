package net.ennway.farworld.utils;

import net.ennway.farworld.item.AccessoryItem;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class AccessoryUtils {
    public static boolean playerHasAccessory(Player player, Item accessoryType)
    {
        for (ItemStack stack : player.getArmorSlots())
        {
            if (stack.get(DataComponents.BUNDLE_CONTENTS) != null)
            {
                for (ItemStack stack2 : stack.get(DataComponents.BUNDLE_CONTENTS).items())
                {
                    if (stack2.is(accessoryType))
                    {
                        return true;
                    }
                }
            }
        }
        return false;
    }
    public static List<AccessoryItem> getPlayerAccessories(Player player)
    {
        List<AccessoryItem> items = new ArrayList<AccessoryItem>();
        for (ItemStack stack : player.getArmorSlots())
        {
            if (stack.get(DataComponents.BUNDLE_CONTENTS) != null)
            {
                for (ItemStack stack2 : stack.get(DataComponents.BUNDLE_CONTENTS).items())
                {
                    if (stack2.getItem() instanceof AccessoryItem accItem)
                    {
                        items.add(accItem);
                    }
                }
            }
        }
        return items;
    }
    public static List<ItemStack> getPlayerAccessoryStacks(Player player)
    {
        List<ItemStack> items = new ArrayList<ItemStack>();
        for (ItemStack stack : player.getArmorSlots())
        {
            if (stack.get(DataComponents.BUNDLE_CONTENTS) != null)
            {
                for (ItemStack stack2 : stack.get(DataComponents.BUNDLE_CONTENTS).items())
                {
                    if (stack2.getItem() instanceof AccessoryItem accItem)
                    {
                        items.add(stack2);
                    }
                }
            }
        }
        return items;
    }
}
