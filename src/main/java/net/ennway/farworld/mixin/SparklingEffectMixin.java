package net.ennway.farworld.mixin;

import net.ennway.farworld.Farworld;
import net.ennway.farworld.registries.ModEffects;
import net.ennway.farworld.utils.BehaviorUtils;
import net.ennway.farworld.utils.ServerUtils;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(MobEffectInstance.class)
public class SparklingEffectMixin {

    @Shadow private int duration;
    @Shadow @Final private Holder<MobEffect> effect;

    @Unique int farworld_mod$sparkler_timer = 0;

    @Inject(method = "tick", at = @At("TAIL"))
    public void tick(LivingEntity entity, Runnable onExpirationRunnable, CallbackInfoReturnable ci)
    {
        if (entity.getActiveEffectsMap().containsKey(ModEffects.SPARKLING) && (!effect.is(ResourceLocation.fromNamespaceAndPath(Farworld.MOD_ID, "sparkling"))))
        {
            farworld_mod$sparkler_timer++;
            if (farworld_mod$sparkler_timer % 2 == 0) duration += effect.value().isBeneficial() ? 1 : -1;
        }
    }
}
