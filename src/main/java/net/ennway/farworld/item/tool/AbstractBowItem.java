package net.ennway.farworld.item.tool;

import net.ennway.farworld.registries.ModDataComponents;
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

    public float powerForTime(int charge)
    {
        float ff = 20.0F;
        ff *= this.components().get(ModDataComponents.BOW_DRAW_SPEED.get()).floatValue();
        float f = (float)charge / ff;
        f = (f * f + f * 2.0F) / 3.0F;
        if (f > 1.0F) {
            f = 1.0F;
        }

        return f;
    }

    @Override
    public void releaseUsing(ItemStack stack, Level level, LivingEntity entityLiving, int timeLeft) {
        if (entityLiving instanceof Player player) {
            ItemStack itemstack = player.getProjectile(stack);
            if (!itemstack.isEmpty()) {
                int i = this.getUseDuration(stack, entityLiving) - timeLeft;
                i = EventHooks.onArrowLoose(stack, level, player, i, !itemstack.isEmpty());
                if (i < 0) {
                    return;
                }

                float f = powerForTime(i);
                if (!((double)f < 0.1)) {
                    List<ItemStack> list = draw(stack, itemstack, player);
                    if (level instanceof ServerLevel) {
                        ServerLevel serverlevel = (ServerLevel)level;
                        if (!list.isEmpty()) {
                            this.shoot(serverlevel, player, player.getUsedItemHand(), stack, list, f * 3.0F, 1.0F, f == 1.0F, (LivingEntity)null);
                        }
                    }

                    level.playSound((Player)null, player.getX(), player.getY(), player.getZ(), SoundEvents.ARROW_SHOOT, SoundSource.PLAYERS, 1.0F, 1.0F / (level.getRandom().nextFloat() * 0.4F + 1.2F) + f * 0.5F);
                    player.awardStat(Stats.ITEM_USED.get(this));
                }
            }
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

    public AbstractBowItem(int durability, Rarity rarity, float velMult, double drawMult) {
        super(new Properties()
                .durability(durability)
                .stacksTo(1)
                .rarity(rarity)
                .component(ModDataComponents.BOW_DRAW_SPEED, drawMult));
        this.velocityMultiplier = velMult;
    }

    @Override
    public UseAnim getUseAnimation(ItemStack stack) {
        return UseAnim.BOW;
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
