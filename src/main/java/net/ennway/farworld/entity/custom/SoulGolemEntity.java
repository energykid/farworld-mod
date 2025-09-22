package net.ennway.farworld.entity.custom;

import net.ennway.farworld.Farworld;
import net.ennway.farworld.entity.goal.FindFuelGoal;
import net.ennway.farworld.registries.ModParticles;
import net.ennway.farworld.registries.ModSounds;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.Resource;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.TagKey;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.goal.target.ResetUniversalAngerTargetGoal;
import net.minecraft.world.entity.ai.sensing.NearestItemSensor;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.animal.AbstractGolem;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.monster.Enemy;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.pathfinder.PathType;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.common.damagesource.DamageContainer;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Comparator;
import java.util.EnumSet;
import java.util.List;

public class SoulGolemEntity extends AbstractGolem {

    public static final EntityDataAccessor<Integer> ACTIVITY_SCALE = SynchedEntityData.defineId(SoulGolemEntity.class, EntityDataSerializers.INT);
    public static final EntityDataAccessor<Integer> REMAINING_UPTIME = SynchedEntityData.defineId(SoulGolemEntity.class, EntityDataSerializers.INT);
    public static final EntityDataAccessor<Integer> ENTITY_STATE_TIMER = SynchedEntityData.defineId(SoulGolemEntity.class, EntityDataSerializers.INT);
    public static final EntityDataAccessor<Integer> ENTITY_PICKUP_TIMER = SynchedEntityData.defineId(SoulGolemEntity.class, EntityDataSerializers.INT);

    public static TagKey<Item> ALLOWED_ITEMS = TagKey.create(BuiltInRegistries.ITEM.key(),
            ResourceLocation.fromNamespaceAndPath(Farworld.MOD_ID, "soul_golem_fuel"));

    public float activityScale = 0F;
    public float walkAnimationScale = 0F;

    public float moveDist2 = 0f;
    public boolean fwoosh = false;

    public final AnimationState idleAnimationState = new AnimationState();
    public final AnimationState inactiveAnimationState = new AnimationState();
    public final AnimationState walkAnimationState = new AnimationState();
    public final AnimationState slamAnimationState = new AnimationState();

    public SoulGolemEntity(EntityType<? extends AbstractGolem> entityType, Level level) {
        super(entityType, level);
        this.setPathfindingMalus(PathType.POWDER_SNOW, -1.0F);
        this.setPathfindingMalus(PathType.DANGER_POWDER_SNOW, -1.0F);
        this.setPathfindingMalus(PathType.WATER, -1.5F);
    }

    public double getEyeY() {
        return this.getPosition(0).y + 2D;
    }

    @Override
    public void addAdditionalSaveData(@NotNull CompoundTag compound) {
        compound.putInt("RemainingUptime", this.getEntityData().get(REMAINING_UPTIME));
        compound.putInt("ActivityScale", this.getEntityData().get(ACTIVITY_SCALE));
        super.addAdditionalSaveData(compound);
    }

    @Override
    public void readAdditionalSaveData(@NotNull CompoundTag compound) {
        super.readAdditionalSaveData(compound);
        this.getEntityData().set(REMAINING_UPTIME, compound.getInt("RemainingUptime"));
        this.getEntityData().set(ACTIVITY_SCALE, compound.getInt("ActivityScale"));
    }

    @Override
    public Vec3 getDeltaMovement() {
        float moveScale = this.getEntityData().get(ACTIVITY_SCALE) > 0 ? 1f : 0f;
        if (slamAnimationState.isStarted()) moveScale = 0f;
        return super.getDeltaMovement().multiply(moveScale, 1f, moveScale);
    }


