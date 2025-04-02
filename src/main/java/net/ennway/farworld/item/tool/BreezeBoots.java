package net.ennway.farworld.item.tool;

import net.ennway.farworld.registries.ModArmorMaterials;
import net.minecraft.world.item.*;

public class BreezeBoots extends ArmorItem {

    public BreezeBoots() {
        super(ModArmorMaterials.BREEZE_ARMOR_MATERIAL, Type.BOOTS, new ArmorItem.Properties()
                .durability(250)
                .rarity(Rarity.RARE)
                .stacksTo(1));
    }
}
