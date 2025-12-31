package net.ennway.farworld.entity.custom;

import net.ennway.farworld.entity.base.DelayedAttackingMonster;
import net.ennway.farworld.entity.control.GoliathMoveControl;
import net.ennway.farworld.entity.control.SlowRotMoveControl;
import net.ennway.farworld.entity.goal.AmethystConstructFindItemGoal;
import net.ennway.farworld.entity.goal.DelayedMeleeHurtGoal;
import net.ennway.farworld.entity.projectile.GloomstonePickup;
import net.ennway.farworld.registries.ModEntities;
import net.ennway.farworld.registries.ModParticles;
import net.ennway.farworld.registries.ModSounds;
import net.ennway.farworld.utils.MathUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.pathfinder.PathType;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.common.damagesource.DamageContainer;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class AmethystConstructEntity extends DelayedAttackingMonster {
    public static final EntityDataAccessor<ItemStack> ITEM_GRINDING = SynchedEntityData.defineId(AmethystConstructEntity.class, EntityDataSerializers.ITEM_STACK);
    public static final EntityDataAccessor<Integer> ITEM_GRIND_TICKS = SynchedEntityData.defineId(AmethystConstructEntity.class, EntityDataSerializers.INT);

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

    public void slamAt(BlockPos pos, int maxDist)
    {
        for (int i = -maxDist; i <= maxDist; i++) {
            for (int j = -maxDist; j <= maxDist; j++) {
                for (int k = maxDist; k > -maxDist; k--) {
                    BlockPos blockPos = new BlockPos(pos.getX() + i, pos.getY() + k, pos.getZ() + j);

                    if (level().getBlockState(blockPos).entityCanStandOn(level(), blockPos, this))
                    {
                        float dist = Mth.sqrt((Mth.abs(i)^2) + (Mth.abs(j)^2));

                        if (dist < maxDist / 2f)
                        {
                            level().addDestroyBlockEffect(blockPos, level().getBlockState(blockPos));
                        }
                    }
                }
            }
        }
    }

    static class AmethystConstructDelayedHurtGoal extends DelayedMeleeHurtGoal
    {
        public AmethystConstructDelayedHurtGoal(PathfinderMob entity) {
            super(entity, 1.5, false);
            this.attackDelay = 16;
            this.attackLength = 30;
            this.attackRange = 15;
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

        this.goalSelector.addGoal(2, new AmethystConstructDelayedHurtGoal(this));
        this.goalSelector.addGoal(3, new WaterAvoidingRandomStrollGoal(this, 1.0));
        this.goalSelector.addGoal(4, new LookAtPlayerGoal(this, Player.class, 8.0F));
        this.goalSelector.addGoal(5, new RandomLookAroundGoal(this));
        this.targetSelector.addGoal(1, new NearestAttackableTargetGoal(this, Player.class, true));
    }

    @Override
    public void onDamageTaken(DamageContainer damageContainer) {
        super.onDamageTaken(damageContainer);
    }

    public double levelValueFromItem(ItemStack stack)
    {
        if (stack.getMaxStackSize() == 1)
        {
            return 15;
        }
        else
        {
            return ((float)stack.getCount()) / 8f;
        }
    }

    public void spawnExperienceForItem()
    {
        for (int i = 0; i < levelValueFromItem(this.getEntityData().get(ITEM_GRINDING)); i++)
        {
            ExperienceOrb proj = new ExperienceOrb(level(), getX(), getY() + 0.5, getZ(), 10);
            proj.setDeltaMovement(getLookAngle());
            proj.addDeltaMovement(new Vec3(
                    MathUtils.randomDouble(getRandom(), -0.5, 0.5),
                    MathUtils.randomDouble(getRandom(), -0.5, 0.5),
                    MathUtils.randomDouble(getRandom(), -0.5, 0.5)
            ));
            level().addFreshEntity(proj);
        }
        this.getEntityData().set(ITEM_GRINDING, ItemStack.EMPTY);
    }

    @Override
    public void tick() {
        super.tick();

        List<ItemEntity> items = this.level().getEntitiesOfClass(ItemEntity.class,
                new AABB(
                        this.getX() - 12, this.getY() - 12, this.getZ() - 12,
                        this.getX() + 12, this.getY() + 12, this.getZ() + 12
                ));

        if (!items.isEmpty())
        {
            this.goalSelector.addGoal(1, new AmethystConstructFindItemGoal(this));
        }

        if (this.level().isClientSide) {
            setupAnimationStates();

            if (getEntityData().get(DelayedAttackingMonster.ATTACK_TICKS) == 1)
            {
                this.attackAnimationState.start(this.tickCount);
            }
            if (getEntityData().get(ITEM_GRIND_TICKS) == 1)
            {
                this.eatSpitAnimationState.start(this.tickCount);
            }
            if (getEntityData().get(DelayedAttackingMonster.ATTACK_TICKS) == 16)
            {
                slamAt(blockPosition(), 3);
            }
        }

        if (this.getEntityData().get(ITEM_GRINDING) != ItemStack.EMPTY) {

            this.getEntityData().set(DelayedAttackingMonster.ATTACK_TICKS, 0);

            if (this.getEntityData().get(ITEM_GRIND_TICKS) % 12 == 2 && this.getEntityData().get(ITEM_GRIND_TICKS) < 30) {
                playSound(ModSounds.AMETHYST_CONSTRUCT_CRUNCH.get());
            }

            this.getEntityData().set(ITEM_GRIND_TICKS, this.getEntityData().get(ITEM_GRIND_TICKS) + 1);

            if (this.getEntityData().get(ITEM_GRIND_TICKS) == 36) {
                playSound(ModSounds.AMETHYST_CONSTRUCT_SPIT.get());
            }
            if (this.getEntityData().get(ITEM_GRIND_TICKS) > 40) {
                spawnExperienceForItem();
                this.getEntityData().set(ITEM_GRIND_TICKS, 0);
            }
            this.getNavigation().stop();
        }
        else
        {
            if (this.getTarget() != null)
                this.getNavigation().moveTo(this.getTarget(), 1.5);
        }
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        super.defineSynchedData(builder
                .define(DelayedAttackingMonster.ATTACK_TICKS, 0)
                .define(ITEM_GRINDING, ItemStack.EMPTY)
                .define(ITEM_GRIND_TICKS, 0));
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
                .add(Attributes.MAX_HEALTH, 28D)
                .add(Attributes.FOLLOW_RANGE, 10D)
                .add(Attributes.ATTACK_DAMAGE, 7)
                .add(Attributes.ARMOR, 8)
                .add(Attributes.MOVEMENT_SPEED, 0.15D);
    }
}
