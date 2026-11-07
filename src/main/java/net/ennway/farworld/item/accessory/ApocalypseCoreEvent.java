package net.ennway.farworld.item.accessory;


import net.ennway.farworld.network.ApocalypseCoreData;
import net.ennway.farworld.registries.ModAttachments;
import net.ennway.farworld.registries.ModItems;
import net.ennway.farworld.utils.AccessoryUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.InputEvent;
import net.neoforged.neoforge.network.PacketDistributor;

@EventBusSubscriber(value = Dist.CLIENT)
public class ApocalypseCoreEvent
{
    @SubscribeEvent
    public static void runInput(InputEvent.InteractionKeyMappingTriggered evt)
    {
        if (evt.isUseItem() && !evt.isCanceled())
        {
            LocalPlayer player = Minecraft.getInstance().player;
            if (player.getItemInHand(evt.getHand()).isEmpty() && evt.getHand() == InteractionHand.MAIN_HAND)
            {
                if (AccessoryUtils.playerHasAccessory(player, ModItems.APOCALYPSE_CORE.asItem()) && player.getData(ModAttachments.APOCALYPSE_ABILITY) <= 0)
                {
                    PacketDistributor.sendToServer(new ApocalypseCoreData(0));
                }
            }
        }
    }
}