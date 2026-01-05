package net.ennway.farworld.entity.projectile;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.ennway.farworld.entity.client.brittle.BrittleModel;
import net.ennway.farworld.entity.custom.BloomedEntity;
import net.ennway.farworld.entity.custom.BrittleEntity;
import net.ennway.farworld.registries.ModDataComponents;
import net.ennway.farworld.registries.ModEntities;
import net.ennway.farworld.registries.ModParticles;
import net.ennway.farworld.registries.ModSounds;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSources;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.projectile.AbstractHurtingProjectile;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.common.Tags;

public class BlackIceImplosionProjectile extends Projectile {
    public BlackIceImplosionProjectile(EntityType<? extends Projectile> entityType, Level level) {
        super(entityType, level);
    }
    private static final EntityDataAccessor<Integer> TIMER = SynchedEntityData.defineId(BlackIceImplosionProjectile.class, EntityDataSerializers.INT);

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        builder.define(TIMER, 0)
                .build();
    }

    @Override
    public void tick() {
        super.tick();
        int timer = this.getEntityData().get(TIMER);

        if (timer == 1) {
            this.playSound(ModSounds.BLACK_ICE_INWARDS.get());
        }
        if (timer == 6)
        {
            this.playSound(ModSounds.BLACK_ICE_OUTWARDS.get());


            this.level().addParticle(ModParticles.BLACK_ICE_WORMHOLE.get(), this.position().x, this.position().y, this.position().z, 0,0,0);

            AABB mobs = new AABB(
                    this.position().add(new Vec3(-3, -3, -3)),
                    this.position().add(new Vec3(3, 3, 3))
            );

            for (Entity mob : level().getEntitiesOfClass(Mob.class, mobs))
            {
                if (mob instanceof OwnableEntity ownable)
                {
                    if (ownable.getOwner() == null)
                    {
                        mob.hurt(mob.damageSources().magic(), 6);
                    }
                }
                else
                {
                    mob.hurt(mob.damageSources().magic(), 6);
                }
            }
        }
        if (timer < 4)
        {
            int dist = (int)Mth.lerp(((float)timer) / 6.0, 5f, 20f);

            for (int i = 0; i < 3; i++) {
                Vec3 pos = this.position().add(new Vec3(
                        this.random.nextInt(-dist, dist) / 5.0,
                        this.random.nextInt(-dist, dist) / 5.0,
                        this.random.nextInt(-dist, dist) / 5.0
                ));

                Vec3 vel = this.position().subtract(pos).multiply(0.2, 0.2, 0.2);

                this.level().addParticle(ModParticles.BLACK_ICE_AOE.get(), true, pos.x, pos.y, pos.z, vel.x, vel.y, vel.z);
            }
        }

        if (timer == 7)
        {
            this.remove(RemovalReason.DISCARDED);
        }

        this.getEntityData().set(TIMER, timer + 1);
    }
}
