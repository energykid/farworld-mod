package net.ennway.farworld.registries;

import net.ennway.farworld.Farworld;
import net.ennway.farworld.entity.client.amethystconstruct.AmethystConstructModel;
import net.ennway.farworld.entity.client.amethystconstruct.AmethystConstructRenderer;
import net.ennway.farworld.entity.client.bloomed.BloomedModel;
import net.ennway.farworld.entity.client.bloomed.BloomedRenderer;
import net.ennway.farworld.entity.client.brittle.BrittleRenderer;
import net.ennway.farworld.entity.client.dustbug.DustbugModel;
import net.ennway.farworld.entity.client.dustbug.DustbugRenderer;
import net.ennway.farworld.entity.client.goliath.GoliathModel;
import net.ennway.farworld.entity.client.goliath.GoliathRenderer;
import net.ennway.farworld.entity.client.redstonecuriosity.RedstoneCuriosityBlastRenderer;
import net.ennway.farworld.entity.client.redstonecuriosity.RedstoneCuriosityLaserRenderer;
import net.ennway.farworld.entity.client.redstonecuriosity.RedstoneCuriosityRenderer;
import net.ennway.farworld.entity.client.redstonecuriosity.RedstoneCuriosityVerticalBlastRenderer;
import net.ennway.farworld.entity.client.sludge.SludgeRenderer;
import net.ennway.farworld.entity.client.soulgolem.SoulGolemModel;
import net.ennway.farworld.entity.client.soulgolem.SoulGolemRenderer;
import net.ennway.farworld.entity.custom.*;
import net.ennway.farworld.entity.custom.redstone_curiosity.RedstoneCuriosityBlastEntity;
import net.ennway.farworld.entity.custom.redstone_curiosity.RedstoneCuriosityEntity;
import net.ennway.farworld.entity.custom.redstone_curiosity.RedstoneCuriosityLaserEntity;
import net.ennway.farworld.entity.custom.redstone_curiosity.RedstoneCuriosityVerticalBlastEntity;
import net.ennway.farworld.entity.projectile.*;
import net.ennway.farworld.entity.projectile.client.ApocalypseBreathRenderer;
import net.ennway.farworld.entity.projectile.client.GloomstonePickupRenderer;
import net.ennway.farworld.entity.projectile.client.SludgeArrowRenderer;
import net.ennway.farworld.registries.entity_definitions.GeoEntityRendererDefinition;
import net.ennway.farworld.registries.entity_definitions.NonGeoEntityLayerDefinition;
import net.minecraft.client.renderer.entity.NoopRenderer;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.SpawnPlacementTypes;
import net.minecraft.world.level.levelgen.Heightmap;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.neoforge.event.entity.EntityAttributeCreationEvent;
import net.neoforged.neoforge.event.entity.RegisterSpawnPlacementsEvent;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.List;

public class ModEntities {
    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES = DeferredRegister.create(
            BuiltInRegistries.ENTITY_TYPE,
            Farworld.MOD_ID);

    //region Mobs
    public static final DeferredHolder<EntityType<?>, EntityType<BloomedEntity>> BLOOMED = ENTITY_TYPES.register(
            "bloomed", () -> EntityType.Builder.of(BloomedEntity::new, MobCategory.MONSTER)
                    .eyeHeight(0.25f)
                    .sized(0.5f, 0.5f)
                    .build("bloomed"));

    public static final DeferredHolder<EntityType<?>, EntityType<SoulGolemEntity>> SOUL_GOLEM = ENTITY_TYPES.register(
            "soul_golem", () -> EntityType.Builder.of(SoulGolemEntity::new, MobCategory.MISC)
                    .eyeHeight(2.55f)
                    .sized(1, 3f)
                    .build("soul_golem"));

    public static final DeferredHolder<EntityType<?>, EntityType<BrittleEntity>> BRITTLE = ENTITY_TYPES.register(
            "brittle", () -> EntityType.Builder.of(BrittleEntity::new, MobCategory.MONSTER)
                    .eyeHeight(1.35f)
                    .sized(0.75f, 2f)
                    .build("brittle"));

    public static final DeferredHolder<EntityType<?>, EntityType<SludgeEntity>> SLUDGE = ENTITY_TYPES.register(
            "sludge", () -> EntityType.Builder.of(SludgeEntity::new, MobCategory.MONSTER)
                    .eyeHeight(0.5f)
                    .sized(0.7f, 0.7f)
                    .build("sludge"));

    public static final DeferredHolder<EntityType<?>, EntityType<DustbugEntity>> DUSTBUG = ENTITY_TYPES.register(
            "dustbug", () -> EntityType.Builder.of(DustbugEntity::new, MobCategory.MONSTER)
                    .eyeHeight(0.1f)
                    .sized(1.2f, 1f)
                    .build("dustbug"));

    public static final DeferredHolder<EntityType<?>, EntityType<GoliathEntity>> GOLIATH = ENTITY_TYPES.register(
            "goliath", () -> EntityType.Builder.of(GoliathEntity::new, MobCategory.MONSTER)
                    .eyeHeight(2f)
                    .sized(2.5f, 1f)
                    .build("goliath"));

