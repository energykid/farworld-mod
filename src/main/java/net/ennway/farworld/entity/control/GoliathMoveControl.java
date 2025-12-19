package net.ennway.farworld.entity.control;
import net.ennway.farworld.entity.custom.GoliathEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.MoveControl;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.pathfinder.NodeEvaluator;
import net.minecraft.world.level.pathfinder.PathType;
import net.minecraft.world.level.pathfinder.PathfindingContext;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.VoxelShape;


public class GoliathMoveControl extends MoveControl {
    public GoliathMoveControl(Mob mob) {
        super(mob);
    }

    private float additionalRot;

    public void tick() {
        float spd = this.mob.getEntityData().get(GoliathEntity.MOVE_SPEED_MULT);

        if (this.operation == Operation.STRAFE) {
            float f = (float) this.mob.getAttributeValue(Attributes.MOVEMENT_SPEED);
            float f1 = (float) this.speedModifier * f;
            float f2 = this.strafeForwards;
            float f3 = this.strafeRight;
            float f4 = Mth.sqrt(f2 * f2 + f3 * f3);
            if (f4 < 1.0F) {
                f4 = 1.0F;
            }
            f4 = f1 / f4;
            f2 *= f4;
            f3 *= f4;
            float f5 = Mth.sin(this.mob.getYRot() * ((float) Math.PI / 180F));
            float f6 = Mth.cos(this.mob.getYRot() * ((float) Math.PI / 180F));
            float f7 = f2 * f6 - f3 * f5;
            float f8 = f3 * f6 + f2 * f5;
            if (!this.isWalkable(f7, f8)) {
                this.strafeForwards = 1.0F;
                this.strafeRight = 0.0F;
            }
            this.mob.setSpeed(f1);
            this.mob.setZza(this.strafeForwards);
            this.mob.setXxa(this.strafeRight);
            this.operation = Operation.WAIT;
        } else if (this.operation == Operation.MOVE_TO) {
            this.operation = Operation.WAIT;
            double d0 = this.wantedX - this.mob.getX();
            double d1 = this.wantedZ - this.mob.getZ();
            double d2 = this.wantedY - this.mob.getY();
            double d3 = d0 * d0 + d2 * d2 + d1 * d1;
            if (d3 < (double) 2.5000003E-7F) {
                this.mob.setZza(0);
                if (additionalRot > 0)
                    additionalRot -= 0.03f;
                return;
            }
            float f9 = (float) (Mth.atan2(d1, d0) * (double) (180F / (float) Math.PI)) - 90.0F;
            if (additionalRot < 1)
                additionalRot += 0.1f;
            this.mob.setYRot(this.rotlerp(this.mob.getYRot(), f9, 5 + additionalRot * 5));
            this.mob.setSpeed(((float) (this.speedModifier * this.mob.getAttributeValue(Attributes.MOVEMENT_SPEED)) * spd));
            BlockPos blockpos = this.mob.blockPosition();
            BlockState blockstate = this.mob.level().getBlockState(blockpos);
            VoxelShape voxelshape = blockstate.getCollisionShape(this.mob.level(), blockpos);
            if (d2 > (double) this.mob.maxUpStep() && d0 * d0 + d1 * d1 < (double) Math.max(1.0F, this.mob.getBbWidth())
                    || !voxelshape.isEmpty() && this.mob.getY() < voxelshape.max(Direction.Axis.Y) + (double) blockpos.getY() && !blockstate.is(BlockTags.DOORS) && !blockstate.is(BlockTags.FENCES)) {
                this.mob.getJumpControl().jump();
                this.operation = Operation.JUMPING;
            }
            if (this.mob.horizontalCollision)
            {
                this.mob.setDeltaMovement(new Vec3(this.mob.getDeltaMovement().x, 0.25f, this.mob.getDeltaMovement().z));
            }
        } else if (this.operation == Operation.JUMPING) {
            this.mob.setSpeed((float) (this.speedModifier * this.mob.getAttributeValue(Attributes.MOVEMENT_SPEED) * spd));
            if (this.mob.onGround()) {
                this.operation = Operation.WAIT;
            }
        } else {
            this.mob.setZza(0);
            if (additionalRot > 0)
                additionalRot -= 0.03f;
        }
    }

    private boolean isWalkable(float p_24997_, float p_24998_) {
        PathNavigation pathnavigation = this.mob.getNavigation();
        if (pathnavigation != null) {
            NodeEvaluator nodeevaluator = pathnavigation.getNodeEvaluator();
            if (nodeevaluator != null && nodeevaluator.getPathType(new PathfindingContext(this.mob.level(), this.mob), Mth.floor(this.mob.getX() + (double) p_24997_), this.mob.getBlockY(), Mth.floor(this.mob.getZ() + (double) p_24998_)) != PathType.WALKABLE) {
                return false;
            }
        }
        return true;
    }
}

