package net.ennway.farworld.entity.custom;

import net.ennway.farworld.entity.custom.redstone_curiosity.RedstoneCuriosityEntity;
import net.ennway.farworld.registries.ModEntities;
import net.ennway.farworld.registries.ModSounds;
import net.ennway.farworld.utils.MathUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerEntity;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageSources;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.attachment.AttachmentType;
import org.jetbrains.annotations.NotNull;
import org.w3c.dom.Attr;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.animation.*;
import software.bernie.geckolib.util.GeckoLibUtil;

import java.util.List;

public class SludgeEntity extends Monster implements GeoEntity {

    @Override
    protected AABB getAttackBoundingBox() {
        float size = 0.6f;
        return new AABB(this.position().x - size, this.getEyePosition().y - size, this.position().z - size, this.position().x + size, this.getEyePosition().y + size, this.position().z + size);
    }

    public float sc = 1f;

    public static final EntityDataAccessor<Integer> JUMP_TICKS = SynchedEntityData.defineId(SludgeEntity.class, EntityDataSerializers.INT);

    public static final EntityDataAccessor<Integer> JUMPS = SynchedEntityData.defineId(SludgeEntity.class, EntityDataSerializers.INT);

    public static final EntityDataAccessor<String> GEM_CONTAINED = SynchedEntityData.defineId(SludgeEntity.class, EntityDataSerializers.STRING);

    public SludgeEntity(EntityType<? extends Monster> entityType, Level level) {
        super(entityType, level);
        List<String> gems = List.of("emerald", "lapis", "amethyst");
        int i = getRandom().nextIntBetweenInclusive(0, 2);
        if (level instanceof ServerLevel)
        {
            this.getEntityData().set(GEM_CONTAINED, gems.get(i));

            if (this.getEntityData().get(GEM_CONTAINED).equals("emerald"))
            {
                this.setItemSlotAndDropWhenKilled(EquipmentSlot.MAINHAND, new ItemStack(Items.EMERALD, getRandom().nextIntBetweenInclusive(1, 3)));
            }
            if (this.getEntityData().get(GEM_CONTAINED).equals("lapis"))
            {
                this.setItemSlotAndDropWhenKilled(EquipmentSlot.MAINHAND, new ItemStack(Items.LAPIS_LAZULI, getRandom().nextIntBetweenInclusive(1, 3)));
            }
            if (this.getEntityData().get(GEM_CONTAINED).equals("amethyst"))
            {
                this.setItemSlotAndDropWhenKilled(EquipmentSlot.MAINHAND, new ItemStack(Items.AMETHYST_SHARD, getRandom().nextIntBetweenInclusive(1, 3)));
            }
        }
    }

    @Override
    public void aiStep() {
        if (level() instanceof ServerLevel) {
            if (!this.isDeadOrDying()) {
                if (this.onGround()) {
                    this.getEntityData().set(JUMP_TICKS, this.getEntityData().get(JUMP_TICKS) + 1);
                    if (this.getEntityData().get(JUMP_TICKS) == 20) {
                        Vec3 v = new Vec3(MathUtils.randomDouble(getRandom(), 0, 6) - 3, 0, MathUtils.randomDouble(getRandom(), 0, 6) - 3).normalize().multiply(0.25, 0, 0.25).add(0, 0.6, 0);
                        this.setOnGround(false);
                        this.hurtMarked = true;
                        if (this.getTarget() != null) {
                            this.getEntityData().set(JUMPS, this.getEntityData().get(JUMPS) + 1);
                            v = this.getTarget().position().subtract(this.position()).normalize().multiply(0.25, 0, 0.25).add(0, 0.6, 0);
                            if (this.getEntityData().get(JUMPS) >= 3) {
                                this.playSound(ModSounds.SLUDGE_WOBBLE.get());
                                v = this.getTarget().position().subtract(this.position()).normalize().multiply(0.15, 0, 0.15).add(0, 1.1, 0);
                            }
                        }
                        this.addDeltaMovement(v);
                        this.playSound(SoundEvents.SLIME_JUMP);
                        if (level() instanceof ServerLevel)
                            triggerAnim("jump_controller", "jump");
                    }

                    if (this.getEntityData().get(JUMP_TICKS) == 24) {
                        this.getEntityData().set(JUMP_TICKS, -getRandom().nextIntBetweenInclusive(0, 10));
                        if (this.getEntityData().get(JUMPS) >= 3) {
                            this.playSound(ModSounds.SLUDGE_CRASH.get());
                            this.getEntityData().set(JUMPS, 0);
                            if (level() instanceof ServerLevel)
                                triggerAnim("jump_controller", "crash");
                        }
                        else {
                            if (level() instanceof ServerLevel)
                                triggerAnim("jump_controller", "land");
                        }
                        this.playSound(SoundEvents.SLIME_SQUISH);
                    }
                } else {
                    if (this.getTarget() != null && this.getEntityData().get(JUMPS) >= 3 && this.getDeltaMovement().y > 0) {
                        Vec3 v = this.getTarget().position().subtract(this.position()).multiply(0.15, 0, 0.15);
                        this.setDeltaMovement(v.x, this.getDeltaMovement().y, v.z);
                    }
                }
            }
            if (!this.onGround())
            {
                this.absRotateTo((float)MathUtils.entityLookAngle(getDeltaMovement()), 0f);
            }
        }
        super.aiStep();
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource damageSource) {
        return SoundEvents.SLIME_HURT;
    }

    @Override
    protected SoundEvent getDeathSound() {
        return SoundEvents.SLIME_DEATH;
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        super.defineSynchedData(builder
                .define(JUMP_TICKS, 0)
                .define(JUMPS, 0)
                .define(GEM_CONTAINED, "emerald"));
    }

    @Override
    public void addAdditionalSaveData(@NotNull CompoundTag compound) {
        compound.putString("Gemstone", this.getEntityData().get(GEM_CONTAINED));
        super.addAdditionalSaveData(compound);
    }

    @Override
    public void readAdditionalSaveData(@NotNull CompoundTag compound) {
        super.readAdditionalSaveData(compound);
        this.getEntityData().set(GEM_CONTAINED, compound.getString("Gemstone"));
    }

    protected static final RawAnimation IDLE_ANIM = RawAnimation.begin().thenLoop("idle");
    protected static final RawAnimation JUMP_ANIM = RawAnimation.begin().thenPlayAndHold("jump");
    protected static final RawAnimation LAND_ANIM = RawAnimation.begin().thenPlayAndHold("land");
    protected static final RawAnimation CRASH_ANIM = RawAnimation.begin().thenPlayAndHold("crash");

    protected <E extends SludgeEntity> PlayState idleAnimController(final AnimationState<E> event) {
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
                .add(Attributes.MAX_HEALTH, 20)
                .add(Attributes.ARMOR, 3)
                .add(Attributes.FOLLOW_RANGE, 20)
                .add(Attributes.ATTACK_DAMAGE, 6)
                .add(Attributes.MOVEMENT_SPEED, 0)
                .add(Attributes.SAFE_FALL_DISTANCE, 15)
                .add(Attributes.FALL_DAMAGE_MULTIPLIER, 0.5);
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {

        controllers.add(new AnimationController<>(this, "", this::idleAnimController));

        controllers.add(new AnimationController<>(this, "jump_controller", animTest -> PlayState.STOP)
                .triggerableAnim("jump", JUMP_ANIM)
                .triggerableAnim("land", LAND_ANIM)
                .triggerableAnim("crash", CRASH_ANIM));
    }

    private final AnimatableInstanceCache geoCache = GeckoLibUtil.createInstanceCache(this);
    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return geoCache;
    }
}
