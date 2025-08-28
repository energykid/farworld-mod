package net.ennway.farworld.entity.client.brittle;

import net.ennway.farworld.Farworld;
import net.ennway.farworld.entity.client.soulgolem.SoulGolemModel;
import net.ennway.farworld.entity.custom.BrittleEntity;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.EyesLayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class BrittleGlowLayer<T extends BrittleEntity, M extends BrittleModel<T>> extends EyesLayer<T, M> {

    public BrittleGlowLayer(RenderLayerParent<T, M> p_117507_) {
        super(p_117507_);
    }

    public RenderType renderType() {
        return RenderType.eyes(ResourceLocation.fromNamespaceAndPath(Farworld.MOD_ID, this.getParentModel().getGlowTexture()));
    }
}