package net.ennway.farworld.event;

import net.ennway.farworld.Farworld;
import net.ennway.farworld.block.dimensiontransitions.FarworldDimensionTransitions;
import net.ennway.farworld.item.tool.AbstractBowItem;
import net.ennway.farworld.registries.ModAttachments;
import net.ennway.farworld.registries.ModBlocks;
import net.ennway.farworld.registries.ModDataComponents;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.portal.DimensionTransition;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.ComputeFovModifierEvent;

@EventBusSubscriber(modid = Farworld.MOD_ID, bus = EventBusSubscriber.Bus.GAME, value = Dist.CLIENT)
public class FOVEvents {

    public static float bowMod(Player player)
    {
        float f = 1.0F;
        ItemStack itemstack = player.getUseItem();
        if (player.isUsingItem()) {
            if (itemstack.getItem() instanceof AbstractBowItem abi) {
                int i = player.getTicksUsingItem();
                float ff = 20.0F;
                ff *= abi.components().get(ModDataComponents.BOW_DRAW_SPEED.get()).floatValue();
                float f1 = (float)i / ff;
                if (f1 > 1.0F) {
                    f1 = 1.0F;
                } else {
                    f1 *= f1;
                }

                f *= 1.0F - f1 * 0.15F;
            } else if (Minecraft.getInstance().options.getCameraType().isFirstPerson() && player.isScoping()) {
                return 0.1F;
            }
        }
        return f;
    }

    public static float bystoneFOVMod = 0f;

    @SubscribeEvent
    public static void fov(ComputeFovModifierEvent event)
    {
        if (event.getPlayer().getInBlockState().is(ModBlocks.BYSTONE_PORTAL.get())) {
            bystoneFOVMod = Mth.lerp(0.2f, bystoneFOVMod, (float)(event.getPlayer().getData(ModAttachments.DIMENSION_TRANSITION_VISUAL.get()) / 80f));
        }
        else {
            bystoneFOVMod = Mth.lerp(0.2f, bystoneFOVMod, 0f);
        }

        event.setNewFovModifier(event.getNewFovModifier() * bowMod(event.getPlayer()) * (1f - bystoneFOVMod));
    }
}
