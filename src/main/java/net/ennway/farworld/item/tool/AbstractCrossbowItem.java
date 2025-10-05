package net.ennway.farworld.item.tool;

import net.ennway.farworld.registries.ModItems;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.CrossbowItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.enchantment.EnchantmentEffectComponents;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;
import org.joml.Quaternionf;
import org.joml.Vector3f;

import java.util.Optional;

public class AbstractCrossbowItem extends CrossbowItem {

    public float velocityMultiplier = 1.0f;

    public float kickback = 0.5f;
    public float kickbackYMod = 1.5f;

    public AbstractCrossbowItem(int durability, Rarity rarity, float velMult) {
        super(new Properties()
                .durability(durability)
                .stacksTo(1)
                .rarity(rarity));
        this.velocityMultiplier = velMult;
    }

    @Override
    public boolean isRepairable(ItemStack stack) {
        return true;
    }

    private boolean startSoundPlayed = false;
    private boolean startSoundPlayedBothSides = false;
    private boolean midLoadSoundPlayed = false;
    private static final ChargingSounds DEFAULT_SOUNDS;

    static {
        DEFAULT_SOUNDS = new ChargingSounds(Optional.of(SoundEvents.CROSSBOW_LOADING_START), Optional.of(SoundEvents.CROSSBOW_LOADING_MIDDLE), Optional.of(SoundEvents.CROSSBOW_LOADING_END));
    }

    ChargingSounds getChargingSounds(ItemStack stack) {
        return (ChargingSounds)EnchantmentHelper.pickHighestLevel(stack, EnchantmentEffectComponents.CROSSBOW_CHARGING_SOUNDS).orElse(DEFAULT_SOUNDS);
    }

    private static Vector3f getProjectileShotVector(LivingEntity shooter, Vec3 distance, float angle) {
        Vector3f vector3f = distance.toVector3f().normalize();
        Vector3f vector3f1 = (new Vector3f(vector3f)).cross(new Vector3f(0.0F, 1.0F, 0.0F));
        if ((double)vector3f1.lengthSquared() <= 1.0E-7) {
            Vec3 vec3 = shooter.getUpVector(1.0F);
            vector3f1 = (new Vector3f(vector3f)).cross(vec3.toVector3f());
        }

        Vector3f vector3f2 = (new Vector3f(vector3f)).rotateAxis(1.5707964F, vector3f1.x, vector3f1.y, vector3f1.z);
        return (new Vector3f(vector3f)).rotateAxis(angle * 0.017453292F, vector3f2.x, vector3f2.y, vector3f2.z);
    }

    @Override
    public void performShooting(Level level, LivingEntity shooter, InteractionHand hand, ItemStack weapon, float velocity, float inaccuracy, @Nullable LivingEntity target) {
        super.performShooting(level, shooter, hand, weapon, velocity, inaccuracy, target);
        if (kickback > 0f) {
            if (!shooter.onGround()) {
                if (shooter instanceof Player player) {
                    if (!player.isCreative()) {
                        if (kickback > 0f) {
                            shooter.fallDistance = 0;
                            shooter.addDeltaMovement(shooter.getLookAngle().multiply(-kickback, -kickback * kickbackYMod, -kickback));
                        }
                    }
                } else {
                    shooter.fallDistance = 0;
                    shooter.addDeltaMovement(shooter.getLookAngle().multiply(-kickback, -kickback * kickbackYMod, -kickback));
                }
            }
        }
    }

    private static float getRandomShotPitch(boolean isHighPitched, RandomSource random) {
        float f = isHighPitched ? 0.63F : 0.43F;
        return 1.0F / (random.nextFloat() * 0.5F + 1.8F) + f;
    }
    private static float getShotPitch(RandomSource random, int index) {
        return index == 0 ? 1.0F : getRandomShotPitch((index & 1) == 1, random);
    }

    @Override
    protected void shootProjectile(LivingEntity shooter, Projectile projectile, int index, float velocity, float inaccuracy, float angle, @Nullable LivingEntity target) {
        Vector3f vector3f;
        if (target != null) {
            double d0 = target.getX() - shooter.getX();
            double d1 = target.getZ() - shooter.getZ();
            double d2 = Math.sqrt(d0 * d0 + d1 * d1);
            double d3 = target.getY(0.3333333333333333) - projectile.getY() + d2 * 0.20000000298023224;
            vector3f = getProjectileShotVector(shooter, new Vec3(d0, d3, d1), angle);
        } else {
            Vec3 vec3 = shooter.getUpVector(1.0F);
            Quaternionf quaternionf = (new Quaternionf()).setAngleAxis((double)(angle * 0.017453292F), vec3.x, vec3.y, vec3.z);
            Vec3 vec31 = shooter.getViewVector(1.0F);
            vector3f = vec31.toVector3f().rotate(quaternionf);
        }

        projectile.shoot((double)vector3f.x(), (double)vector3f.y(), (double)vector3f.z(), velocity * this.velocityMultiplier, inaccuracy);
        float f = getShotPitch(shooter.getRandom(), index);
        shooter.level().playSound((Player)null, shooter.getX(), shooter.getY(), shooter.getZ(), SoundEvents.CROSSBOW_SHOOT, shooter.getSoundSource(), 1.0F, f);
        onShoot(shooter.level(), shooter);
    }

    @Override
    public void releaseUsing(ItemStack stack, Level level, LivingEntity entityLiving, int timeLeft) {
        super.releaseUsing(stack, level, entityLiving, timeLeft);
        onLoad(level, entityLiving, stack);
    }

    @Override
    public void onUseTick(Level level, LivingEntity livingEntity, ItemStack stack, int count) {
        float f = (float) (stack.getUseDuration(livingEntity) - count) / (float) getChargeDuration(stack, livingEntity);

        if (!level.isClientSide())
        {
            ChargingSounds crossbowitem$chargingsounds = this.getChargingSounds(stack);
             if (f < 0.2F) {
                this.startSoundPlayed = false;
                this.midLoadSoundPlayed = false;
            }

            if (f >= 0.2F && !this.startSoundPlayed) {
                this.startSoundPlayed = true;
                crossbowitem$chargingsounds.start().ifPresent((p_352849_) -> {
                    level.playSound((Player) null, livingEntity.getX(), livingEntity.getY(), livingEntity.getZ(), (SoundEvent) p_352849_.value(), SoundSource.PLAYERS, 0.5F, 1.0F);
                });
                onBeginLoad(level, livingEntity, stack);
            }

            if (f >= 0.5F && !this.midLoadSoundPlayed) {
                this.midLoadSoundPlayed = true;
                crossbowitem$chargingsounds.mid().ifPresent((p_352855_) -> {
                    level.playSound((Player) null, livingEntity.getX(), livingEntity.getY(), livingEntity.getZ(), (SoundEvent) p_352855_.value(), SoundSource.PLAYERS, 0.5F, 1.0F);
                });
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
