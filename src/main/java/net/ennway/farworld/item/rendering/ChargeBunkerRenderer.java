package net.ennway.farworld.item.rendering;

import net.ennway.farworld.Farworld;
import net.ennway.farworld.item.tool.ChargeBunker;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.DefaultedItemGeoModel;
import software.bernie.geckolib.renderer.GeoItemRenderer;

public class ChargeBunkerRenderer extends GeoItemRenderer<ChargeBunker>
{
    public <I extends ChargeBunker> ChargeBunkerRenderer() {
        super(new DefaultedItemGeoModel<>(ResourceLocation.fromNamespaceAndPath(Farworld.MOD_ID, "charge_bunker")));
    }
}
