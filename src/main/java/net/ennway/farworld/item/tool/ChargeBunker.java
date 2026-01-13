package net.ennway.farworld.item.tool;

import net.ennway.farworld.Farworld;
import net.ennway.farworld.entity.base.BaseSubattackEntity;
import net.ennway.farworld.entity.custom.redstone_curiosity.RedstoneCuriosityBlastEntity;
import net.ennway.farworld.item.rendering.ChargeBunkerRenderer;
import net.ennway.farworld.registries.ModDataComponents;
import net.ennway.farworld.registries.ModEntities;
import net.ennway.farworld.registries.ModItems;
import net.ennway.farworld.registries.ModSounds;
import net.minecraft.client.Camera;
import net.minecraft.client.CameraType;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.core.Direction;
import net.minecraft.core.component.DataComponentMap;
import net.minecraft.core.component.DataComponents;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import software.bernie.geckolib.animatable.GeoAnimatable;
import software.bernie.geckolib.animatable.GeoItem;
import software.bernie.geckolib.animatable.SingletonGeoAnimatable;
import software.bernie.geckolib.animatable.client.GeoRenderProvider;
import software.bernie.geckolib.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.animation.*;
import software.bernie.geckolib.constant.DataTickets;
import software.bernie.geckolib.util.ClientUtil;
import software.bernie.geckolib.util.GeckoLibUtil;

import java.util.function.Consumer;

public class ChargeBunker extends Item implements GeoItem {
    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);

    public ChargeBunker(Properties properties) {
        super(properties);
        SingletonGeoAnimatable.registerSyncedAnimatable(this);
    }

    @Override
    public DataComponentMap components() {
        return DataComponentMap.composite(super.components(), DataComponentMap.builder().set(ModDataComponents.COOLDOWN, 0).build());
    }

    @Override
    public boolean isValidRepairItem(ItemStack stack, ItemStack repairCandidate) {
        return stack.is(ModItems.CURIOUS_COMPONENT) || stack.is(Items.REDSTONE_BLOCK);
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
        controllers.add(new AnimationController(this, "FireAnim", 0, state -> {
            final ItemDisplayContext context = (ItemDisplayContext) state.getData(DataTickets.ITEM_RENDER_PERSPECTIVE);

            if (context.firstPerson())
                return PlayState.CONTINUE;

            return PlayState.STOP;
        }).receiveTriggeredAnimations().triggerableAnim("shoot", RawAnimation.begin().thenPlay("fire")));
    }

    @Override
    public boolean isPerspectiveAware() {
        return true;
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return this.cache;
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {

        if (!player.getCooldowns().isOnCooldown(this))
        {
            damageItem(player.getItemInHand(hand), 1, player, a -> {
            });

            player.playSound(ModSounds.CHARGE_BUNKER_FIRE.get());

            if (level instanceof ServerLevel serverLevel)
            {
                triggerAnim(player, GeoItem.getOrAssignId(player.getItemInHand(hand), serverLevel), "FireAnim", "shoot");
            }

            player.getCooldowns().addCooldown(this, 90);

            RedstoneCuriosityBlastEntity ent = new RedstoneCuriosityBlastEntity(ModEntities.REDSTONE_CURIOSITY_BLAST.get(), level);
            ent.owner = player;
            ent.damage = 10;
            ent.setPos(new Vec3(player.getX(), player.getEyeY() - 0.5, player.getZ()));
            ent.getEntityData().set(BaseSubattackEntity.ROTATION, -player.yHeadRot);
            ent.getEntityData().set(BaseSubattackEntity.PITCH, (float)player.getLookAngle().y);
            ent.customFinderVector = player.getLookAngle().multiply(0.5, 0.5, 0.5);
            level.addFreshEntity(ent);

            player.setDeltaMovement(player.getDeltaMovement().add(player.getLookAngle().multiply(-0.8, -0.8, -0.8)));
        }

        return super.use(level, player, hand);
    }

    @Override
    public void createGeoRenderer(Consumer<GeoRenderProvider> consumer) {
        consumer.accept(new GeoRenderProvider() {
            private ChargeBunkerRenderer renderer;

            @Override
            public BlockEntityWithoutLevelRenderer getGeoItemRenderer() {
                if (this.renderer == null)
                    this.renderer = new ChargeBunkerRenderer();

                return this.renderer;
            }
        });
    }
}
