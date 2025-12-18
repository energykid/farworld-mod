package net.ennway.farworld.entity.custom;

import net.ennway.farworld.entity.goal.GoliathAttackPlayerGoal;
import net.ennway.farworld.entity.goal.GoliathMeleeHurtGoal;
import net.ennway.farworld.registries.ModItems;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.behavior.Mount;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.pathfinder.PathType;
import net.minecraft.world.phys.Vec2;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.common.damagesource.DamageContainer;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

public class GoliathEntity extends TamableAnimal implements OwnableEntity, PlayerRideable, Saddleable {

    public float walkAnimationScale = 0F;
    public float walkAnimationSpeed = 1F;

    public final AnimationState idleAnimationState = new AnimationState();
    public final AnimationState walkAnimationState = new AnimationState();
    public final AnimationState attackAnimationState = new AnimationState();
    public final AnimationState petAnimationState = new AnimationState();

    @Override
    public void baseTick() {
        super.baseTick();
    }

    public GoliathEntity(EntityType<? extends TamableAnimal> entityType, Level level) {
        super(entityType, level);
        this.setTame(false, false);
        this.setPathfindingMalus(PathType.POWDER_SNOW, -1.0F);
        this.setPathfindingMalus(PathType.DANGER_POWDER_SNOW, -1.0F);
        this.setPathfindingMalus(PathType.WATER, -1.5F);
    }

    public double getEyeY() {
        return this.getPosition(0).y + 2D;
    }

    public final TargetingConditions targeting = TargetingConditions.forCombat().range(4.0).ignoreLineOfSight();

    @Override
    protected void registerGoals() {
        super.registerGoals();
        this.targetSelector.addGoal(1, new GoliathAttackPlayerGoal<>(this, Player.class, true));
        this.goalSelector.addGoal(1, new GoliathMeleeHurtGoal(this, 1.5, true));
        this.goalSelector.addGoal(2, new TemptGoal(this, 1.0F, this::isFood, false));
        this.goalSelector.addGoal(3, new FloatGoal(this));
        this.goalSelector.addGoal(4, new FollowOwnerGoal(this, 1.0F, 6f, 100f));
        this.goalSelector.addGoal(5, new LookAtPlayerGoal(this, Player.class, 8.0F));
        this.goalSelector.addGoal(6, new RandomLookAroundGoal(this));
        this.goalSelector.addGoal(7, new RandomStrollGoal(this, 1.0));
    }

    @Override
    public boolean shouldTryTeleportToOwner() {
        return false;
    }

    @Override
    public InteractionResult interactAt(Player player, Vec3 vec, InteractionHand hand) {
        if (!isTame() && isFood(player.getItemInHand(hand)))
        {
            this.setTame(true, true);
            this.setOwnerUUID(player.getUUID());
            return InteractionResult.SUCCESS;
        }
        else if (isTame() && player.getItemInHand(hand).isEmpty() && player.isCrouching() && getEntityData().get(PET_TICKS) < -10)
        {
            petTheBuddyYippee();
            return InteractionResult.SUCCESS;
        }
        else if (isTame() && isSaddled())
        {
            doPlayerRide(player);
            return InteractionResult.PASS;
        }
        return super.interactAt(player, vec, hand);
    }



    @Override
    public void onDamageTaken(DamageContainer damageContainer) {
        super.onDamageTaken(damageContainer);
    }

    @Override
    public @Nullable LivingEntity getControllingPassenger() {

        if (this.isSaddled()) {
            Entity entity = this.getFirstPassenger();
            if (entity instanceof Player) {
                return (Player) entity;
            }
        }

        return super.getControllingPassenger();
    }

    @Override
    protected void tickRidden(Player player, Vec3 travelVector) {
        super.tickRidden(player, travelVector);

        if (this.horizontalCollision) setDeltaMovement(getDeltaMovement().x, 0.2f, getDeltaMovement().z);

        Vec2 vec2 = this.getRiddenRotation(player);
        this.setRot(vec2.y, vec2.x);
        this.yRotO = this.yBodyRot = this.yHeadRot = this.getYRot();
    }

    protected Vec2 getRiddenRotation(LivingEntity entity) {
        return new Vec2(entity.getXRot() * 0.5F, entity.getYRot());
    }

    protected float getRiddenSpeed(Player player) {
        return (float)this.getAttributeValue(Attributes.MOVEMENT_SPEED);
    }

    @Override
    public void addAdditionalSaveData(CompoundTag compound) {
        super.addAdditionalSaveData(compound);
        compound.putBoolean("saddled", this.isSaddled());
    }

    @Override
    public void readAdditionalSaveData(CompoundTag compound) {
        super.readAdditionalSaveData(compound);
        this.getEntityData().set(SADDLED, compound.getBoolean("saddled"));
    }

    @Override
    protected Vec3 getRiddenInput(Player player, Vec3 travelVector) {
        float f = player.xxa * 0.5F;
        float f1 = player.zza;
        if (f1 <= 0.0F) {
            f1 *= 0.25F;
        }

        return new Vec3((double)f, 0f, (double)f1);
    }

