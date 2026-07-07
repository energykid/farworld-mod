package net.ennway.farworld.entity.custom;

import net.ennway.farworld.entity.base.BaseSubattackEntity;
import net.ennway.farworld.registries.ModParticles;
import net.ennway.farworld.utils.MathUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import org.joml.Vector2f;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.animation.AnimatableManager;
import software.bernie.geckolib.util.GeckoLibUtil;

public class ScrappedLaserEntity extends BaseSubattackEntity implements GeoEntity {
    public static ScrappedLaserEntity newForDefinition(EntityType<?> entityType, Level level) {
        return new ScrappedLaserEntity(entityType, level);
    }

    public ScrappedLaserEntity(EntityType<?> entityType, Level level) {
        super(entityType, level, 6, 0, 3, 40);
    }

    public ScrappedLaserEntity(EntityType<?> entityType, Level level, Vec3 pos, Vec3 finder) {
        super(entityType, level, 6, 0, 3, 40);
        setPos(pos);
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        super.defineSynchedData(builder);
    }

    public float distInBlocks = 0f;
    public float visualDistInBlocks = 0f;
    public float scale = 1f;
    public float visualScale = 1f;

    @Override
    public boolean canHit(Entity potentialTarget) {
        if (potentialTarget == getOwner()) return false;

        Vec3 pos = position();

        for (int i = 0; i < distInBlocks; i++) {
            Vec3 finderVec = getDirectionVector();

            pos = pos.add(finderVec);

            double dist = 0.6;

            AABB bb = potentialTarget.getBoundingBox();
            AABB thisone = new AABB(pos.x - dist, pos.y - dist, pos.z - dist, pos.x + dist, pos.y + dist, pos.z + dist);

            if (bb.intersects(thisone)) return true;
        }
        return false;
    }

    @Override
    public void tick() {
        for (double i = 0; i < distInBlocks; i++)
        {
            Vec3 v = position().add(getDirectionVector().multiply(i, i, i));
            if (random.nextBoolean()) {
                level().addParticle(ModParticles.REDSTONE_CURIOSITY_PARTICLE.get(), v.x, v.y, v.z, 0, 0, 0);
            }
        }

        for (double i = 0; i < 4; i += 0.5)
        {
            Vec3 p = position().add(getDirectionVector().multiply(new Vec3(distInBlocks, distInBlocks, distInBlocks)));
            if (!level().getBlockState(BlockPos.containing(p)).isSolid())
                distInBlocks+=0.5f;
        }

        Vec3 v = position().add(getDirectionVector().multiply(distInBlocks, distInBlocks, distInBlocks));
        level().addParticle(ModParticles.SCRAPPED_LASER_STREAK.get(), v.x, v.y, v.z, getDirectionVector().x, getDirectionVector().y, getDirectionVector().z);

        scale *= 0.5f;
        if (scale <= 0.1f) remove(RemovalReason.DISCARDED);

        super.tick();
    }

    private final AnimatableInstanceCache geoCache = GeckoLibUtil.createInstanceCache(this);

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {}

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return geoCache;
    }
}
