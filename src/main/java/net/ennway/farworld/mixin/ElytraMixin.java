package net.ennway.farworld.mixin;

import net.ennway.farworld.utils.AccessoryUtils;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ItemLike;
import net.neoforged.neoforge.common.extensions.IItemStackExtension;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(IItemStackExtension.class)
public interface ElytraMixin {
    @Shadow @Nullable EquipmentSlot getEquipmentSlot();

    @Inject(method = "canElytraFly", at = @At("HEAD"), cancellable = true)
    default void setFlyable(LivingEntity entity, CallbackInfoReturnable<Boolean> cir)
    {
        if (entity instanceof Player plr) {
            if (AccessoryUtils.playerHasAccessory(plr, Items.ELYTRA)) cir.setReturnValue(true);
        }
    }

    @Inject(method = "elytraFlightTick", at = @At("HEAD"), cancellable = true)
    default void retainFlyable(LivingEntity entity, int flightTicks, CallbackInfoReturnable<Boolean> cir) {
        if (entity instanceof Player plr) {
            if (AccessoryUtils.playerHasAccessory(plr, Items.ELYTRA)) cir.setReturnValue(true);
        }
    }
}