    @Override
    public void aiStep() {
        super.aiStep();
        if (this.entityData.get(ENTITY_STATE_TIMER) == 60)
        {
            this.slamAnimationState.start(this.tickCount);
        }
        if (this.entityData.get(ENTITY_STATE_TIMER) == 80)
        {
            this.slamAttack(true);
        }
        if (this.entityData.get(ENTITY_STATE_TIMER) >= 110)
        {
            this.slamAnimationState.stop();
        }

        List<ItemEntity> items = this.level().getEntitiesOfClass(ItemEntity.class,
                new AABB(
                        this.getX() - 12, this.getY() - 12, this.getZ() - 12,
                        this.getX() + 12, this.getY() + 12, this.getZ() + 12
                ));

        if (!items.isEmpty())
            items.removeIf(item -> !item.getItem().is(ALLOWED_ITEMS));

        items.sort(Comparator.comparingInt(c -> (int) c.distanceToSqr(this)));

        this.getEntityData().set(ENTITY_PICKUP_TIMER, this.getEntityData().get(ENTITY_PICKUP_TIMER) - 1);

        if (this.entityData.get(REMAINING_UPTIME) < 400 && this.entityData.get(ACTIVITY_SCALE) != 0)
        {
            if (!items.isEmpty())
            {
                Vec3 pos = this.getPosition(0).add(0f, 1f, 0f);

                Vec3 pos2 = new Vec3(items.getFirst().getX(), items.getFirst().getY(), items.getFirst().getZ());

                this.getNavigation().moveTo(pos2.x,
                        pos2.y,
                        pos2.z,
                        0,
                        1f);

                if (items.getFirst().distanceTo(this) < 2 && this.getEntityData().get(ENTITY_PICKUP_TIMER) < 0)
                {
                    items.getFirst().setDeltaMovement(pos.subtract(pos2).multiply(0.2f, 0.2f, 0.2f));
                }
                if (items.getFirst().distanceToSqr(pos) < 0.3f && this.getEntityData().get(ENTITY_PICKUP_TIMER) < 0)
                {
                    this.getEntityData().set(ENTITY_PICKUP_TIMER, 10);
                    this.fuelGolem(150);
                    items.getFirst().getItem().consume(1, this);
                }
            }
        }
    }

    public final TargetingConditions targeting = TargetingConditions.forCombat().range(4.0).ignoreLineOfSight();

    @Override
    protected void customServerAiStep() {

        int activityScale = this.getEntityData().get(ACTIVITY_SCALE);

        {
            boolean hasTargetInRange = this.getTarget() != null;
            if (hasTargetInRange)
            {
                List<LivingEntity> list = this.level().getNearbyEntities(LivingEntity.class, targeting, this,
                        new AABB(this.getX() - 4, this.getY() - 4, this.getZ() - 4,
                                this.getX() + 4, this.getY() + 4, this.getZ() + 4));

                if (list.isEmpty()) hasTargetInRange = false;
            }
            if (activityScale == 0) hasTargetInRange = false;

            if (this.getTarget() != null && !hasTargetInRange)
            {
                Vec3 vec3 = this.getTarget().position();
                this.getNavigation().moveTo(vec3.x, vec3.y, vec3.z, 1.2f);
            }

            if (hasTargetInRange || this.entityData.get(ENTITY_STATE_TIMER) > 60)
            {
                this.entityData.set(ENTITY_STATE_TIMER, this.entityData.get(ENTITY_STATE_TIMER) + 1);
            }

            if (this.entityData.get(ENTITY_STATE_TIMER) == 80)
            {
                this.slamAttack(false);
            }
            if (this.entityData.get(ENTITY_STATE_TIMER) >= 112)
            {
                this.entityData.set(ENTITY_STATE_TIMER, 20 + this.random.nextInt(20));
            }
        }

        super.customServerAiStep();
    }

    public void fuelGolem(int amount)
    {
        fuelGolemClient();
        this.entityData.set(REMAINING_UPTIME, Math.max(this.entityData.get(REMAINING_UPTIME), 0) + amount);
        if (this.entityData.get(ACTIVITY_SCALE) == 0)
        {
            this.playSound(ModSounds.SOUL_GOLEM_ACTIVATE.get());
        }
        this.entityData.set(ACTIVITY_SCALE, 2);
        this.playSound(SoundEvents.FIRECHARGE_USE);
        this.heal(6);
    }

