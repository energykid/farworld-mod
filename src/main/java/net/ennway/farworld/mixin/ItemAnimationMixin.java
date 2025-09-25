package net.ennway.farworld.mixin;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.ennway.farworld.registries.ModDataComponents;
import net.ennway.farworld.registries.ModItems;
import net.minecraft.client.OptionInstance;
import net.minecraft.client.Options;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.ItemInHandRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = ItemInHandRenderer.class)
public class ItemAnimationMixin {
    @Unique
    public float farworld$handAnimationScale = 0f;

    @Inject(method = "renderArmWithItem", at = @At("HEAD"))
    public void applyTransform(AbstractClientPlayer player, float partialTicks, float pitch, InteractionHand hand, float swingProgress, ItemStack stack, float equippedProgress, PoseStack poseStack, MultiBufferSource buffer, int combinedLight, CallbackInfo ci) {
        if (stack.is(ModItems.WISHBONE) && hand == player.getUsedItemHand())
        {
            if (player.isUsingItem())
            {
                farworld$handAnimationScale = Mth.lerp(0.1f, farworld$handAnimationScale, 1f);
            }
            else
            {
                farworld$handAnimationScale = Mth.lerp(0.18f, farworld$handAnimationScale, 0f);
            }
            poseStack.mulPose(Axis.XP.rotationDegrees(farworld$handAnimationScale * 10f));
        }
    }
}
