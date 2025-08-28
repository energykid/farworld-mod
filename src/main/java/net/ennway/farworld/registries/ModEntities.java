package net.ennway.farworld.registries;

import net.ennway.farworld.Farworld;
import net.ennway.farworld.entity.client.bloomed.BloomedModel;
import net.ennway.farworld.entity.client.bloomed.BloomedRenderer;
import net.ennway.farworld.entity.client.brittle.BrittleModel;
import net.ennway.farworld.entity.client.soulgolem.SoulGolemModel;
import net.ennway.farworld.entity.client.soulgolem.SoulGolemRenderer;
import net.ennway.farworld.entity.custom.BloomedEntity;
import net.ennway.farworld.entity.custom.BrittleEntity;
import net.ennway.farworld.entity.custom.SoulGolemEntity;
import net.ennway.farworld.registries.entity_definitions.MobLayerDefinition;
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


    public static final List<MobLayerDefinition> mobDefinitions = List.of(
            new MobLayerDefinition(
                    BloomedModel.LAYER_LOCATION,
                    BloomedModel::createBodyLayer
            ),
            new MobLayerDefinition(
                    SoulGolemModel.LAYER_LOCATION,
                    SoulGolemModel::createBodyLayer
            ),
            new MobLayerDefinition(
                    BrittleModel.LAYER_LOCATION,
                    BrittleModel::createBodyLayer
            )
    );

    public static final DeferredHolder<EntityType<?>, EntityType<BloomedEntity>> BLOOMED = ENTITY_TYPES.register(
            "bloomed", () -> EntityType.Builder.of(BloomedEntity::new, MobCategory.CREATURE)
                    .sized(0.5f, 0.5f)
                    .build("bloomed"));

    public static final DeferredHolder<EntityType<?>, EntityType<SoulGolemEntity>> SOUL_GOLEM = ENTITY_TYPES.register(
            "soul_golem", () -> EntityType.Builder.of(SoulGolemEntity::new, MobCategory.MISC)
                    .sized(1, 2.75f)
                    .build("soul_golem"));

    public static final DeferredHolder<EntityType<?>, EntityType<BrittleEntity>> BRITTLE = ENTITY_TYPES.register(
            "brittle", () -> EntityType.Builder.of(BrittleEntity::new, MobCategory.MISC)
                    .sized(1, 2)
                    .build("brittle"));
}
