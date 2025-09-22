package net.ennway.farworld.entity.custom;

import net.ennway.farworld.Farworld;
import net.ennway.farworld.registries.ModBlocks;
import net.ennway.farworld.registries.ModEntities;
import net.ennway.farworld.registries.ModParticles;
import net.ennway.farworld.registries.ModSounds;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.TagKey;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.animal.Ocelot;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.monster.Silverfish;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.pathfinder.PathType;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.common.damagesource.DamageContainer;
import net.neoforged.neoforge.event.entity.living.MobSpawnEvent;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;
import java.util.function.Predicate;

public class DustbugEntity extends Monster {

    public final AnimationState idleAnimationState = new AnimationState();
    public final AnimationState walkAnimationState = new AnimationState();
    public final AnimationState hurtAnimationState = new AnimationState();

    public DustbugEntity(EntityType<? extends Monster> entityType, Level level) {
        super(entityType, level);
        this.setPathfindingMalus(PathType.POWDER_SNOW, -1.0F);
        this.setPathfindingMalus(PathType.DANGER_POWDER_SNOW, -1.0F);
        this.setPathfindingMalus(PathType.WATER, -1.5F);
    }

    public double getEyeY() {
        return this.getPosition(0).y + 2D;
    }

    public final TargetingConditions targeting = TargetingConditions.forCombat().range(4.0).ignoreLineOfSight();

    static class DustbugAvoidEntityGoal<T extends LivingEntity> extends AvoidEntityGoal<T> {
        private final DustbugEntity bug;

        public DustbugAvoidEntityGoal(DustbugEntity bug, Class<T> entityClassToAvoid, float maxDist, double walkSpeedModifier, double sprintSpeedModifier) {
            super(bug, entityClassToAvoid, maxDist, walkSpeedModifier, sprintSpeedModifier, EntitySelector.NO_CREATIVE_OR_SPECTATOR::test);
            Objects.requireNonNull(EntitySelector.NO_CREATIVE_OR_SPECTATOR);
            this.bug = bug;
        }

        public boolean canUse() {
            return super.canUse();
        }

        public boolean canContinueToUse() {
            return super.canContinueToUse();
        }
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();

