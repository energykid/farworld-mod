package net.ennway.farworld.feature;

import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.serialization.Codec;
import net.ennway.farworld.Farworld;
import net.ennway.farworld.feature.configuration.TemplateFeatureConfiguration;
import net.minecraft.ResourceLocationException;
import net.minecraft.client.multiplayer.chat.ChatListener;
import net.minecraft.client.multiplayer.chat.ChatLog;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.coordinates.BlockPosArgument;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.ChatType;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.OutgoingChatMessage;
import net.minecraft.network.chat.PlayerChatMessage;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.commands.PlaceCommand;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.entity.StructureBlockEntity;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.structure.templatesystem.BlockRotProcessor;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructurePlaceSettings;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplateManager;
import org.apache.logging.log4j.core.jmx.Server;

import java.util.Optional;

public class SimpleTemplateFeature extends Feature<TemplateFeatureConfiguration> {
    public SimpleTemplateFeature(Codec<TemplateFeatureConfiguration> codec) {
        super(codec);
    }

    private static boolean checkLoaded(ServerLevel level, ChunkPos start, ChunkPos end) {
        if (ChunkPos.rangeClosed(start, end).filter((p_313494_) -> {
            return !level.isLoaded(p_313494_.getWorldPosition());
        }).findAny().isPresent()) {
            return false;
        }
        return true;
    }

    public static boolean placeTemplate(WorldGenLevel level, ResourceLocation template, BlockPos pos, Rotation rotation, Mirror mirror, long seed) {
        ServerLevel serverlevel = level.getLevel();
        StructureTemplateManager structuretemplatemanager = serverlevel.getStructureManager();

        Optional optional = structuretemplatemanager.get(template);

        if (optional.isPresent())
        {
            StructureTemplate structuretemplate = (StructureTemplate) optional.get();
            if (checkLoaded(serverlevel, new ChunkPos(pos), new ChunkPos(pos.offset(structuretemplate.getSize())))) {
                StructurePlaceSettings structureplacesettings = (new StructurePlaceSettings()).setMirror(mirror).setRotation(rotation);

                boolean flag = structuretemplate.placeInWorld(serverlevel, pos, pos, structureplacesettings, StructureBlockEntity.createRandom(seed), 2);
                if (!flag) {
                    sendMessage(level.getLevel(), "Placement out of whack!");
                    return false;
                } else {
                    return true;
                }
            }
        }

        sendMessage(level.getLevel(), "Template not found!");

        return false;
    }

    @Override
    public boolean place(FeaturePlaceContext<TemplateFeatureConfiguration> featurePlaceContext) {

        Rotation rot = Rotation.getRandom(featurePlaceContext.random());
        Mirror mirror = Mirror.NONE;

        return placeTemplate(featurePlaceContext.level(), ResourceLocation.fromNamespaceAndPath(Farworld.MOD_ID, featurePlaceContext.config().toPlace()), featurePlaceContext.origin(), rot, mirror, featurePlaceContext.level().getSeed());
    }

    public static void sendMessage(ServerLevel level, String str)
    {
        ServerPlayer plr = level.getRandomPlayer();
        plr.sendChatMessage(OutgoingChatMessage.create(PlayerChatMessage.system(str)), false, ChatType.bind(ChatType.SAY_COMMAND, plr));

    }
}
