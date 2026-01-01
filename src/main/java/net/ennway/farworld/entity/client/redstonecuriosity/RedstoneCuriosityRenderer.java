package net.ennway.farworld.entity.client.redstonecuriosity;

import com.mojang.blaze3d.vertex.PoseStack;
import net.ennway.farworld.Farworld;
import net.ennway.farworld.entity.client.redstonecuriosity.RedstoneCuriosityModel;
import net.ennway.farworld.entity.custom.RedstoneCuriosityEntity;
import net.ennway.farworld.entity.custom.SoulGolemEntity;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class RedstoneCuriosityRenderer extends GeoEntityRenderer<RedstoneCuriosityEntity> {

    public RedstoneCuriosityRenderer(EntityRendererProvider.Context context) {
        super(context, new RedstoneCuriosityModel());
    }

    @Override
    public ResourceLocation getTextureLocation(RedstoneCuriosityEntity redstoneCuriosityEntity) {
        return ResourceLocation.fromNamespaceAndPath(Farworld.MOD_ID, "textures/entity/redstone_curiosity.png");
    }
}
