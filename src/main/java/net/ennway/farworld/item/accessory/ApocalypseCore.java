package net.ennway.farworld.item.accessory;

import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.ennway.farworld.Farworld;
import net.ennway.farworld.entity.custom.redstone_curiosity.RedstoneCuriosityLaserEntity;
import net.ennway.farworld.entity.projectile.ApocalypseBreathProjectile;
import net.ennway.farworld.item.AccessoryItem;
import net.ennway.farworld.registries.ModAttachments;
import net.ennway.farworld.registries.ModDataComponents;
import net.ennway.farworld.registries.ModEntities;
import net.ennway.farworld.registries.ModItems;
import net.ennway.farworld.utils.AccessoryUtils;
import net.ennway.farworld.utils.MathUtils;
import net.ennway.farworld.utils.ServerUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponentMap;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.component.DataComponents;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.ClickEvent;
import net.minecraft.server.commands.SummonCommand;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.players.ServerOpList;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.entity.projectile.WitherSkull;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.enchantment.effects.SummonEntityEffect;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.neoforged.bus.api.Event;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.InputEvent;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent;
import net.neoforged.neoforge.event.tick.EntityTickEvent;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;
import org.jetbrains.annotations.Nullable;

import java.util.Set;

public class ApocalypseCore extends AccessoryItem {

    public ApocalypseCore(Properties properties) {
        super(properties.rarity(Rarity.RARE));
    }

    @Override
    public void postTick(Player player, ItemStack stack, EntityTickEvent.Post event) {
        if (AccessoryUtils.playerHasAccessory(player, ModItems.APOCALYPSE_CORE.get())) {
            if (player.getData(ModAttachments.APOCALYPSE_ABILITY) > 170) {// && player.getData(ModAttachments.APOCALYPSE_ABILITY) % 2 == 1) {
                Vec3 shootVel = player.getLookAngle();

                ApocalypseBreathProjectile ent = new ApocalypseBreathProjectile(ModEntities.APOCALYPSE_BREATH.get(), ServerUtils.getServerLevelFromPlayer(player));
                ent.setPos(player.getEyePosition().add(0, -0.4, 0));
                ent.setOwner(player);
                ent.setDeltaMovement(shootVel);
                ServerUtils.getServerLevelFromPlayer(player).addFreshEntity(ent);
            }
        }
    }

    @Override
    public void onRightClickUseItem(Player player, ItemStack stack, PlayerInteractEvent evt) {
        if (evt instanceof PlayerInteractEvent.RightClickEmpty)
        {
            if (evt.getItemStack().isEmpty() && player.getData(ModAttachments.APOCALYPSE_ABILITY) <= 0 && AccessoryUtils.playerHasAccessory(player, ModItems.APOCALYPSE_CORE.get()))
            {
                player.setData(ModAttachments.APOCALYPSE_ABILITY, 200f);
                player.playSound(SoundEvents.ENDER_DRAGON_SHOOT, 0.5f, 1);
                player.playSound(SoundEvents.ENDER_DRAGON_AMBIENT, 0.3f, 2);
            }
        }
        else if (evt instanceof PlayerInteractEvent.RightClickBlock block)
        {
            if (block.getUseItem().isDefault())
            {
                if (evt.getItemStack().isEmpty() && player.getData(ModAttachments.APOCALYPSE_ABILITY) <= 0 && AccessoryUtils.playerHasAccessory(player, ModItems.APOCALYPSE_CORE.get())) {
                    player.setData(ModAttachments.APOCALYPSE_ABILITY, 200f);
                    player.playSound(SoundEvents.ENDER_DRAGON_SHOOT, 0.5f, 1);
                    player.playSound(SoundEvents.ENDER_DRAGON_AMBIENT, 0.3f, 2);
                }
            }
        }
    }
}
