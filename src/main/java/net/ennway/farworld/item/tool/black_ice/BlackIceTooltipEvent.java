package net.ennway.farworld.item.tool.black_ice;

import net.ennway.farworld.Farworld;
import net.ennway.farworld.registries.ModItems;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.AddAttributeTooltipsEvent;

import java.util.List;

@EventBusSubscriber(modid = Farworld.MOD_ID)
public class BlackIceTooltipEvent {

    public static final List<Holder<Item>> TOOLS_TO_ADD_TOOLTIP_TO = List.of(
            ModItems.BLACK_ICE_SWORD,
            ModItems.BLACK_ICE_AXE,
            ModItems.BLACK_ICE_PICKAXE,
            ModItems.BLACK_ICE_SHOVEL,
            ModItems.BLACK_ICE_HOE
    );

    @SubscribeEvent
    public static void tooltip(AddAttributeTooltipsEvent evt) {
        for (Holder<Item> i : TOOLS_TO_ADD_TOOLTIP_TO)
        {
            if (evt.shouldShow() && evt.getStack().is(i)) {
                evt.addTooltipLines(Component.translatable("extra.farworld.black_ice_effect"));
            }
        }
    }
}
