package net.ennway.farworld.mixin;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import net.ennway.farworld.Farworld;
import net.ennway.farworld.event.ExtraModelEvent;
import net.ennway.farworld.registries.ModDataComponents;
import net.ennway.farworld.registries.ModItems;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ItemInHandRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.core.Direction;
import net.minecraft.core.component.DataComponents;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.BundleContents;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.client.extensions.common.IClientItemExtensions;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;
import java.util.Objects;

@Mixin(value = ItemRenderer.class)
public abstract class ArmoryModelMixin {

    @Shadow
    public abstract void renderModelLists(BakedModel model, ItemStack stack, int combinedLight, int combinedOverlay, PoseStack poseStack, VertexConsumer buffer);

    @Shadow
    public abstract void renderQuadList(PoseStack poseStack, VertexConsumer buffer, List<BakedQuad> quads, ItemStack itemStack, int combinedLight, int combinedOverlay);

    @Unique
    private boolean farworld_mod$countsAsArmorForAccessories(ItemStack stack) {
        return (stack.is(ItemTags.HEAD_ARMOR) || stack.is(ItemTags.CHEST_ARMOR) || stack.is(ItemTags.LEG_ARMOR) || stack.is(ItemTags.FOOT_ARMOR));
    }

    @Inject(method = "render", at = @At(value = "INVOKE", target = "Lcom/mojang/blaze3d/vertex/PoseStack;popPose()V", args = "ldc="))
    void A(ItemStack stack, ItemDisplayContext displayContext, boolean leftHand, PoseStack poseStack, MultiBufferSource bufferSource, int combinedLight, int combinedOverlay, BakedModel p_model, CallbackInfo ci)
    {
        if (stack.get(ModDataComponents.ARMOR_ACCESSORIES) != null)
        {
            if (!Objects.requireNonNull(stack.get(ModDataComponents.ARMOR_ACCESSORIES)).isEmpty()) {
                poseStack.pushPose();

                poseStack.scale(1.00015f, 1.00015f, 1.01f);
                poseStack.translate(0, 0, -0.005f);

                for (int stackNum = 0; stackNum < stack.get(ModDataComponents.ARMOR_ACCESSORIES).size(); stackNum++) {

                    String name = ExtraModelEvent.getExtraModelName(stack.get(ModDataComponents.ARMOR_ACCESSORIES).getItemUnsafe(stackNum).getItem());

                    if (ModItems.ALL_ACCESSORY_NAMES.contains(name)) {
                        BakedModel mdl = Minecraft.getInstance().getModelManager().getModel(
                                ModelResourceLocation.standalone(ResourceLocation.fromNamespaceAndPath(Farworld.MOD_ID, "item/overlay/" + name))
                        );

                        renderModelLists(mdl, stack, combinedLight, combinedOverlay, poseStack, bufferSource.getBuffer(RenderType.CUTOUT));
                    }
                }

                poseStack.popPose();
            }
        }
    }
}
