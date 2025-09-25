package net.ennway.farworld.mixin;

import net.ennway.farworld.registries.ModItems;
import net.ennway.farworld.registries.ModTags;
import net.minecraft.client.renderer.entity.DisplayRenderer;
import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponentMap;
import net.minecraft.core.component.DataComponents;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.entity.SlotAccess;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ClickAction;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.inventory.tooltip.TooltipComponent;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.BundleContents;
import net.minecraft.world.item.component.ItemAttributeModifiers;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Mixin(value = Item.class)
public abstract class ArmoryMixin {

    @Shadow public abstract DataComponentMap components();

    @Shadow @Final private Holder.Reference<Item> builtInRegistryHolder;

    @Shadow private DataComponentMap components;

    @Unique
    private void farworld_mod$playRemoveOneSound(Entity entity) {
        entity.playSound(SoundEvents.BUNDLE_REMOVE_ONE, 0.8F, 0.8F + entity.level().getRandom().nextFloat() * 0.4F);
    }

    @Unique
    private void farworld_mod$playInsertSound(Entity entity) {
        entity.playSound(SoundEvents.BUNDLE_INSERT, 0.8F, 0.8F + entity.level().getRandom().nextFloat() * 0.4F);
    }

    @Unique
    private boolean farworld_mod$countsAsArmorForAccessories(ItemStack stack)
    {
        return (stack.is(ItemTags.HEAD_ARMOR) || stack.is(ItemTags.CHEST_ARMOR) || stack.is(ItemTags.LEG_ARMOR) || stack.is(ItemTags.FOOT_ARMOR));
    }

    /*
    @Inject(method = "<init>", at = @At("RETURN"))
    public void initalizePropertiesForArmor(Item.Properties properties, CallbackInfo ci)
    {
        var holder = this.builtInRegistryHolder;

        if (holder.is(ItemTags.HEAD_ARMOR) || holder.is(ItemTags.LEG_ARMOR) || holder.is(ItemTags.LEG_ARMOR) || holder.is(ItemTags.FOOT_ARMOR))
        {
            this.components.
        }
    }*/

    @Inject(method = "appendHoverText", at = @At("TAIL"))
    void changeDescription(ItemStack stack, Item.TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag, CallbackInfo ci) {
        if (farworld_mod$countsAsArmorForAccessories(stack) && stack.get(DataComponents.BUNDLE_CONTENTS) != null) {
            if (!stack.get(DataComponents.BUNDLE_CONTENTS).isEmpty()) {
                tooltipComponents.add(Component.translatable("accessory.farworld.accessory_attached").append(stack.get(DataComponents.BUNDLE_CONTENTS).getItemUnsafe(0).getDisplayName()));
            }
        }

        boolean showTooltip = false;

        if (stack.is(ModTags.HELMET_ACCESSORIES))
        {
            showTooltip = true;
            tooltipComponents.add(Component.translatable("accessory.farworld.accessory_helmet_description"));
        }
        if (stack.is(ModTags.CHESTPLATE_ACCESSORIES))
        {
            showTooltip = true;
            tooltipComponents.add(Component.translatable("accessory.farworld.accessory_chestplate_description"));
        }
        if (stack.is(ModTags.LEGGINGS_ACCESSORIES))
        {
            showTooltip = true;
            tooltipComponents.add(Component.translatable("accessory.farworld.accessory_leggings_description"));
        }
        if (stack.is(ModTags.BOOTS_ACCESSORIES))
        {
            showTooltip = true;
            tooltipComponents.add(Component.translatable("accessory.farworld.accessory_boots_description"));
        }

        if (showTooltip)
        {
            tooltipComponents.add(Component.translatable("accessory." + stack.getDescriptionId() + ".desc"));
        }
    }


    @Inject(method = "verifyComponentsAfterLoad", at = @At("TAIL"))
    void addComponents(ItemStack stack, CallbackInfo ci)
    {
        if (farworld_mod$countsAsArmorForAccessories(stack))
        {
            if (stack.get(DataComponents.BUNDLE_CONTENTS) == null)
                stack.set(DataComponents.BUNDLE_CONTENTS, BundleContents.EMPTY);
        }
    }

