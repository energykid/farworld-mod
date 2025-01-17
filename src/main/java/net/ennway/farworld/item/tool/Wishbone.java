package net.ennway.farworld.item.tool;

import net.ennway.farworld.Farworld;
import net.ennway.farworld.particle.WishboneSparkleParticleProvider;
import net.ennway.farworld.registries.ModDataComponents;
import net.ennway.farworld.registries.ModParticles;
import net.ennway.farworld.registries.ModSounds;
import net.minecraft.BlockUtil;
import net.minecraft.core.GlobalPos;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderSet;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.DimensionTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.TagKey;
import net.minecraft.util.Mth;
import net.minecraft.util.ParticleUtils;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.ThrownEnderpearl;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.item.component.CustomModelData;
import net.minecraft.world.item.component.LodestoneTracker;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.dimension.DimensionType;
import net.minecraft.world.phys.Vec3;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.neoforge.client.event.RenderTooltipEvent;
import net.neoforged.neoforge.event.entity.RegisterSpawnPlacementsEvent;
import org.jetbrains.annotations.NotNull;
import org.jline.utils.Log;

import java.awt.*;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.logging.Logger;

public class Wishbone extends Item {

    public static final TagKey<Enchantment> MENDING = TagKey.create(
            Registries.ENCHANTMENT,
            ResourceLocation.fromNamespaceAndPath(Farworld.MOD_ID, "mending"));

    public static final TagKey<Enchantment> ATTUNEMENT = TagKey.create(
            Registries.ENCHANTMENT,
            ResourceLocation.fromNamespaceAndPath(Farworld.MOD_ID, "attunement"));

    @Override
    public void inventoryTick(ItemStack stack, Level level, Entity entity, int slotId, boolean isSelected) {
        if (entity instanceof Player)
        {
            if (stack.get(ModDataComponents.FRAME.get()) != null)
            {
                if (stack.get(ModDataComponents.FRAME.get()) > 0)
                {
                    for (int i = 0; i < 12; i++) {
                        level.addParticle(ModParticles.WISHBONE_SPARKLE.get(), entity.position().x, entity.position().y + (level.getRandom().nextDouble() * 2), entity.position().z, 0f, 0f, 0f);
                    }

                    level.addParticle(ModParticles.WISHBONE_PORTAL.get(), entity.position().x, entity.position().y + 1, entity.position().z, 0f, 0f, 0f);

                    stack.set(ModDataComponents.FRAME, 0);
                }
            }

            if (getDamage(stack) <= 0)
            {
                stack.set(DataComponents.CUSTOM_MODEL_DATA, new CustomModelData(1));
            }
        }
    }

    @Override
    public int getMaxDamage(ItemStack stack) {
        return 200;
    }

    @Override
    public int getBarColor(ItemStack stack) {
        return 0x6BD9FF;
    }

    @Override
    public boolean isBarVisible(ItemStack stack) {
        return stack.get(DataComponents.CUSTOM_MODEL_DATA).value() == 2 && EnchantmentHelper.hasTag(stack, MENDING);
    }

    public Wishbone() {
        super(new Properties()
                .stacksTo(1)
                .component(DataComponents.CUSTOM_MODEL_DATA, new CustomModelData(1))
                .component(ModDataComponents.FRAME, 0));
    }

    @Override
    public int getUseDuration(ItemStack stack, LivingEntity entity) {
        if (Objects.equals(stack.get(DataComponents.CUSTOM_MODEL_DATA), new CustomModelData(2))) {
            return super.getUseDuration(stack, entity);
        }
        return 30;
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
        super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);

