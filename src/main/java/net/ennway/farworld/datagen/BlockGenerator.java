package net.ennway.farworld.datagen;

import net.minecraft.data.PackOutput;
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
        dye_colors.forEach((a, name) -> {
            dyeChargedSleekstone("block/charged_sleekstone", name);
        });
    }
}
