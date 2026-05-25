package net.ennway.farworld.effect;

import net.ennway.farworld.registries.ModParticles;
import net.minecraft.core.Holder;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.neoforged.neoforge.common.EffectCures;
import org.jetbrains.annotations.NotNull;

public class SparklingEffect extends MobEffect {
    public SparklingEffect() {
        super(MobEffectCategory.BENEFICIAL, 0xAA5BE1);
    }
}
