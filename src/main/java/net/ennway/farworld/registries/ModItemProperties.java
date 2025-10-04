package net.ennway.farworld.registries;

import com.google.common.collect.Maps;
import net.ennway.farworld.item.tool.black_ice.BlackIceCrossbow;
import net.minecraft.client.renderer.item.ClampedItemPropertyFunction;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.client.renderer.item.ItemPropertyFunction;
import net.minecraft.core.component.DataComponents;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.datafix.fixes.ItemPotionFix;
import net.minecraft.world.item.CrossbowItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.component.ChargedProjectiles;

import java.util.Map;

public class ModItemProperties {

    static void registerBow(Item item)
    {
        ItemProperties.register(item, ResourceLocation.withDefaultNamespace("pull"), (p_344163_, p_344164_, p_344165_, p_344166_) -> {
            if (p_344165_ == null) {
                return 0.0F;
            } else {
                return p_344165_.getUseItem() != p_344163_ ? 0.0F : (float) (p_344163_.getUseDuration(p_344165_) - p_344165_.getUseItemRemainingTicks()) / 20.0F;
            }
        });

        ItemProperties.register(item, ResourceLocation.withDefaultNamespace("pulling"), (p_174630_, p_174631_, p_174632_, p_174633_) -> {
            return p_174632_ != null && p_174632_.isUsingItem() && p_174632_.getUseItem() == p_174630_ ? 1.0F : 0.0F;
        });
    }

    static void registerCrossbow(Item item)
    {
        if (item instanceof CrossbowItem crossbow)
        {
            ItemProperties.register(crossbow, ResourceLocation.withDefaultNamespace("pull"), (p_351682_, p_351683_, p_351684_, p_351685_) -> {
                if (p_351684_ == null) {
                    return 0.0F;
                } else {
                    return crossbow.isCharged(p_351682_) ? 0.0F : (float) (p_351682_.getUseDuration(p_351684_) - p_351684_.getUseItemRemainingTicks()) / (float) crossbow.getChargeDuration(p_351682_, p_351684_);
                }
            });
            ItemProperties.register(crossbow, ResourceLocation.withDefaultNamespace("pulling"), (p_174605_, p_174606_, p_174607_, p_174608_) -> {
                return p_174607_ != null && p_174607_.isUsingItem() && p_174607_.getUseItem() == p_174605_ && !crossbow.isCharged(p_174605_) ? 1.0F : 0.0F;
            });
            ItemProperties.register(crossbow, ResourceLocation.withDefaultNamespace("charged"), (p_275891_, p_275892_, p_275893_, p_275894_) -> {
                return crossbow.isCharged(p_275891_) ? 1.0F : 0.0F;
            });
            ItemProperties.register(crossbow, ResourceLocation.withDefaultNamespace("firework"), (p_329796_, p_329797_, p_329798_, p_329799_) -> {
                ChargedProjectiles chargedprojectiles = (ChargedProjectiles) p_329796_.get(DataComponents.CHARGED_PROJECTILES);
                return chargedprojectiles != null && chargedprojectiles.contains(Items.FIREWORK_ROCKET) ? 1.0F : 0.0F;
            });
        }
    }

    public static void addCustomItemProperties()
    {
        registerBow(ModItems.BLACK_ICE_BOW.asItem());
        registerCrossbow(ModItems.BLACK_ICE_CROSSBOW.asItem());
    }
}