        if (stack.get(DataComponents.CUSTOM_MODEL_DATA) != null)
        {
            if (Objects.requireNonNull(stack.get(DataComponents.CUSTOM_MODEL_DATA)).value() == 2) {
                for (int i = 0; i < tooltipComponents.size(); i++) {
                    Component tooltipComponent = tooltipComponents.get(i);

                    if (tooltipComponent.getString().equals(Component.translatable("item.farworld.wishbone").getString())) {
                        tooltipComponents.set(i, Component.translatable("item.farworld.consumed_wishbone"));
                    }
                }
            }
        }
    }

    @Override
    public @NotNull UseAnim getUseAnimation(@NotNull ItemStack stack) {
        return UseAnim.NONE;
    }

    @Override
    public ItemStack finishUsingItem(ItemStack stack, Level level, LivingEntity livingEntity) {

        if (!Objects.equals(stack.get(DataComponents.CUSTOM_MODEL_DATA), new CustomModelData(2))) {
            if (level.isClientSide) {
                level.playSound(livingEntity, livingEntity.blockPosition(), ModSounds.WISHBONE_SHATTER.get(), SoundSource.PLAYERS, 1f, 1f);
            }
            Vec3 pos1 = livingEntity.getPosition(0f);

            for (int i = 0; i < 12; i++) {
                level.addParticle(ModParticles.WISHBONE_SPARKLE.get(), pos1.x, pos1.y + (level.getRandom().nextDouble() * 2), pos1.z, 0f, 0f, 0f);
            }
            level.addParticle(ModParticles.WISHBONE_PORTAL.get(), pos1.x, pos1.y + 1, pos1.z, 0f, 0f, 0f);

            if (livingEntity instanceof Player player) {
                if (player instanceof ServerPlayer servPlayer) {
                    boolean foundLodestone = false;

                    if (stack.get(DataComponents.LODESTONE_TRACKER) != null)
                    {
                        LodestoneTracker tracker = stack.get(DataComponents.LODESTONE_TRACKER);

                        GlobalPos globalPos = tracker.target().get();

                        if (level.getBlockState(tracker.target().get().pos()).is(Blocks.LODESTONE))
                        {
                            servPlayer.teleportTo(
                                    level.getServer().getLevel(globalPos.dimension()),
                                    globalPos.pos().getX() + 0.5, globalPos.pos().getY() + 1, globalPos.pos().getZ() + 0.5,
                                    0f, 0f
                            );
                            foundLodestone = true;
                        }
                    }

                    if (!foundLodestone)
                    {
                        if (servPlayer.getRespawnPosition() != null)
                        {
                            if (level.getServer().getLevel(servPlayer.getRespawnDimension()) != null)
                            {
                                servPlayer.teleportTo(
                                        level.getServer().getLevel(servPlayer.getRespawnDimension()),
                                        servPlayer.getRespawnPosition().getX() + 0.5, servPlayer.getRespawnPosition().getY() + 1, servPlayer.getRespawnPosition().getZ() + 0.5,
                                        0f, 0f
                                );
                            }
                        }
                        else {
                            servPlayer.teleportTo(
                                    level.getServer().overworld(),
                                    level.getSharedSpawnPos().getX() + 0.5, level.getSharedSpawnPos().getY() + 1, level.getSharedSpawnPos().getZ() + 0.5,
                                    livingEntity.getXRot(), livingEntity.getYHeadRot()
                            );
                        }
                    }
                }
            }

            stack.set(DataComponents.CUSTOM_MODEL_DATA, new CustomModelData(2));
            stack.set(ModDataComponents.FRAME, 1);
            setDamage(stack, getMaxDamage(stack) - 1);

            return stack;
        }
        return super.finishUsingItem(stack, level, livingEntity);
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        if (EnchantmentHelper.hasTag(context.getItemInHand(), ATTUNEMENT)) {
            if (context.getLevel().getBlockState(context.getClickedPos()).is(Blocks.LODESTONE)) {
                context.getItemInHand().set(DataComponents.LODESTONE_TRACKER, new LodestoneTracker(Optional.of(GlobalPos.of(context.getLevel().dimension(), context.getClickedPos())), true));
                context.getPlayer().playSound(SoundEvents.LODESTONE_COMPASS_LOCK);
            }
        }
        return super.useOn(context);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand usedHand) {

        if (!Objects.equals(player.getItemInHand(usedHand).get(DataComponents.CUSTOM_MODEL_DATA), new CustomModelData(2)))
        {
            player.startUsingItem(usedHand);
            if (level.isClientSide) {
                level.playSound(player, player.blockPosition(), ModSounds.WISHBONE_CRACK.get(), SoundSource.PLAYERS, 1f, 1f);
            }
            return InteractionResultHolder.success(player.getUseItem());
        }
        return super.use(level, player, usedHand);
    }
}
