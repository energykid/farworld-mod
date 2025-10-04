package net.ennway.farworld.item.tool;

import net.ennway.farworld.registries.ModItems;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.CrossbowItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.enchantment.EnchantmentEffectComponents;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

public class AbstractCrossbowItem extends CrossbowItem {

    public AbstractCrossbowItem(int durability, Rarity rarity) {
        super(new Properties()
                .durability(durability)
                .stacksTo(1)
                .rarity(rarity));
    }

    @Override
    public int getUseDuration(ItemStack stack, LivingEntity entity) {
        return getChargeDuration(stack, entity) + 3;
    }

    public static int getChargeDuration(ItemStack stack, LivingEntity shooter) {
        float f = EnchantmentHelper.modifyCrossbowChargingTime(stack, shooter, 1.25F);
        return Mth.floor(f * 12.0F);
    }

    private boolean startSoundPlayed = false;
    private boolean midLoadSoundPlayed = false;
    private static final ChargingSounds DEFAULT_SOUNDS;

    static {
        DEFAULT_SOUNDS = new ChargingSounds(Optional.of(SoundEvents.CROSSBOW_LOADING_START), Optional.of(SoundEvents.CROSSBOW_LOADING_MIDDLE), Optional.of(SoundEvents.CROSSBOW_LOADING_END));
    }

    ChargingSounds getChargingSounds(ItemStack stack) {
        return (ChargingSounds)EnchantmentHelper.pickHighestLevel(stack, EnchantmentEffectComponents.CROSSBOW_CHARGING_SOUNDS).orElse(DEFAULT_SOUNDS);
    }

    @Override
    protected void shootProjectile(LivingEntity shooter, Projectile projectile, int index, float velocity, float inaccuracy, float angle, @Nullable LivingEntity target) {
        super.shootProjectile(shooter, projectile, index, velocity, inaccuracy, angle, target);
        onShoot(shooter.level(), shooter);
    }

    @Override
    public void onUseTick(Level level, LivingEntity livingEntity, ItemStack stack, int count) {
        if (!level.isClientSide) {
            ChargingSounds crossbowitem$chargingsounds = this.getChargingSounds(stack);
            float f = (float)(stack.getUseDuration(livingEntity) - count) / (float)getChargeDuration(stack, livingEntity);
            if (f < 0.2F) {
                this.startSoundPlayed = false;
                this.midLoadSoundPlayed = false;
            }

            if (f >= 0.2F && !this.startSoundPlayed) {
                this.startSoundPlayed = true;
                crossbowitem$chargingsounds.start().ifPresent((p_352849_) -> {
                    level.playSound((Player)null, livingEntity.getX(), livingEntity.getY(), livingEntity.getZ(), (SoundEvent)p_352849_.value(), SoundSource.PLAYERS, 0.5F, 1.0F);
                });
                onBeginLoad(level, livingEntity, stack);
            }

            if (f >= 0.5F && !this.midLoadSoundPlayed) {
                this.midLoadSoundPlayed = true;
                crossbowitem$chargingsounds.mid().ifPresent((p_352855_) -> {
                    level.playSound((Player)null, livingEntity.getX(), livingEntity.getY(), livingEntity.getZ(), (SoundEvent)p_352855_.value(), SoundSource.PLAYERS, 0.5F, 1.0F);
                });
                onLoad(level, livingEntity, stack);
            }
        }

    }

    @Override
    public int getDefaultProjectileRange() {
        return 25;
    }

    public void onBeginLoad(Level level, LivingEntity livingEntity, ItemStack stack)
    {

    }
    public void onLoad(Level level, LivingEntity livingEntity, ItemStack stack)
    {

    }
    public void onShoot(Level level, LivingEntity livingEntity)
    {

    }
}
