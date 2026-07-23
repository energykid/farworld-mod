package net.ennway.farworld.utils;

import net.minecraft.network.chat.Component;

import java.util.List;

public class ItemUtils
{
    public static void addUsesLeft(List<Component> tooltipComponents, int usesLeft)
    {
        tooltipComponents.add(Component.literal(Component.translatable("item.tooltip.uses_left").getString() + usesLeft));
    }
}