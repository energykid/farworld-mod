package net.ennway.farworld.registries;

import net.ennway.farworld.Farworld;
import net.ennway.farworld.particle.*;
import net.ennway.farworld.particle.on_hit.*;
import net.minecraft.client.particle.SimpleAnimatedParticle;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.util.RandomSource;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RegisterParticleProvidersEvent;

@EventBusSubscriber(modid = Farworld.MOD_ID, value = Dist.CLIENT)
public class ModParticleProviders {

    public SpriteSet setFrom(TextureAtlasSprite sprite)
    {
        return new SpriteSet() {
            @Override
            public TextureAtlasSprite get(int i, int i1) {
                return sprite;
            }

            @Override
            public TextureAtlasSprite get(RandomSource randomSource) {
                return sprite;
            }
        };
    }

    @SubscribeEvent
    public static void registerParticleProviders(RegisterParticleProvidersEvent event) {
        event.registerSpriteSet(ModParticles.REDSTONE_CHARGE_PARTICLE.get(), RedstoneChargeParticle.Provider::new);

        event.registerSpriteSet(ModParticles.REDSTONE_CURIOSITY_PARTICLE.get(), RedstoneCuriosityParticle.Provider::new);
        event.registerSpriteSet(ModParticles.REDSTONE_CURIOSITY_BURST.get(), RedstoneCuriosityBurstProvider::new);

        event.registerSpriteSet(ModParticles.PARALYSIS.get(), ParalysisParticleProvider::new);

        event.registerSpriteSet(ModParticles.BLACK_ICE_AOE.get(), BlackIceParticleProvider::new);
        event.registerSpriteSet(ModParticles.BLACK_ICE_WORMHOLE.get(), BlackIceWormholeProvider::new);

        event.registerSpriteSet(ModParticles.PHOSPHEN_PARTICLE.get(), PhosphenParticleProvider::new);

        event.registerSpriteSet(ModParticles.BYSTONE_PORTAL.get(), BystonePortalParticleProvider::new);

        event.registerSpriteSet(ModParticles.WISHBONE_SPARKLE.get(), WishboneSparkleParticleProvider::new);
        event.registerSpriteSet(ModParticles.WISHBONE_PORTAL.get(), WishbonePortalParticleProvider::new);

        event.registerSpriteSet(ModParticles.OBSIDIAN_SHATTER.get(), ObsidianShatterParticleProvider::new);

        event.registerSpriteSet(ModParticles.GLITTERING_PARTICLE.get(), GlitteringParticleProvider::new);

        event.registerSpriteSet(ModParticles.SPUR_PARTICLE.get(), SpurParticleProvider::new);

        event.registerSpriteSet(ModParticles.SOUL_SMOKE.get(), SoulSmokeParticleProvider::new);
        event.registerSpriteSet(ModParticles.SOUL_FIRE_TENDRIL.get(), SoulFireTendrilParticleProvider::new);

        event.registerSpriteSet(ModParticles.INFERNAL_SMOKE.get(), InfernalSmokeParticleProvider::new);
        event.registerSpriteSet(ModParticles.INFERNAL_TENDRIL.get(), InfernalTendrilParticleProvider::new);

        event.registerSpriteSet(ModParticles.DIAMOND_DUST.get(), DiamondDustParticleProvider::new);
        event.registerSpriteSet(ModParticles.DIAMOND_SPARKLE.get(), DiamondSparkleParticleProvider::new);
    }
}
