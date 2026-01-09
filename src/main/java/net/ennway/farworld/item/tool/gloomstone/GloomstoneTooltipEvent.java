package net.ennway.farworld.item.tool.gloomstone;

import net.ennway.farworld.Farworld;
import net.ennway.farworld.registries.ModItems;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.AddAttributeTooltipsEvent;
import net.neoforged.neoforge.registries.DeferredHolder;

import java.util.List;

@EventBusSubscriber(modid = Farworld.MOD_ID)
public class GloomstoneTooltipEvent {

    public static final List<Holder<Item>> TOOLS_TO_ADD_TOOLTIP_TO = List.of(
            ModItems.GLOOMSTONE_SWORD,
            ModItems.GLOOMSTONE_AXE,
            ModItems.GLOOMSTONE_PICKAXE,
            ModItems.GLOOMSTONE_SHOVEL,
            ModItems.GLOOMSTONE_HOE
    );

    @SubscribeEvent
    public static void tooltip(AddAttributeTooltipsEvent evt) {
        for (Holder<Item> i : TOOLS_TO_ADD_TOOLTIP_TO)
        {
            if (evt.shouldShow() && evt.getStack().is(i)) {
                evt.addTooltipLines(Component.translatable("extra.farworld.gloomstone_effect"));
            }
        }
    }
}
