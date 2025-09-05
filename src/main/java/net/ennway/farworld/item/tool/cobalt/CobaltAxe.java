package net.ennway.farworld.item.tool.cobalt;

import net.ennway.farworld.registries.sets.SetTiers;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.AxeItem;
import net.minecraft.world.item.component.ItemAttributeModifiers;

public class CobaltAxe extends AxeItem {
    public CobaltAxe(Properties properties) {
        super(SetTiers.COBALT_TIER, properties
                .attributes(ItemAttributeModifiers.builder()
                        .add(Attributes.ATTACK_DAMAGE,
                                new AttributeModifier(BASE_ATTACK_DAMAGE_ID, 8.5, AttributeModifier.Operation.ADD_VALUE),
                                EquipmentSlotGroup.MAINHAND)
                        .add(Attributes.ATTACK_SPEED,
                                new AttributeModifier(BASE_ATTACK_SPEED_ID, -3.1, AttributeModifier.Operation.ADD_VALUE),
                                EquipmentSlotGroup.MAINHAND)
                .build()));
    }
}