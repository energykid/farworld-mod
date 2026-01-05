package net.ennway.farworld.mixin;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.ennway.farworld.registries.ModAttachments;
import net.ennway.farworld.registries.ModDataComponents;
import net.ennway.farworld.registries.ModTags;
import net.ennway.farworld.utils.curve.InOutBack;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.ItemInHandRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Objects;

@Mixin(value = HumanoidModel.class)
public class SwordStancePlayerMixin<T extends LivingEntity> {
    @Shadow @Final public ModelPart rightArm;
    @Shadow @Final public ModelPart leftArm;
    @Unique
    public float farworld$stanceAnimationScale = 0f;

    @Inject(method = "poseRightArm", at = @At("HEAD"))
    private void poseArm(T livingEntity, CallbackInfo ci) {
        if (livingEntity instanceof Player player)
        {
            ItemStack stack = player.getItemInHand(player.getUsedItemHand());
            if (stack.is(ModTags.BREEZE_STANCEABLE_WEAPONS) || stack.is(ModTags.BLAZE_STANCEABLE_WEAPONS))
            {
                if (player.getData(ModAttachments.BATTLE_STANCE.get()))
                {
                    farworld$stanceAnimationScale = Mth.lerp(0.12f, farworld$stanceAnimationScale, 1f);
                }
                else
                {
                    farworld$stanceAnimationScale = Mth.lerp(0.25f, farworld$stanceAnimationScale, 0f);
                }

                float animScale = new InOutBack().invoke(farworld$stanceAnimationScale);

                if (!player.getData(ModAttachments.BATTLE_STANCE.get()))
                    animScale = farworld$stanceAnimationScale * 0.65f;

                this.rightArm.xRot = Mth.lerp(animScale, this.rightArm.xRot, -5.5f);

                this.leftArm.xRot += animScale * 0.15f;

                this.rightArm.zRot -= (animScale * 0.25f);

                this.leftArm.zRot -= (animScale * 0.25f);
            }
        }
    }
}
