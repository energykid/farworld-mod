package net.ennway.farworld.network;

import io.netty.buffer.ByteBuf;
import net.ennway.farworld.Farworld;
import net.ennway.farworld.registries.ModAttachments;
import net.ennway.farworld.utils.BehaviorUtils;
import net.ennway.farworld.utils.BossMusicHandling;
import net.ennway.farworld.utils.MathUtils;
import net.ennway.farworld.utils.ServerUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.handling.DirectionalPayloadHandler;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;
import org.apache.logging.log4j.core.jmx.Server;

@EventBusSubscriber(modid = Farworld.MOD_ID)
public class Payloads {
    @SubscribeEvent
    public static void register(final RegisterPayloadHandlersEvent event) {
        final PayloadRegistrar registrar = event.registrar("1");

        registrar.playBidirectional(
                AccessoryData.TYPE,
                AccessoryData.STREAM_CODEC,
                new DirectionalPayloadHandler<>(
                        AccessoryClientPayloadHandler::handleDataOnMain,
                        AccessoryServerPayloadHandler::handleDataOnMain
                )
        );

        registrar.playBidirectional(
                BossMusicData.TYPE,
                BossMusicData.STREAM_CODEC,
                new DirectionalPayloadHandler<>(
                        BossMusicClientPayloadHandler::handleDataOnMain,
                        BossMusicServerPayloadHandler::handleDataOnMain
                )
        );
    }

    public record BossMusicData(String musicName) implements CustomPacketPayload {
        public static final CustomPacketPayload.Type<BossMusicData> TYPE = new CustomPacketPayload.Type<>(ResourceLocation.fromNamespaceAndPath(Farworld.MOD_ID, "boss_music_data"));

        // Each pair of elements defines the stream codec of the element to encode/decode and the getter for the element to encode
        // 'name' will be encoded and decoded as a string
        // 'age' will be encoded and decoded as an integer
        // The final parameter takes in the previous parameters in the order they are provided to construct the payload object
        public static final StreamCodec<ByteBuf, BossMusicData> STREAM_CODEC = StreamCodec.composite(
                ByteBufCodecs.STRING_UTF8,
                BossMusicData::musicName,
                BossMusicData::new
        );

        @Override
        public CustomPacketPayload.Type<? extends CustomPacketPayload> type() {
            return TYPE;
        }
    }

    public static class BossMusicClientPayloadHandler {
        public static void handleDataOnMain(final Payloads.BossMusicData data, final IPayloadContext context) {
            BossMusicHandling.playMyMusic(data.musicName);
        }
    }

    public static class BossMusicServerPayloadHandler {
        public static void handleDataOnMain(final Payloads.BossMusicData data, final IPayloadContext context) {

        }
    }

    public record AccessoryData(ItemStack armor) implements CustomPacketPayload
    {
        public static final CustomPacketPayload.Type<AccessoryData> TYPE = new CustomPacketPayload.Type<>(ResourceLocation.fromNamespaceAndPath(Farworld.MOD_ID, "accessory_data"));

        // Each pair of elements defines the stream codec of the element to encode/decode and the getter for the element to encode
        // The final parameter takes in the previous parameters in the order they are provided to construct the payload object
        public static final StreamCodec<RegistryFriendlyByteBuf, AccessoryData> STREAM_CODEC = StreamCodec.composite(
                ItemStack.STREAM_CODEC,
                AccessoryData::armor,
                AccessoryData::new
        );

        @Override
        public Type<? extends CustomPacketPayload> type() {
            return TYPE;
        }
    }

    public static class AccessoryClientPayloadHandler {
        public static void handleDataOnMain(final Payloads.AccessoryData data, final IPayloadContext context) {
            context.player().setItemSlot(context.player().getEquipmentSlotForItem(data.armor), data.armor);
        }
    }

    public static class AccessoryServerPayloadHandler {
        public static void handleDataOnMain(final Payloads.AccessoryData data, final IPayloadContext context) {
            context.player().setItemSlot(context.player().getEquipmentSlotForItem(data.armor), data.armor);
        }
    }
}
