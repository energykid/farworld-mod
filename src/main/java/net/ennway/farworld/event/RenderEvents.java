package net.ennway.farworld.event;

import net.ennway.farworld.entity.client.PlayerAccessoryLayer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.Model;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.player.PlayerRenderer;
import net.minecraft.client.resources.PlayerSkin;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;

@EventBusSubscriber
public class RenderEvents {

    @SubscribeEvent
    public static void addLayers(EntityRenderersEvent.AddLayers evt)
    {
        for (PlayerSkin.Model skin : evt.getSkins()) {
            EntityRenderer<? extends Player> renderer = evt.getSkin(skin);

            if (renderer instanceof PlayerRenderer livingRenderer) {
                livingRenderer.addLayer(
                        new PlayerAccessoryLayer(livingRenderer, livingRenderer.getModel(), livingRenderer.getModel(), Minecraft.getInstance().getModelManager(), skin == PlayerSkin.Model.SLIM)
                );
            }
        }
    }
}
