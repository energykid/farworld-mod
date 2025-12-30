package net.ennway.farworld.entity.custom;

import net.ennway.farworld.entity.base.DelayedAttackingMonster;
import net.ennway.farworld.entity.control.GoliathMoveControl;
import net.ennway.farworld.entity.control.SlowRotMoveControl;
import net.ennway.farworld.entity.goal.DelayedMeleeHurtGoal;
import net.ennway.farworld.registries.ModSounds;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
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
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.pathfinder.PathType;
import net.neoforged.neoforge.common.damagesource.DamageContainer;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class AmethystConstructEntity extends DelayedAttackingMonster {

    public float walkAnimationScale = 0F;

    public final AnimationState idleAnimationState = new AnimationState();
    public final AnimationState walkAnimationState = new AnimationState();
    public final AnimationState attackAnimationState = new AnimationState();
    public final AnimationState eatAnimationState = new AnimationState();
    public final AnimationState eatSpitAnimationState = new AnimationState();

    public AmethystConstructEntity(EntityType<? extends DelayedAttackingMonster> entityType, Level level) {
        super(entityType, level);
        this.setPathfindingMalus(PathType.POWDER_SNOW, -1.0F);
        this.setPathfindingMalus(PathType.DANGER_POWDER_SNOW, -1.0F);
        this.setPathfindingMalus(PathType.WATER, -1.5F);
        this.moveControl = new SlowRotMoveControl(this);
    }

    public double getEyeY() {
        return this.getPosition(0).y + 2D;
    }

    public final TargetingConditions targeting = TargetingConditions.forCombat().range(4.0).ignoreLineOfSight();

    static class AmethystGolemDelayedHurtGoal extends DelayedMeleeHurtGoal
    {
        public AmethystGolemDelayedHurtGoal(PathfinderMob entity, double speedModifier, boolean followingTargetEvenIfNotSeen) {
            super(entity, speedModifier, followingTargetEvenIfNotSeen);
            this.attackDelay = 16;
            this.attackLength = 30;
        }



        @Override
        public void onBeginAttack(LivingEntity target) {
            this.mob.playSound(ModSounds.AMETHYST_CONSTRUCT_WINDUP.get());
        }

        @Override
        public void onImpactAttack(LivingEntity target) {
            this.mob.playSound(ModSounds.AMETHYST_CONSTRUCT_SMASH.get());
        }
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();

        this.goalSelector.addGoal(1, new AmethystGolemDelayedHurtGoal(this, 1.0, false));
        this.goalSelector.addGoal(2, new WaterAvoidingRandomStrollGoal(this, 1.0));
        this.goalSelector.addGoal(3, new LookAtPlayerGoal(this, Player.class, 8.0F));
        this.goalSelector.addGoal(4, new RandomLookAroundGoal(this));
        this.targetSelector.addGoal(1, new HurtByTargetGoal(this));
    }

    @Override
    public void onDamageTaken(DamageContainer damageContainer) {
        super.onDamageTaken(damageContainer);
    }

    @Override
    public void tick() {
        super.tick();

        if (this.level().isClientSide) {
            setupAnimationStates();

            if (getEntityData().get(DelayedAttackingMonster.ATTACK_TICKS) == 1)
            {
                this.attackAnimationState.start(this.tickCount);
            }
        }
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        super.defineSynchedData(builder.define(DelayedAttackingMonster.ATTACK_TICKS, 0));
    }

    private void setupAnimationStates()
    {
        this.idleAnimationState.startIfStopped(this.tickCount);
        this.walkAnimationState.startIfStopped(this.tickCount);
    }

    @Override
    protected @Nullable SoundEvent getAmbientSound() {
        return ModSounds.AMETHYST_CONSTRUCT_IDLE.get();
    }

    @Override
    protected @Nullable SoundEvent getHurtSound(@NotNull DamageSource damageSource) {
        return ModSounds.AMETHYST_CONSTRUCT_HURT.get();
    }

    @Override
    protected InteractionResult mobInteract(Player player, InteractionHand hand) {
        return super.mobInteract(player, hand);
    }

    @Override
    protected @Nullable SoundEvent getDeathSound() {
        return ModSounds.AMETHYST_CONSTRUCT_DEATH.get();
    }

    public static AttributeSupplier.Builder createAttributes()
    {
        return Mob.createLivingAttributes()
                .add(Attributes.MAX_HEALTH, 14D)
                .add(Attributes.FOLLOW_RANGE, 10D)
                .add(Attributes.ATTACK_DAMAGE, 7)
                .add(Attributes.MOVEMENT_SPEED, 0.15D);
    }
}
