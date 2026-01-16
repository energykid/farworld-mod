package net.ennway.farworld.entity.custom.redstone_curiosity;

import net.ennway.farworld.entity.base.BaseSubattackEntity;
import net.ennway.farworld.registries.ModEntities;
import net.ennway.farworld.registries.ModParticles;
import net.ennway.farworld.registries.ModSounds;
import net.ennway.farworld.utils.BehaviorUtils;
import net.ennway.farworld.utils.MathUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerBossEvent;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.Music;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.util.Mth;
import net.minecraft.world.BossEvent;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.FlyingMoveControl;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.pathfinder.PathType;
import net.minecraft.world.phys.Vec2;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.joml.Vector2f;
import org.joml.Vector3f;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.animatable.SingletonGeoAnimatable;
import software.bernie.geckolib.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.animation.AnimatableManager;
import software.bernie.geckolib.animation.AnimationController;
import software.bernie.geckolib.animation.AnimationState;
import software.bernie.geckolib.animation.PlayState;
import software.bernie.geckolib.animation.RawAnimation;
import software.bernie.geckolib.util.GeckoLibUtil;

import java.util.List;

public class RedstoneCuriosityEntity extends Monster implements GeoEntity {

    public double rot;
    public double finalRot;
    public Vec3[] trailPositions;

    private final ServerBossEvent bossEvent;

    public static final int ATTACK_STATE_BUILDUP = 0;
    public static final int ATTACK_STATE_THREE_ZIPS = 1;
    public static final int ATTACK_STATE_SLOW_FLY = 2;
    public static final int ATTACK_STATE_GATLING = 3;
    public static final int ATTACK_STATE_BLAST = 4;
    public static final int ATTACK_STATE_ENERGY_RAIN = 5;
    public static final int ATTACK_STATE_PUNCH = 6;
    public static final int ATTACK_STATE_DEATH = 7;

    public static final Music BATTLE_THEME = new Music(
            Holder.direct(ModSounds.MUSIC_AWAKE_AWARE.get()), 0, 0, true
    );

    @Override
    public void startSeenByPlayer(ServerPlayer player) {
        super.startSeenByPlayer(player);
        this.bossEvent.addPlayer(player);

        Minecraft.getInstance().getMusicManager().stopPlaying();
        Minecraft.getInstance().getMusicManager().startPlaying(BATTLE_THEME);
    }

    @Override
    public void stopSeenByPlayer(ServerPlayer player) {
        super.stopSeenByPlayer(player);
        this.bossEvent.removePlayer(player);
        if (Minecraft.getInstance().getMusicManager().isPlayingMusic(BATTLE_THEME))
            Minecraft.getInstance().getMusicManager().stopPlaying(BATTLE_THEME);
    }

    @Override
    protected void tickDeath() {
        super.tickDeath();
        if (Minecraft.getInstance().getMusicManager().isPlayingMusic(BATTLE_THEME))
            Minecraft.getInstance().getMusicManager().stopPlaying(BATTLE_THEME);
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
        this.progress = 0f;
        SingletonGeoAnimatable.registerSyncedAnimatable(this);
    }

    public void moveMeTo(Vec3 pos)
    {
        move(MoverType.SELF, pos.subtract(position()));
    }

    @Override
    public void addAdditionalSaveData(CompoundTag compound) {
        compound.putBoolean("JustSpawned", this.getEntityData().get(JUST_SPAWNED));
        compound.putInt("AttackState", this.getEntityData().get(ATTACK_STATE));
        compound.putInt("AttackTime1", this.getEntityData().get(ATTACK_TIME_1));
        compound.putFloat("AttackTime2", this.getEntityData().get(ATTACK_VALUE_1));
        super.addAdditionalSaveData(compound);
    }

