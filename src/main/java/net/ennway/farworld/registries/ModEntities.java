package net.ennway.farworld.registries;

import net.ennway.farworld.Farworld;
import net.ennway.farworld.entity.client.amethystconstruct.AmethystConstructModel;
import net.ennway.farworld.entity.client.amethystconstruct.AmethystConstructRenderer;
import net.ennway.farworld.entity.client.bloomed.BloomedModel;
import net.ennway.farworld.entity.client.bloomed.BloomedRenderer;
import net.ennway.farworld.entity.client.brittle.BrittleModel;
import net.ennway.farworld.entity.client.brittle.BrittleRenderer;
import net.ennway.farworld.entity.client.dustbug.DustbugModel;
import net.ennway.farworld.entity.client.dustbug.DustbugRenderer;
import net.ennway.farworld.entity.client.goliath.GoliathModel;
import net.ennway.farworld.entity.client.goliath.GoliathRenderer;
import net.ennway.farworld.entity.client.redstonecuriosity.RedstoneCuriosityRenderer;
import net.ennway.farworld.entity.client.soulgolem.SoulGolemModel;
import net.ennway.farworld.entity.client.soulgolem.SoulGolemRenderer;
import net.ennway.farworld.entity.custom.*;
import net.ennway.farworld.entity.projectile.BlackIceImplosionProjectile;
import net.ennway.farworld.entity.projectile.GloomstonePickup;
import net.ennway.farworld.entity.projectile.client.GloomstonePickupRenderer;
import net.ennway.farworld.registries.entity_definitions.GeoEntityRendererDefinition;
import net.ennway.farworld.registries.entity_definitions.NonGeoEntityLayerDefinition;
import net.minecraft.client.renderer.entity.NoopRenderer;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.List;

public class ModEntities {
    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES = DeferredRegister.create(
            BuiltInRegistries.ENTITY_TYPE,
            Farworld.MOD_ID);

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

    //region Projectiles

    public static final DeferredHolder<EntityType<?>, EntityType<BlackIceImplosionProjectile>> BLACK_ICE_AOE_ENTITY = ENTITY_TYPES.register(
            "black_ice_aoe", () -> EntityType.Builder.of(BlackIceImplosionProjectile::new, MobCategory.MISC).build("black_ice_aoe"));

    public static final DeferredHolder<EntityType<?>, EntityType<GloomstonePickup>> GLOOMSTONE_PICKUP = ENTITY_TYPES.register(
            "gloomstone_pickup", () -> EntityType.Builder.of(GloomstonePickup::new, MobCategory.MISC).build("gloomstone_pickup"));

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
            new NonGeoEntityLayerDefinition<BrittleEntity>(
                    BRITTLE,
                    BrittleModel.LAYER_LOCATION,
                    BrittleModel::createBodyLayer,
                    BrittleRenderer::new
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
            )
    );
    //endregion
    //region Geo Entity Renderers
    public static final List<GeoEntityRendererDefinition> geoMobDefinitions = List.of(
            new GeoEntityRendererDefinition<RedstoneCuriosityEntity>(
                    REDSTONE_CURIOSITY,
                    RedstoneCuriosityRenderer::new
            )
    );
    //endregion
}
