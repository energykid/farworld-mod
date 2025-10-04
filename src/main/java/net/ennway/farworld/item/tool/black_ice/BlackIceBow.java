package net.ennway.farworld.item.tool.black_ice;

import net.ennway.farworld.item.tool.AbstractBowItem;
import net.ennway.farworld.registries.ModItems;
import net.ennway.farworld.registries.ModSounds;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BowItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;

public class BlackIceBow extends AbstractBowItem {

    public BlackIceBow(Item.Properties properties) {
        super(1600, Rarity.UNCOMMON, 1.25f);
    }

    @Override
    public boolean isValidRepairItem(ItemStack stack, ItemStack repairCandidate) {
        return stack.is(ModItems.BLACK_ICE_GEM);
    }

    @Override
    public void onDraw(Level level, LivingEntity livingEntity) {
        livingEntity.playSound(ModSounds.VOID_CROSSBOW_INWARDS.get());
    }

    @Override
    public void onShoot(Level level, LivingEntity livingEntity) {
        level.playSound(null, livingEntity.getX(), livingEntity.getY(), livingEntity.getZ(), ModSounds.VOID_BOW_RELEASE.get(), livingEntity.getSoundSource());
    }
}