    public void fuelGolemClient()
    {
        for (int i = 0; i < 30; i++) {
            this.level().addParticle(ModParticles.INFERNAL_SMOKE.get(), this.getX(), this.getY() + (this.random.nextFloat() * 2), this.getZ(), 0f, 0f, 0f);
        }
    }

    public void slamAttack(boolean isClient)
    {
        if (isClient)
        {
            for (int i = -4; i <= 4; i++) {
                for (int j = -4; j <= 4; j++) {
                    for (int k = 4; k > -4; k--) {
                        BlockPos blockPos = new BlockPos(this.getBlockX() + i, this.getBlockY() + k, this.getBlockZ() + j);

                        if (level().getBlockState(blockPos).entityCanStandOn(level(), blockPos, this))
                        {
                            float dist = Mth.sqrt((Mth.abs(i)^2) + (Mth.abs(j)^2));

                            if (dist < 4)
                            {
                                level().addDestroyBlockEffect(blockPos, level().getBlockState(blockPos));
                            }
                            if (dist < 2)
                            {
                                level().addParticle(ModParticles.INFERNAL_SMOKE.get(), blockPos.getX(), blockPos.getY() + 1, blockPos.getZ(), (level().getRandom().nextDouble() * 0.5f) - 0.25f, level().getRandom().nextDouble(), (level().getRandom().nextDouble() * 0.5f) - 0.25f);
                            }
                            if (dist < 4)
                            {
                                level().addParticle(ModParticles.INFERNAL_SMOKE.get(), blockPos.getX(), blockPos.getY() + 1, blockPos.getZ(), (level().getRandom().nextDouble() * 0.5f) - 0.25f, level().getRandom().nextDouble() / 3f, (level().getRandom().nextDouble() * 0.5f) - 0.25f);
                            }
                        }
                    }
                }
            }
        }
        else {
            this.playSound(SoundEvents.MACE_SMASH_GROUND_HEAVY);
            this.level().getEntitiesOfClass(LivingEntity.class, new AABB(
                    this.position().x - 4,
                    this.position().y - 4,
                    this.position().z - 4,
                    this.position().x + 4,
                    this.position().y + 4,
                    this.position().z + 4)).forEach(entity -> {

                if (entity instanceof Enemy)
                {
                    entity.addDeltaMovement(new Vec3(0f, 0.4f, 0f));
                    DamageSource source = this.damageSources().source(DamageTypes.MOB_ATTACK);
                    entity.hurt(new DamageSource(source.typeHolder(), entity),
                            20);
                    if (entity instanceof Mob mob)
                    {
                        mob.setTarget(this);
                    }
                }
            });
        }
    }

