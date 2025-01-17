package net.ennway.farworld.effect;

import net.ennway.farworld.particle.ParalysisParticle;
import net.ennway.farworld.particle.ParalysisParticleProvider;
import net.ennway.farworld.registries.ModParticles;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleDescription;
import net.minecraft.client.particle.ParticleEngine;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.util.Mth;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

public class ParalysisEffect extends MobEffect {
    public ParalysisEffect() {
        super(MobEffectCategory.HARMFUL, 0xFF9900);
    }

    @Override
    public boolean applyEffectTick(LivingEntity livingEntity, int amplifier) {
        livingEntity.level().addParticle(ModParticles.PARALYSIS.get(),
                livingEntity.getRandomX(1d),
                livingEntity.getRandomY(),
                livingEntity.getRandomZ(1d),
                0d,
                0d,
                0d);
        return super.applyEffectTick(livingEntity, amplifier);
    }

    @Override
    public @NotNull ParticleOptions createParticleOptions(@NotNull MobEffectInstance effect) {
        return ModParticles.PARALYSIS.get();
    }
}
