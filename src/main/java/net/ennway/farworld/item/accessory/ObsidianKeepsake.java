package net.ennway.farworld.item.accessory;

import net.ennway.farworld.item.AccessoryItem;
import net.ennway.farworld.registries.ModParticles;
import net.ennway.farworld.utils.MathUtils;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;
import net.neoforged.neoforge.network.negotiation.NetworkComponentNegotiator;
import net.neoforged.neoforge.network.registration.NetworkChannel;

public class ObsidianKeepsake extends AccessoryItem {

    public ObsidianKeepsake(Properties properties) {
        super(properties);
    }

    double distance = 4.5;

    @Override
    public void onDamagedByEnemy(Entity enemy, Entity player, ItemStack stack, LivingDamageEvent.Pre event) {

        AABB range = new AABB(player.getX() - distance, player.getY() - distance, player.getZ() - distance, player.getX() + distance, player.getY() + distance, player.getZ() + distance);

        for (LivingEntity mob : player.level().getEntitiesOfClass(LivingEntity.class, range))
        {
            if (!mob.is(player) && mob.canAttack((LivingEntity)player))
            {
                mob.hurt(mob.damageSources().playerAttack((Player)player), 4f);

                Vec3 v3 = mob.position().add(new Vec3(0.0f, mob.getBbHeight() / 2.0f, 0.0f));

                mob.playSound(SoundEvents.DECORATED_POT_SHATTER);
            }
        }
    }
}
