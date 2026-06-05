package net.ennway.farworld.entity.client.sludge;


import net.ennway.farworld.Farworld;
import net.ennway.farworld.entity.custom.BrittleEntity;
import net.ennway.farworld.entity.custom.SludgeEntity;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.model.GeoModel;

@OnlyIn(Dist.CLIENT)
public class SludgeModel<T extends SludgeEntity> extends GeoModel<T> {

	@Override
	public @Nullable RenderType getRenderType(T animatable, ResourceLocation texture) {
		return RenderType.entityTranslucent(texture);
	}

	@Override
	public ResourceLocation getModelResource(T animatable) {
		return ResourceLocation.fromNamespaceAndPath(Farworld.MOD_ID, "geo/entity/sludge.geo.json");
	}

	@Override
	public ResourceLocation getTextureResource(T animatable) {
		if (animatable.getEntityData().get(SludgeEntity.GEM_CONTAINED).equals("emerald"))
			return ResourceLocation.fromNamespaceAndPath(Farworld.MOD_ID, "textures/entity/sludge_emerald.png");
		if (animatable.getEntityData().get(SludgeEntity.GEM_CONTAINED).equals("lapis"))
			return ResourceLocation.fromNamespaceAndPath(Farworld.MOD_ID, "textures/entity/sludge_lapis.png");

		return ResourceLocation.fromNamespaceAndPath(Farworld.MOD_ID, "textures/entity/sludge_amethyst.png");
	}

	@Override
	public ResourceLocation getAnimationResource(T animatable) {
		return ResourceLocation.fromNamespaceAndPath(Farworld.MOD_ID, "animations/entity/sludge.animation.json");
	}
}