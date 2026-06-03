package net.ennway.farworld.item.accessory;

import net.ennway.farworld.registries.ModItems;
import net.ennway.farworld.registries.ModParticles;
import net.ennway.farworld.utils.AccessoryUtils;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;

import java.util.Objects;

@EventBusSubscriber
public class SlimeBraceEvent {
    @SubscribeEvent
    public static void fallDmg(LivingIncomingDamageEvent evt)
    {
        if (evt.getEntity() instanceof Player plr)
        {
            if (AccessoryUtils.playerHasAccessory(plr, ModItems.SLIME_BRACE.get()) && !AccessoryUtils.playerHasAccessory(plr, ModItems.GLOOM_BRACE.get()))
            {
                if (evt.getSource().is(DamageTypes.FALL))
                {
                    if (!plr.isCrouching())
                    {
                        Objects.requireNonNull(plr.level().getServer().getLevel(plr.level().dimension())).sendParticles(ModParticles.SLIME_STREAKS.get(), plr.getX(), plr.getY() + 0.1, plr.getZ(),1, 0, 0, 0, 0);
                        Objects.requireNonNull(plr.level().getServer().getLevel(plr.level().dimension())).sendParticles(ModParticles.BRACE_BOUNCE.get(), plr.getX(), plr.getY() + 0.1, plr.getZ(),1, 0, 0, 0, 0);

                        plr.setDeltaMovement(plr.getDeltaMovement().x, 0.4, plr.getDeltaMovement().x);
                        plr.hurtMarked = true;
                    }
                    if (evt.getAmount() <= 4)
                    {
                        evt.setCanceled(true);
                    }
                    else
                    {
                        evt.setAmount(evt.getAmount() - 4);
                    }
                }
            }
        }
    }
}
