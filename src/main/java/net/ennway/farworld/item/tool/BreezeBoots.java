package net.ennway.farworld.item.tool;

import net.ennway.farworld.registries.ModArmorMaterials;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.*;
import net.minecraft.world.item.component.ItemAttributeModifiers;

public class BreezeBoots extends ArmorItem {

    public BreezeBoots() {
        super(ModArmorMaterials.BREEZE_ARMOR_MATERIAL, Type.BOOTS, new Properties()
                .durability(250)
                .attributes(ItemAttributeModifiers.builder().add(
                        Attributes.MOVEMENT_SPEED,
                        new AttributeModifier(Attributes.MOVEMENT_SPEED.getKey().location(), 0.025, AttributeModifier.Operation.ADD_VALUE),
                        EquipmentSlotGroup.FEET).build())
                .rarity(Rarity.RARE)
                .stacksTo(1));
    }
}
