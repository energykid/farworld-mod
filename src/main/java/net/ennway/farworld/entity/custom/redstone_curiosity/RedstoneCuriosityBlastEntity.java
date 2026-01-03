package net.ennway.farworld.entity.custom.redstone_curiosity;

import net.ennway.farworld.entity.base.BaseSubattackEntity;
import net.ennway.farworld.registries.ModParticles;
import net.ennway.farworld.utils.MathUtils;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec2;
import net.minecraft.world.phys.Vec3;
import org.joml.Vector2f;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.animation.AnimatableManager;
import software.bernie.geckolib.animation.AnimationController;
import software.bernie.geckolib.animation.PlayState;
import software.bernie.geckolib.animation.RawAnimation;
import software.bernie.geckolib.util.GeckoLibUtil;

public class RedstoneCuriosityBlastEntity extends BaseSubattackEntity implements GeoEntity {
    public RedstoneCuriosityBlastEntity(EntityType<?> entityType, Level level) {
        super(entityType, level, 10, 0, 2, 7);
    }

    public float rot = 0f;
    public int blastFrame = 1;

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
        controllers.add(new AnimationController<>(this, "", this::bangAnimController));
    }

    @Override
    public void tick() {
        super.tick();
        blastFrame = blastFrame + 1;
        if (blastFrame > 7)
        {
            remove(RemovalReason.DISCARDED);
        }
    }

    @Override
    public boolean canHit(Entity potentialTarget) {
        if (potentialTarget instanceof RedstoneCuriosityEntity) return false;

        Vec3 pos = position();

        double rotate = Math.toRadians(-getEntityData().get(BaseSubattackEntity.ROTATION));
        for (int i = 0; i < 12; i++) {
            Vector2f rotVec = MathUtils.flatVec2FromRotation(rotate + Math.toRadians(90));
            pos = pos.add(new Vec3(rotVec.x() * 0.5, 0, rotVec.y() * 0.5));

            if (potentialTarget.distanceToSqr(pos) < 1) return true;
        }
        return false;
    }

    private final AnimatableInstanceCache geoCache = GeckoLibUtil.createInstanceCache(this);

    protected static final RawAnimation BANG_ANIM = RawAnimation.begin().thenPlayAndHold("bang");

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return geoCache;
    }

    protected <E extends RedstoneCuriosityBlastEntity> PlayState bangAnimController(final software.bernie.geckolib.animation.AnimationState<E> event) {
        return event.setAndContinue(BANG_ANIM);
    }
}
