package net.ennway.farworld.entity.client.soulgolem;

import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.VertexFormat;
import net.ennway.farworld.Farworld;
import net.ennway.farworld.entity.client.bloomed.BloomedModel;
import net.minecraft.client.renderer.RenderStateShard;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.EyesLayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.neoforge.client.event.ScreenEvent;

@OnlyIn(Dist.CLIENT)
public class SoulGolemGlowLayer<T extends Entity, M extends SoulGolemModel<T>> extends EyesLayer<T, M> {

    public SoulGolemGlowLayer(RenderLayerParent<T, M> p_117507_) {
        super(p_117507_);
    }

    public RenderType renderType() {
        return RenderType.eyes(ResourceLocation.fromNamespaceAndPath(Farworld.MOD_ID, this.getParentModel().getGlowTexture()));
    }
}