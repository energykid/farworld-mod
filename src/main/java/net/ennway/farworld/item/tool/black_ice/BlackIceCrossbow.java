package net.ennway.farworld.item.tool.black_ice;

import net.ennway.farworld.item.tool.AbstractCrossbowItem;
import net.ennway.farworld.registries.ModItems;
import net.ennway.farworld.registries.ModSounds;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.*;
import net.minecraft.world.item.component.ChargedProjectiles;
import net.minecraft.world.item.enchantment.EnchantmentEffectComponents;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

public class BlackIceCrossbow extends AbstractCrossbowItem {

    public BlackIceCrossbow(Item.Properties properties) {
        super(1800, Rarity.UNCOMMON, 1.35f);
        this.velocityMultiplier = 1.35f;
    }

    @Override
    public boolean isValidRepairItem(ItemStack stack, ItemStack repairCandidate) {
        return stack.is(ModItems.BLACK_ICE_GEM);
    }

    @Override
    public void onBeginLoad(Level level, LivingEntity livingEntity, ItemStack stack) {
        level.playSound((Player)null, livingEntity.getX(), livingEntity.getY(), livingEntity.getZ(), ModSounds.VOID_CROSSBOW_INWARDS.get(), livingEntity.getSoundSource());
    }

    @Override
    public void onShoot(Level level, LivingEntity livingEntity) {
        level.playSound((Player)null, livingEntity.getX(), livingEntity.getY(), livingEntity.getZ(), ModSounds.VOID_BOW_RELEASE.get(), livingEntity.getSoundSource());
    }
}
