package net.ennway.farworld.event;

import net.ennway.farworld.Farworld;
import net.ennway.farworld.item.AccessoryItem;
import net.ennway.farworld.utils.AccessoryUtils;
import net.minecraft.core.component.DataComponents;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.BundleContents;
import net.minecraft.world.item.component.ItemAttributeModifiers;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.ItemAttributeModifierEvent;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;
import net.neoforged.neoforge.event.entity.living.LivingHealEvent;
import net.neoforged.neoforge.event.tick.EntityTickEvent;

import java.util.List;

@EventBusSubscriber(modid = Farworld.MOD_ID, bus = EventBusSubscriber.Bus.GAME)
public class AccessoryEvents {

    @SubscribeEvent
    public static void onEntityHurt(LivingDamageEvent.Pre event)
    {
        if (event.getEntity() instanceof Player player)
        {
            if (event.getSource().getEntity() != null)
            {
                List<AccessoryItem> items = AccessoryUtils.getPlayerAccessories(player);
                List<ItemStack> itemStacks = AccessoryUtils.getPlayerAccessoryStacks(player);

                for (int i = 0; i < items.size(); i++)
                {
                    items.get(i).onDamagedByEnemy(event.getSource().getEntity(), player, itemStacks.get(i), event);
                }
            }
        }

        if (event.getEntity() instanceof Mob mob)
        {
            if (event.getSource().getEntity() instanceof Player player)
            {
                List<AccessoryItem> items = AccessoryUtils.getPlayerAccessories(player);
                List<ItemStack> itemStacks = AccessoryUtils.getPlayerAccessoryStacks(player);

                for (int i = 0; i < items.size(); i++)
                {
                    items.get(i).onDamageEnemy(player, mob, itemStacks.get(i), event);
                }
            }
        }
    }

    @SubscribeEvent
    public static void postTick(EntityTickEvent.Post event)
    {
        if (event.getEntity() instanceof Player player)
        {
            List<AccessoryItem> items = AccessoryUtils.getPlayerAccessories(player);
            List<ItemStack> itemStacks = AccessoryUtils.getPlayerAccessoryStacks(player);

            for (int i = 0; i < items.size(); i++)
            {
                items.get(i).postTick(player, itemStacks.get(i), event);
            }
        }
    }

    @SubscribeEvent
    public static void preTick(EntityTickEvent.Pre event)
    {
        if (event.getEntity() instanceof Player player)
        {
            List<AccessoryItem> items = AccessoryUtils.getPlayerAccessories(player);
            List<ItemStack> itemStacks = AccessoryUtils.getPlayerAccessoryStacks(player);

            for (int i = 0; i < items.size(); i++)
            {
                items.get(i).preTick(player, itemStacks.get(i), event);
            }
        }
    }

    @SubscribeEvent
    public static void modifyAttributes(ItemAttributeModifierEvent event)
    {
        ItemStack stack = event.getItemStack();
        if ((stack.is(ItemTags.HEAD_ARMOR) || stack.is(ItemTags.CHEST_ARMOR) || stack.is(ItemTags.LEG_ARMOR) || stack.is(ItemTags.FOOT_ARMOR)))
        {
            BundleContents contents = stack.get(DataComponents.BUNDLE_CONTENTS);
            if (contents != null)
            {
                for (ItemStack accStack : contents.items())
                {
                    for (ItemAttributeModifiers.Entry entry : accStack.getAttributeModifiers().modifiers()) {
                        event.addModifier(
                            entry.attribute(),
                            entry.modifier(),
                            EquipmentSlotGroup.ANY
                        );
                    }
                }
            }
        }
    }
}