    public boolean shouldAttack(Player playerAt)
    {
        return !(isTame() || isFood((playerAt.getItemInHand(InteractionHand.MAIN_HAND))) || isFood((playerAt.getItemInHand(InteractionHand.OFF_HAND))));
    }

    public void petTheBuddyYippee()
    {
        ParticleOptions particleoptions = ParticleTypes.HEART;

        for(int i = 0; i < 7; ++i) {
            double d0 = this.random.nextGaussian() * 0.02;
            double d1 = this.random.nextGaussian() * 0.02;
            double d2 = this.random.nextGaussian() * 0.02;
            this.level().addParticle(particleoptions, this.getRandomX(1.0), this.getRandomY() + 0.5, this.getRandomZ(1.0), d0, d1, d2);
        }

        getEntityData().set(PET_TICKS, 40);

        petAnimationState.start(this.tickCount);
    }

    @Override
    protected void positionRider(Entity passenger, MoveFunction callback) {
        super.positionRider(passenger, callback);
    }

    @Override
    protected Vec3 getPassengerAttachmentPoint(Entity entity, EntityDimensions dimensions, float partialTick) {
        return super.getPassengerAttachmentPoint(entity, dimensions, partialTick).add((new Vec3(0.0, -0.25, 0.0)));
    }

    @Override
    public void tick() {

        getEntityData().set(ATTACK_TICKS, getEntityData().get(ATTACK_TICKS) + 1);

        getEntityData().set(PET_TICKS, getEntityData().get(PET_TICKS) - 1);

        if (this.level().isClientSide) {
            if (this.getEntityData().get(ATTACK_TICKS) == 1)
            {
                this.attackAnimationState.start(this.tickCount);
            }
            if (this.getDeltaMovement().x != 0 || this.getDeltaMovement().z != 0)
                walkAnimationScale = Mth.lerp(0.25f, walkAnimationScale, 1f);
            else
                walkAnimationScale = Mth.lerp(0.5f, walkAnimationScale, 0f);
        }

        super.tick();

        if (this.level().isClientSide) {
            setupAnimationStates();
        }
    }

    @Override
    public @Nullable AgeableMob getBreedOffspring(ServerLevel serverLevel, AgeableMob ageableMob) {
        return null;
    }

    private void setupAnimationStates()
    {
        this.idleAnimationState.startIfStopped(this.tickCount);
        this.walkAnimationState.startIfStopped(this.tickCount);
    }

    @Override
    protected @Nullable SoundEvent getHurtSound(@NotNull DamageSource damageSource) {
        return SoundEvents.SPIDER_HURT;
    }

    @Override
    protected @Nullable SoundEvent getDeathSound() {
        return SoundEvents.SPIDER_DEATH;
    }

    protected void doPlayerRide(Player player) {
        if (!this.level().isClientSide) {
            player.setYRot(this.getYRot());
            player.setXRot(this.getXRot());
            player.startRiding(this);
        }
    }

    public static AttributeSupplier.Builder createAttributes()
    {
        return Mob.createLivingAttributes()
                .add(Attributes.MAX_HEALTH, 80D)
                .add(Attributes.FOLLOW_RANGE, 12D)
                .add(Attributes.ATTACK_DAMAGE, 14)
                .add(Attributes.MOVEMENT_SPEED, 0.25D);
    }

    public static final EntityDataAccessor<Boolean> SADDLED = SynchedEntityData.defineId(GoliathEntity.class, EntityDataSerializers.BOOLEAN);
    public static final EntityDataAccessor<Boolean> TAMED = SynchedEntityData.defineId(GoliathEntity.class, EntityDataSerializers.BOOLEAN);
    public static final EntityDataAccessor<Integer> PET_TICKS = SynchedEntityData.defineId(GoliathEntity.class, EntityDataSerializers.INT);
    public static final EntityDataAccessor<Integer> ATTACK_TICKS = SynchedEntityData.defineId(GoliathEntity.class, EntityDataSerializers.INT);

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        super.defineSynchedData(builder
                .define(SADDLED, false)
                .define(TAMED, false)
                .define(PET_TICKS, -1)
                .define(ATTACK_TICKS, 50));
    }

    @Override
    public boolean doHurtTarget(Entity entity) {
        this.attackAnimationState.start(this.tickCount);
        return super.doHurtTarget(entity);
    }

    @Override
    public boolean isFood(ItemStack itemStack) {
        return itemStack.is(ModItems.GLOOMSPORES) || itemStack.is(ModItems.GLOOMCAP);
    }

    @Override
    protected void playStepSound(BlockPos pos, BlockState state) {
        level().playSound(this, pos, SoundEvents.SPIDER_STEP, SoundSource.NEUTRAL, 0.5f, 1f);
    }

    @Override
    public boolean isSaddleable() {
        return isTame();
    }

    @Override
    public void equipSaddle(ItemStack itemStack, @Nullable SoundSource soundSource) {
        getEntityData().set(SADDLED, true);
        playSound(SoundEvents.HORSE_SADDLE);
    }

    @Override
    public boolean isSaddled() {
        return getEntityData().get(SADDLED);
    }
}
