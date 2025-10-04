package net.ennway.farworld.item.tool;

import net.ennway.farworld.registries.ModItems;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.*;
import net.minecraft.world.item.enchantment.EnchantmentEffectComponents;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

public class AbstractBowItem extends BowItem {

    private boolean startSoundPlayed = false;

    @Override
    public void onUseTick(Level level, LivingEntity livingEntity, ItemStack stack, int count) {
        if (!level.isClientSide) {
            float f = (float)(stack.getUseDuration(livingEntity) - count) / (float)getUseDuration(stack, livingEntity);
            if (f < 0.2F) {
                this.startSoundPlayed = false;
            }

            if (f >= 0.2F && !this.startSoundPlayed) {
                this.startSoundPlayed = true;
                onDraw(level, livingEntity);
            }
        }
    }

    public AbstractBowItem(int durability, Rarity rarity) {
        super(new Properties()
                .durability(durability)
                .stacksTo(1)
                .rarity(rarity));
    }

    @Override
    public int getUseDuration(ItemStack stack, LivingEntity entity) {
        return 65000;
    }

    @Override
    public int getDefaultProjectileRange() {
        return 25;
    }

    @Override
    protected void shootProjectile(LivingEntity shooter, Projectile projectile, int index, float velocity, float inaccuracy, float angle, @Nullable LivingEntity target) {
        super.shootProjectile(shooter, projectile, index, velocity, inaccuracy, angle, target);
        onShoot(shooter.level(), shooter);
    }



    public void onDraw(Level level, LivingEntity livingEntity)
    {

    }
    public void onShoot(Level level, LivingEntity livingEntity)
    {

    }
}
