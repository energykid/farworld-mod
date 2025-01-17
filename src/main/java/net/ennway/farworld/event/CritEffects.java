package net.ennway.farworld.event;

import net.ennway.farworld.Farworld;
import net.ennway.farworld.registries.ModItems;
import net.ennway.farworld.registries.ModParticles;
import net.ennway.farworld.utils.MathUtils;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.phys.Vec3;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.player.AttackEntityEvent;

import java.util.function.Supplier;

@EventBusSubscriber(modid = Farworld.MOD_ID, bus = EventBusSubscriber.Bus.GAME)
public class CritEffects {

    public static void spawnFireEffect(Entity entityAt, Supplier<SimpleParticleType> particleType, Supplier<SimpleParticleType> tendrilType)
    {
        Vec3 v3 = entityAt.position().add(entityAt.getBbWidth() / 2, entityAt.getBbHeight() / 2, entityAt.getBbWidth() / 2).add(
                MathUtils.randomDouble(entityAt.getRandom(), -0.7, 0.7),
                MathUtils.randomDouble(entityAt.getRandom(), -0.7, 0.7),
                MathUtils.randomDouble(entityAt.getRandom(), -0.7, 0.7)
        );
        Vec3 vel3 = new Vec3(0, 2, 0);

        for (int e = 0; e < 15; e++) {
            entityAt.level().addParticle(
                    particleType.get(),
                    v3.x,
                    v3.y,
                    v3.z,
                    vel3.x + MathUtils.randomDouble(entityAt.getRandom(), -2, 2),
                    vel3.y + MathUtils.randomDouble(entityAt.getRandom(), -2, 2),
                    vel3.z + MathUtils.randomDouble(entityAt.getRandom(), -2, 2)
            );
        }
        for (int e = 0; e < 6; e++) {
            entityAt.level().addParticle(
                    tendrilType.get(),
                    v3.x,
                    v3.y,
                    v3.z,
                    0f,
                    0f,
                    0f
            );
        }
    }

    @SubscribeEvent
    public static void onHitWithSword(AttackEntityEvent event)
    {
        Player player = event.getEntity();

        ItemStack stack = event.getEntity().getItemInHand(event.getEntity().getUsedItemHand());

        if (event.getTarget() instanceof LivingEntity enemy) {

            if (!enemy.isDeadOrDying() && player.fallDistance > 0f && player.canAttack(enemy) && !player.swinging)
            {
                if (stack.is(ModItems.SOUL_STEEL_SWORD) || stack.is(ModItems.SOUL_STEEL_AXE))
                {
                    enemy.level().playSound(enemy, enemy.blockPosition(), SoundEvents.FIRECHARGE_USE, SoundSource.PLAYERS, 1f, 1.6f);
                    enemy.level().playSound(enemy, enemy.blockPosition(), SoundEvents.SOUL_ESCAPE.getDelegate().value(), SoundSource.PLAYERS, 1.2f, 0.9f);

                    spawnFireEffect(enemy, ModParticles.SOUL_SMOKE, ModParticles.SOUL_FIRE_TENDRIL);
                }
                if (stack.is(Items.NETHERITE_SWORD) || stack.is(Items.NETHERITE_AXE))
                {
                    enemy.level().playSound(enemy, enemy.blockPosition(), SoundEvents.FIRECHARGE_USE, SoundSource.PLAYERS, 1f, 0.6f);

                    spawnFireEffect(enemy, ModParticles.INFERNAL_SMOKE, ModParticles.INFERNAL_TENDRIL);
                }
            }
        }
    }
}
