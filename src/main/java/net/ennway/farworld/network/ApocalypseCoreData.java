package net.ennway.farworld.network;


import io.netty.buffer.ByteBuf;
import net.ennway.farworld.item.accessory.ApocalypseCore;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.network.PacketDistributor;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public record ApocalypseCoreData(int idk) implements CustomPacketPayload {

    public static final CustomPacketPayload.Type<ApocalypseCoreData> TYPE = new CustomPacketPayload.Type<>(ResourceLocation.fromNamespaceAndPath("mymod", "apocalypse_core_packet"));

    public static final StreamCodec<ByteBuf, ApocalypseCoreData> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.VAR_INT,
            ApocalypseCoreData::idk,
            ApocalypseCoreData::new
    );

    @Override
    public CustomPacketPayload.Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    public static class ClientPayloadHandler {
        public static void handleDataOnMain(final ApocalypseCoreData data, final IPayloadContext context) {
            ApocalypseCore.runAttack(context.player());
        }
    }

    public static class ServerPayloadHandler {
        public static void handleDataOnMain(final ApocalypseCoreData data, final IPayloadContext context) {
            ApocalypseCore.runAttack(context.player());
            PacketDistributor.sendToAllPlayers(new ApocalypseCoreData(0));
        }
    }
}