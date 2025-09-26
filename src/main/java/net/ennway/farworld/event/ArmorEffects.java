package net.ennway.farworld.event;

import net.ennway.farworld.Farworld;
import net.ennway.farworld.item.tool.BreezeBoots;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.monster.breeze.Breeze;
import net.minecraft.world.entity.player.Player;
import net.neoforged.bus.api.Event;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.tick.EntityTickEvent;

@EventBusSubscriber(modid = Farworld.MOD_ID, bus = EventBusSubscriber.Bus.GAME)
public class ArmorEffects {

    @SubscribeEvent
    public static void runBreezeBoots(EntityTickEvent.Post event) {
        if (event.getEntity() instanceof Player player)
        {
            if (player.hasItemInSlot(EquipmentSlot.FEET))
            {
                if (player.getItemBySlot(EquipmentSlot.FEET).getItem() instanceof BreezeBoots)
                {
                    player.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 20));
                }
            }
        }
    }
}
