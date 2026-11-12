package net.ennway.farworld.datagen;

import net.ennway.farworld.Farworld;
import net.ennway.farworld.registries.ModBlocks;
import net.ennway.farworld.registries.ModItems;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.PackType;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.armortrim.TrimMaterial;
import net.minecraft.world.item.armortrim.TrimMaterials;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.client.model.generators.ItemModelBuilder;
import net.neoforged.neoforge.client.model.generators.ItemModelProvider;
import net.neoforged.neoforge.client.model.generators.ModelFile;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredItem;

import java.util.LinkedHashMap;

public class ItemGenerator extends ItemModelProvider {
    private static LinkedHashMap<ResourceKey<TrimMaterial>, Float> trimMaterials = new LinkedHashMap<>();
    static {
        trimMaterials.put(TrimMaterials.QUARTZ, 0.1F);
        trimMaterials.put(TrimMaterials.IRON, 0.2F);
        trimMaterials.put(TrimMaterials.NETHERITE, 0.3F);
        trimMaterials.put(TrimMaterials.REDSTONE, 0.4F);
        trimMaterials.put(TrimMaterials.COPPER, 0.5F);
        trimMaterials.put(TrimMaterials.GOLD, 0.6F);
        trimMaterials.put(TrimMaterials.EMERALD, 0.7F);
        trimMaterials.put(TrimMaterials.DIAMOND, 0.8F);
        trimMaterials.put(TrimMaterials.LAPIS, 0.9F);
        trimMaterials.put(TrimMaterials.AMETHYST, 1.0F);
    }

    private void blockItem(DeferredItem<BlockItem> item, DeferredBlock<Block> block)
    {
        this.withExistingParent(item.getId().getPath(),
                mcLoc(block.getId().getPath()));
    }

    private void gearItem(DeferredItem<Item> itemDeferredItem) {

        this.withExistingParent(itemDeferredItem.getId().getPath(),
                        mcLoc("item/handheld"))
                .texture("layer0", modLoc(itemDeferredItem.getRegisteredName()));
    }

    public ItemGenerator(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, Farworld.MOD_ID, existingFileHelper);
    }

    @Override
    protected void registerModels() {

    }

    // Shoutout to El_Redstoniano for making this
    private void trimmedArmorItem(DeferredItem<ArmorItem> itemDeferredItem) {
        final String MOD_ID = Farworld.MOD_ID; // Change this to your mod id

        if(itemDeferredItem.get() instanceof ArmorItem armorItem) {
            trimMaterials.forEach((trimMaterial, value) -> {
                float trimValue = value;

                String armorType = switch (armorItem.getEquipmentSlot()) {
                    case HEAD -> "helmet";
                    case CHEST -> "chestplate";
                    case LEGS -> "leggings";
                    case FEET -> "boots";
                    default -> "";
                };

                String armorItemPath = armorItem.toString();
                String trimPath = "trims/items/" + armorType + "_trim_" + trimMaterial.location().getPath();
                String currentTrimName = armorItemPath + "_" + trimMaterial.location().getPath() + "_trim";
                ResourceLocation armorItemResLoc = ResourceLocation.parse(armorItemPath);
                ResourceLocation trimResLoc = ResourceLocation.parse(trimPath); // minecraft namespace
                ResourceLocation trimNameResLoc = ResourceLocation.parse(currentTrimName);

                // This is used for making the ExistingFileHelper acknowledge that this texture exist, so this will
                // avoid an IllegalArgumentException
                existingFileHelper.trackGenerated(trimResLoc, PackType.CLIENT_RESOURCES, ".png", "textures");

                // Trimmed armorItem files
                getBuilder(currentTrimName)
                        .parent(new ModelFile.UncheckedModelFile("item/generated"))
                        .texture("layer0", armorItemResLoc.getNamespace() + ":item/" + armorItemResLoc.getPath())
                        .texture("layer1", trimResLoc);

                // Non-trimmed armorItem file (normal variant)
                this.withExistingParent(itemDeferredItem.getId().getPath(),
                                mcLoc("item/generated"))
                        .override()
                        .model(new ModelFile.UncheckedModelFile(trimNameResLoc.getNamespace()  + ":item/" + trimNameResLoc.getPath()))
                        .predicate(mcLoc("trim_type"), trimValue).end()
                        .texture("layer0",
                                ResourceLocation.fromNamespaceAndPath(MOD_ID,
                                        "item/" + itemDeferredItem.getId().getPath()));
            });
        }
    }

    public void basicParentedBlockItem(DeferredBlock<?> block, DeferredBlock<Block> baseBlock, String texture) {
        this.withExistingParent(block.getId().getPath(), mcLoc(texture))
                .texture("texture",  ResourceLocation.fromNamespaceAndPath(Farworld.MOD_ID,
                        "block/" + baseBlock.getId().getPath()));
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