    @Inject(method = "overrideStackedOnOther", at = @At("HEAD"), cancellable = true)
    public void overrideStackedOnOther(ItemStack stack, Slot slot, ClickAction action, Player player, CallbackInfoReturnable<Boolean> cir) {
        boolean shouldRunLogic = false;

        if (stack.is(ItemTags.HEAD_ARMOR) && slot.getItem().is(ModTags.HELMET_ACCESSORIES)) shouldRunLogic = true;
        if (stack.is(ItemTags.CHEST_ARMOR) && slot.getItem().is(ModTags.CHESTPLATE_ACCESSORIES)) shouldRunLogic = true;
        if (stack.is(ItemTags.LEG_ARMOR) && slot.getItem().is(ModTags.LEGGINGS_ACCESSORIES)) shouldRunLogic = true;
        if (stack.is(ItemTags.FOOT_ARMOR) && slot.getItem().is(ModTags.BOOTS_ACCESSORIES)) shouldRunLogic = true;

        if (farworld_mod$countsAsArmorForAccessories(stack))
            if (slot.getItem().isEmpty()) shouldRunLogic = true;

        if (action != ClickAction.SECONDARY) shouldRunLogic = false;

        if (shouldRunLogic)
        {
            if (stack.getCount() == 1) {
                BundleContents bundlecontents = (BundleContents)stack.get(DataComponents.BUNDLE_CONTENTS);
                if (bundlecontents == null) {

                } else {
                    ItemStack itemstack = slot.getItem();
                    BundleContents.Mutable bundlecontents$mutable = new BundleContents.Mutable(bundlecontents);
                    if (itemstack.isEmpty()) {
                        this.farworld_mod$playRemoveOneSound(player);
                        ItemStack itemstack1 = bundlecontents$mutable.removeOne();
                        if (itemstack1 != null) {
                            ItemStack itemstack2 = slot.safeInsert(itemstack1);
                            bundlecontents$mutable.tryInsert(itemstack2);
                        }
                    } else if (itemstack.getItem().canFitInsideContainerItems()) {
                        int i = bundlecontents$mutable.tryTransfer(slot, player);
                        if (i > 0) {
                            this.farworld_mod$playInsertSound(player);
                        }
                    }

                    stack.set(DataComponents.BUNDLE_CONTENTS, bundlecontents$mutable.toImmutable());
                    cir.setReturnValue(true);
                }
            }
        }
    }

    @Inject(method = "overrideOtherStackedOnMe", at = @At("HEAD"), cancellable = true)
    public void overrideOtherStackedOnMe(ItemStack stack, ItemStack other, Slot slot, ClickAction action, Player player, SlotAccess access, CallbackInfoReturnable<Boolean> cir) {
        boolean shouldRunLogic = false;

        if (stack.is(ItemTags.HEAD_ARMOR) && other.is(ModTags.HELMET_ACCESSORIES)) shouldRunLogic = true;
        if (stack.is(ItemTags.CHEST_ARMOR) && other.is(ModTags.CHESTPLATE_ACCESSORIES)) shouldRunLogic = true;
        if (stack.is(ItemTags.LEG_ARMOR) && other.is(ModTags.LEGGINGS_ACCESSORIES)) shouldRunLogic = true;
        if (stack.is(ItemTags.FOOT_ARMOR) && other.is(ModTags.BOOTS_ACCESSORIES)) shouldRunLogic = true;

        if (farworld_mod$countsAsArmorForAccessories(stack))
            if (slot.getItem().isEmpty()) shouldRunLogic = true;

        if (action != ClickAction.SECONDARY) shouldRunLogic = false;

        if (shouldRunLogic)
        {
            if (stack.getCount() != 1) {

            } else if (slot.allowModification(player)) {
                BundleContents bundlecontents = (BundleContents)stack.get(DataComponents.BUNDLE_CONTENTS);
                if (bundlecontents != null) {
                    BundleContents.Mutable bundlecontents$mutable = new BundleContents.Mutable(bundlecontents);
                    if (other.isEmpty()) {
                        ItemStack itemstack = bundlecontents$mutable.removeOne();
                        if (itemstack != null) {
                            this.farworld_mod$playRemoveOneSound(player);
                            access.set(itemstack);
                        }
                    } else {
                        ItemStack itemstack = slot.getItem();
                        int i = bundlecontents$mutable.tryInsert(other);
                        if (i > 0) {
                            this.farworld_mod$playInsertSound(player);
                        }
                    }

                    stack.set(DataComponents.BUNDLE_CONTENTS, bundlecontents$mutable.toImmutable());
                    cir.setReturnValue(true);
                }
            }
        }
    }
}
