package net.ennway.farworld.entity.custom;

import net.ennway.farworld.registries.ModSounds;
import net.ennway.farworld.utils.BehaviorUtils;
import net.ennway.farworld.utils.MathUtils;
import net.minecraft.commands.arguments.EntityAnchorArgument;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerBossEvent;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.world.BossEvent;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.FlyingMoveControl;
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
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec2;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.common.damagesource.DamageContainer;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.joml.Vector2f;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.animation.AnimatableManager;
import software.bernie.geckolib.animation.AnimationController;
import software.bernie.geckolib.animation.PlayState;
import software.bernie.geckolib.animation.RawAnimation;
import software.bernie.geckolib.util.GeckoLibUtil;

public class RedstoneCuriosityEntity extends Monster implements GeoEntity {

    public double rot;
    public double finalRot;
    public Vec3[] trailPositions;

    private final ServerBossEvent bossEvent;

    public static final int ATTACK_STATE_BUILDUP = 0;
    public static final int ATTACK_STATE_THREE_ZIPS = 1;

    @Override
    public void startSeenByPlayer(ServerPlayer player) {
        super.startSeenByPlayer(player);
        this.bossEvent.addPlayer(player);
    }

    @Override
    public void stopSeenByPlayer(ServerPlayer player) {
        super.stopSeenByPlayer(player);
        this.bossEvent.removePlayer(player);
    }

    @Override
    protected double getDefaultGravity() {
        return 0;
    }

    public RedstoneCuriosityEntity(EntityType<? extends Monster> entityType, Level level) {
        super(entityType, level);
        this.setPathfindingMalus(PathType.POWDER_SNOW, -1.0F);
        this.setPathfindingMalus(PathType.DANGER_POWDER_SNOW, -1.0F);
        this.setPathfindingMalus(PathType.WATER, -1.5F);
        this.bossEvent = (ServerBossEvent)(new ServerBossEvent(this.getDisplayName(), BossEvent.BossBarColor.RED, BossEvent.BossBarOverlay.PROGRESS)).setDarkenScreen(true);
        this.moveControl = new FlyingMoveControl(this, 10, false);
        this.setHealth(this.getMaxHealth());
        this.xpReward = 35;
    }

    @Override
    public void addAdditionalSaveData(CompoundTag compound) {
        compound.putBoolean("JustSpawned", this.getEntityData().get(JUST_SPAWNED));
        compound.putInt("AttackState", this.getEntityData().get(ATTACK_STATE));
        compound.putInt("AttackTime1", this.getEntityData().get(ATTACK_TIME_1));
        compound.putInt("AttackTime2", this.getEntityData().get(ATTACK_TIME_2));
        super.addAdditionalSaveData(compound);
    }

    @Override
    public void readAdditionalSaveData(CompoundTag compound) {
        this.getEntityData().set(JUST_SPAWNED, compound.getBoolean("JustSpawned"));
        this.getEntityData().set(ATTACK_STATE, compound.getInt("AttackState"));
        this.getEntityData().set(ATTACK_TIME_1, compound.getInt("AttackTime1"));
        this.getEntityData().set(ATTACK_TIME_2, compound.getInt("AttackTime2"));
        super.readAdditionalSaveData(compound);
    }

    @Override
    public void setCustomName(@javax.annotation.Nullable Component name) {
        super.setCustomName(name);
        this.bossEvent.setName(this.getDisplayName());
    }

    @Override
    public double getEyeY() {
        return this.getPosition(0).y + 1.5D;
    }

    @Override
    protected boolean wouldNotSuffocateAtTargetPose(Pose pose) {
        return true;
    }

    @Override
    public void tick() {
        this.setNoGravity(false);

        this.bossEvent.setProgress(this.getHealth() / this.getMaxHealth());

        this.setYRot(0f);

        Player plr = this.getCommandSenderWorld().getNearestPlayer(this, 20);
        if (plr != null)
        {
            rot = Mth.rotLerp(0.2, rot, -look(plr.position()));
            this.setTarget(plr);
        }

        this.getEntityData().set(ATTACK_TIME_1, this.getEntityData().get(ATTACK_TIME_1) + 1);

        if (getEntityData().get(ATTACK_STATE) == ATTACK_STATE_BUILDUP)
        {
            if (getEntityData().get(ATTACK_TIME_1) == 2)
            {
                playSound(ModSounds.REDSTONE_CURIOSITY_SPAWN.get());
            }

            if (getEntityData().get(ATTACK_TIME_1) > 50)
                changeState(ATTACK_STATE_THREE_ZIPS);
        }
        if (getEntityData().get(ATTACK_STATE) == ATTACK_STATE_THREE_ZIPS)
        {
            if (this.getEntityData().get(ATTACK_TIME_1) % 6 == 1 && this.getEntityData().get(ATTACK_TIME_1) < 15)
            {
                Vec3 pos = this.getNearTargetPosition(9);
                if (!level().isClientSide)
                    this.setPos(pos);
                this.playSound(ModSounds.REDSTONE_CURIOSITY_ZIP.get());
            }
            if (this.getEntityData().get(ATTACK_TIME_1) > 28)
            {
                changeState(ATTACK_STATE_THREE_ZIPS);
            }
        }

        super.tick();
    }

