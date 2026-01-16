package net.ennway.farworld.item.accessory;

import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.ennway.farworld.Farworld;
import net.ennway.farworld.entity.custom.redstone_curiosity.RedstoneCuriosityLaserEntity;
import net.ennway.farworld.entity.projectile.ApocalypseBreathProjectile;
import net.ennway.farworld.item.AccessoryItem;
import net.ennway.farworld.registries.*;
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
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.players.ServerOpList;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
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
import net.neoforged.neoforge.event.tick.ServerTickEvent;
import org.jetbrains.annotations.Nullable;

import java.util.Set;
import java.util.UUID;

public class ApocalypseCore extends AccessoryItem {

    public ApocalypseCore(Properties properties) {
        super(properties.rarity(Rarity.RARE));
    }

    @Override
    public void postTick(Player player, ItemStack stack, EntityTickEvent.Post event) {
        if (player.getData(ModAttachments.APOCALYPSE_ABILITY) > 170) {
            Vec3 shootVel = player.getLookAngle();
            if (player.getData(ModAttachments.APOCALYPSE_ABILITY) % 2 == 1)
                player.level().addParticle(ModParticles.APOCALYPSE_ROAR.get(), player.getX() + (shootVel.x * 0.6), player.getEyePosition().y + (shootVel.y * 0.6), player.getZ() + (shootVel.z * 0.6), 0, 0, 0);
        }
    }

    @Override
    public void postTickServer(Player player, ItemStack stack, ServerLevel level) {
        if (player.getData(ModAttachments.APOCALYPSE_ABILITY) > 170) {
            Vec3 shootVel = player.getLookAngle();

            if (player.getDeltaMovement().y < 0)
                player.setDeltaMovement(player.getDeltaMovement().multiply(1, 0.7, 1));

            if (player.getData(ModAttachments.APOCALYPSE_ABILITY) % 2 == 1)
                level.addParticle(ModParticles.APOCALYPSE_ROAR.get(), player.getX() + (shootVel.x * 0.6), player.getEyePosition().y + (shootVel.y * 0.6), player.getZ() + (shootVel.z * 0.6), 0, 0, 0);

            level.getPlayerByUUID(player.getUUID()).resetFallDistance();

            ApocalypseBreathProjectile ent = new ApocalypseBreathProjectile(ModEntities.APOCALYPSE_BREATH.get(), level);
            ent.setPos(player.getEyePosition().add(0, -0.4, 0));
            ent.setOwner(player);
            ent.setDeltaMovement(shootVel);
            level.addFreshEntity(ent);
        }
    }

    @Override
    public void onRightClickUseItem(Player player, ItemStack stack, PlayerInteractEvent evt) {
        if (evt instanceof PlayerInteractEvent.RightClickBlock block)
        {
            if (block.getUseItem().isTrue()) {
                if (evt.getItemStack().isEmpty() && player.getData(ModAttachments.APOCALYPSE_ABILITY) <= 0 && AccessoryUtils.playerHasAccessory(player, ModItems.APOCALYPSE_CORE.get())) {
                    player.setData(ModAttachments.APOCALYPSE_ABILITY, 200f);
                    player.playSound(ModSounds.APOCALYPSE_FIRE_BREATH.get());
                    player.swing(InteractionHand.MAIN_HAND);
                }
            }
        }
        else
        {
            if (evt.getItemStack().isEmpty() && player.getData(ModAttachments.APOCALYPSE_ABILITY) <= 0 && AccessoryUtils.playerHasAccessory(player, ModItems.APOCALYPSE_CORE.get())) {
                player.setData(ModAttachments.APOCALYPSE_ABILITY, 200f);
                player.playSound(ModSounds.APOCALYPSE_FIRE_BREATH.get());
                player.swing(InteractionHand.MAIN_HAND);
            }
        }
    }
}
