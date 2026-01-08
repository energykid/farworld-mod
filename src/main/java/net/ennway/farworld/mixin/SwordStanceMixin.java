package net.ennway.farworld.mixin;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.ennway.farworld.registries.ModAttachments;
import net.ennway.farworld.registries.ModDataComponents;
import net.ennway.farworld.registries.ModItems;
import net.ennway.farworld.registries.ModTags;
import net.ennway.farworld.utils.curve.EasingCurve;
import net.ennway.farworld.utils.curve.InOutBack;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.ItemInHandRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.common.Tags;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Objects;

@Mixin(value = ItemInHandRenderer.class)
public class SwordStanceMixin {
    @Unique
    public float farworld$stanceAnimationScale = 0f;

    @Inject(method = "renderArmWithItem", at = @At("HEAD"))
    public void applyTransform(AbstractClientPlayer player, float partialTicks, float pitch, InteractionHand hand, float swingProgress, ItemStack stack, float equippedProgress, PoseStack poseStack, MultiBufferSource buffer, int combinedLight, CallbackInfo ci) {
        if (stack.is(ModTags.BREEZE_STANCEABLE_WEAPONS) || stack.is(ModTags.BLAZE_STANCEABLE_WEAPONS) && hand == InteractionHand.MAIN_HAND)
        {
            if (player.getData(ModAttachments.BATTLE_STANCE))
            {
                farworld$stanceAnimationScale = Mth.lerp(0.12f, farworld$stanceAnimationScale, 1f);
            }
            else
            {
                farworld$stanceAnimationScale = Mth.lerp(0.1f, farworld$stanceAnimationScale, 0f);
            }

            float animScale = new InOutBack().invoke(farworld$stanceAnimationScale);

            poseStack.mulPose(Axis.XP.rotationDegrees(animScale * 15f));
            poseStack.translate(0, animScale * 0.1f, 0);
            poseStack.translate(0, 0, animScale * 0.2f);
        }
    }

    @Inject(method = "renderArmWithItem", at = @At("TAIL"))
    public void unApplyTransform(AbstractClientPlayer player, float partialTicks, float pitch, InteractionHand hand, float swingProgress, ItemStack stack, float equippedProgress, PoseStack poseStack, MultiBufferSource buffer, int combinedLight, CallbackInfo ci) {
        if (stack.is(ModTags.BREEZE_STANCEABLE_WEAPONS) || stack.is(ModTags.BLAZE_STANCEABLE_WEAPONS) && hand == InteractionHand.MAIN_HAND)
        {
            if (player.getData(ModAttachments.BATTLE_STANCE))
            {
                farworld$stanceAnimationScale = Mth.lerp(0.12f, farworld$stanceAnimationScale, 1f);
            }
            else
            {
                farworld$stanceAnimationScale = Mth.lerp(0.1f, farworld$stanceAnimationScale, 0f);
            }

            float animScale = new InOutBack().invoke(farworld$stanceAnimationScale);

            poseStack.mulPose(Axis.XP.rotationDegrees(animScale * -15f));
            poseStack.translate(0, animScale * -0.1f, 0);
            poseStack.translate(0, 0, animScale * -0.2f);
        }
    }
}
