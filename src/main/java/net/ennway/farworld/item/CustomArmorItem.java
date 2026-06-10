package net.ennway.farworld.item;

import net.minecraft.core.Holder;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;

public class CustomArmorItem extends ArmorItem {

    public Holder<SoundEvent> snd;

    public CustomArmorItem(Holder<ArmorMaterial> material, Type type, Properties properties, Holder<SoundEvent> sound) {
        super(material, type, properties);
        snd = sound;
    }

    @Override
    public Holder<SoundEvent> getEquipSound() {
        return snd;
    }
}
