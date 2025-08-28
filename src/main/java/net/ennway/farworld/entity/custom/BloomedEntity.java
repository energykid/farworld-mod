package net.ennway.farworld.entity.custom;

import net.ennway.farworld.Farworld;
import net.ennway.farworld.registries.ModEntities;
import net.ennway.farworld.registries.ModSounds;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.chat.ChatListener;
import net.minecraft.client.multiplayer.chat.ChatLog;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.PlayerChatMessage;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.Main;
import net.minecraft.server.commands.ScoreboardCommand;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.EntityTypeTags;
import net.minecraft.tags.TagKey;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.animal.ShoulderRidingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.monster.Endermite;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.entity.EntityTypeTest;
import net.minecraft.world.level.pathfinder.PathType;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.scores.PlayerTeam;
import net.minecraft.world.scores.Scoreboard;
import net.minecraft.world.scores.ScoreboardSaveData;
import net.minecraft.world.scores.Team;
import net.neoforged.neoforge.common.Tags;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;
import java.util.function.Consumer;

public class BloomedEntity extends TamableAnimal {

    private static final EntityDataAccessor<String> FLOWER_TO_GROW = SynchedEntityData.defineId(BloomedEntity.class, EntityDataSerializers.STRING);
    private static final EntityDataAccessor<String> FLOWER_TO_DISPLAY = SynchedEntityData.defineId(BloomedEntity.class, EntityDataSerializers.STRING);

    public String flowerType;

    public static final TagKey<Item> BLOOMED_COMPATIBLE = TagKey.create(
            BuiltInRegistries.ITEM.key(),
            ResourceLocation.fromNamespaceAndPath(Farworld.MOD_ID, "bloomed_compatible"));

    public BloomedEntity(EntityType<? extends TamableAnimal> entityType, Level level) {
        super(entityType, level);
        this.setTame(false, false);
        this.setPathfindingMalus(PathType.POWDER_SNOW, -1.0F);
        this.setPathfindingMalus(PathType.DANGER_POWDER_SNOW, -1.0F);
        this.setPathfindingMalus(PathType.WATER, -1.5F);
        if (this.flowerType == null)
        {
            if (!this.getEntityData().get(FLOWER_TO_DISPLAY).isEmpty())
            {
                this.flowerType = this.getEntityData().get(FLOWER_TO_DISPLAY);
            }
            else {
                this.flowerType = "poppy";
            }
        }
    }

    public boolean isArthur()
    {
        if (this.hasCustomName()){
            return Objects.requireNonNull(this.getCustomName()).getString().equals("Arthur");
        }

        return false;
    }

    @Override
    public void addAdditionalSaveData(@NotNull CompoundTag compound) {
        compound.putString("FlowerToGrow", this.getEntityData().get(FLOWER_TO_GROW));
        compound.putString("FlowerToDisplay", this.getEntityData().get(FLOWER_TO_DISPLAY));
        super.addAdditionalSaveData(compound);
    }

    @Override
    public void readAdditionalSaveData(@NotNull CompoundTag compound) {
        super.readAdditionalSaveData(compound);
        this.getEntityData().set(FLOWER_TO_GROW, compound.getString("FlowerToGrow"));
        this.getEntityData().set(FLOWER_TO_DISPLAY, compound.getString("FlowerToDisplay"));

        this.flowerType = this.getEntityData().get(FLOWER_TO_DISPLAY);
    }

    @Override
    public void tick() {

        // Animation scale logic

        if (this.level().isClientSide) {
            if (this.getDeltaMovement().x != 0 || this.getDeltaMovement().z != 0)
                walkAnimationScale = Mth.lerp(0.25f, walkAnimationScale, 1f);
            else
                walkAnimationScale = Mth.lerp(0.5f, walkAnimationScale, 0f);

            tamedAnimationScale = Mth.lerp(0.1f, tamedAnimationScale, 0f);
        }

        // Super

        super.tick();

        // Flower types

        this.flowerType = this.getEntityData().get(FLOWER_TO_DISPLAY);

        if (this.level().isClientSide) {
            setupAnimationStates();
        }

        Vec3 pos = this.position();

        double length = 15d;
    }

