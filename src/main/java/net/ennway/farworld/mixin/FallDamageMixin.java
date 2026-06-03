package net.ennway.farworld.mixin;

import net.ennway.farworld.registries.ModItems;
import net.ennway.farworld.registries.ModSounds;
import net.ennway.farworld.utils.AccessoryUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Player.class)
public abstract class FallDamageMixin extends LivingEntity {

    protected FallDamageMixin(EntityType<? extends LivingEntity> entityType, Level level) {
        super(entityType, level);
    }

    @Inject(method = "getFallSounds", at = @At("HEAD"), cancellable = true)
    public void fallSounds(CallbackInfoReturnable<LivingEntity.Fallsounds> cir)
    {
        Player t = (Player)(Object)this;
        if (AccessoryUtils.playerHasAccessory(t, ModItems.GLOOM_BRACE.get())) {
            cir.setReturnValue(ModSounds.gloomSounds());
        }
        else if (AccessoryUtils.playerHasAccessory(t, ModItems.SLIME_BRACE.get())) {
            cir.setReturnValue(ModSounds.slimeSounds());
        }
    }
}
