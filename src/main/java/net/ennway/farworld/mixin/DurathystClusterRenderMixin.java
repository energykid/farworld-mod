package net.ennway.farworld.mixin;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.ennway.farworld.Farworld;
import net.ennway.farworld.event.ExtraModelEvent;
import net.ennway.farworld.registries.ModDataComponents;
import net.ennway.farworld.registries.ModItems;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.Mth;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.client.ClientHooks;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;
import java.util.Objects;

@Mixin(value = ItemRenderer.class)
public abstract class DurathystClusterRenderMixin {

    @Inject(method = "render", at = @At(value = "INVOKE", target = "Lnet/neoforged/neoforge/client/ClientHooks;handleCameraTransforms(Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/resources/model/BakedModel;Lnet/minecraft/world/item/ItemDisplayContext;Z)Lnet/minecraft/client/resources/model/BakedModel;", args = "ldc="))
    void renderstuff(ItemStack stack, ItemDisplayContext displayContext, boolean leftHand, PoseStack poseStack, MultiBufferSource bufferSource, int combinedLight, int combinedOverlay, BakedModel p_model, CallbackInfo ci)
    {
        if (stack.is(ModItems.DURATHYST_CLUSTER))
        {
            stack.set(ModDataComponents.EFFECT_INTENSITY, stack.get(ModDataComponents.EFFECT_INTENSITY) * 0.8f);

            float intensity = stack.get(ModDataComponents.EFFECT_INTENSITY);

            float rnd1 = Mth.randomBetween(Minecraft.getInstance().cameraEntity.getRandom(), -intensity * 0.25f, intensity * 0.15f);
            float rnd2 = Mth.randomBetween(Minecraft.getInstance().cameraEntity.getRandom(), -intensity * 0.15f, intensity * 0.15f);

            poseStack.translate(
                    rnd1,
                    rnd2,0
            );
        }
    }
}
