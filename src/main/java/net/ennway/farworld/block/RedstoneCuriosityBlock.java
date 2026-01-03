package net.ennway.farworld.block;

import net.ennway.farworld.entity.custom.redstone_curiosity.RedstoneCuriosityEntity;
import net.ennway.farworld.registries.ModEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.VoxelShape;

public class RedstoneCuriosityBlock extends Block {
    public RedstoneCuriosityBlock(Properties properties) {
        super(properties);
    }

    @Override
    protected VoxelShape getBlockSupportShape(BlockState state, BlockGetter level, BlockPos pos) {
        return Block.box(2, 2, 2, 14, 14, 14);
    }

    @Override
    protected void neighborChanged(BlockState state, Level level, BlockPos pos, Block block, BlockPos fromPos, boolean isMoving) {

        if (level.hasNeighborSignal(pos)) {
            if (!level.isClientSide) {
                level.destroyBlock(pos, false);
                RedstoneCuriosityEntity entity = new RedstoneCuriosityEntity(ModEntities.REDSTONE_CURIOSITY.get(), level);
                entity.setPos(pos.getBottomCenter());
                level.addFreshEntity(entity);
            }
        }
    }
}
