
package net.ennway.farworld.entity.client.scrapped;

import net.ennway.farworld.Farworld;
import net.ennway.farworld.entity.custom.ScrappedLaserEntity;
import net.ennway.farworld.entity.custom.redstone_curiosity.RedstoneCuriosityBlastEntity;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import software.bernie.geckolib.model.GeoModel;

@OnlyIn(Dist.CLIENT)
public class ScrappedLaserModel<T extends ScrappedLaserEntity> extends GeoModel<T> {

	@Override
	public ResourceLocation getModelResource(T animatable) {
		return ResourceLocation.fromNamespaceAndPath(Farworld.MOD_ID, "geo/scrapped_laser");
	}

	@Override
	public ResourceLocation getTextureResource(T animatable) {
		return ResourceLocation.fromNamespaceAndPath(Farworld.MOD_ID, "redstone_curiosity_laser");
	}

	@Override
	public ResourceLocation getAnimationResource(T animatable) {
		return ResourceLocation.fromNamespaceAndPath(Farworld.MOD_ID, "animations/redstone_curiosity_laser");
	}
}