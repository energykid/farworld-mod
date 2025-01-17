package net.ennway.farworld.item.tool;

import net.ennway.farworld.Farworld;
import net.ennway.farworld.registries.ModDataComponents;
import net.ennway.farworld.registries.ModSounds;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.Resource;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.EnchantmentTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.tooltip.TooltipComponent;
import net.minecraft.world.item.*;
import net.minecraft.world.item.component.CustomModelData;
import net.minecraft.world.item.component.ItemAttributeModifiers;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.item.enchantment.ItemEnchantments;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.common.ItemAbilities;
import net.neoforged.neoforge.common.ItemAbility;
import net.neoforged.neoforge.common.SimpleTier;
import net.neoforged.neoforge.registries.DeferredRegister;
import org.jetbrains.annotations.NotNull;

import java.security.Provider;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

public class Allsaw extends DiggerItem {

    private static final Tier TOOL_TIER = new SimpleTier(
            BlockTags.INCORRECT_FOR_IRON_TOOL,
            1000,
            5f,
            5f,
            15,
            () -> Ingredient.of(new ItemStack[]{new ItemStack(Items.IRON_INGOT)})
    ) {};

    public static final TagKey<Block> ALLSAW_MINEABLE = TagKey.create(
            BuiltInRegistries.BLOCK.key(),
            ResourceLocation.fromNamespaceAndPath(Farworld.MOD_ID, "allsaw_mineable"));

    public static final TagKey<Enchantment> SILK_TOUCH = TagKey.create(
            Registries.ENCHANTMENT,
            ResourceLocation.fromNamespaceAndPath(Farworld.MOD_ID, "silk_touch"));

    public static final TagKey<Enchantment> SCYTHING = TagKey.create(
            Registries.ENCHANTMENT,
            ResourceLocation.fromNamespaceAndPath(Farworld.MOD_ID, "scything"));

    public static final TagKey<Enchantment> ALLSAW_COMPATIBLE_ENCHANTMENTS = TagKey.create(
            Registries.ENCHANTMENT,
            ResourceLocation.fromNamespaceAndPath(Farworld.MOD_ID, "allsaw_compatible"));

    public Allsaw() {
        super(TOOL_TIER, ALLSAW_MINEABLE, new Properties()
                .component(DataComponents.CUSTOM_MODEL_DATA, new CustomModelData(1))
                .durability(1250)
                .rarity(Rarity.UNCOMMON)
                .attributes(
                        ItemAttributeModifiers.builder()
                                .add(Attributes.ATTACK_DAMAGE,
                                        new AttributeModifier(BASE_ATTACK_DAMAGE_ID, 3.0, AttributeModifier.Operation.ADD_VALUE),
                                        EquipmentSlotGroup.MAINHAND)
                                .add(Attributes.ATTACK_SPEED,
                                        new AttributeModifier(BASE_ATTACK_SPEED_ID, -2.8, AttributeModifier.Operation.ADD_VALUE),
                                        EquipmentSlotGroup.MAINHAND)
                                .build()
                ));
    }

    @Override
    public boolean supportsEnchantment(ItemStack stack, Holder<Enchantment> enchantment) {
        return enchantment.is(ALLSAW_COMPATIBLE_ENCHANTMENTS);
    }

    @Override
    public boolean isValidRepairItem(ItemStack toRepair, ItemStack repair) {
        if (toRepair.is(Items.IRON_INGOT)) return true;
        return super.isValidRepairItem(toRepair, repair);
    }

    @Override
    public boolean isFoil(ItemStack stack) {
        if (EnchantmentHelper.hasTag(stack, SILK_TOUCH) && EnchantmentHelper.getEnchantmentsForCrafting(stack).size() <= 1) return false;
        return super.isFoil(stack);
    }

    @Override
    public @NotNull ItemAttributeModifiers getDefaultAttributeModifiers(ItemStack stack) {
        return super.getDefaultAttributeModifiers(stack);
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
        super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);

        if (stack.get(DataComponents.CUSTOM_MODEL_DATA) != null)
        {
            if (stack.get(DataComponents.CUSTOM_MODEL_DATA).value() > 1 && stack.get(DataComponents.CUSTOM_MODEL_DATA).value() < 5) {
                for (int i = 0; i < tooltipComponents.size(); i++) {
                    Component tooltipComponent = tooltipComponents.get(i);

                    if (tooltipComponent.getString().equals(Component.translatable("item.farworld.allsaw").getString())) {
                        tooltipComponents.set(i, Component.translatable("item.farworld.allsaw_precision"));
                    }
                }
            }
        }
    }

    @Override
    public float getDestroySpeed(ItemStack stack, BlockState state) {
        if (EnchantmentHelper.hasTag(stack, SILK_TOUCH) && !EnchantmentHelper.hasTag(stack, SCYTHING)) return super.getDestroySpeed(stack, state) * 0.5f;
        else return super.getDestroySpeed(stack, state);
    }

    @Override
    public void inventoryTick(ItemStack stack, Level level, Entity entity, int slotId, boolean isSelected) {
        if (stack.get(DataComponents.CUSTOM_MODEL_DATA) != null)
        {
            if (stack.get(DataComponents.CUSTOM_MODEL_DATA).value() != 1 && stack.get(DataComponents.CUSTOM_MODEL_DATA).value() != 4)
                stack.set(DataComponents.CUSTOM_MODEL_DATA, new CustomModelData(stack.get(DataComponents.CUSTOM_MODEL_DATA).value() + 1));
            if (stack.get(DataComponents.CUSTOM_MODEL_DATA).value() >= 8)
                stack.set(DataComponents.CUSTOM_MODEL_DATA, new CustomModelData(0));
        }
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand usedHand) {

        ItemStack stack = player.getItemInHand(usedHand);

        if (stack.get(DataComponents.CUSTOM_MODEL_DATA) != null)
        {
            if (stack.get(DataComponents.CUSTOM_MODEL_DATA).value() == 0 || stack.get(DataComponents.CUSTOM_MODEL_DATA).value() == 1)
            {
                stack.set(DataComponents.CUSTOM_MODEL_DATA, new CustomModelData(2));
                stack.enchant(level.registryAccess().holderOrThrow(Enchantments.SILK_TOUCH), 1);
                player.playSound(ModSounds.ALLSAW_SWITCH_TO_PRECISION.get());
            }

            if (stack.get(DataComponents.CUSTOM_MODEL_DATA).value() == 4)
            {
                stack.set(DataComponents.CUSTOM_MODEL_DATA, new CustomModelData(5));
                EnchantmentHelper.updateEnchantments(stack,
                        enchantment -> enchantment.removeIf(enchantmentType -> enchantmentType.is(Enchantments.SILK_TOUCH)));
                player.playSound(ModSounds.ALLSAW_SWITCH_FROM_PRECISION.get());
            }
        }

        return super.use(level, player, usedHand);
    }
}
