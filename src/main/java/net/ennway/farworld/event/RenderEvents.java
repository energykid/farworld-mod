package net.ennway.farworld.event;

import net.ennway.farworld.Farworld;
import net.ennway.farworld.entity.client.PlayerAccessoryLayer;
import net.ennway.farworld.utils.MathUtils;
import net.minecraft.client.Camera;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.LayeredDraw;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.Model;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.player.PlayerRenderer;
import net.minecraft.client.resources.PlayerSkin;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.memory.NearestVisibleLivingEntities;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.AABB;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.neoforge.client.event.RegisterGuiLayersEvent;
import org.joml.Vector2f;

import java.util.List;

@EventBusSubscriber(modid = Farworld.MOD_ID, value = Dist.CLIENT)
public class RenderEvents {

    @SubscribeEvent
    public static void addLayers(EntityRenderersEvent.AddLayers evt)
    {
        for (PlayerSkin.Model skin : evt.getSkins()) {
            EntityRenderer<? extends Player> renderer = evt.getSkin(skin);

            if (renderer instanceof PlayerRenderer livingRenderer) {
                livingRenderer.addLayer(
                        new PlayerAccessoryLayer(livingRenderer, livingRenderer.getModel(), livingRenderer.getModel(), Minecraft.getInstance().getModelManager(), skin == PlayerSkin.Model.SLIM)
                );
            }
        }
    }

    public static LayeredDraw.Layer layer = new LayeredDraw.Layer() {
        @Override
        public void render(GuiGraphics guiGraphics, DeltaTracker deltaTracker) {

            /*
            List<ItemEntity> items = Minecraft.getInstance().level.getEntitiesOfClass(ItemEntity.class,
                    new AABB(
                            Minecraft.getInstance().player.getX() - 12, Minecraft.getInstance().player.getY() - 12, Minecraft.getInstance().player.getZ() - 12,
                            Minecraft.getInstance().player.getX() + 12, Minecraft.getInstance().player.getY() + 12, Minecraft.getInstance().player.getZ() + 12
                    ));

            for (var a : items)
            {
                Camera c = Minecraft.getInstance().getEntityRenderDispatcher().camera;
                Vector2f v = MathUtils.worldToScreen(a.getPosition(c.getPartialTickTime()).toVector3f(), guiGraphics.pose());

                guiGraphics.blit((int)v.x + (guiGraphics.guiWidth() / 2) - 16, (int)v.y + (guiGraphics.guiHeight() / 2) - 16, 0, 32, 32,
                        Minecraft.getInstance().getGuiSprites().getSprite(ResourceLocation.fromNamespaceAndPath(Farworld.MOD_ID, "overlay/redstone_crosshair")),
                        1f, 1f, 1f, 1f);
            }
             */
        }
    };

    @SubscribeEvent
    public static void possiblyCreateWeirdTest(RegisterGuiLayersEvent event)
    {
        ResourceLocation loc = ResourceLocation.tryBuild(Farworld.MOD_ID, "weird_test");

        if (loc != null) {
            event.registerBelowAll(loc, layer);
        }
    }
}
