package net.ennway.farworld.mixin;

import net.ennway.farworld.Farworld;
import net.ennway.farworld.item.data.ArmorAccessories;
import net.ennway.farworld.registries.ModDataComponents;
import net.ennway.farworld.registries.ModTags;
import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponentMap;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.SlotAccess;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ClickAction;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;

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
        if (farworld_mod$countsAsArmorForAccessories(stack) && stack.get(ModDataComponents.ARMOR_ACCESSORIES) != null) {
            tooltipComponents.add(Component.translatable("accessory.farworld.accessory_slots").append(stack.get(ModDataComponents.ACCESSORY_SLOTS).toString()));
            if (!stack.get(ModDataComponents.ARMOR_ACCESSORIES).isEmpty()) {
                MutableComponent str = Component.empty();

                stack.get(ModDataComponents.ARMOR_ACCESSORIES).items().forEach(
                        item -> {
                            if (!str.equals(Component.empty()))
                            {
                                str.append(", ");
                            }
                            str.append(item.getDisplayName().getString());
                        }
                );

                tooltipComponents.add(Component.translatable("accessory.farworld.accessory_attached").append(str));
                tooltipComponents.add(Component.translatable("accessory.farworld.accessory_detach_hint"));
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

    private static final TagKey<Item> TWO_ACCESSORY_ARMOR = TagKey.create(
            BuiltInRegistries.ITEM.key(),
            ResourceLocation.fromNamespaceAndPath(Farworld.MOD_ID, "two_accessory_armor"));

    private static final TagKey<Item> THREE_ACCESSORY_ARMOR = TagKey.create(
            BuiltInRegistries.ITEM.key(),
            ResourceLocation.fromNamespaceAndPath(Farworld.MOD_ID, "three_accessory_armor"));

    @Inject(method = "verifyComponentsAfterLoad", at = @At("TAIL"))
    void addComponents(ItemStack stack, CallbackInfo ci)
    {
        if (farworld_mod$countsAsArmorForAccessories(stack))
        {
            if (stack.get(ModDataComponents.ARMOR_ACCESSORIES) == null)
                stack.set(ModDataComponents.ARMOR_ACCESSORIES, ArmorAccessories.EMPTY);

            if (stack.get(ModDataComponents.ACCESSORY_SLOTS) == null)
            {
                stack.set(ModDataComponents.ACCESSORY_SLOTS, 1);
                if (stack.is(TWO_ACCESSORY_ARMOR))
                    stack.set(ModDataComponents.ACCESSORY_SLOTS, 2);
                if (stack.is(THREE_ACCESSORY_ARMOR))
                    stack.set(ModDataComponents.ACCESSORY_SLOTS, 3);
            }
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
                ArmorAccessories bundlecontents = (ArmorAccessories)stack.get(ModDataComponents.ARMOR_ACCESSORIES);
                if (bundlecontents != null) {
                    ItemStack itemstack = slot.getItem();
                    ArmorAccessories.Mutable bundlecontents$mutable = new ArmorAccessories.Mutable(bundlecontents);
                    if (itemstack.isEmpty()) {
                        this.farworld_mod$playRemoveOneSound(player);
                        ItemStack itemstack1 = bundlecontents$mutable.removeOne();
                        if (itemstack1 != null) {
                            ItemStack itemstack2 = slot.safeInsert(itemstack1);
                            bundlecontents$mutable.tryInsert(stack, itemstack2);
                        }
                    } else if (itemstack.getItem().canFitInsideContainerItems()) {
                        int i = bundlecontents$mutable.tryInsert(stack, slot.getItem());
                        if (i > 0) {
                            this.farworld_mod$playInsertSound(player);
                        }
                    }

                    stack.set(ModDataComponents.ARMOR_ACCESSORIES, bundlecontents$mutable.toImmutable());
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
            if (stack.getCount() == 1 && slot.allowModification(player)) {
                ArmorAccessories bundlecontents = (ArmorAccessories)stack.get(ModDataComponents.ARMOR_ACCESSORIES);
                if (bundlecontents != null) {
                    ArmorAccessories.Mutable bundlecontents$mutable = new ArmorAccessories.Mutable(bundlecontents);
                    if (other.isEmpty()) {
                        ItemStack itemstack = bundlecontents$mutable.removeOne();
                        if (itemstack != null) {
                            this.farworld_mod$playRemoveOneSound(player);
                            access.set(itemstack);
                        }
                    } else {
                        if (!slot.getItem().isEmpty())
                        {
                            int i = bundlecontents$mutable.tryInsert(stack, other);
                            if (i > 0) {
                                this.farworld_mod$playInsertSound(player);
                            }
                        }
                    }

                    stack.set(ModDataComponents.ARMOR_ACCESSORIES, bundlecontents$mutable.toImmutable());
                    cir.setReturnValue(true);
                }
            }
        }
    }
}
