package net.ennway.farworld.item.accessory;

import net.ennway.farworld.registries.ModDataComponents;
import net.ennway.farworld.registries.ModTags;
import net.ennway.farworld.utils.StringUtils;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.AddAttributeTooltipsEvent;

import java.util.List;
import java.util.Objects;

@EventBusSubscriber
public class AccessoryTooltipEvent {

    @SubscribeEvent
    public static void tooltip(AddAttributeTooltipsEvent evt) {
        String st = evt.getStack().getDescriptionId() + ".attributes";
        if (!Component.translatable(st).getString().contains(st)) {
            List<String> strs = StringUtils.linesFrom(Component.translatable(st).getString());

            for (String s : strs)
            {
                evt.addTooltipLines(
                        Component.literal(s)
                );
            }
        }
        if (evt.shouldShow() && evt.getStack().has(ModDataComponents.ARMOR_ACCESSORIES)) {
            for (ItemStack stack : Objects.requireNonNull(evt.getStack().get(ModDataComponents.ARMOR_ACCESSORIES)).items())
            {
                String str = "accessory." + stack.getDescriptionId() + ".attributes";
                if (!Component.translatable(str).getString().contains(str)) {
                    List<String> strs = StringUtils.linesFrom(Component.translatable(str).getString());

                    for (String s : strs)
                    {
                        evt.addTooltipLines(
                                Component.literal(s)
                        );
                    }
                }
            }
        }
        if (evt.shouldShow() && evt.getStack().is(ModTags.ACCESSORIES)) {
            String str = "accessory." + evt.getStack().getDescriptionId() + ".attributes";
            if (!Component.translatable(str).getString().contains(str))
            {
                if (evt.getStack().getAttributeModifiers().modifiers().isEmpty())
                {
                    evt.addTooltipLines(Component.empty());
                    evt.addTooltipLines(
                            Component.literal("§7" +
                            Component.translatable("item.modifiers.body").getString()));
                }

                List<String> strs = StringUtils.linesFrom(Component.translatable(str).getString());

                for (String s : strs)
                {
                    evt.addTooltipLines(
                            Component.literal(s)
                    );
                }
            }
        }
    }
}
