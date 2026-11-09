package net.ennway.farworld.network;


import io.netty.buffer.ByteBuf;
import net.ennway.farworld.item.accessory.ApocalypseCore;
import net.ennway.farworld.item.accessory.TechnicLaunchers;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.network.PacketDistributor;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public record TechnicLaunchersData(int direction) implements CustomPacketPayload {

    @Override
    public int direction() {
        return direction;
    }

    public static final Type<TechnicLaunchersData> TYPE = new Type<>(ResourceLocation.fromNamespaceAndPath("mymod", "technic_launchers_packet"));

    public static final StreamCodec<ByteBuf, TechnicLaunchersData> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.VAR_INT,
            TechnicLaunchersData::direction,
            TechnicLaunchersData::new
    );

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    public static class ClientPayloadHandler {
        public static void handleDataOnMain(final TechnicLaunchersData data, final IPayloadContext context) {
            TechnicLaunchers.runZip(context.player(), data.direction());
        }
    }

    public static class ServerPayloadHandler {
        public static void handleDataOnMain(final TechnicLaunchersData data, final IPayloadContext context) {
            TechnicLaunchers.runZip(context.player(), data.direction());
            PacketDistributor.sendToAllPlayers(data);
        }
    }
}