package net.ennway.farworld.entity.custom.redstone_curiosity;

import net.ennway.farworld.entity.base.BaseSubattackEntity;
import net.ennway.farworld.registries.ModParticles;
import net.ennway.farworld.registries.ModSounds;
import net.ennway.farworld.utils.BehaviorUtils;
import net.ennway.farworld.utils.MathUtils;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import org.joml.Vector2f;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.animation.AnimatableManager;
import software.bernie.geckolib.animation.AnimationController;
import software.bernie.geckolib.animation.PlayState;
import software.bernie.geckolib.animation.RawAnimation;
import software.bernie.geckolib.util.GeckoLibUtil;

public class RedstoneCuriosityVerticalBlastEntity extends BaseSubattackEntity implements GeoEntity {
    public RedstoneCuriosityVerticalBlastEntity(EntityType<?> entityType, Level level) {
        super(entityType, level, 17, 15, 2, 7);
        this.canRender = false;
    }

    public boolean canRender = false;
    public int blastFrame = 1;

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
        controllers.add(new AnimationController<>(this, "", this::bangAnimController));
    }

    @Override
    public void tick() {
        super.tick();
        if (this.getEntityData().get(BaseSubattackEntity.ATTACK_TICKS) > this.delay)
        {
            this.canRender = true;
            this.blastFrame = this.blastFrame + 1;
            if (this.blastFrame > 7)
            {
                remove(RemovalReason.DISCARDED);
            }
        }
        else if (this.getEntityData().get(BaseSubattackEntity.ATTACK_TICKS) == 1)
        {
            BehaviorUtils.groundEntity(this);

            setOnGround(true);

            playSound(ModSounds.REDSTONE_CURIOSITY_TELEGRAPH.get());

            level().addParticle(ModParticles.REDSTONE_CURIOSITY_TELEGRAPH.get(), getX(), getY() + 0.015, getZ(), 0, 0, 0);
        }

        if (this.getEntityData().get(BaseSubattackEntity.ATTACK_TICKS) == delay + 1)
        {
            playSound(ModSounds.REDSTONE_CURIOSITY_RAIN_LAND.get(), 1f, (float)MathUtils.randomDouble(getRandom(), 0.8, 1.2));
        }
    }

    @Override
    public boolean canHit(Entity potentialTarget) {
        if (potentialTarget instanceof RedstoneCuriosityEntity) return false;

        Vec3 pos = position();

        float dist = 1;

        for (int i = 0; i < 6; i++) {
            pos = pos.add(new Vec3(0, 0.1, 0));

            if (new AABB(potentialTarget.position().x - dist, potentialTarget.position().y - dist, potentialTarget.position().z - dist, potentialTarget.position().x + dist, potentialTarget.position().y + dist, potentialTarget.position().z + dist).contains(pos)) return true;
        }
        return false;
    }

    private final AnimatableInstanceCache geoCache = GeckoLibUtil.createInstanceCache(this);

    protected static final RawAnimation BANG_ANIM = RawAnimation.begin().thenPlayAndHold("bang");

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return geoCache;
    }

    protected <E extends RedstoneCuriosityVerticalBlastEntity> PlayState bangAnimController(final software.bernie.geckolib.animation.AnimationState<E> event) {
        return event.setAndContinue(BANG_ANIM);
    }
}
