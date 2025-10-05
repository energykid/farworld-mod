package net.ennway.farworld.item.tool.soul_steel;

import net.ennway.farworld.item.tool.AbstractBowItem;
import net.ennway.farworld.registries.ModItems;
import net.ennway.farworld.registries.ModSounds;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.level.Level;

public class SoulSteelBow extends AbstractBowItem {
    public SoulSteelBow() {
        super(450, Rarity.COMMON, 1.15f);
    }

    @Override
    public boolean isValidRepairItem(ItemStack stack, ItemStack repairCandidate) {
        return stack.is(ModItems.SOUL_STEEL);
    }

    @Override
    public void onShoot(Level level, LivingEntity livingEntity) {
        level.playSound(null, livingEntity.getX(), livingEntity.getY(), livingEntity.getZ(), SoundEvents.SOUL_ESCAPE.value(), livingEntity.getSoundSource(), 0.7f, 0.5f);
    }
}
