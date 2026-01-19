package net.ennway.farworld.event;

import com.google.common.collect.ImmutableList;
import it.unimi.dsi.fastutil.ints.Int2ObjectArrayMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import net.ennway.farworld.Farworld;
import net.ennway.farworld.registries.ModItems;
import net.ennway.farworld.registries.ModTags;
import net.minecraft.FileUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.main.GameConfig;
import net.minecraft.client.renderer.block.model.ItemModelGenerator;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.renderer.texture.atlas.sources.DirectoryLister;
import net.minecraft.client.resources.IndexedAssetSource;
import net.minecraft.client.resources.TextureAtlasHolder;
import net.minecraft.client.resources.model.Material;
import net.minecraft.client.resources.model.ModelBakery;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.client.resources.model.UnbakedModel;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderSet;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.FileToIdConverter;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.FilePackResources;
import net.minecraft.server.packs.resources.Resource;
import net.minecraft.server.packs.resources.ResourceProvider;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.Event;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.ModList;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.loading.moddiscovery.locators.ModsFolderLocator;
import net.neoforged.neoforge.client.event.ModelEvent;
import net.neoforged.neoforge.client.model.ExtraFaceData;
import net.neoforged.neoforge.client.model.ItemLayerModel;
import net.neoforged.neoforge.client.textures.NamespacedDirectoryLister;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforgespi.language.IModInfo;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@EventBusSubscriber(modid = Farworld.MOD_ID)
public class ExtraModelEvent {

    public static String getExtraModelName(Item item)
    {
        String fullId = item.getDescriptionId();
        int itemModNameLength = "item.farworld".length() + 1;
        String id = fullId.substring(itemModNameLength);
        return id;
    }

    @SubscribeEvent
    public static void addModels(ModelEvent.RegisterAdditional event)
    {
        for (int i = 0; i < ModItems.ITEMS_ALL.getEntries().size(); i++) {

        }
        for (int i = 0; i < ModItems.ALL_ACCESSORY_NAMES.size(); i++) {
            event.register(ModelResourceLocation.standalone(
                    ResourceLocation.fromNamespaceAndPath(Farworld.MOD_ID, "item/overlay/" + ModItems.ALL_ACCESSORY_NAMES.get(i))));
        }
    }
}