    public Vec3 getNearTargetPosition(double dist)
    {
        if (this.getTarget() != null)
        {
            Vec3 origPos = this.getTarget().position();
            Vec3 playerPos = origPos;

            for (int i = 0; i < 20; i++) {
                playerPos = origPos.offsetRandom(random, 8);
                if (level().getBlockState(BlockPos.containing(playerPos.x, playerPos.y, playerPos.z)).isAir())
                {
                    return playerPos;
                }
            }
        }
        return position();
    }

    public void changeState(int stateTo)
    {
        this.getEntityData().set(ATTACK_STATE, stateTo);
        this.getEntityData().set(ATTACK_TIME_1, 0);
        this.getEntityData().set(ATTACK_TIME_2, 0);
    }

    public double look(Vec3 target) {
        Vec3 vec3 = this.position();
        double d0 = target.x - vec3.x;
        double d2 = target.z - vec3.z;
        return Mth.wrapDegrees((float)(Mth.atan2(d2, d0) * 180.0 / 3.1415927410125732) - 90.0F);
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        super.defineSynchedData(builder
                .define(JUST_SPAWNED, true)
                .define(ATTACK_STATE, ATTACK_STATE_BUILDUP)
                .define(ATTACK_TIME_1, 0)
                .define(ATTACK_TIME_2, 0));
    }

    @Override
    protected @Nullable SoundEvent getHurtSound(@NotNull DamageSource damageSource) {
        return ModSounds.REDSTONE_CURIOSITY_HURT.get();
    }

    @Override
    protected @Nullable SoundEvent getDeathSound() {
        return ModSounds.REDSTONE_CURIOSITY_KILL.get();
    }

    public static AttributeSupplier.Builder createAttributes()
    {
        return Mob.createLivingAttributes()
                .add(Attributes.MAX_HEALTH, 200D)
                .add(Attributes.FOLLOW_RANGE, 10D)
                .add(Attributes.ATTACK_DAMAGE, 10)
                .add(Attributes.MOVEMENT_SPEED, 0.15D);
    }

    public static final EntityDataAccessor<Integer> ATTACK_STATE = SynchedEntityData.defineId(RedstoneCuriosityEntity.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Integer> ATTACK_TIME_1 = SynchedEntityData.defineId(RedstoneCuriosityEntity.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Integer> ATTACK_TIME_2 = SynchedEntityData.defineId(RedstoneCuriosityEntity.class, EntityDataSerializers.INT);
    public static final EntityDataAccessor<Boolean> JUST_SPAWNED = SynchedEntityData.defineId(RedstoneCuriosityEntity.class, EntityDataSerializers.BOOLEAN);

    @Override
    public boolean doHurtTarget(Entity entity) {
        return false;
    }
    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
        controllers.add(new AnimationController<>(this, "", this::idleAnimController));
        controllers.add(new AnimationController<>(this, "", this::spawnAnimController));
    }

    private final AnimatableInstanceCache geoCache = GeckoLibUtil.createInstanceCache(this);

    protected static final RawAnimation IDLE_ANIM = RawAnimation.begin().thenLoop("idle");
    protected static final RawAnimation SPAWN_ANIM = RawAnimation.begin().thenPlayAndHold("spawn");

    protected <E extends RedstoneCuriosityEntity> PlayState idleAnimController(final software.bernie.geckolib.animation.AnimationState<E> event) {
        return event.setAndContinue(IDLE_ANIM);
    }
    protected <E extends RedstoneCuriosityEntity> PlayState spawnAnimController(final software.bernie.geckolib.animation.AnimationState<E> event) {
        return event.setAndContinue(SPAWN_ANIM);
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return geoCache;
    }
}