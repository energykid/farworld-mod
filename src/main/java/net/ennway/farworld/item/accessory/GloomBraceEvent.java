package net.ennway.farworld.item.accessory;

import net.ennway.farworld.registries.ModEffects;
import net.ennway.farworld.registries.ModItems;
import net.ennway.farworld.registries.ModParticles;
import net.ennway.farworld.utils.AccessoryUtils;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.AABB;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;

import java.util.Objects;

@EventBusSubscriber
public class GloomBraceEvent {
    @SubscribeEvent
    public static void fallDmg(LivingIncomingDamageEvent evt)
    {
        if (evt.getEntity() instanceof Player plr)
        {
            if (AccessoryUtils.playerHasAccessory(plr, ModItems.GLOOM_BRACE.get()))
            {
                if (evt.getSource().is(DamageTypes.FALL))
                {
                    if (!plr.isCrouching())
                    {
                        Objects.requireNonNull(plr.level().getServer().getLevel(plr.level().dimension())).sendParticles(ModParticles.GLOOM_SHOCKWAVE.get(), plr.getX(), plr.getY() + 0.1, plr.getZ(),1, 0, 0, 0, 0);

                        double distance = 4;

                        AABB range = new AABB(plr.getX() - distance, plr.getY() - distance, plr.getZ() - distance, plr.getX() + distance, plr.getY() + distance, plr.getZ() + distance);

                        for (Mob mob : plr.level().getEntitiesOfClass(Mob.class, range)) {
                            if (mob.getTarget() == plr) {
                                if (!mob.is(plr) && mob.canAttack(plr)) {
                                    mob.addEffect(new MobEffectInstance(ModEffects.PARALYSIS, 40));
                                }
                            }
                        }
                    }
                    if (evt.getAmount() <= 6)
                    {
                        evt.setCanceled(true);
                    }
                    else
                    {
                        evt.setAmount(evt.getAmount() - 6);
                    }
                }
            }
        }
    }
}
