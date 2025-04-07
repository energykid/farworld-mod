package net.ennway.farworld.mixin;

import net.ennway.farworld.block.dimensiontransitions.FarworldDimensionTransitions;
import net.ennway.farworld.event.PortalEvents;
import net.ennway.farworld.event.PortalLayerEvents;
import net.ennway.farworld.registries.ModItems;
import net.minecraft.client.Camera;
import net.minecraft.client.CameraType;
import net.minecraft.client.OptionInstance;
import net.minecraft.client.Options;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.core.component.DataComponents;
import net.minecraft.util.Mth;
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
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Objects;

@Mixin(value = Options.class)
public class PortalAnimationMixin {
    @Shadow @Final private OptionInstance<Integer> fov;

    @Inject(method = "fov", at = @At("TAIL"))
    public void fovDeterminer(CallbackInfoReturnable<OptionInstance<Integer>> cir)
    {
        this.fov.set(this.fov.get() + 90);
        cir.setReturnValue(this.fov);
    }
}
