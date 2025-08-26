package net.ennway.farworld.mixin;

import net.ennway.farworld.registries.ModDataComponents;
import net.ennway.farworld.registries.ModItems;
import net.minecraft.client.model.AnimationUtils;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.core.component.DataComponents;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.CustomModelData;
import org.joml.Vector3f;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;

import java.util.Objects;

@Mixin(value = HumanoidModel.class)
public class WishboneAnimationMixin<T extends LivingEntity> {

    @Shadow
    @Final
    public ModelPart rightArm;

    @Shadow
    @Final
    public ModelPart leftArm;

    @Unique
    public float farworld$modelAnimationScale = 0f;

    @Inject(method = "poseRightArm", at = @At("HEAD"), cancellable = true)
    private void customArmPositions(T entity, CallbackInfo ci)
    {
        if (entity instanceof Player player)
        {
            ItemStack stack = player.getItemInHand(player.getUsedItemHand());

            if (player.isUsingItem() && stack.is(ModItems.WISHBONE) && !Objects.equals(stack.get(DataComponents.CUSTOM_MODEL_DATA), new CustomModelData(2)))
            {
                farworld$modelAnimationScale = Mth.lerp(0.05f, farworld$modelAnimationScale, 1f);
            }
            else
            {
                farworld$modelAnimationScale = Mth.lerp(0.2f, farworld$modelAnimationScale, 0f);
            }

            this.rightArm.xRot += farworld$modelAnimationScale * 1.5f;
            this.rightArm.zRot += (farworld$modelAnimationScale * 1.5f);

            this.leftArm.xRot += farworld$modelAnimationScale * 0.5f;
            this.leftArm.zRot -= (farworld$modelAnimationScale * 1.5f);
        }
    }
}
