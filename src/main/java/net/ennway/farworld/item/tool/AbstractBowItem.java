package net.ennway.farworld.item.tool;

import net.ennway.farworld.registries.ModItems;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
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
import net.neoforged.neoforge.event.EventHooks;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Optional;

public class AbstractBowItem extends BowItem {

    private boolean startSoundPlayed = false;

    public float velocityMultiplier = 1.0f;

    @Override
    public void onUseTick(Level level, LivingEntity livingEntity, ItemStack stack, int count) {
            float f = (float)(stack.getUseDuration(livingEntity) - count) / (float)getUseDuration(stack, livingEntity);
            if (f < 0.2F) {
                this.startSoundPlayed = false;
            }

            if (f >= 0.2F && !this.startSoundPlayed) {
                this.startSoundPlayed = true;
                onDraw(level, livingEntity);
            }
    }

    @Override
    public boolean isRepairable(ItemStack stack) {
        return true;
    }

    public AbstractBowItem(int durability, Rarity rarity, float velMult) {
        super(new Properties()
                .durability(durability)
                .stacksTo(1)
                .rarity(rarity));
        this.velocityMultiplier = velMult;
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
        projectile.shootFromRotation(shooter, shooter.getXRot(), shooter.getYRot() + angle, 0.0F, velocity * this.velocityMultiplier, inaccuracy);
        onShoot(shooter.level(), shooter);
    }


    public void onDraw(Level level, LivingEntity livingEntity)
    {

    }
    public void onShoot(Level level, LivingEntity livingEntity)
    {

    }
}
