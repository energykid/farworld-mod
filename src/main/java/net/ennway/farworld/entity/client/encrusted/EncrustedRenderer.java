package net.ennway.farworld.entity.client.encrusted;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import net.ennway.farworld.Farworld;
import net.ennway.farworld.entity.custom.BrittleEntity;
import net.ennway.farworld.entity.custom.EncrustedEntity;
import net.ennway.farworld.utils.RenderingUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.level.block.Blocks;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.cache.object.BakedGeoModel;
import software.bernie.geckolib.cache.object.GeoBone;
import software.bernie.geckolib.model.DefaultedEntityGeoModel;
import software.bernie.geckolib.renderer.GeoEntityRenderer;
import software.bernie.geckolib.renderer.layer.AutoGlowingGeoLayer;

@OnlyIn(Dist.CLIENT)
public class EncrustedRenderer extends GeoEntityRenderer<EncrustedEntity> {

    public EncrustedRenderer(EntityRendererProvider.Context context) {
        super(context, new DefaultedEntityGeoModel<>(ResourceLocation.fromNamespaceAndPath(Farworld.MOD_ID, "encrusted")));
        addRenderLayer(new AutoGlowingGeoLayer<>(this));
    }
}