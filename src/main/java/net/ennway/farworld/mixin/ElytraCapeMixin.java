package net.ennway.farworld.mixin;

import com.mojang.blaze3d.vertex.PoseStack;
import net.ennway.farworld.utils.AccessoryUtils;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.layers.CapeLayer;
import net.minecraft.client.renderer.entity.layers.ElytraLayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(CapeLayer.class)
public class ElytraCapeMixin<T extends LivingEntity> {
    @Inject(method = "render(Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;ILnet/minecraft/client/player/AbstractClientPlayer;FFFFFF)V", at = @At("HEAD"), cancellable = true)
    void setElytraRenderer(PoseStack poseStack, MultiBufferSource buffer, int packedLight, AbstractClientPlayer entity, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch, CallbackInfo ci)
    {
        if (entity instanceof Player plr)
        {
            if (AccessoryUtils.playerHasAccessory(plr, Items.ELYTRA))
                ci.cancel();
        }
    }

    @Inject(method = "render(Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;ILnet/minecraft/world/entity/Entity;FFFFFF)V", at = @At("HEAD"), cancellable = true)
    void setElytraRenderer(PoseStack par1, MultiBufferSource par2, int par3, Entity entity, float par5, float par6, float par7, float par8, float par9, float par10, CallbackInfo ci)
    {
        if (entity instanceof Player plr)
        {
            if (AccessoryUtils.playerHasAccessory(plr, Items.ELYTRA))
                ci.cancel();
        }
    }
}