    public static final DeferredHolder<EntityType<?>, EntityType<AmethystConstructEntity>> AMETHYST_CONSTRUCT = ENTITY_TYPES.register(
            "amethyst_construct", () -> EntityType.Builder.of(AmethystConstructEntity::new, MobCategory.MONSTER)
                    .eyeHeight(1f)
                    .sized(2.25f, 2f)
                    .build("amethyst_construct"));

    public static final DeferredHolder<EntityType<?>, EntityType<RedstoneCuriosityEntity>> REDSTONE_CURIOSITY = ENTITY_TYPES.register(
            "redstone_curiosity", () -> EntityType.Builder.of(RedstoneCuriosityEntity::new, MobCategory.MONSTER)
                    .eyeHeight(2f)
                    .sized(1f, 2.25f)
                    .build("redstone_curiosity"));
    //endregion

    //region Projectiles

    public static final DeferredHolder<EntityType<?>, EntityType<SludgeArrowProjectile>> SLUDGE_ARROW = ENTITY_TYPES.register(
            "sludge_arrow", () -> EntityType.Builder.of(SludgeArrowProjectile::getBase, MobCategory.MISC)
                    .sized(0.5f, 0.5f)
                    .eyeHeight(0.13F)
                    .clientTrackingRange(4)
                    .updateInterval(20)
                    .build("sludge_arrow"));

    public static final DeferredHolder<EntityType<?>, EntityType<BlazeStanceProjectile>> BLAZE_STANCE_SLASH = ENTITY_TYPES.register(
            "blaze_stance_slash", () -> EntityType.Builder.of(BlazeStanceProjectile::new, MobCategory.MISC).build("blaze_stance_slash"));

    public static final DeferredHolder<EntityType<?>, EntityType<BlackIceImplosionProjectile>> BLACK_ICE_AOE_ENTITY = ENTITY_TYPES.register(
            "black_ice_aoe", () -> EntityType.Builder.of(BlackIceImplosionProjectile::new, MobCategory.MISC).build("black_ice_aoe"));

    public static final DeferredHolder<EntityType<?>, EntityType<RedstoneCuriosityBlastEntity>> REDSTONE_CURIOSITY_BLAST = ENTITY_TYPES.register(
            "redstone_curiosity_blast", () -> EntityType.Builder.of(RedstoneCuriosityBlastEntity::new, MobCategory.MISC).build("redstone_curiosity_blast"));

    public static final DeferredHolder<EntityType<?>, EntityType<RedstoneCuriosityVerticalBlastEntity>> REDSTONE_CURIOSITY_VERTICAL_BLAST = ENTITY_TYPES.register(
            "redstone_curiosity_vertical_blast", () -> EntityType.Builder.of(RedstoneCuriosityVerticalBlastEntity::new, MobCategory.MISC).build("redstone_curiosity_vertical_blast"));

    public static final DeferredHolder<EntityType<?>, EntityType<RedstoneCuriosityLaserEntity>> REDSTONE_CURIOSITY_LASER = ENTITY_TYPES.register(
            "redstone_curiosity_laser", () -> EntityType.Builder.of(RedstoneCuriosityLaserEntity::new, MobCategory.MISC).build("redstone_curiosity_laser"));

    public static final DeferredHolder<EntityType<?>, EntityType<GloomstonePickup>> GLOOMSTONE_PICKUP = ENTITY_TYPES.register(
            "gloomstone_pickup", () -> EntityType.Builder.of(GloomstonePickup::new, MobCategory.MISC).build("gloomstone_pickup"));

    public static final DeferredHolder<EntityType<?>, EntityType<ApocalypseBreathProjectile>> APOCALYPSE_BREATH = ENTITY_TYPES.register(
            "apocalypse_breath", () -> EntityType.Builder.of(ApocalypseBreathProjectile::new, MobCategory.MISC).build("apocalypse_breath"));

    //endregion

