package net.ennway.farworld.event;

import net.ennway.farworld.Farworld;
import net.ennway.farworld.item.AccessoryItem;
import net.ennway.farworld.item.data.ArmorAccessories;
import net.ennway.farworld.registries.ModDataComponents;
import net.ennway.farworld.utils.AccessoryUtils;
import net.ennway.farworld.utils.ServerUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.core.component.DataComponents;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
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
import net.neoforged.bus.api.Event;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RenderItemInFrameEvent;
import net.neoforged.neoforge.event.ItemAttributeModifierEvent;
import net.neoforged.neoforge.event.entity.item.ItemEvent;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;
import net.neoforged.neoforge.event.entity.living.LivingEntityUseItemEvent;
import net.neoforged.neoforge.event.entity.living.LivingHealEvent;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent;
import net.neoforged.neoforge.event.tick.EntityTickEvent;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;
import net.neoforged.neoforge.event.tick.ServerTickEvent;

import java.util.List;

@EventBusSubscriber(modid = Farworld.MOD_ID)
public class AccessoryEvents {

    @SubscribeEvent
    public static void onEntityHurt(LivingDamageEvent.Pre event) {
        if (event.getEntity() instanceof Player player) {
            if (event.getSource().getEntity() != null) {
                List<AccessoryItem> items = AccessoryUtils.getPlayerAccessories(player);
                List<ItemStack> itemStacks = AccessoryUtils.getPlayerAccessoryStacks(player);

                for (int i = 0; i < items.size(); i++) {
                    items.get(i).onDamagedByEnemy(event.getSource().getEntity(), player, itemStacks.get(i), event);
                }
            }
        }

        if (event.getEntity() instanceof Mob mob) {
            if (event.getSource().getEntity() instanceof Player player) {
                List<AccessoryItem> items = AccessoryUtils.getPlayerAccessories(player);
                List<ItemStack> itemStacks = AccessoryUtils.getPlayerAccessoryStacks(player);

                for (int i = 0; i < items.size(); i++) {
                    items.get(i).onDamageEnemy(player, mob, itemStacks.get(i), event);
                }
            }
        }
    }

    @SubscribeEvent
    public static void onEntityHurt(LivingDamageEvent.Post event) {
        if (event.getEntity() instanceof Mob mob) {
            if (event.getSource().getEntity() instanceof Player player) {
                List<AccessoryItem> items = AccessoryUtils.getPlayerAccessories(player);
                List<ItemStack> itemStacks = AccessoryUtils.getPlayerAccessoryStacks(player);

                for (int i = 0; i < items.size(); i++) {
                    items.get(i).postDamageEnemy(player, mob, itemStacks.get(i), event);
                }
            }
        }
    }

    @SubscribeEvent
    public static void postTick(EntityTickEvent.Post event) {
        if (event.getEntity() instanceof Player player) {
            List<AccessoryItem> items = AccessoryUtils.getPlayerAccessories(player);
            List<ItemStack> itemStacks = AccessoryUtils.getPlayerAccessoryStacks(player);

            for (int i = 0; i < items.size(); i++) {
                items.get(i).postTick(player, itemStacks.get(i), event);


                if (Minecraft.getInstance().getSingleplayerServer() != null) {
                    items.get(i).postTickServer(player, itemStacks.get(i), Minecraft.getInstance().getSingleplayerServer().getLevel(player.level().dimension()));
                }
            }
        }
    }

    @SubscribeEvent
    public static void postTick(ServerTickEvent.Post event) {
        for (ServerPlayer player : event.getServer().getPlayerList().getPlayers()) {

            List<AccessoryItem> items = AccessoryUtils.getPlayerAccessories(player);
            List<ItemStack> itemStacks = AccessoryUtils.getPlayerAccessoryStacks(player);

            for (int i = 0; i < items.size(); i++) {
                items.get(i).postTickServer(player, itemStacks.get(i), event.getServer().getLevel(player.level().dimension()));
            }
        }
    }

    @SubscribeEvent
    public static void preTick(EntityTickEvent.Pre event) {
        if (event.getEntity() instanceof Player player) {
            List<AccessoryItem> items = AccessoryUtils.getPlayerAccessories(player);
            List<ItemStack> itemStacks = AccessoryUtils.getPlayerAccessoryStacks(player);

            for (int i = 0; i < items.size(); i++) {
                items.get(i).preTick(player, itemStacks.get(i), event);

                if (Minecraft.getInstance().getSingleplayerServer() != null) {
                    items.get(i).preTickServer(player, itemStacks.get(i), Minecraft.getInstance().getSingleplayerServer().getLevel(player.level().dimension()));
                }
            }
        }
    }

