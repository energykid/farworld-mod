
package net.ennway.farworld.entity.client.redstonecuriosity;

import net.ennway.farworld.Farworld;
import net.ennway.farworld.entity.custom.redstone_curiosity.RedstoneCuriosityEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.cache.object.BakedGeoModel;
import software.bernie.geckolib.loading.object.BakedAnimations;
import software.bernie.geckolib.model.GeoModel;

public class RedstoneCuriosityModel<T extends RedstoneCuriosityEntity> extends GeoModel<T> {

	@Override
	public ResourceLocation getModelResource(T animatable) {
		return ResourceLocation.fromNamespaceAndPath(Farworld.MOD_ID, "geo/redstone_curiosity.json");
	}

	@Override
	public ResourceLocation getTextureResource(T animatable) {
		return ResourceLocation.fromNamespaceAndPath(Farworld.MOD_ID, "redstone_curiosity.json");
	}

	@Override
	public ResourceLocation getAnimationResource(T animatable) {
		return ResourceLocation.fromNamespaceAndPath(Farworld.MOD_ID, "animations/redstone_curiosity.json");
	}
}