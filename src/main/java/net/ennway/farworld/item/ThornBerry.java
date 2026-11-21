package net.ennway.farworld.item;

import net.ennway.farworld.entity.projectile.ThornBerryProjectile;
import net.ennway.farworld.registries.ModEntities;
import net.ennway.farworld.registries.ModItems;
import net.ennway.farworld.registries.ModSounds;
import net.minecraft.core.Direction;
import net.minecraft.core.Position;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ProjectileItem;
import net.minecraft.world.level.Level;

public class ThornBerry extends Item implements ProjectileItem {
    public ThornBerry() {
        super(new Properties().stacksTo(16));
    }

    @Override
    public Projectile asProjectile(Level level, Position position, ItemStack itemStack, Direction direction) {
        return new ThornBerryProjectile(ModEntities.THORN_BERRY.get(), level);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand usedHand) {
        ThornBerryProjectile ent = new ThornBerryProjectile(ModEntities.THORN_BERRY.get(), level);

        ent.setPos(player.getEyePosition().add(0, -0.3, 0).add(player.getLookAngle().multiply(0.35, 0.15, 0.35)));
        ent.setDeltaMovement(player.getLookAngle().add(0, 0.2, 0));
        level.addFreshEntity(ent);

        player.getItemInHand(usedHand).consume(1, player);

        player.swing(usedHand);

        player.playSound(ModSounds.THORN_BERRY_THROW.get());

        player.getCooldowns().addCooldown(ModItems.THORN_BERRY.get(), 20);

        return InteractionResultHolder.consume(player.getItemInHand(usedHand));
    }
}
