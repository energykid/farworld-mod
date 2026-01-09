package net.ennway.farworld.item.tool.soul_steel;

import net.ennway.farworld.Farworld;
import net.ennway.farworld.registries.ModItems;
import net.ennway.farworld.registries.sets.SetTiers;
import net.minecraft.network.chat.Component;
import net.minecraft.world.damagesource.DamageSources;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.ItemAttributeModifiers;
import net.neoforged.bus.api.Event;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.AddAttributeTooltipsEvent;

import java.util.List;
import java.util.Objects;

public class SoulSteelSword extends SwordItem {
    public SoulSteelSword(Properties properties) {
        super(SetTiers.SOUL_STEEL_TIER, properties
                .attributes(ItemAttributeModifiers.builder()
                        .add(Attributes.ATTACK_DAMAGE,
                                new AttributeModifier(BASE_ATTACK_DAMAGE_ID, 4, AttributeModifier.Operation.ADD_VALUE),
                                EquipmentSlotGroup.MAINHAND)
                        .add(Attributes.ATTACK_SPEED,
                                new AttributeModifier(BASE_ATTACK_SPEED_ID, -2.4, AttributeModifier.Operation.ADD_VALUE),
                                EquipmentSlotGroup.MAINHAND)
                .build()));
    }
}