    public int getActivity()
    {
        return this.getEntityData().get(ACTIVITY_SCALE);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(2, new MoveTowardsTargetGoal(this, 1d, 13));

        this.targetSelector.addGoal(1, new HurtByTargetGoal(this));
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal(this, Mob.class, 5, true, false, e -> (e instanceof Enemy)));
        this.targetSelector.addGoal(3, new ResetUniversalAngerTargetGoal(this, false));
    }

    @Override
    protected InteractionResult mobInteract(Player player, InteractionHand hand) {

        ItemStack stack = player.getItemInHand(hand);

        if (stack.is(ALLOWED_ITEMS) && this.entityData.get(REMAINING_UPTIME) < 1000)
        {
            fuelGolem(300);

            stack.consume(1, player);

            return InteractionResult.SUCCESS;
        }

        return super.mobInteract(player, hand);
    }

    @Override
    public void onDamageTaken(DamageContainer damageContainer) {
        super.onDamageTaken(damageContainer);
        this.entityData.set(REMAINING_UPTIME, this.entityData.get(REMAINING_UPTIME) - (int)(damageContainer.getNewDamage() * 10));
    }

    @Override
    public void tick() {

        if (this.fwoosh)
        {
            fuelGolem(150);
            fwoosh = false;
        }

        if (this.moveDist > this.moveDist2)
        {
            this.playSound(SoundEvents.IRON_GOLEM_STEP);
            this.moveDist2 += 0.35f;
        }

        this.getEntityData().set(REMAINING_UPTIME, this.getEntityData().get(REMAINING_UPTIME) - 1);

        if (this.getEntityData().get(REMAINING_UPTIME) < 0) {
            if (this.getEntityData().get(ACTIVITY_SCALE) != 0)
            {
                this.playSound(ModSounds.SOUL_GOLEM_DEACTIVATE.get());
            }
            this.getEntityData().set(ACTIVITY_SCALE, 0);
        }
        else if (this.getEntityData().get(REMAINING_UPTIME) < 150) {
            this.getEntityData().set(ACTIVITY_SCALE, 1);
        }
        else {
            this.getEntityData().set(ACTIVITY_SCALE, 2);
        }

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
        super.defineSynchedData(builder
                .define(ACTIVITY_SCALE, 0)
                .define(REMAINING_UPTIME, 0)
                .define(ENTITY_STATE_TIMER, 0)
                .define(ENTITY_PICKUP_TIMER, 0));
    }

    private void setupAnimationStates()
    {
        this.inactiveAnimationState.startIfStopped(this.tickCount);
        this.idleAnimationState.startIfStopped(this.tickCount);
        this.walkAnimationState.startIfStopped(this.tickCount);
    }

    @Override
    protected void playStepSound(BlockPos pos, BlockState state) { }

    @Override
    protected @Nullable SoundEvent getHurtSound(@NotNull DamageSource damageSource) {
        return SoundEvents.IRON_GOLEM_HURT;
    }

    @Override
    protected @Nullable SoundEvent getDeathSound() {
        return SoundEvents.IRON_GOLEM_DEATH;
    }

    public static AttributeSupplier.Builder createAttributes()
    {
        return AbstractGolem.createLivingAttributes()
                .add(Attributes.MAX_HEALTH, 70D)
                .add(Attributes.FOLLOW_RANGE, 9D)
                .add(Attributes.ATTACK_DAMAGE, 8)
                .add(Attributes.ARMOR, 20)
                .add(Attributes.MOVEMENT_SPEED, 0.1D)
                .add(Attributes.KNOCKBACK_RESISTANCE, 1F);
    }
    class SoulGolemEntitySearchForItemsGoal extends Goal {
        public SoulGolemEntitySearchForItemsGoal() {
            this.setFlags(EnumSet.of(Flag.MOVE));
        }

        public boolean canUse() {
            if (!SoulGolemEntity.this.getItemBySlot(EquipmentSlot.MAINHAND).isEmpty()) {
                return false;
            } else if (SoulGolemEntity.this.getTarget() == null && SoulGolemEntity.this.getLastHurtByMob() == null) {
                if (SoulGolemEntity.this.getRandom().nextInt(reducedTickDelay(10)) != 0) {
                    return false;
                } else {
                    List<ItemEntity> list = SoulGolemEntity.this.level().getEntitiesOfClass(ItemEntity.class, SoulGolemEntity.this.getBoundingBox().inflate(8.0, 8.0, 8.0));
                    list.removeIf(a -> !a.getItem().is(SoulGolemEntity.ALLOWED_ITEMS));
                    return !list.isEmpty() && SoulGolemEntity.this.getItemBySlot(EquipmentSlot.MAINHAND).isEmpty();
                }
            } else {
                return false;
            }
        }

        public void tick() {

        }

        public void start() {
            List<ItemEntity> list = SoulGolemEntity.this.level().getEntitiesOfClass(ItemEntity.class, SoulGolemEntity.this.getBoundingBox().inflate(8.0, 8.0, 8.0));
            list.removeIf(a -> !a.getItem().is(SoulGolemEntity.ALLOWED_ITEMS));
            if (!list.isEmpty()) {
                SoulGolemEntity.this.getNavigation().moveTo((Entity)list.get(0), 1.2000000476837158);
            }

        }
    }
}
