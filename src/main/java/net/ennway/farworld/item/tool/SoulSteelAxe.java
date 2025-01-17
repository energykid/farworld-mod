package net.ennway.farworld.item.tool;

import net.ennway.farworld.registries.sets.SoulSteelSet;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.AxeItem;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.component.ItemAttributeModifiers;

import java.util.Objects;

public class SoulSteelAxe extends AxeItem {
    public SoulSteelAxe(Properties properties) {
        super(SoulSteelSet.SOUL_STEEL_TIER, properties
                .attributes(ItemAttributeModifiers.builder()
                        .add(Attributes.ENTITY_INTERACTION_RANGE,
                                new AttributeModifier(Objects.requireNonNull(Attributes.ENTITY_INTERACTION_RANGE.getKey()).location(), 1f, AttributeModifier.Operation.ADD_VALUE),
                                EquipmentSlotGroup.MAINHAND)
                        .add(Attributes.ATTACK_DAMAGE,
                                new AttributeModifier(BASE_ATTACK_DAMAGE_ID, 8, AttributeModifier.Operation.ADD_VALUE),
                                EquipmentSlotGroup.MAINHAND)
                        .add(Attributes.ATTACK_SPEED,
                                new AttributeModifier(BASE_ATTACK_SPEED_ID, -3.1, AttributeModifier.Operation.ADD_VALUE),
                                EquipmentSlotGroup.MAINHAND)
                .build()));
    }
}