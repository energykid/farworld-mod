package net.ennway.farworld.mixin;

import net.ennway.farworld.utils.AccessoryUtils;
import net.minecraft.client.renderer.entity.layers.ElytraLayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ElytraLayer.class)
public class ElytraVisualMixin<T extends LivingEntity> {
    @Inject(method = "shouldRender", at = @At("HEAD"), cancellable = true)
    void setElytraRenderer(ItemStack stack, T entity, CallbackInfoReturnable<Boolean> cir)
    {
        if (entity instanceof Player plr)
        {
            if (AccessoryUtils.playerHasAccessory(plr, Items.ELYTRA))
                cir.setReturnValue(true);
        }
    }
}
