package net.ennway.farworld.network;

import net.ennway.farworld.Farworld;
import net.minecraft.network.RegistryFriendlyByteBuf;
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
