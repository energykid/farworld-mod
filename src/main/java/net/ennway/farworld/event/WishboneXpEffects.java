package net.ennway.farworld.event;

import net.ennway.farworld.Farworld;
import net.ennway.farworld.item.tool.Wishbone;
import net.ennway.farworld.registries.ModDataComponents;
import net.ennway.farworld.registries.ModItems;
import net.ennway.farworld.registries.ModParticles;
import net.ennway.farworld.utils.MathUtils;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.phys.Vec3;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.player.AttackEntityEvent;
import net.neoforged.neoforge.event.entity.player.PlayerXpEvent;

import java.util.function.Supplier;

@EventBusSubscriber(modid = Farworld.MOD_ID, bus = EventBusSubscriber.Bus.GAME)
public class WishboneXpEffects {

    @SubscribeEvent
    public static void GainXp(PlayerXpEvent.PickupXp event)
    {
        if (event.getEntity() instanceof Player player)
        {
            for (int i = 0; i < player.getInventory().getContainerSize(); i++) {
                if (player.getInventory().getItem(i).is(ModItems.WISHBONE.get()))
                {
                    ItemStack stack = player.getInventory().getItem(i);

                    if (EnchantmentHelper.hasTag(stack, Wishbone.MENDING))
                    {
                        stack.setDamageValue(stack.getDamageValue() - event.getOrb().getValue());
                    }
                }
            }
        }
    }
}
