package net.ennway.farworld.registries.entity_definitions;

import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.neoforged.neoforge.registries.DeferredHolder;

import java.util.function.Supplier;

public class GeoEntityRendererDefinition<T extends Entity> {

    public DeferredHolder<EntityType<?>, EntityType<T>> type;
    public EntityRendererProvider<T> renderer;

    public GeoEntityRendererDefinition(DeferredHolder<EntityType<?>, EntityType<T>> t,
                                       EntityRendererProvider<T> ren) {
        type = t;
        renderer = ren;
    }
}
