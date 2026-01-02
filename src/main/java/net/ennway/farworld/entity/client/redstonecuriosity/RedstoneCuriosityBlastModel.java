
package net.ennway.farworld.entity.client.redstonecuriosity;

import net.ennway.farworld.Farworld;
import net.ennway.farworld.entity.custom.redstone_curiosity.RedstoneCuriosityBlastEntity;
import net.ennway.farworld.entity.custom.redstone_curiosity.RedstoneCuriosityEntity;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.cache.object.BakedGeoModel;
import software.bernie.geckolib.loading.object.BakedAnimations;
import software.bernie.geckolib.model.GeoModel;

public class RedstoneCuriosityBlastModel<T extends RedstoneCuriosityBlastEntity> extends GeoModel<T> {

	@Override
	public ResourceLocation getModelResource(T animatable) {
		return ResourceLocation.fromNamespaceAndPath(Farworld.MOD_ID, "geo/redstone_curiosity_blast");
	}

	@Override
	public ResourceLocation getTextureResource(T animatable) {
		return ResourceLocation.fromNamespaceAndPath(Farworld.MOD_ID, "redstone_curiosity_blast");
	}

	@Override
	public ResourceLocation getAnimationResource(T animatable) {
		return ResourceLocation.fromNamespaceAndPath(Farworld.MOD_ID, "animations/redstone_curiosity_blast");
	}
}