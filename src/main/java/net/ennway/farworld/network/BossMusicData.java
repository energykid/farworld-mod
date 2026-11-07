package net.ennway.farworld.network;

import io.netty.buffer.ByteBuf;
import net.ennway.farworld.Farworld;
import net.ennway.farworld.utils.BossMusicHandling;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public record BossMusicData(String musicName) implements CustomPacketPayload {

    @Override
    public String musicName() {
        return musicName;
    }

    public static final CustomPacketPayload.Type<BossMusicData> TYPE = new CustomPacketPayload.Type<>(ResourceLocation.fromNamespaceAndPath(Farworld.MOD_ID, "boss_music_data"));

    public static final StreamCodec<ByteBuf, BossMusicData> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.STRING_UTF8,
            BossMusicData::musicName,
            BossMusicData::new
    );

    @Override
    public CustomPacketPayload.Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    public static class ClientPayloadHandler {
        public static void handleDataOnMain(final BossMusicData data, final IPayloadContext context) {
            BossMusicHandling.playMyMusic(data.musicName());
        }
    }

    public static class ServerPayloadHandler {
        public static void handleDataOnMain(final BossMusicData data, final IPayloadContext context) {

        }
    }
}