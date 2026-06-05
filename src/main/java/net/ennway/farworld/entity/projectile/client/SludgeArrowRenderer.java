package net.ennway.farworld.entity.projectile.client;

import net.ennway.farworld.Farworld;
import net.ennway.farworld.entity.projectile.SludgeArrowProjectile;
import net.minecraft.client.renderer.entity.ArrowRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.projectile.SpectralArrow;

public class SludgeArrowRenderer extends ArrowRenderer<SludgeArrowProjectile> {
    public static final ResourceLocation SLUDGE_ARROW_LOCATION = ResourceLocation.fromNamespaceAndPath(Farworld.MOD_ID, "textures/entity/projectiles/sludge_arrow.png");

    public SludgeArrowRenderer(EntityRendererProvider.Context context) {
        super(context);
    }

    @Override
    public ResourceLocation getTextureLocation(SludgeArrowProjectile entity) {
        return SLUDGE_ARROW_LOCATION;
    }
}
