package net.ennway.farworld.registries;

import net.ennway.farworld.Farworld;
import net.ennway.farworld.entity.custom.BloomedEntity;
import net.ennway.farworld.entity.custom.SoulGolemEntity;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

public class ModEntities {
    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES = DeferredRegister.create(
            BuiltInRegistries.ENTITY_TYPE,
            Farworld.MOD_ID);

    public static final DeferredHolder<EntityType<?>, EntityType<BloomedEntity>> BLOOMED = ENTITY_TYPES.register(
            "bloomed", () -> EntityType.Builder.of(BloomedEntity::new, MobCategory.CREATURE)
                    .sized(0.5f, 0.5f)
                    .build("bloomed"));

    public static final DeferredHolder<EntityType<?>, EntityType<SoulGolemEntity>> SOUL_GOLEM = ENTITY_TYPES.register(
            "soul_golem", () -> EntityType.Builder.of(SoulGolemEntity::new, MobCategory.MISC)
                    .sized(1, 2.75f)
                    .build("soul_golem"));
}