    //region Non-Geo Entity Layers
    public static final List<NonGeoEntityLayerDefinition> mobDefinitions = List.of(
            new NonGeoEntityLayerDefinition<BloomedEntity>(
                    BLOOMED,
                    BloomedModel.LAYER_LOCATION,
                    BloomedModel::createBodyLayer,
                    BloomedRenderer::new
            ),
            new NonGeoEntityLayerDefinition<SoulGolemEntity>(
                    SOUL_GOLEM,
                    SoulGolemModel.LAYER_LOCATION,
                    SoulGolemModel::createBodyLayer,
                    SoulGolemRenderer::new
            ),
            new NonGeoEntityLayerDefinition<DustbugEntity>(
                    DUSTBUG,
                    DustbugModel.LAYER_LOCATION,
                    DustbugModel::createBodyLayer,
                    DustbugRenderer::new
            ),
            new NonGeoEntityLayerDefinition<GoliathEntity>(
                    GOLIATH,
                    GoliathModel.LAYER_LOCATION,
                    GoliathModel::createBodyLayer,
                    GoliathRenderer::new
            ),
            new NonGeoEntityLayerDefinition<AmethystConstructEntity>(
                    AMETHYST_CONSTRUCT,
                    AmethystConstructModel.LAYER_LOCATION,
                    AmethystConstructModel::createBodyLayer,
                    AmethystConstructRenderer::new
            ),
            new NonGeoEntityLayerDefinition<BlackIceImplosionProjectile>(
                    BLACK_ICE_AOE_ENTITY,
                    null,
                    null,
                    NoopRenderer::new
            ),
            new NonGeoEntityLayerDefinition<GloomstonePickup>(
                    GLOOMSTONE_PICKUP,
                    null,
                    null,
                    GloomstonePickupRenderer::new
            ),
            new NonGeoEntityLayerDefinition<BlazeStanceProjectile>(
                    BLAZE_STANCE_SLASH,
                    null,
                    null,
                    NoopRenderer::new
            ),
            new NonGeoEntityLayerDefinition<ApocalypseBreathProjectile>(
                    APOCALYPSE_BREATH,
                    null,
                    null,
                    ApocalypseBreathRenderer::new
            ),
            new NonGeoEntityLayerDefinition<SludgeArrowProjectile>(
                    SLUDGE_ARROW,
                    null,
                    null,
                    SludgeArrowRenderer::new
            )
    );
    //endregion

    //region Geo Entity Renderers
    public static final List<GeoEntityRendererDefinition> geoMobDefinitions = List.of(
            new GeoEntityRendererDefinition<SludgeEntity>(
                    SLUDGE,
                    SludgeRenderer::new
            ),
            new GeoEntityRendererDefinition<BrittleEntity>(
                    BRITTLE,
                    BrittleRenderer::new
            ),
            new GeoEntityRendererDefinition<RedstoneCuriosityEntity>(
                    REDSTONE_CURIOSITY,
                    RedstoneCuriosityRenderer::new
            ),
            new GeoEntityRendererDefinition<RedstoneCuriosityBlastEntity>(
                    REDSTONE_CURIOSITY_BLAST,
                    RedstoneCuriosityBlastRenderer::new
            ),
            new GeoEntityRendererDefinition<RedstoneCuriosityVerticalBlastEntity>(
                    REDSTONE_CURIOSITY_VERTICAL_BLAST,
                    RedstoneCuriosityVerticalBlastRenderer::new
            ),
            new GeoEntityRendererDefinition<RedstoneCuriosityLaserEntity>(
                    REDSTONE_CURIOSITY_LASER,
                    RedstoneCuriosityLaserRenderer::new
            )
    );
    //endregion

    @EventBusSubscriber(modid = Farworld.MOD_ID)
    public static class EntityDataEvents {
        @SubscribeEvent
        public static void registerSpawnConditions(RegisterSpawnPlacementsEvent event)
        {
            event.register(BLOOMED.get(), SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, (a, b, c, d, e) -> true, RegisterSpawnPlacementsEvent.Operation.REPLACE);
            event.register(SLUDGE.get(), SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, (a, b, c, pos, e) -> pos.getY() < 0, RegisterSpawnPlacementsEvent.Operation.REPLACE);
            event.register(BRITTLE.get(), SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, (a, b, c, d, e) -> true, RegisterSpawnPlacementsEvent.Operation.REPLACE);
            event.register(DUSTBUG.get(), SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, (a, b, c, d, e) -> true, RegisterSpawnPlacementsEvent.Operation.REPLACE);
            event.register(GOLIATH.get(), SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, (a, b, c, d, e) -> true, RegisterSpawnPlacementsEvent.Operation.REPLACE);
            event.register(AMETHYST_CONSTRUCT.get(), SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, (a, b, c, d, e) -> true, RegisterSpawnPlacementsEvent.Operation.REPLACE);
        }
        @SubscribeEvent
        public static void registerLayers(EntityRenderersEvent.RegisterLayerDefinitions event)
        {
            for (int i = 0; i < mobDefinitions.size(); i++) {
                NonGeoEntityLayerDefinition def = mobDefinitions.get(i);

                if (def.location != null && def.definition != null)
                {
                    event.registerLayerDefinition(def.location, def.definition);
                }
            }
        }
        @SubscribeEvent
        public static void registerAttributes(EntityAttributeCreationEvent event)
        {
            event.put(BLOOMED.get(), BloomedEntity.createAttributes().build());
            event.put(SLUDGE.get(), SludgeEntity.createAttributes().build());
            event.put(SOUL_GOLEM.get(), SoulGolemEntity.createAttributes().build());
            event.put(BRITTLE.get(), BrittleEntity.createAttributes().build());
            event.put(DUSTBUG.get(), DustbugEntity.createAttributes().build());
            event.put(GOLIATH.get(), GoliathEntity.createAttributes().build());
            event.put(AMETHYST_CONSTRUCT.get(), AmethystConstructEntity.createAttributes().build());
            event.put(REDSTONE_CURIOSITY.get(), RedstoneCuriosityEntity.createAttributes().build());
        }
    }
}
