package net.ennway.farworld.entity.client.bloomed;

import net.ennway.farworld.Farworld;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.EyesLayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class BloomedEyesLayer<T extends Entity, M extends BloomedModel<T>> extends EyesLayer<T, M> {
    private static final RenderType DEFAULT_EYES = RenderType.eyes(ResourceLocation.fromNamespaceAndPath(Farworld.MOD_ID, "textures/entity/bloomed_glow.png"));
    private static final RenderType ARTHUR_EYES = RenderType.eyes(ResourceLocation.fromNamespaceAndPath(Farworld.MOD_ID, "textures/entity/arthur_glow.png"));

    public BloomedEyesLayer(RenderLayerParent<T, M> p_117507_) {
        super(p_117507_);
    }

    public RenderType renderType() {
        if (!this.getParentModel().isArthur) return DEFAULT_EYES;
        else return ARTHUR_EYES;
    }
}
