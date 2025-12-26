package net.ennway.farworld.mixin;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.ennway.farworld.registries.ModDataComponents;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ItemInHandRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.core.component.DataComponents;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.BundleContents;
import net.neoforged.neoforge.client.extensions.common.IClientItemExtensions;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Objects;

@Mixin(value = ItemRenderer.class)
public class ArmoryModelMixin {

    @Unique
    private boolean farworld_mod$countsAsArmorForAccessories(ItemStack stack)
    {
        return (stack.is(ItemTags.HEAD_ARMOR) || stack.is(ItemTags.CHEST_ARMOR) || stack.is(ItemTags.LEG_ARMOR) || stack.is(ItemTags.FOOT_ARMOR));
    }

    @Inject(method = "render", at = @At("TAIL"))
    void postrender(ItemStack stack, ItemDisplayContext displayContext, boolean leftHand, PoseStack poseStack, MultiBufferSource bufferSource, int combinedLight, int combinedOverlay, BakedModel p_model, CallbackInfo ci)
    {
        if (!displayContext.firstPerson())
        {
            ItemRenderer rend = Minecraft.getInstance().getItemRenderer();

            if (stack.get(ModDataComponents.ARMOR_ACCESSORIES) != null)
            {
                if (!Objects.requireNonNull(stack.get(ModDataComponents.ARMOR_ACCESSORIES)).isEmpty())
                {
                    for (int stackNum = 0; stackNum < stack.get(ModDataComponents.ARMOR_ACCESSORIES).size(); stackNum++) {
                        ItemStack stack2 = Objects.requireNonNull(stack.get(ModDataComponents.ARMOR_ACCESSORIES)).getItemUnsafe(stackNum);

                        poseStack.pushPose();
                        poseStack.translate(0.25 - ((double)stackNum * 0.5), -0.25, 0);
                        poseStack.scale(0.5f, 0.5f, 1.1f);
                        poseStack.mulPose(Axis.YP.rotationDegrees(0f));

                        rend.renderStatic(stack2, ItemDisplayContext.GUI, combinedLight, combinedOverlay, poseStack, bufferSource, Minecraft.getInstance().level, 1);

                        poseStack.popPose();
                    }
                }
            }
        }
    }
}
