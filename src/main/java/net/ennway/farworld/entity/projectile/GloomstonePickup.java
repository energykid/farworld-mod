package net.ennway.farworld.entity.projectile;

import net.ennway.farworld.registries.ModSounds;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

public class GloomstonePickup extends Entity {

    public GloomstonePickup(EntityType<?> entityType, Level level) {
        super(entityType, level);
        this.setNoGravity(true);
    }

    public static final EntityDataAccessor<String> PLAYER_NAME = SynchedEntityData.defineId(GloomstonePickup.class, EntityDataSerializers.STRING);
    private static final EntityDataAccessor<Integer> TIMER = SynchedEntityData.defineId(GloomstonePickup.class, EntityDataSerializers.INT);

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        builder.define(PLAYER_NAME, "")
                .define(TIMER, 0)
                .build();
    }

    @Override
    protected void readAdditionalSaveData(CompoundTag compoundTag) { }

    @Override
    protected void addAdditionalSaveData(CompoundTag compoundTag) { }

    public void applyToPlayer(Player plr)
    {
        this.playSound(ModSounds.GLOOMSTONE_PICKUP.get());
        plr.heal(2f);
    }

    @Override
    public void tick() {
        super.tick();

        this.xo = this.getX();
        this.yo = this.getY();
        this.zo = this.getZ();

        this.getEntityData().set(TIMER, this.getEntityData().get(TIMER) + 1);

        this.setDeltaMovement(this.getDeltaMovement().add(0, -0.05, 0).multiply(0.9, 0.9, 0.9));

        if (this.getEntityData().get(TIMER) > 5)
        {
            float f = Math.min((float)(this.getEntityData().get(TIMER)- 5) / 15f, 1f);

            if (!this.getEntityData().get(PLAYER_NAME).isEmpty())
            {
                var players = this.level().getEntitiesOfClass(Player.class, new AABB(
                        this.position().x - 3.5f,
                        this.position().y - 3f,
                        this.position().z - 3.5f,
                        this.position().x + 3.5f,
                        this.position().y + 3f,
                        this.position().z + 3.5f));

                for (Player plr : players) {
                    if (plr.getName().getString().equals(this.getEntityData().get(PLAYER_NAME))) {
                        this.setDeltaMovement(
                                this.getDeltaMovement().add(new Vec3((plr.getX() - getX()) * 0.1, (plr.getY() - getY()) * 0.1, (plr.getZ() - getZ()) * 0.1).multiply(f,f,f))
                        );

                        if (this.distanceTo(plr) < 0.8) {
                            applyToPlayer(plr);
                            discard();
                        }

                        break;
                    }
                }
            }
        }

        this.move(MoverType.SELF, this.getDeltaMovement());
    }
}
