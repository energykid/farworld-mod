package net.ennway.farworld.network;


import com.mojang.serialization.Codec;
import io.netty.buffer.ByteBuf;
import net.ennway.farworld.item.accessory.ApocalypseCore;
import net.minecraft.client.Minecraft;
import net.minecraft.core.UUIDUtil;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.datafix.fixes.PlayerUUIDFix;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.network.PacketDistributor;
import net.neoforged.neoforge.network.handling.IPayloadContext;

import java.util.UUID;

public record ApocalypseCoreData(UUID player) implements CustomPacketPayload {

    public static final CustomPacketPayload.Type<ApocalypseCoreData> TYPE = new CustomPacketPayload.Type<>(ResourceLocation.fromNamespaceAndPath("mymod", "apocalypse_core_packet"));

    public static final StreamCodec<ByteBuf, ApocalypseCoreData> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.fromCodec(UUIDUtil.CODEC),
            ApocalypseCoreData::player,
            ApocalypseCoreData::new
    );

    @Override
    public CustomPacketPayload.Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    public static class ClientPayloadHandler {
        public static void handleDataOnMain(final ApocalypseCoreData data, final IPayloadContext context) {
            ApocalypseCore.runAttack(context.player().level().getPlayerByUUID(data.player));
        }
    }

    public static class ServerPayloadHandler {
        public static void handleDataOnMain(final ApocalypseCoreData data, final IPayloadContext context) {
            ApocalypseCore.runAttack(context.player().level().getPlayerByUUID(data.player));
            PacketDistributor.sendToAllPlayers(data);
        }
    }
}