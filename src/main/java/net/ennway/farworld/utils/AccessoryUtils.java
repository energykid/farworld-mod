package net.ennway.farworld.utils;

import net.ennway.farworld.item.AccessoryItem;
import net.ennway.farworld.registries.ModDataComponents;
import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponents;
import net.minecraft.tags.ItemTags;
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
            if (stack.get(ModDataComponents.ARMOR_ACCESSORIES) != null)
            {
                for (ItemStack stack2 : stack.get(ModDataComponents.ARMOR_ACCESSORIES).items())
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
            if (stack.get(ModDataComponents.ARMOR_ACCESSORIES) != null)
            {
                for (ItemStack stack2 : stack.get(ModDataComponents.ARMOR_ACCESSORIES).items())
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
            if (stack.get(ModDataComponents.ARMOR_ACCESSORIES) != null)
            {
                for (int i = 0; i < stack.get(ModDataComponents.ARMOR_ACCESSORIES).size(); i++)
                {
                    if (stack.get(ModDataComponents.ARMOR_ACCESSORIES).getItemUnsafe(i).getItem() instanceof AccessoryItem)
                    {
                        items.add(stack.get(ModDataComponents.ARMOR_ACCESSORIES).getItemUnsafe(i));
                    }
                }
            }
        }
        return items;
    }
    public static boolean itemCanBeEquippedTo(ItemStack stack)
    {
        return (stack.is(ItemTags.HEAD_ARMOR) || stack.is(ItemTags.CHEST_ARMOR) || stack.is(ItemTags.LEG_ARMOR) || stack.is(ItemTags.FOOT_ARMOR));
    }
    public static boolean itemCanBeEquippedTo(Holder.Reference<Item> item)
    {
        return (item.is(ItemTags.HEAD_ARMOR) || item.is(ItemTags.CHEST_ARMOR) || item.is(ItemTags.LEG_ARMOR) || item.is(ItemTags.FOOT_ARMOR));
    }
}
