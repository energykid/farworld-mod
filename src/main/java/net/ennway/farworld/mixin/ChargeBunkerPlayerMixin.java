package net.ennway.farworld.mixin;

import net.ennway.farworld.registries.ModItems;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.core.component.DataComponents;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.CustomModelData;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Objects;

@Mixin(value = HumanoidModel.class)
public class ChargeBunkerPlayerMixin<T extends LivingEntity> {
    @Shadow @Final public ModelPart rightArm;
    @Shadow @Final public ModelPart leftArm;
    @Shadow @Final public ModelPart head;
    @Unique
    public float farworld$chargeBunkerAnimationScaleRight = 0f;
    @Unique
    public float farworld$chargeBunkerAnimationScaleLeft = 0f;

    @Inject(method = "poseRightArm", at = @At("HEAD"))
    public void A(T entity, CallbackInfo ci)
    {
        if (entity instanceof Player player)
        {
            ItemStack stack = player.getItemInHand(InteractionHand.MAIN_HAND);
            ItemStack stack2 = player.getItemInHand(InteractionHand.OFF_HAND);

            if (stack.is(ModItems.CHARGE_BUNKER))
            {
                farworld$chargeBunkerAnimationScaleRight = Mth.lerp(0.3f, farworld$chargeBunkerAnimationScaleRight, 1f);
            }
            else
            {
                farworld$chargeBunkerAnimationScaleRight = Mth.lerp(0.3f, farworld$chargeBunkerAnimationScaleRight, 0f);
            }

            if (stack2.is(ModItems.CHARGE_BUNKER))
            {
                farworld$chargeBunkerAnimationScaleLeft = Mth.lerp(0.3f, farworld$chargeBunkerAnimationScaleLeft, 1f);
            }
            else
            {
                farworld$chargeBunkerAnimationScaleLeft = Mth.lerp(0.3f, farworld$chargeBunkerAnimationScaleLeft, 0f);
            }

            this.rightArm.setRotation(Mth.lerp(farworld$chargeBunkerAnimationScaleRight, this.rightArm.xRot, this.head.xRot - 1.4f), Mth.lerp(farworld$chargeBunkerAnimationScaleRight, this.rightArm.yRot, this.head.yRot), this.rightArm.zRot * Mth.lerp(farworld$chargeBunkerAnimationScaleRight, 1f, 0.7f));
            this.leftArm.setRotation(Mth.lerp(farworld$chargeBunkerAnimationScaleLeft, this.leftArm.xRot, this.head.xRot - 1.4f), Mth.lerp(farworld$chargeBunkerAnimationScaleLeft, this.leftArm.yRot, this.head.yRot), this.leftArm.zRot * Mth.lerp(farworld$chargeBunkerAnimationScaleLeft, 1f, 0.7f));
        }
    }
}
