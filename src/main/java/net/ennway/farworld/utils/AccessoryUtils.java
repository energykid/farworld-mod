package net.ennway.farworld.utils;

import net.minecraft.core.component.DataComponents;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

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
}
