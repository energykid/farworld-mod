package net.ennway.farworld.entity.client.obelisk;

import net.ennway.farworld.Farworld;
import net.ennway.farworld.entity.custom.ObeliskEntity;
import net.ennway.farworld.entity.custom.ScrappedEntity;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import software.bernie.geckolib.model.DefaultedEntityGeoModel;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

@OnlyIn(Dist.CLIENT)
public class ObeliskRenderer extends GeoEntityRenderer<ObeliskEntity> {

    public ObeliskRenderer(EntityRendererProvider.Context context) {
        super(context, new DefaultedEntityGeoModel<>(ResourceLocation.fromNamespaceAndPath(Farworld.MOD_ID, "obelisk")));
    }
}