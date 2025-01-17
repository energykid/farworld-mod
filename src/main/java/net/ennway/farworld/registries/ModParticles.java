package net.ennway.farworld.registries;

import net.ennway.farworld.Farworld;
import net.ennway.farworld.particle.ParalysisParticle;
import net.minecraft.core.Holder;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class ModParticles {
    public static final DeferredRegister<ParticleType<?>> PARTICLE_TYPES = DeferredRegister.create(BuiltInRegistries.PARTICLE_TYPE, Farworld.MOD_ID);

    public static final Supplier<SimpleParticleType> PARALYSIS = PARTICLE_TYPES.register(
            "paralysis",
            () -> new SimpleParticleType(false)
    );
    public static final Supplier<SimpleParticleType> WISHBONE_SPARKLE = PARTICLE_TYPES.register(
            "wishbone_sparkle",
            () -> new SimpleParticleType(false)
    );
    public static final Supplier<SimpleParticleType> WISHBONE_PORTAL = PARTICLE_TYPES.register(
            "wishbone_portal",
            () -> new SimpleParticleType(false)
    );
    public static final Supplier<SimpleParticleType> SOUL_SMOKE = PARTICLE_TYPES.register(
            "soul_smoke",
            () -> new SimpleParticleType(false)
    );
    public static final Supplier<SimpleParticleType> SOUL_FIRE_TENDRIL = PARTICLE_TYPES.register(
            "soul_fire_tendril",
            () -> new SimpleParticleType(false)
    );
    public static final Supplier<SimpleParticleType> INFERNAL_SMOKE = PARTICLE_TYPES.register(
            "infernal_smoke",
            () -> new SimpleParticleType(false)
    );
    public static final Supplier<SimpleParticleType> INFERNAL_TENDRIL = PARTICLE_TYPES.register(
            "infernal_tendril",
            () -> new SimpleParticleType(false)
    );

}
