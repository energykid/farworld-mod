package net.ennway.farworld.registries.entity_definitions;

import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.neoforged.neoforge.registries.DeferredHolder;

import java.util.function.Supplier;

public class NonGeoEntityLayerDefinition<T extends Entity> {

    public DeferredHolder<EntityType<?>, EntityType<T>> type;
    public ModelLayerLocation location;
    public Supplier<LayerDefinition> definition;
    public EntityRendererProvider<T> renderer;

    public NonGeoEntityLayerDefinition(DeferredHolder<EntityType<?>, EntityType<T>> t,
                                       ModelLayerLocation loc,
                                       Supplier<LayerDefinition> def,
                                       EntityRendererProvider<T> ren) {
        type = t;
        location = loc;
        definition = def;
        renderer = ren;
    }
}