    @Override
    public @NotNull InteractionResult mobInteract(@NotNull Player player, @NotNull InteractionHand hand) {

        if (!this.isArthur()) {
            ItemStack stack = player.getItemInHand(hand);

            if (this.isFood(stack))
            {
                this.setOwnerUUID(player.getUUID());
            }

            if (!Objects.equals(this.getEntityData().get(FLOWER_TO_DISPLAY), "air")) {
                if (stack.is(Items.SHEARS)) {

                    // If there is a flower on tail, only perform shear actions
                    stack.hurtAndBreak(1, player, player.getEquipmentSlotForItem(stack));

                    this.level().playSound(this, this.blockPosition(), SoundEvents.SHEEP_SHEAR,
                            SoundSource.NEUTRAL, 1f, 1f);

                    this.level().addFreshEntity(new ItemEntity(this.level(),
                            this.position().x,
                            this.position().y,
                            this.position().z,
                            new ItemStack(BuiltInRegistries.ITEM.get(ResourceLocation.withDefaultNamespace(
                                    this.getEntityData().get(FLOWER_TO_DISPLAY)
                            )))));

                    this.getEntityData().set(FLOWER_TO_DISPLAY, "air");

                }
            } else {

                // If there is NO flower on tail, perform either bone meal or flower planting actions

                if (stack.is(Items.BONE_MEAL))
                {
                    this.level().playSound(this, this.blockPosition(), SoundEvents.BONE_MEAL_USE,
                                SoundSource.NEUTRAL, 1f, 1f);

                    player.getItemInHand(hand).consume(1, player);

                    this.getEntityData().set(FLOWER_TO_DISPLAY, this.getEntityData().get(FLOWER_TO_GROW));

                    this.tamedAnimationState.start(this.tickCount);
                    this.tamedAnimationScale = 1f;
                }

                if (stack.is(BLOOMED_COMPATIBLE))
                {
                    this.level().playSound(this, this.blockPosition(), SoundEvents.BONE_MEAL_USE,
                            SoundSource.NEUTRAL, 1f, 1f);

                    player.getItemInHand(hand).consume(1, player);

                    String string = stack.getItem().toString();
                    int indexTo = string.indexOf(":") + 1;
                    this.getEntityData().set(FLOWER_TO_DISPLAY, string.substring(indexTo));
                    this.getEntityData().set(FLOWER_TO_GROW, string.substring(indexTo));

                    this.tamedAnimationState.start(this.tickCount);
                    this.tamedAnimationScale = 1f;
                }
            }

            this.flowerType = this.getEntityData().get(FLOWER_TO_DISPLAY);
        }

        return super.mobInteract(player, hand);
    }

    private void setupAnimationStates()
    {
        if (this.idleAnimationTimeout <= 0)
        {
            this.idleAnimationTimeout = this.random.nextInt(40) + 80;
            this.idleAnimationState.start(this.tickCount);
            this.walkAnimationState.start(this.tickCount);
        }
        else --this.idleAnimationTimeout;
    }

    public final AnimationState idleAnimationState = new AnimationState();
    public final AnimationState walkAnimationState = new AnimationState();
    public final AnimationState tamedAnimationState = new AnimationState();
    public float walkAnimationScale = 0F;
    public float tamedAnimationScale = 0F;
    private int idleAnimationTimeout = 0;

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new SitWhenOrderedToGoal(this));
        this.goalSelector.addGoal(1, new FloatGoal(this));
        this.goalSelector.addGoal(2, new AvoidEntityGoal<>(this, Endermite.class, 4F, 1.3F, 1.4F));
        this.goalSelector.addGoal(3, new TemptGoal(this, 1.0F, this::isFood, false));
        this.goalSelector.addGoal(4, new FollowOwnerGoal(this, 1.0F, 6f, 20f));
        this.goalSelector.addGoal(5, new PanicGoal(this, 1.0F));
        this.goalSelector.addGoal(6, new WaterAvoidingRandomStrollGoal(this, 1.0));
        this.goalSelector.addGoal(7, new RandomLookAroundGoal(this));
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {

        var flower = "poppy";

        super.defineSynchedData(builder
                .define(FLOWER_TO_GROW, flower)
                .define(FLOWER_TO_DISPLAY, flower));

        this.flowerType = flower;
    }

    @Override
    protected @Nullable SoundEvent getAmbientSound() {
        return ModSounds.BLOOMED_HURT.get();
    }

    @Override
    protected @Nullable SoundEvent getHurtSound(@NotNull DamageSource damageSource) {
        return ModSounds.BLOOMED_HURT.get();
    }

    @Override
    protected void playStepSound(BlockPos pos, BlockState state) {
        level().playSound(this, pos, SoundEvents.SPIDER_STEP, SoundSource.NEUTRAL, 0.5f, 1f);
    }

    @Override
    protected @Nullable SoundEvent getDeathSound() {
        return ModSounds.BLOOMED_DEATH.get();
    }

    public static AttributeSupplier.Builder createAttributes()
    {
        return ShoulderRidingEntity.createLivingAttributes()
                .add(Attributes.MAX_HEALTH, 6D)
                .add(Attributes.FOLLOW_RANGE, 24D)
                .add(Attributes.MOVEMENT_SPEED, 0.35D);
    }

    @Override
    public boolean isFood(ItemStack itemStack) {
        return itemStack.is(Items.POTATO);
    }

    @Override
    public @Nullable AgeableMob getBreedOffspring(@NotNull ServerLevel serverLevel, @NotNull AgeableMob ageableMob) {
        return null;
    }
}
