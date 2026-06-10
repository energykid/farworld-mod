package net.ennway.farworld.event;

import net.ennway.farworld.Farworld;
import net.ennway.farworld.entity.projectile.BlackIceImplosionProjectile;
import net.ennway.farworld.item.tool.gloomstone.GloomstoneEffects;
import net.ennway.farworld.registries.*;
import net.ennway.farworld.utils.AccessoryUtils;
import net.ennway.farworld.utils.MathUtils;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.EnderMan;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.phys.Vec3;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.EntityEvent;
import net.neoforged.neoforge.event.entity.EntityTeleportEvent;
import net.neoforged.neoforge.event.entity.living.EnderManAngerEvent;
import net.neoforged.neoforge.event.entity.player.AttackEntityEvent;
import net.neoforged.neoforge.event.entity.player.PlayerXpEvent;
import net.neoforged.neoforge.event.level.BlockDropsEvent;
import net.neoforged.neoforge.event.level.BlockEvent;
import net.neoforged.neoforge.event.tick.EntityTickEvent;

import java.util.function.Supplier;

@EventBusSubscriber(modid = Farworld.MOD_ID)
public class GearEffects {

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

    /*
    @SubscribeEvent
    public static void dontDealEnderPearlDamage(EntityTeleportEvent.EnderPearl event)
    {
        Player player = event.getPlayer();

        if (AccessoryUtils.playerHasAccessory(player, ModItems.APOCALYPSE_CORE.get()))
        {
            event.setAttackDamage(0);
        }
    }

    @SubscribeEvent
    public static void dontAggro(EnderManAngerEvent event)
    {
        if (AccessoryUtils.playerHasAccessory(event.getPlayer(), ModItems.APOCALYPSE_CORE.get()))
        {
            event.setCanceled(true);
        }
    }
    */

    @SubscribeEvent
    public static void onHitWithSword(AttackEntityEvent evt)
    {
        Player player = evt.getEntity();

        ItemStack stack = evt.getEntity().getItemInHand(evt.getEntity().getUsedItemHand());

        if (evt.getTarget() instanceof LivingEntity enemy) {

            if (!stack.isEmpty() && !enemy.isDeadOrDying() && player.fallDistance > 0f && player.canAttack(enemy) && player.swingTime == 0)
            {
                if (stack.is(ModTags.HAS_NECROMIUM_EFFECT))
                {
                    enemy.level().playSound(enemy, enemy.blockPosition(), SoundEvents.SOUL_ESCAPE.getDelegate().value(), SoundSource.PLAYERS, 1.2f, 0.4f);
                    enemy.level().playSound(enemy, enemy.blockPosition(), SoundEvents.ANCIENT_DEBRIS_BREAK, SoundSource.PLAYERS, 1.2f, 0.7f);

                    for (int i = 0; i < 5; i++)
                    {
                        enemy.level().addParticle(
                                ModParticles.NECROMIUM_EFFECT.get(),
                                enemy.getPosition(0).x + Mth.nextDouble(enemy.getRandom(), -0.6, 0.6),
                                enemy.getPosition(0).y,
                                enemy.getPosition(0).z + Mth.nextDouble(enemy.getRandom(), -0.6, 0.6),
                                0f,
                                0f,
                                0f
                        );
                    }
                }
                if (stack.is(ModTags.HAS_SOUL_STEEL_EFFECT))
                {
                    enemy.level().playSound(enemy, enemy.blockPosition(), SoundEvents.FIRECHARGE_USE, SoundSource.PLAYERS, 0.3f, 1.4f);
                    enemy.level().playSound(enemy, enemy.blockPosition(), SoundEvents.SOUL_ESCAPE.getDelegate().value(), SoundSource.PLAYERS, 1.2f, 1.2f);

                    spawnFireEffect(enemy, ModParticles.SOUL_SMOKE, ModParticles.SOUL_FIRE_TENDRIL);
                }
                if (stack.is(ModTags.HAS_NETHERITE_EFFECT))
                {
                    enemy.level().playSound(enemy, enemy.blockPosition(), SoundEvents.FIRECHARGE_USE, SoundSource.PLAYERS, 0.3f, 0.6f);
                    enemy.level().playSound(enemy, enemy.blockPosition(), SoundEvents.NETHERITE_BLOCK_BREAK, SoundSource.PLAYERS, 1.2f, 0.7f);

                    spawnFireEffect(enemy, ModParticles.INFERNAL_SMOKE, ModParticles.INFERNAL_TENDRIL);
                }
                if (stack.is(ModTags.HAS_BLACK_ICE_EFFECT))
                {
                    BlackIceImplosionProjectile proj = new BlackIceImplosionProjectile(ModEntities.BLACK_ICE_AOE_ENTITY.get(), enemy.level());
                    proj.setPos(enemy.position().add(new Vec3(0.0, enemy.getBbHeight() / 2.0, 0.0)));

                    enemy.level().addFreshEntity(proj);
                }
                if (stack.is(ModTags.HAS_GLOOMSTONE_EFFECT))
                {
                    GloomstoneEffects.doCombatEffect(enemy, player);
                }
            }
        }
    }

    @SubscribeEvent
    public static void onMineBlock(BlockEvent.BreakEvent evt) {
        Player player = evt.getPlayer();

        ItemStack stack = player.getItemInHand(player.getUsedItemHand());

        if (stack.is(ModTags.HAS_GLOOMSTONE_EFFECT)) {
            GloomstoneEffects.doMiningEffect(player, evt.getPos());
        }
    }
}
