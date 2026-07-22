package net.ennway.farworld.item.accessory;

import net.ennway.farworld.registries.ModTags;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.AddAttributeTooltipsEvent;

@EventBusSubscriber
public class AccessoryTooltipEvent {

    @SubscribeEvent
    public static void tooltip(AddAttributeTooltipsEvent evt) {
        if (evt.shouldShow() && evt.getStack().is(ModTags.ACCESSORIES)) {
            String str = "accessory." + evt.getStack().getDescriptionId() + ".desc";
            if (!Component.translatable(str).getString().contains(str))
            {
                if (evt.getStack().getAttributeModifiers().modifiers().isEmpty())
                {
                    evt.addTooltipLines(Component.empty());
                    evt.addTooltipLines(
                            Component.literal("§7" +
                            Component.translatable("item.modifiers.body").getString()));
                }

                evt.addTooltipLines(
                        Component.literal("§9" +
                                Component.translatable(str).getString()
                        )
                );
            }
        }
    }
}
