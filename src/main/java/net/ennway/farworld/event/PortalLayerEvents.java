package net.ennway.farworld.event;

import net.ennway.farworld.Farworld;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.LayeredDraw;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RegisterGuiLayersEvent;

@EventBusSubscriber(modid = Farworld.MOD_ID)
public class PortalLayerEvents {

    public static float transitionOpacity = 0f;
    public static String transitionResource = "";

    public static LayeredDraw.Layer layer = new LayeredDraw.Layer() {
        @Override
        public void render(GuiGraphics guiGraphics, DeltaTracker deltaTracker) {
            guiGraphics.blit(0, 0, 0, guiGraphics.guiWidth(), guiGraphics.guiHeight(),
                    Minecraft.getInstance().getGuiSprites().getSprite(ResourceLocation.fromNamespaceAndPath(Farworld.MOD_ID, transitionResource)),
                    1f, 1f, 1f, transitionOpacity);
        }
    };

    @SubscribeEvent
    public static void createPortalEffect(RegisterGuiLayersEvent event)
    {
        ResourceLocation loc = ResourceLocation.tryBuild(Farworld.MOD_ID, "all_portals_overlay");

        if (loc != null) {
            event.registerBelowAll(loc, layer);
        }
    }
}