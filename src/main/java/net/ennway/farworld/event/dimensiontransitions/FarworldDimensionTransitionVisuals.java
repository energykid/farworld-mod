package net.ennway.farworld.event.dimensiontransitions;

import net.ennway.farworld.Farworld;
import net.ennway.farworld.event.PortalLayerEvents;
import net.ennway.farworld.registries.ModAttachments;
import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.tick.EntityTickEvent;

@EventBusSubscriber(modid = Farworld.MOD_ID, value = Dist.CLIENT)
public class FarworldDimensionTransitionVisuals {


    @SubscribeEvent
    public static void runDimensionTransitions(EntityTickEvent.Post event) {
        Entity entity = event.getEntity();

        // dimension transitions for players
        if (entity instanceof Player player) {
            if (!player.isPassenger())
            {
                boolean shouldBeMoving = false;
                BlockPos blockPos = BlockPos.containing(entity.getEyePosition());
                BlockState currentBlockState = entity.level().getBlockState(blockPos);
                for (int i = 0; i < AllDimensionLinks.links.length; i++) {
                    DimensionLink link = AllDimensionLinks.links[i];
                    if (currentBlockState.is(link.portalBlock)) {
                        PortalLayerEvents.transitionOpacity = Mth.lerp(0.03f, PortalLayerEvents.transitionOpacity, 0.85f);
                        PortalLayerEvents.transitionResource = link.resourceLocation;
                        shouldBeMoving = true;
                    }
                }
                if (!shouldBeMoving) {
                    PortalLayerEvents.transitionOpacity = Mth.lerp(0.06f, PortalLayerEvents.transitionOpacity, 0f);
                }
            }
        }
    }
}
