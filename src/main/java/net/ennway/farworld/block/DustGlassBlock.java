package net.ennway.farworld.block;

import net.ennway.farworld.block.entity.DustGlassBE;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.TransparentBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

public class DustGlassBlock extends TransparentBlock implements EntityBlock {
    public DustGlassBlock(Properties p_309186_) {
        super(p_309186_);
    }

    @Override
    public @Nullable BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new DustGlassBE(pos, state);
    }
}