    @SubscribeEvent
    public static void preTick(ServerTickEvent.Pre event) {
        for (ServerPlayer player : event.getServer().getPlayerList().getPlayers()) {

            List<AccessoryItem> items = AccessoryUtils.getPlayerAccessories(player);
            List<ItemStack> itemStacks = AccessoryUtils.getPlayerAccessoryStacks(player);

            for (int i = 0; i < items.size(); i++) {
                items.get(i).preTickServer(player, itemStacks.get(i), event.getServer().getLevel(player.level().dimension()));
            }
        }
    }

    public static void runRightClickEffects(PlayerInteractEvent event) {
        List<AccessoryItem> items = AccessoryUtils.getPlayerAccessories(event.getEntity());
        List<ItemStack> itemStacks = AccessoryUtils.getPlayerAccessoryStacks(event.getEntity());

        for (int i = 0; i < items.size(); i++) {
            items.get(i).onRightClickUseItem(event.getEntity(), itemStacks.get(i), event);
        }
    }

    public static void runLeftClickEffects(PlayerInteractEvent event) {
        List<AccessoryItem> items = AccessoryUtils.getPlayerAccessories(event.getEntity());
        List<ItemStack> itemStacks = AccessoryUtils.getPlayerAccessoryStacks(event.getEntity());

        for (int i = 0; i < items.size(); i++) {
            items.get(i).onLeftClickUseItem(event.getEntity(), itemStacks.get(i), event);
        }
    }

    @SubscribeEvent
    public static void rightClick(PlayerInteractEvent.RightClickEmpty event) {
        List<AccessoryItem> items = AccessoryUtils.getPlayerAccessories(event.getEntity());
        List<ItemStack> itemStacks = AccessoryUtils.getPlayerAccessoryStacks(event.getEntity());

        for (int i = 0; i < items.size(); i++) {
            items.get(i).onRightClickUseItem(event.getEntity(), itemStacks.get(i), event);
        }
    }

    @SubscribeEvent
    public static void rightClick(PlayerInteractEvent.RightClickBlock event) {
        List<AccessoryItem> items = AccessoryUtils.getPlayerAccessories(event.getEntity());
        List<ItemStack> itemStacks = AccessoryUtils.getPlayerAccessoryStacks(event.getEntity());

        for (int i = 0; i < items.size(); i++) {
            items.get(i).onRightClickUseItem(event.getEntity(), itemStacks.get(i), event);
        }
    }

    @SubscribeEvent
    public static void rightClick(PlayerInteractEvent.RightClickItem event) {
        List<AccessoryItem> items = AccessoryUtils.getPlayerAccessories(event.getEntity());
        List<ItemStack> itemStacks = AccessoryUtils.getPlayerAccessoryStacks(event.getEntity());

        for (int i = 0; i < items.size(); i++) {
            items.get(i).onRightClickUseItem(event.getEntity(), itemStacks.get(i), event);
        }
    }

    @SubscribeEvent
    public static void leftClick(PlayerInteractEvent.LeftClickEmpty event) {
        runLeftClickEffects(event);
    }

    @SubscribeEvent
    public static void leftClick(PlayerInteractEvent.LeftClickBlock event) {
        runLeftClickEffects(event);
    }

    @SubscribeEvent
    public static void modifyAttributes(ItemAttributeModifierEvent event) {
        ItemStack stack = event.getItemStack();
        if ((stack.is(ItemTags.HEAD_ARMOR) || stack.is(ItemTags.CHEST_ARMOR) || stack.is(ItemTags.LEG_ARMOR) || stack.is(ItemTags.FOOT_ARMOR))) {
            ArmorAccessories contents = stack.get(ModDataComponents.ARMOR_ACCESSORIES);
            if (contents != null) {
                if (contents.isEmpty()) {
                    stack.set(ModDataComponents.HAS_ACCESSORY, false);
                } else {
                    stack.set(ModDataComponents.HAS_ACCESSORY, true);
                }

                for (ItemStack accStack : contents.items()) {
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