        this.goalSelector.addGoal(8, new RandomLookAroundGoal(this));
        this.goalSelector.addGoal(7, new WaterAvoidingRandomStrollGoal(this, 1.0));
        this.goalSelector.addGoal(6, new DustbugAvoidEntityGoal<Player>(this, Player.class, 6f, 1.1f, 1.3f));
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
        }
    }

    public static final EntityDataAccessor<Integer> ENTITY_STATE_TIMER = SynchedEntityData.defineId(DustbugEntity.class, EntityDataSerializers.INT);
    public static final EntityDataAccessor<Integer> ENTITY_SUMMON_TIMER = SynchedEntityData.defineId(DustbugEntity.class, EntityDataSerializers.INT);


    @Override
    public Vec3 getDeltaMovement() {
        float moveScale = 1f;
        if (entityData.get(ENTITY_SUMMON_TIMER) >= 0) moveScale = 0.2f;
        return super.getDeltaMovement().multiply(moveScale, 1f, moveScale);
    }

    public static final TagKey<Block> DUSTBUG_SUMMON_BLOCKS = TagKey.create(
            BuiltInRegistries.BLOCK.key(),
            ResourceLocation.fromNamespaceAndPath(Farworld.MOD_ID, "dustbug_summonable"));



    @Override
    public void aiStep() {
        super.aiStep();

        // unconditionally, clamp the value of entity_state_timer between 0 and 55
        // this is so that it doesn't get screwed up by descending below zero
        entityData.set(ENTITY_STATE_TIMER, Mth.clamp(entityData.get(ENTITY_STATE_TIMER), 0, 55));

        if (entityData.get(ENTITY_SUMMON_TIMER) >= 0) // if summon is currently ongoing
        {
            entityData.set(ENTITY_SUMMON_TIMER, entityData.get(ENTITY_SUMMON_TIMER) + 1);

            if (entityData.get(ENTITY_SUMMON_TIMER) == 12 || entityData.get(ENTITY_SUMMON_TIMER) == 14 || entityData.get(ENTITY_SUMMON_TIMER) == 16) {
                int x = getBlockX() + (int) this.getRandom().nextIntBetweenInclusive(-2, 2);
                int y = getBlockY() + 1;
                int z = getBlockZ() + (int) this.getRandom().nextIntBetweenInclusive(-2, 2);

                for (int k = 0; k < 5; k++) {
                    y--;
                    BlockPos blockPos = new BlockPos(x, y, z);
                    if (level().getBlockState(blockPos).entityCanStandOn(level(), blockPos, this)) {
                        break;
                    }
                }

                BlockPos pos = new BlockPos(x, y, z);
                BlockPos pos2 = new BlockPos(x, y + 1, z);

                this.level().addDestroyBlockEffect(pos2, this.level().getBlockState(pos));
                this.level().addDestroyBlockEffect(pos, this.level().getBlockState(pos));

                this.level().playSound(this, pos, SoundEvents.STONE_BREAK, SoundSource.HOSTILE, 1f, 1f);

                if (!this.level().isClientSide()) {
                    EntityType<?> mobType = EntityType.SILVERFISH;
                    Entity mob = mobType.create(this.level());
                    mob.setPosRaw(x + 0.5, y + 1.6, z + 0.5);
                    this.level().addFreshEntity(mob);
                }
            }
            if (entityData.get(ENTITY_SUMMON_TIMER) >= 30)
            {
                // set entity_summon_timer to -1. any value below 0 means that AI is proceeding normally
                entityData.set(ENTITY_SUMMON_TIMER, -1);
            }
        }
        else { // if summon is not occurring
            if (!this.level().getEntitiesOfClass(Player.class, new AABB(
                    this.position().x - 6.5f,
                    this.position().y - 3f,
                    this.position().z - 6.5f,
                    this.position().x + 6.5f,
                    this.position().y + 3f,
                    this.position().z + 6.5f)).isEmpty())
            {
                // if there is a player nearby, increment entity_state_timer upwards
                entityData.set(ENTITY_STATE_TIMER, entityData.get(ENTITY_STATE_TIMER) + 1);

                if (entityData.get(ENTITY_STATE_TIMER) >= 50)
                {
                    // set entity_summon_timer to 0. any value above or equal to 0 means that a summon is occurring
                    entityData.set(ENTITY_SUMMON_TIMER, 0);

                    this.playSound(ModSounds.DUSTBUG_SCREECH.get());

                    // set entity_state_timer to 0 so that, after summoning the silverfish, it will reset the timer
                    entityData.set(ENTITY_STATE_TIMER, 0);
                }
            }
            else {
                // if there is not a player nearby, increment entity_state_timer downwards instead
                entityData.set(ENTITY_STATE_TIMER, entityData.get(ENTITY_STATE_TIMER) - 1);
            }
        }
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        super.defineSynchedData(builder
                .define(ENTITY_STATE_TIMER, 0)
                .define(ENTITY_SUMMON_TIMER, -1));
    }

    private void setupAnimationStates()
    {
        this.idleAnimationState.startIfStopped(this.tickCount);
        this.walkAnimationState.startIfStopped(this.tickCount);
    }
    @Override
    protected @Nullable SoundEvent getAmbientSound() {
        return SoundEvents.SILVERFISH_AMBIENT;
    }

    @Override
    protected @Nullable SoundEvent getHurtSound(@NotNull DamageSource damageSource) {
        this.hurtAnimationState.start(this.tickCount);
        return SoundEvents.SILVERFISH_HURT;
    }

    @Override
    protected @Nullable SoundEvent getDeathSound() {
        return SoundEvents.SILVERFISH_DEATH;
    }

    public static AttributeSupplier.Builder createAttributes()
    {
        return Mob.createLivingAttributes()
                .add(Attributes.MAX_HEALTH, 10D)
                .add(Attributes.FOLLOW_RANGE, 12D)
                .add(Attributes.ATTACK_DAMAGE, 3)
                .add(Attributes.MOVEMENT_SPEED, 0.3D);
    }
}
