package net.ennway.farworld.item.tool;

import net.ennway.farworld.registries.ModItems;
import net.ennway.farworld.registries.ModSounds;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

public class NetheriteCrossbow extends AbstractCrossbowItem {

    float kickback = 0.6f;

    public NetheriteCrossbow() {
        super(1800, Rarity.COMMON, 1.2f);
    }

    @Override
    public boolean isValidRepairItem(ItemStack stack, ItemStack repairCandidate) {
        return stack.is(Items.NETHERITE_INGOT);
    }

    @Override
    public void performShooting(Level level, LivingEntity shooter, InteractionHand hand, ItemStack weapon, float velocity, float inaccuracy, @Nullable LivingEntity target) {
        super.performShooting(level, shooter, hand, weapon, velocity, inaccuracy, target);
        shooter.addDeltaMovement(shooter.getLookAngle().multiply(-kickback, -kickback, -kickback));
    }

    @Override
    public void onShoot(Level level, LivingEntity livingEntity) {
        level.playSound((Player)null, livingEntity.getX(), livingEntity.getY(), livingEntity.getZ(), SoundEvents.FIREWORK_ROCKET_BLAST, livingEntity.getSoundSource());
    }
}
