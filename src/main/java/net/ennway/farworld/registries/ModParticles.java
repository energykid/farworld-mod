package net.ennway.farworld.registries;

import net.ennway.farworld.Farworld;
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
    public static final Supplier<SimpleParticleType> PHOSPHEN_PARTICLE = PARTICLE_TYPES.register(
            "phosphen_particle",
            () -> new SimpleParticleType(false)
    );
    public static final Supplier<SimpleParticleType> DIAMOND_DUST = PARTICLE_TYPES.register(
            "diamond_dust",
            () -> new SimpleParticleType(false)
    );
    public static final Supplier<SimpleParticleType> DIAMOND_SPARKLE = PARTICLE_TYPES.register(
            "diamond_sparkle",
            () -> new SimpleParticleType(false)
    );
    public static final Supplier<SimpleParticleType> BYSTONE_PORTAL = PARTICLE_TYPES.register(
            "bystone_portal",
            () -> new SimpleParticleType(false)
    );
    public static final Supplier<SimpleParticleType> OBSIDIAN_SHATTER = PARTICLE_TYPES.register(
            "obsidian_shatter",
            () -> new SimpleParticleType(false)
    );
    public static final Supplier<SimpleParticleType> BLACK_ICE_AOE = PARTICLE_TYPES.register(
            "black_ice_aoe_particle",
            () -> new SimpleParticleType(false)
    );
    public static final Supplier<SimpleParticleType> BLACK_ICE_WORMHOLE = PARTICLE_TYPES.register(
            "black_ice_wormhole",
            () -> new SimpleParticleType(false)
    );
    public static final Supplier<SimpleParticleType> GLITTERING_PARTICLE = PARTICLE_TYPES.register(
            "glittering_particle",
            () -> new SimpleParticleType(false)
    );
    public static final Supplier<SimpleParticleType> SPUR_PARTICLE = PARTICLE_TYPES.register(
            "spur_particle",
            () -> new SimpleParticleType(false)
    );
    public static final Supplier<SimpleParticleType> REDSTONE_CHARGE_PARTICLE = PARTICLE_TYPES.register(
            "redstone_charge_particle",
            () -> new SimpleParticleType(false)
    );
    public static final Supplier<SimpleParticleType> REDSTONE_CURIOSITY_PARTICLE = PARTICLE_TYPES.register(
            "redstone_curiosity_particle",
            () -> new SimpleParticleType(false)
    );
    public static final Supplier<SimpleParticleType> REDSTONE_CURIOSITY_BURST = PARTICLE_TYPES.register(
            "redstone_curiosity_burst",
            () -> new SimpleParticleType(false)
    );
    public static final Supplier<SimpleParticleType> REDSTONE_CURIOSITY_TELEGRAPH = PARTICLE_TYPES.register(
            "redstone_curiosity_telegraph",
            () -> new SimpleParticleType(false)
    );
    public static final Supplier<SimpleParticleType> BLAZE_STANCE_SLASH = PARTICLE_TYPES.register(
            "blaze_stance_slash",
            () -> new SimpleParticleType(false)
    );

}
