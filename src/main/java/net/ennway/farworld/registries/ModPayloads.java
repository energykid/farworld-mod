package net.ennway.farworld.registries;

import net.ennway.farworld.Farworld;
import net.ennway.farworld.network.AccessoryData;
import net.ennway.farworld.network.ApocalypseCoreData;
import net.ennway.farworld.network.BossMusicData;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.handling.DirectionalPayloadHandler;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;

@EventBusSubscriber(modid = Farworld.MOD_ID)
public class ModPayloads {

    @SubscribeEvent
    public static void register(final RegisterPayloadHandlersEvent event) {
        final PayloadRegistrar registrar = event.registrar("1");

        registrar.playBidirectional(
                AccessoryData.TYPE,
                AccessoryData.STREAM_CODEC,
                new DirectionalPayloadHandler<>(
                        AccessoryData.ClientPayloadHandler::handleDataOnMain,
                        AccessoryData.ServerPayloadHandler::handleDataOnMain
                )
        );

        registrar.playBidirectional(
                BossMusicData.TYPE,
                BossMusicData.STREAM_CODEC,
                new DirectionalPayloadHandler<>(
                        BossMusicData.ClientPayloadHandler::handleDataOnMain,
                        BossMusicData.ServerPayloadHandler::handleDataOnMain
                )
        );

        registrar.playBidirectional(
                ApocalypseCoreData.TYPE,
                ApocalypseCoreData.STREAM_CODEC,
                new DirectionalPayloadHandler<>(
                        ApocalypseCoreData.ClientPayloadHandler::handleDataOnMain,
                        ApocalypseCoreData.ServerPayloadHandler::handleDataOnMain
                )
        );
    }
}
