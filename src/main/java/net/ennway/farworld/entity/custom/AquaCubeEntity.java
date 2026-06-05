package net.ennway.farworld.entity.custom;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.animation.*;
import software.bernie.geckolib.util.GeckoLibUtil;

public class AquaCubeEntity extends Monster implements GeoEntity {

    public float sc = 1f;

    public static final EntityDataAccessor<Integer> JUMP_TICKS = SynchedEntityData.defineId(AquaCubeEntity.class, EntityDataSerializers.INT);
    public static final EntityDataAccessor<Boolean> NESTLED = SynchedEntityData.defineId(AquaCubeEntity.class, EntityDataSerializers.BOOLEAN);

    public AquaCubeEntity(EntityType<? extends Monster> entityType, Level level) {
        super(entityType, level);
    }

    @Override
    public void tick() {

        sc = this.onGround() ? 0 : 1;
        super.tick();
    }

    @Override
    public void aiStep() {
        if (this.onGround())
        {
            if (this.getTarget() != null) {
                this.getEntityData().set(NESTLED, false);
                this.lookAt(this.getTarget(), 20f, 20f);
                if (this.onGround())
                {
                    this.getEntityData().set(JUMP_TICKS, this.getEntityData().get(JUMP_TICKS) + 1);
                    if (this.getEntityData().get(JUMP_TICKS) == 20)
                    {
                        Vec3 v = this.getTarget().position().subtract(this.position()).normalize().multiply(0.25, 0, 0.25).add(0, 0.6, 0);
                        this.setOnGround(false);
                        this.hurtMarked = true;
                        this.addDeltaMovement(v);
                        this.playSound(SoundEvents.SLIME_JUMP);
                        if (level() instanceof ServerLevel)
                            triggerAnim("jump_controller", "jump");
                    }
                    if (this.getEntityData().get(JUMP_TICKS) == 24)
                    {
                        this.getEntityData().set(JUMP_TICKS, 0);
                        this.playSound(SoundEvents.SLIME_SQUISH);
                        if (level() instanceof ServerLevel)
                            triggerAnim("jump_controller", "land");
                    }
                }
            }
            else {
                this.getEntityData().set(NESTLED, true);
                sc = 0f;
            }
        }
        super.aiStep();
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        super.defineSynchedData(builder
                .define(NESTLED, true)
                .define(JUMP_TICKS, 0));
    }

    @Override
    public void addAdditionalSaveData(@NotNull CompoundTag compound) {
        compound.putBoolean("Nestled", this.getEntityData().get(NESTLED));
        super.addAdditionalSaveData(compound);
    }

    @Override
    public void readAdditionalSaveData(@NotNull CompoundTag compound) {
        super.readAdditionalSaveData(compound);
        this.getEntityData().set(NESTLED, compound.getBoolean("Nestled"));
    }

    protected static final RawAnimation IDLE_ANIM = RawAnimation.begin().thenLoop("idle");
    protected static final RawAnimation JUMP_ANIM = RawAnimation.begin().thenPlayAndHold("jump");
    protected static final RawAnimation LAND_ANIM = RawAnimation.begin().thenPlayAndHold("land");

    protected <E extends AquaCubeEntity> PlayState idleAnimController(final AnimationState<E> event) {
        return event.setAndContinue(IDLE_ANIM);
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();

        this.goalSelector.addGoal(0, new MeleeAttackGoal(this, 1.0, false));
        this.targetSelector.addGoal(0, new NearestAttackableTargetGoal(this, Player.class, true));
    }

    public static AttributeSupplier.Builder createAttributes()
    {
        return Mob.createLivingAttributes()
                .add(Attributes.MAX_HEALTH, 14)
                .add(Attributes.FOLLOW_RANGE, 6)
                .add(Attributes.ATTACK_DAMAGE, 6)
                .add(Attributes.MOVEMENT_SPEED, 0)
                .add(Attributes.SAFE_FALL_DISTANCE, 8)
                .add(Attributes.FALL_DAMAGE_MULTIPLIER, 0.5);
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {

        controllers.add(new AnimationController<>(this, "", this::idleAnimController));

        controllers.add(new AnimationController<>(this, "jump_controller", animTest -> PlayState.STOP)
                .triggerableAnim("jump", JUMP_ANIM)
                .triggerableAnim("land", LAND_ANIM));
    }

    private final AnimatableInstanceCache geoCache = GeckoLibUtil.createInstanceCache(this);
    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return geoCache;
    }
}
