package net.ennway.farworld.event.dimensiontransitions;

import net.ennway.farworld.Farworld;
import net.ennway.farworld.registries.ModBlocks;
import net.ennway.farworld.registries.ModDimensions;
import net.ennway.farworld.registries.ModPois;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;

public class AllDimensionLinks {
    public static final DimensionLink[] links = {
        new DimensionLink(Level.OVERWORLD, ModDimensions.BYSTONE, ModBlocks.BYSTONE_PORTAL.get(), ModPois.BYSTONE_PORTAL, -42, -14, ResourceLocation.fromNamespaceAndPath(Farworld.MOD_ID, "overlay/bystone_portal"))
    };
}