package net.ennway.farworld.utils;

import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.entity.StructureBlockEntity;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructurePlaceSettings;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplateManager;

import java.util.Optional;

import static net.ennway.farworld.feature.SimpleTemplateFeature.checkLoaded;

public class WorldUtils {
    public static boolean placeTemplate(ServerLevel level, ResourceLocation template, BlockPos pos, Rotation rotation, Mirror mirror, long seed) {
        StructureTemplateManager structuretemplatemanager = level.getStructureManager();

        Optional optional = structuretemplatemanager.get(template);

        if (optional.isPresent())
        {
            StructureTemplate structuretemplate = (StructureTemplate) optional.get();
            if (checkLoaded(level, new ChunkPos(pos), new ChunkPos(pos.offset(structuretemplate.getSize())))) {
                StructurePlaceSettings structureplacesettings = (new StructurePlaceSettings()).setMirror(mirror).setRotation(rotation);

                return structuretemplate.placeInWorld(level, pos, pos, structureplacesettings, StructureBlockEntity.createRandom(seed), 2);
            }
        }

        return false;
    }

    public static boolean placeTemplateFloorCenteredXZ(ServerLevel level, ResourceLocation template, BlockPos pos, Rotation rotation, Mirror mirror, long seed) {
        StructureTemplateManager structuretemplatemanager = level.getStructureManager();

        Optional optional = structuretemplatemanager.get(template);

        if (optional.isPresent())
        {
            StructureTemplate structuretemplate = (StructureTemplate) optional.get();

            if (checkLoaded(level, new ChunkPos(pos), new ChunkPos(pos.offset(structuretemplate.getSize())))) {
                BlockPos.MutableBlockPos p = pos.mutable();
                p.move(-structuretemplate.getSize().getX() / 2, -1, -structuretemplate.getSize().getZ() / 2);

                StructurePlaceSettings structureplacesettings = (new StructurePlaceSettings()).setMirror(mirror).setRotation(rotation);

                return structuretemplate.placeInWorld(level, p, p, structureplacesettings, StructureBlockEntity.createRandom(seed), 2);
            }
        }

        return false;
    }
}
