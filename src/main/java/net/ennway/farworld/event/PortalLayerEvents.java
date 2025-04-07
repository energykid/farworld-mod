package net.ennway.farworld.event;

import net.ennway.farworld.Farworld;
import net.ennway.farworld.block.BystonePortalBlock;
import net.ennway.farworld.block.dimensiontransitions.DimensionLink;
import net.ennway.farworld.block.dimensiontransitions.FarworldDimensionTransitions;
import net.ennway.farworld.registries.ModAttachments;
import net.ennway.farworld.registries.ModBlocks;
import net.ennway.farworld.registries.ModParticles;
import net.ennway.farworld.utils.MathUtils;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.LayeredDraw;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LanternBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.pattern.BlockInWorld;
import net.minecraft.world.level.block.state.pattern.BlockPattern;
import net.minecraft.world.level.block.state.pattern.BlockPatternBuilder;
import net.minecraft.world.level.block.state.predicate.BlockStatePredicate;
import net.minecraft.world.level.gameevent.GameEvent;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RegisterGuiLayersEvent;
import net.neoforged.neoforge.event.entity.player.UseItemOnBlockEvent;
import org.apache.logging.log4j.core.jmx.Server;
import org.checkerframework.checker.units.qual.Current;

import java.awt.*;

@EventBusSubscriber(modid = Farworld.MOD_ID, bus = EventBusSubscriber.Bus.MOD)
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