package net.ennway.farworld.item.tool.soul_steel;

import net.ennway.farworld.Farworld;
import net.ennway.farworld.registries.ModItems;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.AddAttributeTooltipsEvent;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;
import net.neoforged.neoforge.event.entity.player.AttackEntityEvent;

@EventBusSubscriber(modid = Farworld.MOD_ID)
public class SoulSteelEffectEvents {

    @SubscribeEvent
    public static void damage(LivingDamageEvent.Pre evt)
    {
        Entity attacker = evt.getEntity().getLastAttacker();
        if (attacker instanceof Player plr)
        {
            if (plr.getWeaponItem().is(ModItems.SOUL_STEEL_SWORD))
            {
                evt.setNewDamage(evt.getNewDamage() + (2 * plr.getAttackStrengthScale(0f)));
            }

            if (plr.getWeaponItem().is(ModItems.SOUL_STEEL_AXE))
            {
                evt.setNewDamage(evt.getOriginalDamage() + (3 * plr.getAttackStrengthScale(0f)));
            }
        }
    }

    @SubscribeEvent
    public static void tooltip(AddAttributeTooltipsEvent evt)
    {
        if (evt.getStack().is(ModItems.SOUL_STEEL_SWORD))
        {
            evt.addTooltipLines(Component.literal( "§a+2 ").append(Component.translatable("extra.farworld.soul_steel_magic_damage")));
        }

        if (evt.getStack().is(ModItems.SOUL_STEEL_AXE))
        {
            evt.addTooltipLines(Component.literal( "§a+3 ").append(Component.translatable("extra.farworld.soul_steel_magic_damage")));
        }
    }
}