    @Override
    public void readAdditionalSaveData(CompoundTag compound) {
        this.getEntityData().set(JUST_SPAWNED, compound.getBoolean("JustSpawned"));
        this.getEntityData().set(ATTACK_STATE, compound.getInt("AttackState"));
        this.getEntityData().set(ATTACK_TIME_1, compound.getInt("AttackTime1"));
        this.getEntityData().set(ATTACK_VALUE_1, compound.getFloat("AttackTime2"));
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

    public void rotateToNearestPlayer()
    {
        Entity plr = this.getTarget();
        if (plr != null)
        {
            this.rot = Mth.rotLerp(this.getEntityData().get(ROTATION_LERP), this.rot, -look(plr.position()));
        }
    }

    float progress = 0f;

    @Override
    public void tick() {
        if (getTarget() == null)
        {
            setTarget(level().getNearestPlayer(this, 20));
        }

        this.setNoGravity(false);

        progress += 0.05f;

        float hp = this.getHealth() / this.getMaxHealth();
        if (progress > hp)
        {
            progress = hp;
        }

        this.bossEvent.setProgress(progress);

        this.setYRot(0f);

        if (!isDeadOrDying())
        {
            doAI();
        }

        super.tick();
    }

    @Override
    public boolean canDisableShield() {
        return true;
    }

    public void doAI()
    {
        this.getEntityData().set(ATTACK_TIME_1, this.getEntityData().get(ATTACK_TIME_1) + 1);

        if (getEntityData().get(ATTACK_STATE) == ATTACK_STATE_BUILDUP)
        {
            this.entityData.set(ROTATION_LERP, 0.1f);

            if (getEntityData().get(ATTACK_TIME_1) == 2)
            {
                playSound(ModSounds.REDSTONE_CURIOSITY_SPAWN.get());
            }

            if (getEntityData().get(ATTACK_TIME_1) > 50)
                changeState(ATTACK_STATE_THREE_ZIPS);
        }
        if (getEntityData().get(ATTACK_STATE) == ATTACK_STATE_THREE_ZIPS)
        {
            this.entityData.set(ROTATION_LERP, 0.5f);

            rotateToNearestPlayer();
            if (this.getEntityData().get(ATTACK_TIME_1) % 6 == 1 && this.getEntityData().get(ATTACK_TIME_1) < 15)
            {
                Vec3 pos = this.getNearTargetPosition(9);

                zipTo(pos);
            }
            if (this.getEntityData().get(ATTACK_TIME_1) > 28)
            {
                changeState(getRandomState());
            }
        }
        if (getEntityData().get(ATTACK_STATE) == ATTACK_STATE_SLOW_FLY)
        {
            this.entityData.set(ROTATION_LERP, 0.2f);
            rotateToNearestPlayer();
            if (getTarget() != null)
            {
                this.setDeltaMovement(getTarget().position().subtract(position()).normalize().multiply(0.1, 0.1, 0.1));
            }
            else
            {
                this.setDeltaMovement(Vec3.ZERO);
            }

            if (this.getEntityData().get(ATTACK_TIME_1) > 25)
            {
                changeState(getRandomState());
            }
        }
        if (getEntityData().get(ATTACK_STATE) == ATTACK_STATE_BLAST)
        {
            rotateToNearestPlayer();
            this.entityData.set(ROTATION_LERP, 0f);
            if (getTarget() != null)
            {
                if (this.getEntityData().get(ATTACK_TIME_1) <= 16)
                {
                    setDeltaMovement(0,0,0);
                    if (this.getEntityData().get(ATTACK_TIME_1) == 6)
                    {
                        zipToPlayer(2, 0);
                    }
                    if (this.getEntityData().get(ATTACK_TIME_1) < 13)
                    {
                        this.entityData.set(ROTATION_LERP, 0.8f);
                    }
                    if (this.getEntityData().get(ATTACK_TIME_1) == 13)
                    {
                        playSound(ModSounds.REDSTONE_CURIOSITY_CHARGE.get());
                    }
                    if (this.getEntityData().get(ATTACK_TIME_1) == 11)
                    {
                        if (level() instanceof ServerLevel)
                            triggerAnim("attack_controller", "blast");
                    }
                }

                if (this.getEntityData().get(ATTACK_TIME_1) == 25)
                {
                    playSound(ModSounds.REDSTONE_CURIOSITY_SHOOT_LARGE.get());

                    Vec3 position = position().add(0, 1.5, 0);

                    for (int i = 0; i < 15; i++) {
                        level().addParticle(ModParticles.REDSTONE_CURIOSITY_PARTICLE.get(), position.x + random.nextDouble(), position.y + random.nextDouble(), position.z + random.nextDouble(), MathUtils.randomDouble(getRandom(), -3, 3), MathUtils.randomDouble(getRandom(), -3, 3), MathUtils.randomDouble(getRandom(), -3, 3));
                    }

                    RedstoneCuriosityBlastEntity ent = new RedstoneCuriosityBlastEntity(ModEntities.REDSTONE_CURIOSITY_BLAST.get(),
                            level());
                    ent.setPos(position);
                    ent.owner = this;
                    ent.getEntityData().set(BaseSubattackEntity.ROTATION, (float)this.rot);
                    level().addFreshEntity(ent);
                }

                if (this.getEntityData().get(ATTACK_TIME_1) > 31)
                {
                    changeState(ATTACK_STATE_SLOW_FLY);
                }
            }
            else
            {
                changeState(ATTACK_STATE_SLOW_FLY);
            }
        }
        if (getEntityData().get(ATTACK_STATE) == ATTACK_STATE_GATLING)
        {
            getEntityData().set(SHOULD_TILT_HEAD, true);
            rotateToNearestPlayer();
            if (getEntityData().get(ATTACK_TIME_1) == 2)
            {
                zipToPlayer(8, 1);

                getEntityData().set(ATTACK_VALUE_1, 0.65f);

                if (level() instanceof ServerLevel)
                    triggerAnim("attack_controller", "gatling");

                playSound(ModSounds.REDSTONE_CURIOSITY_CHARGE_GATLING.get());
            }
            getEntityData().set(ROTATION_LERP, 0.5f);
            if (getEntityData().get(ATTACK_TIME_1) > 28 && getEntityData().get(ATTACK_TIME_1) % 2 == 1)
            {
                getEntityData().set(ATTACK_VALUE_1, getEntityData().get(ATTACK_VALUE_1) + 0.05f);
                shoot(getEntityData().get(ATTACK_VALUE_1));
            }
            if (this.getEntityData().get(ATTACK_TIME_1) > 61)
            {
                getEntityData().set(SHOULD_TILT_HEAD, false);
                changeState(ATTACK_STATE_THREE_ZIPS);
            }
        }
        if (getEntityData().get(ATTACK_STATE) == ATTACK_STATE_ENERGY_RAIN)
        {
            getEntityData().set(ROTATION_LERP, 0.5f);
            rotateToNearestPlayer();
            if (getEntityData().get(ATTACK_TIME_1) == 2)
            {
                zipToPlayer(10, 4);

                if (level() instanceof ServerLevel)
                    triggerAnim("attack_controller", "cast");
            }
            if (getEntityData().get(ATTACK_TIME_1) > 2 && getEntityData().get(ATTACK_TIME_1) % 3 == 0) {
                if (getTarget() != null) {
                    for (int i = 0; i < 2; i++) {
                        Vector3f pos = getTarget().position().toVector3f().add((float) MathUtils.randomDouble(getRandom(), 0, 20), -4, (float) MathUtils.randomDouble(getRandom(), 0, 20));

                        pos = pos.sub(10, 0, 10);

                        RedstoneCuriosityVerticalBlastEntity ent = new RedstoneCuriosityVerticalBlastEntity(ModEntities.REDSTONE_CURIOSITY_VERTICAL_BLAST.get(),
                                level());
                        ent.setPos(new Vec3(pos));
                        ent.owner = this;
                        level().addFreshEntity(ent);
                    }
                }
            }
            if (getEntityData().get(ATTACK_TIME_1) > 40)
            {
                changeState(ATTACK_STATE_THREE_ZIPS);
            }
        }
    }

    public void zipToPlayer(int horizontalDistance, int verticalDistance)
    {
        if (getTarget() != null)
        {
            Vec3 p = getTarget().getLookAngle().multiply(horizontalDistance, 0, horizontalDistance);

            Vec3 pos = getTarget().position().add(p).add(0, verticalDistance, 0);

            zipTo(pos);
        }
    }

    public void shoot(float soundPitch)
    {
        if (getTarget() == null) return;

        Vector3f pos = getEyePosition().toVector3f().add(0, 1.25f, 0);
        Vector3f r = new Vector3f(0, 1, 0).rotateZ((float)Math.toRadians(MathUtils.randomDouble(random, 0, 360)));
        r = r.rotateY((float)-Math.toRadians(rot));
        pos = pos.add(r);

        Vector3f plrPos = getTarget().getEyePosition().toVector3f();

        Vector3f shootVel = plrPos.sub(pos).normalize(2f);

        pos.add(shootVel.mul(0.2f));

        RedstoneCuriosityLaserEntity ent = new RedstoneCuriosityLaserEntity(ModEntities.REDSTONE_CURIOSITY_LASER.get(), level());
        ent.setPos(new Vec3(pos.x, pos.y, pos.z));
        ent.setOwner(this);
        ent.setDeltaMovement(new Vec3(shootVel.x, shootVel.y, shootVel.z));
        ProjectileUtil.rotateTowardsMovement(ent, 1F);
        level().addFreshEntity(ent);

        level().addParticle(ModParticles.REDSTONE_CURIOSITY_BURST.get(), pos.x, pos.y, pos.z, 0, 0, 0);

        playSound(ModSounds.REDSTONE_CURIOSITY_SHOOT_SMALL.get(), 1f, soundPitch);
    }
    public static List<Integer> VALID_STATES_TO_SWITCH_TO = List.of(
            ATTACK_STATE_BLAST,
            ATTACK_STATE_GATLING,
            ATTACK_STATE_ENERGY_RAIN
    );

    public int getRandomState()
    {
        return VALID_STATES_TO_SWITCH_TO.get(random.nextInt(VALID_STATES_TO_SWITCH_TO.size()));
    }

    public void zipTo(Vec3 pos)
    {
        if (!level().isClientSide)
            moveMeTo(pos);
        this.playSound(ModSounds.REDSTONE_CURIOSITY_ZIP.get());
    }

    public Vec3 getNearTargetPosition(double dist)
    {
        if (this.getTarget() != null)
        {
            Vec3 origPos = this.getTarget().position();
            Vec3 playerPos;

            for (int i = 0; i < 20; i++) {
                playerPos = origPos.offsetRandom(random, 10);
                double yy = Mth.lerp(0.5, playerPos.y, origPos.y);
                playerPos = new Vec3(playerPos.x, yy, playerPos.z);
                if (level().getBlockState(BlockPos.containing(playerPos.x, playerPos.y, playerPos.z)).isAir() && this.getTarget().distanceToSqr(playerPos) > 4)
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
        this.getEntityData().set(ATTACK_VALUE_1, 0f);
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
                .define(ATTACK_VALUE_1, 0f)
                .define(ROTATION_LERP, 0f)
                .define(SHOULD_TILT_HEAD, false));
    }

    @Override
    protected @Nullable SoundEvent getHurtSound(@NotNull DamageSource damageSource) {
        return ModSounds.REDSTONE_CURIOSITY_HURT.get();
    }

    @Override
    public boolean shouldRender(double x, double y, double z) {
        return true;
    }

    @Override
    protected @Nullable SoundEvent getDeathSound() {
        return ModSounds.REDSTONE_CURIOSITY_KILL.get();
    }

    public static AttributeSupplier.Builder createAttributes()
    {
        return Mob.createLivingAttributes()
                .add(Attributes.MAX_HEALTH, 375D)
                .add(Attributes.FOLLOW_RANGE, 10D)
                .add(Attributes.ATTACK_DAMAGE, 10)
                .add(Attributes.MOVEMENT_SPEED, 0.15D)
                .add(Attributes.KNOCKBACK_RESISTANCE, 1f);
    }

    public static final EntityDataAccessor<Integer> ATTACK_STATE = SynchedEntityData.defineId(RedstoneCuriosityEntity.class, EntityDataSerializers.INT);
    public static final EntityDataAccessor<Integer> ATTACK_TIME_1 = SynchedEntityData.defineId(RedstoneCuriosityEntity.class, EntityDataSerializers.INT);
    public static final EntityDataAccessor<Float> ATTACK_VALUE_1 = SynchedEntityData.defineId(RedstoneCuriosityEntity.class, EntityDataSerializers.FLOAT);
    public static final EntityDataAccessor<Float> ROTATION_LERP = SynchedEntityData.defineId(RedstoneCuriosityEntity.class, EntityDataSerializers.FLOAT);
    public static final EntityDataAccessor<Boolean> JUST_SPAWNED = SynchedEntityData.defineId(RedstoneCuriosityEntity.class, EntityDataSerializers.BOOLEAN);
    public static final EntityDataAccessor<Boolean> SHOULD_TILT_HEAD = SynchedEntityData.defineId(RedstoneCuriosityEntity.class, EntityDataSerializers.BOOLEAN);

    @Override
    public boolean doHurtTarget(Entity entity) {
        return false;
    }
    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
        controllers.add(new AnimationController<>(this, "", this::idleAnimController));
        controllers.add(new AnimationController<>(this, "", this::spawnAnimController));

        controllers.add(new AnimationController<>(this, "attack_controller", animTest -> PlayState.STOP)
                .triggerableAnim("blast", BLAST_ANIM)
                .triggerableAnim("cast", CAST_ANIM)
                .triggerableAnim("punch", PUNCH_ANIM)
                .triggerableAnim("gatling", GATLING_ANIM));
    }

    private final AnimatableInstanceCache geoCache = GeckoLibUtil.createInstanceCache(this);

    protected static final RawAnimation IDLE_ANIM = RawAnimation.begin().thenLoop("idle");
    protected static final RawAnimation SPAWN_ANIM = RawAnimation.begin().thenPlayAndHold("spawn");
    protected static final RawAnimation BLAST_ANIM = RawAnimation.begin().thenPlay("blast");
    protected static final RawAnimation CAST_ANIM = RawAnimation.begin().thenPlay("cast");
    protected static final RawAnimation PUNCH_ANIM = RawAnimation.begin().thenPlay("punch");
    protected static final RawAnimation GATLING_ANIM = RawAnimation.begin().thenPlay("gatling");

    protected <E extends RedstoneCuriosityEntity> PlayState idleAnimController(final AnimationState<E> event) {
        return event.setAndContinue(IDLE_ANIM);
    }
    protected <E extends RedstoneCuriosityEntity> PlayState spawnAnimController(final AnimationState<E> event) {
        return event.setAndContinue(SPAWN_ANIM);
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return geoCache;
    }
}