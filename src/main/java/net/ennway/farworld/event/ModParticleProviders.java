package net.ennway.farworld.event;

import net.ennway.farworld.Farworld;
import net.ennway.farworld.entity.client.bloomed.BloomedModel;
import net.ennway.farworld.particle.*;
import net.ennway.farworld.registries.ModParticles;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.neoforge.client.event.RegisterParticleProvidersEvent;

@EventBusSubscriber(modid = Farworld.MOD_ID, bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ModParticleProviders {

    @SubscribeEvent
    public static void registerParticleProviders(RegisterParticleProvidersEvent event) {
        event.registerSpriteSet(ModParticles.PARALYSIS.get(), ParalysisParticleProvider::new);

        event.registerSpriteSet(ModParticles.PHOSPHEN_PARTICLE.get(), PhosphenParticleProvider::new);

        event.registerSpriteSet(ModParticles.WISHBONE_SPARKLE.get(), WishboneSparkleParticleProvider::new);
        event.registerSpriteSet(ModParticles.WISHBONE_PORTAL.get(), WishbonePortalParticleProvider::new);

        event.registerSpriteSet(ModParticles.SOUL_SMOKE.get(), SoulSmokeParticleProvider::new);
        event.registerSpriteSet(ModParticles.SOUL_FIRE_TENDRIL.get(), SoulFireTendrilParticleProvider::new);

        event.registerSpriteSet(ModParticles.INFERNAL_SMOKE.get(), InfernalSmokeParticleProvider::new);
        event.registerSpriteSet(ModParticles.INFERNAL_TENDRIL.get(), InfernalTendrilParticleProvider::new);

        event.registerSpriteSet(ModParticles.DIAMOND_DUST.get(), DiamondDustParticleProvider::new);
        event.registerSpriteSet(ModParticles.DIAMOND_SPARKLE.get(), DiamondSparkleParticleProvider::new);
    }
}
