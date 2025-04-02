package net.ennway.farworld.registries.entity_definitions;

import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.renderer.entity.MobRenderer;

import java.util.function.Supplier;

public class MobLayerDefinition {

    public ModelLayerLocation location;
    public Supplier<LayerDefinition> definition;

    public MobLayerDefinition(ModelLayerLocation loc,
                              Supplier<LayerDefinition> def) {
        location = loc;
        definition = def;
    }
}
