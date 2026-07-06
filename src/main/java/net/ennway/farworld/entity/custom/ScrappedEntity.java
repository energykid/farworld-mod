package net.ennway.farworld.entity.custom;

import io.netty.buffer.ByteBuf;
import net.ennway.farworld.Farworld;
import io.netty.buffer.ByteBuf;
import net.ennway.farworld.Farworld;
import net.ennway.farworld.entity.base.BaseSubattackEntity;
import net.ennway.farworld.registries.ModAttachments;
import net.ennway.farworld.utils.BehaviorUtils;
import net.ennway.farworld.utils.BossMusicHandling;
import net.ennway.farworld.utils.MathUtils;
import net.ennway.farworld.utils.ServerUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.ai.behavior.Behavior;
import net.minecraft.world.entity.ai.goal.target.TargetGoal;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec2;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.handling.DirectionalPayloadHandler;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;
import org.apache.logging.log4j.core.jmx.Server;
import net.ennway.farworld.network.Payloads;
import net.ennway.farworld.registries.ModEntities;
import net.ennway.farworld.registries.ModSounds;
import net.ennway.farworld.utils.BehaviorUtils;
import net.ennway.farworld.utils.BossMusicHandling;
import net.ennway.farworld.utils.MathUtils;
import net.ennway.farworld.utils.ServerUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.ResourceLocation;
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
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.pathfinder.PathType;
import net.minecraft.world.phys.Vec3;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.common.damagesource.DamageContainer;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.handling.DirectionalPayloadHandler;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.joml.Vector3f;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.animation.AnimatableManager;
import software.bernie.geckolib.animation.AnimationController;
import software.bernie.geckolib.animation.PlayState;
import software.bernie.geckolib.animation.RawAnimation;
import software.bernie.geckolib.constant.DefaultAnimations;
import software.bernie.geckolib.util.GeckoLibUtil;

import java.util.UUID;

public class ScrappedEntity extends Monster implements GeoEntity {

    public float walkAnimationScale = 0F;

    public float headRotation = 0f;
    public float headRotationLerp = 0f;

    int attackTimer = 0;
    String attackState = "none";

    Vec3 finderPos = Vec3.ZERO;

    public final AnimationState idleAnimationState = new AnimationState();
    public final AnimationState walkAnimationState = new AnimationState();
    public final AnimationState hurtAnimationState = new AnimationState();

    public ScrappedEntity(EntityType<? extends Monster> entityType, Level level) {
        super(entityType, level);
        this.setPathfindingMalus(PathType.POWDER_SNOW, -1.0F);
        this.setPathfindingMalus(PathType.DANGER_POWDER_SNOW, -1.0F);
        this.setPathfindingMalus(PathType.WATER, -1.5F);
    }

    @Override
    protected @Nullable SoundEvent getAmbientSound() {
        return ModSounds.SCRAPPED_IDLE.get();
    }

    @Override
    protected void playStepSound(BlockPos pos, BlockState state) {
        playSound(SoundEvents.ZOMBIE_STEP);
    }

    public double getEyeY() {
        return this.getPosition(0).y + 1.5D;
    }

    @Override
    public float getSpeed() {
        if (attackState == "laser") return 0f;
        return super.getSpeed();
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();

        this.goalSelector.addGoal(1, new LookAtPlayerGoal(this, Player.class, 15.0F));
        this.goalSelector.addGoal(2, new WaterAvoidingRandomStrollGoal(this, 1.0));
        this.goalSelector.addGoal(0, new MeleeAttackGoal(this, 1, false));
        this.targetSelector.addGoal(0, new NearestAttackableTargetGoal(this, Player.class, true, false));
    }

    @Override
    public void onDamageTaken(DamageContainer damageContainer) {
        super.onDamageTaken(damageContainer);
    }

    @Override
    public void aiStep() {
        super.aiStep();
    }

    @Override
    public void tick() {
        super.tick();
        float rot = 0f;

        target = BehaviorUtils.getNearestPlayer(this, 14d);

        if (target != null && level().isClientSide)
        {
            float r2 = (float)MathUtils.entityLookAngle(target.position().subtract(position()));

            if (Mth.degreesDifference(getYRot(), r2) < 70) {
                rot = Mth.degreesDifference(getYRot(), r2);
            }
        }

        headRotation = Mth.lerp(0.4f, headRotation, rot);
    }

