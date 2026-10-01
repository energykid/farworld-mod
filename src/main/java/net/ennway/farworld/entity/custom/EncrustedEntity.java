package net.ennway.farworld.entity.custom;

import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.pathfinder.PathType;
import net.neoforged.neoforge.common.damagesource.DamageContainer;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.animation.AnimatableManager;
import software.bernie.geckolib.animation.AnimationController;
import software.bernie.geckolib.animation.PlayState;
import software.bernie.geckolib.animation.RawAnimation;
import software.bernie.geckolib.constant.DefaultAnimations;
import software.bernie.geckolib.util.GeckoLibUtil;

public class EncrustedEntity extends Monster implements GeoEntity {

    public float walkAnimationScale = 0F;

    public float auraScale = 0F;
    public float auraTimer = 0F;

    public final AnimationState idleAnimationState = new AnimationState();
    public final AnimationState walkAnimationState = new AnimationState();
    public final AnimationState hurtAnimationState = new AnimationState();

    public EncrustedEntity(EntityType<? extends Monster> entityType, Level level) {
        super(entityType, level);
        this.setPathfindingMalus(PathType.POWDER_SNOW, -1.0F);
        this.setPathfindingMalus(PathType.DANGER_POWDER_SNOW, -1.0F);
        this.setPathfindingMalus(PathType.WATER, -1.5F);
    }

    public double getEyeY() {
        return this.getPosition(0).y + 1.5D;
    }

    public final TargetingConditions targeting = TargetingConditions.forCombat().range(4.0).ignoreLineOfSight();

    public class EncrustedAttackGoal extends MeleeAttackGoal
    {
        public EncrustedAttackGoal(PathfinderMob mob, double speedModifier, boolean followingTargetEvenIfNotSeen) {
            super(mob, speedModifier, followingTargetEvenIfNotSeen);
        }

        @Override
        protected void checkAndPerformAttack(LivingEntity target) {
            if (this.canPerformAttack(target)) {
                this.resetAttackCooldown();
                if (this.mob instanceof EncrustedEntity entity)
                {
                    entity.triggerAnim("attack_controller", "attack");
                }
                this.mob.doHurtTarget(target);
            }
        }
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();

        this.goalSelector.addGoal(8, new LookAtPlayerGoal(this, Player.class, 8.0F));
        this.goalSelector.addGoal(8, new RandomLookAroundGoal(this));
        this.goalSelector.addGoal(2, new EncrustedAttackGoal(this, 1.0, false));
        this.goalSelector.addGoal(7, new WaterAvoidingRandomStrollGoal(this, 1.0));
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal(this, Player.class, true));
    }

    @Override
    public void onDamageTaken(DamageContainer damageContainer) {
        super.onDamageTaken(damageContainer);
    }

    @Override
    public void tick() {
        if (this.level().isClientSide) {
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
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        super.defineSynchedData(builder);
    }

    private void setupAnimationStates()
    {
        this.idleAnimationState.startIfStopped(this.tickCount);
        this.walkAnimationState.startIfStopped(this.tickCount);
    }

    @Override
    protected @Nullable SoundEvent getHurtSound(@NotNull DamageSource damageSource) {
        this.hurtAnimationState.start(this.tickCount);
        return SoundEvents.SKELETON_HURT;
    }

    @Override
    protected @Nullable SoundEvent getDeathSound() {
        return SoundEvents.SKELETON_DEATH;
    }

    public static AttributeSupplier.Builder createAttributes()
    {
        return Mob.createLivingAttributes()
                .add(Attributes.MAX_HEALTH, 14D)
                .add(Attributes.FOLLOW_RANGE, 16D)
                .add(Attributes.ATTACK_DAMAGE, 5)
                .add(Attributes.MOVEMENT_SPEED, 0.18D);
    }

    @Override
    public boolean doHurtTarget(Entity entity) {
        boolean flag = super.doHurtTarget(entity);

        if (level() instanceof ServerLevel)
            triggerAnim("attack_controller", "attack");

        return flag;
    }


    protected static final RawAnimation ATTACK_ANIM = RawAnimation.begin().thenPlay("attack");

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
        controllers.add(DefaultAnimations.genericWalkIdleController(this));

        controllers.add(new AnimationController<>(this, "attack_controller", animTest -> PlayState.STOP)
                .triggerableAnim("attack", ATTACK_ANIM));
    }

    private final AnimatableInstanceCache geoCache = GeckoLibUtil.createInstanceCache(this);
    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return geoCache;
    }
}
