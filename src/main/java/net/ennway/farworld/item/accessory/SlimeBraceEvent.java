package net.ennway.farworld.item.accessory;

import net.ennway.farworld.registries.ModEffects;
import net.ennway.farworld.registries.ModItems;
import net.ennway.farworld.registries.ModParticles;
import net.ennway.farworld.utils.AccessoryUtils;
import net.ennway.farworld.utils.BehaviorUtils;
import net.ennway.farworld.utils.WorldUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.TamableAnimal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec2;
import net.minecraft.world.phys.Vec3;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;

import java.util.Objects;

@EventBusSubscriber
public class SlimeBraceEvent {
    @SubscribeEvent
    public static void fallDmg(LivingIncomingDamageEvent evt)
    {
        if (evt.getEntity() instanceof Player plr)
        {
            if (AccessoryUtils.playerHasAccessory(plr, ModItems.SLIME_BRACE.get()) || AccessoryUtils.playerHasAccessory(plr, ModItems.GLOOM_BRACE.get()))
            {
                if (evt.getSource().is(DamageTypes.FALL))
                {
                    int val = 6;
                    if (AccessoryUtils.playerHasAccessory(plr, ModItems.SLIME_BRACE.get()))
                    {
                        Objects.requireNonNull(plr.level().getServer().getLevel(plr.level().dimension())).sendParticles(ModParticles.SLIME_STREAKS.get(), plr.getX(), plr.getY() + 0.1, plr.getZ(),1, 0, 0, 0, 0);
                        Objects.requireNonNull(plr.level().getServer().getLevel(plr.level().dimension())).sendParticles(ModParticles.BRACE_BOUNCE.get(), plr.getX(), plr.getY() + 0.2, plr.getZ(),1, 0, 0, 0, 0);

                        plr.setDeltaMovement(plr.getDeltaMovement().x, 0.4, plr.getDeltaMovement().z);
                    }
                    else
                    {
                        val = 8;
                        Objects.requireNonNull(plr.level().getServer().getLevel(plr.level().dimension())).sendParticles(ModParticles.GLOOM_SHOCKWAVE.get(), plr.getX(), plr.getY() + 0.1, plr.getZ(),1, 0, 0, 0, 0);

                        double distance = 4;

                        // spawn block particles in a circle
                        for (int i = -3; i <= 3; i++)
                        {
                            for (int j = -3; j <= 3; j++)
                            {
                                // use distance function to determine circle
                                if (new Vec2(i, j).distanceToSqr(Vec2.ZERO) < 3)
                                {
                                    int y = (int)BehaviorUtils.groundedVec3(plr.level(), plr.getEyePosition().add(i, 0, j).toVector3f()).y;
                                    BlockPos p = new BlockPos(plr.getOnPos().getX() + i, y - 1, plr.getOnPos().getZ() + j);
                                    plr.level().addDestroyBlockEffect(p, plr.level().getBlockState(p));
                                }
                            }
                        }

                        // get all mobs in a range around the player
                        AABB range = new AABB(plr.getX() - distance, plr.getY() - distance, plr.getZ() - distance, plr.getX() + distance, plr.getY() + distance, plr.getZ() + distance);

                        for (LivingEntity mob : plr.level().getEntitiesOfClass(LivingEntity.class, range)) {
                            if (!(mob instanceof TamableAnimal animal) || !animal.isTame()) {
                                if (!(mob instanceof Player))
                                {
                                    Vec3 v = mob.position().subtract(plr.position()).normalize().multiply(1, 0,1);
                                    mob.addDeltaMovement(v);
                                    mob.addDeltaMovement(new Vec3(0, 0.4, 0));
                                    mob.addEffect(new MobEffectInstance(ModEffects.PARALYSIS, 40));
                                    mob.hurt(plr.damageSources().magic(), 5);
                                }
                            }
                        }
                    }

                    plr.hurtMarked = true;

                    if (evt.getAmount() <= val)
                    {
                        evt.setCanceled(true);
                    }
                    evt.setAmount(evt.getAmount() - val);
                }
            }
        }
    }
}
