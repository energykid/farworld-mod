package net.ennway.farworld.event;

import net.ennway.farworld.Farworld;
import net.ennway.farworld.entity.client.bloomed.BloomedModel;
import net.ennway.farworld.entity.client.soulgolem.SoulGolemModel;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;

@EventBusSubscriber(modid = Farworld.MOD_ID, bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ModEntityRenderLayers {
    @SubscribeEvent
    public static void registerLayers(EntityRenderersEvent.RegisterLayerDefinitions event)
    {
        event.registerLayerDefinition(BloomedModel.LAYER_LOCATION, BloomedModel::createBodyLayer);
        event.registerLayerDefinition(SoulGolemModel.LAYER_LOCATION, SoulGolemModel::createBodyLayer);
    }
}
