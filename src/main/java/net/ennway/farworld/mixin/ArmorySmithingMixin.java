package net.ennway.farworld.mixin;

import net.ennway.farworld.item.data.ArmorAccessories;
import net.ennway.farworld.registries.ModDataComponents;
import net.ennway.farworld.registries.ModTags;
import net.ennway.farworld.utils.AccessoryUtils;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.component.DataComponentPatch;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.SmithingRecipeInput;
import net.minecraft.world.item.crafting.SmithingTransformRecipe;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(SmithingTransformRecipe.class)
public class ArmorySmithingMixin {
    @Inject(method = "assemble(Lnet/minecraft/world/item/crafting/SmithingRecipeInput;Lnet/minecraft/core/HolderLookup$Provider;)Lnet/minecraft/world/item/ItemStack;", at = @At("TAIL"), cancellable = true)
    void a(SmithingRecipeInput input, HolderLookup.Provider registries, CallbackInfoReturnable<ItemStack> cir)
    {
        ItemStack stack = cir.getReturnValue();

        if (stack.is(ModTags.TWO_ACCESSORY_ARMOR))
            stack.applyComponents(DataComponentPatch.builder().set(ModDataComponents.ACCESSORY_SLOTS.get(), 2).build());
        if (stack.is(ModTags.THREE_ACCESSORY_ARMOR))
            stack.applyComponents(DataComponentPatch.builder().set(ModDataComponents.ACCESSORY_SLOTS.get(), 3).build());

        cir.setReturnValue(stack);
    }
}
