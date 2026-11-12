package net.ennway.farworld.datagen;

import net.ennway.farworld.Farworld;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.Resource;
import net.minecraft.world.item.DyeColor;
import net.neoforged.neoforge.client.model.generators.BlockModelProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

import java.util.LinkedHashMap;

public class BlockGenerator extends BlockModelProvider {

    public static LinkedHashMap<DyeColor, String> dye_colors = new LinkedHashMap<>();
    static {
        dye_colors.put(DyeColor.WHITE, "white");
        dye_colors.put(DyeColor.LIGHT_GRAY, "light_gray");
        dye_colors.put(DyeColor.GRAY, "gray");
        dye_colors.put(DyeColor.BLACK, "black");
        dye_colors.put(DyeColor.BROWN, "brown");
        dye_colors.put(DyeColor.RED, "red");
        dye_colors.put(DyeColor.ORANGE, "orange");
        dye_colors.put(DyeColor.YELLOW, "yellow");
        dye_colors.put(DyeColor.LIME, "lime");
        dye_colors.put(DyeColor.GREEN, "green");
        dye_colors.put(DyeColor.LIGHT_BLUE, "light_blue");
        dye_colors.put(DyeColor.CYAN, "cyan");
        dye_colors.put(DyeColor.BLUE, "blue");
        dye_colors.put(DyeColor.PURPLE, "purple");
        dye_colors.put(DyeColor.MAGENTA, "magenta");
        dye_colors.put(DyeColor.PINK, "pink");
    }

    public BlockGenerator(PackOutput output, String modid, ExistingFileHelper existingFileHelper) {
        super(output, modid, existingFileHelper);
    }

    private void dyeChargedSleekstone(String name, String col)
    {
        String nm = name + "_" + col;
        this.withExistingParent(nm,
                mcLoc("block/cube_all"))
                .texture("layer0", modLoc(nm));
    }

    @Override
    protected void registerModels() {
        /*
        woodSet("crystallia",
                ResourceLocation.fromNamespaceAndPath(Farworld.MOD_ID, "block/crystallia_planks"),
                ResourceLocation.fromNamespaceAndPath(Farworld.MOD_ID, "block/crystallia_log"),
                ResourceLocation.fromNamespaceAndPath(Farworld.MOD_ID, "block/crystallia_log_top"),
                ResourceLocation.fromNamespaceAndPath(Farworld.MOD_ID, "block/stripped_crystallia_log"),
                ResourceLocation.fromNamespaceAndPath(Farworld.MOD_ID, "block/stripped_crystallia_log_top"),
                ResourceLocation.withDefaultNamespace("entity/signs/crystallia"),
                ResourceLocation.fromNamespaceAndPath(Farworld.MOD_ID, "block/crystallia_door_bottom"),
                ResourceLocation.fromNamespaceAndPath(Farworld.MOD_ID, "block/crystallia_door_top"),
                ResourceLocation.fromNamespaceAndPath(Farworld.MOD_ID, "block/crystallia_trapdoor"));
         */
    }

    public void woodSet(String name,
                        ResourceLocation planks,
                        ResourceLocation log,
                        ResourceLocation logTop,
                        ResourceLocation strippedLog,
                        ResourceLocation strippedLogTop,
                        ResourceLocation sign,
                        ResourceLocation doorBottom,
                        ResourceLocation doorTop,
                        ResourceLocation trapdoor)
    {
        String Name = name + "_";
        cubeColumn(Name+"log", log, logTop);
        cubeColumn(name, log, log);
        cubeColumn("stripped_"+Name+"log", strippedLog, strippedLogTop);
        cubeColumn("stripped_"+name, strippedLog, strippedLog);
        cubeAll(Name+"planks", planks);
        stairs(Name+"stairs", planks, planks, planks);
        stairsInner(Name+"stairs_inner", planks, planks, planks);
        stairsOuter(Name+"stairs_outer", planks, planks, planks);
        slab(Name+"slab", planks, planks, planks);
        slabTop(Name+"slab_top", planks, planks, planks);
        button(Name+"button", planks);
        buttonInventory(Name+"button_inventory", planks);
        buttonPressed(Name+"button_pressed", planks);
        pressurePlate(Name+"pressure_plate", planks);
        pressurePlateDown(Name+"pressure_plate_down", planks);
        fenceGate(Name+"fence_gate", planks);
        fenceGateOpen(Name+"fence_gate_open", planks);
        fenceGateWallOpen(Name+"fence_gate_wall_open", planks);
        fenceGateWall(Name+"fence_gate_wall", planks);
        fencePost(Name+"fence_post", planks);
        fenceInventory(Name+"fence_inventory", planks);
        fenceSide(Name+"fence_side", planks);
        doorBottomLeft(Name+"door_bottom_left", doorBottom, doorTop);
        doorBottomLeftOpen(Name+"door_bottom_left_open", doorBottom, doorTop);
        doorBottomRight(Name+"door_bottom_right", doorBottom, doorTop);
        doorBottomRightOpen(Name+"door_bottom_right_open", doorBottom, doorTop);
        doorTopLeft(Name+"door_top_left", doorBottom, doorTop);
        doorTopLeftOpen(Name+"door_top_left_open", doorBottom, doorTop);
        doorTopRight(Name+"door_top_right", doorBottom, doorTop);
        doorTopRightOpen(Name+"door_top_right_open", doorBottom, doorTop);
        trapdoorBottom(name+"trapdoor_bottom", trapdoor);
        trapdoorTop(Name+"trapdoor_top", trapdoor);
        trapdoorOpen(Name+"trapdoor_open", trapdoor);
        sign(Name+"sign", sign);
    }

    public void genericBlockset(String name, ResourceLocation texture, boolean walls, boolean pressurePlates)
    {
        String Name = name + "_";
        cubeAll(name, texture);
        stairs(Name+"stairs", texture, texture, texture);
        stairsInner(Name+"stairs_inner", texture, texture, texture);
        stairsOuter(Name+"stairs_outer", texture, texture, texture);
        slab(Name+"slab", texture, texture, texture);
        slabTop(Name+"slab_top", texture, texture, texture);

        if (walls)
        {
            wallPost(Name+"wall_post", texture);
            wallInventory(Name+"wall_inventory", texture);
            wallSide(Name+"wall_side", texture);
        }

        if (pressurePlates)
        {
            pressurePlate(Name+"pressure_plate", texture);
            pressurePlateDown(Name+"pressure_plate_down", texture);

            button(Name+"button", texture);
            buttonPressed(Name+"button_pressed", texture);
            buttonInventory(Name+"button_inventory", texture);
        }
    }
}