    Player target = null;

    @Override
    protected void customServerAiStep() {

        if (!isDeadOrDying()) {
            if (attackState == "none")
                    target = BehaviorUtils.getNearestPlayer(this, 14d);

            if (target != null) {

                attackTimer++;

                if (attackTimer < 10) {
                    finderPos = target.position();
                }

                if (attackState == "none") {
                    if (attackTimer > 40) {
                        attackState = "laser";
                        attackTimer = 0;

                        playSound(ModSounds.SCRAPPED_WINDUP.get());

                        if (level() instanceof ServerLevel)
                            triggerAnim("attack_controller", "laser");
                    }
                }
                if (attackState == "laser") {
                    double r = MathUtils.entityLookAngle(target.position().subtract(position()));
                    absRotateTo((float)r, 0);

                    if (attackTimer == 20) {

                        Vec3 p = finderPos.subtract(position()).normalize();

                        ScrappedLaserEntity ent = new ScrappedLaserEntity(ModEntities.SCRAPPED_LASER.get(), level(), getEyePosition().add(0f, 0.1f, 0f), p);

                        ent.owner = this;

                        ent.getEntityData().set(ScrappedLaserEntity.DIR_X, (float) p.x);
                        ent.getEntityData().set(ScrappedLaserEntity.DIR_Y, (float) p.y);
                        ent.getEntityData().set(ScrappedLaserEntity.DIR_Z, (float) p.z);

                        playSound(ModSounds.SCRAPPED_BEAM.get());

                        if (level() != null) {
                            level().addFreshEntity(ent);
                        }
                    }
                    if (attackTimer > 23) {
                        attackState = "none";
                        attackTimer = 0;
                    }
                }
            } else {
                attackTimer = 0;
            }
        }

        super.customServerAiStep();
    }

    private void setupAnimationStates()
    {
        this.idleAnimationState.startIfStopped(this.tickCount);
        this.walkAnimationState.startIfStopped(this.tickCount);
    }

    @Override
    protected @Nullable SoundEvent getHurtSound(@NotNull DamageSource damageSource) {
        this.hurtAnimationState.start(this.tickCount);
        return ModSounds.SCRAPPED_HURT.get();
    }

    @Override
    protected @Nullable SoundEvent getDeathSound() {
        return ModSounds.SCRAPPED_KILL.get();
    }

    public static AttributeSupplier.Builder createAttributes()
    {
        return Mob.createLivingAttributes()
                .add(Attributes.MAX_HEALTH, 25D)
                .add(Attributes.FOLLOW_RANGE, 35D)
                .add(Attributes.ATTACK_DAMAGE, 5)
                .add(Attributes.ATTACK_KNOCKBACK, 2)
                .add(Attributes.MOVEMENT_SPEED, 0.2D);
    }

    @Override
    public boolean doHurtTarget(Entity entity) {
        boolean flag = super.doHurtTarget(entity);
        if (flag && this.getMainHandItem().isEmpty() && entity instanceof LivingEntity) {
            float f = this.level().getCurrentDifficultyAt(this.blockPosition()).getEffectiveDifficulty();
            ((LivingEntity)entity).addEffect(new MobEffectInstance(MobEffects.WEAKNESS, 140 * (int)f), this);
        }

        if (level() instanceof ServerLevel)
            triggerAnim("attack_controller", "attack");

        return flag;
    }


    protected static final RawAnimation ATTACK_ANIM = RawAnimation.begin().thenPlay("attack.basic");
    protected static final RawAnimation LASER_ANIM = RawAnimation.begin().thenPlay("attack.laser");

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
        controllers.add(DefaultAnimations.genericWalkIdleController(this));
        controllers.add(DefaultAnimations.genericDeathController(this));

        controllers.add(new AnimationController<>(this, "attack_controller", animTest -> PlayState.STOP)
                .triggerableAnim("laser", LASER_ANIM)
                .triggerableAnim("attack", ATTACK_ANIM));
    }

    private final AnimatableInstanceCache geoCache = GeckoLibUtil.createInstanceCache(this);
    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return geoCache;
    }
}
