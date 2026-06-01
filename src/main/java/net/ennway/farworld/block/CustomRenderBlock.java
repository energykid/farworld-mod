package net.ennway.farworld.block;

import com.mojang.blaze3d.vertex.PoseStack;
import net.ennway.farworld.block.entity.CustomRenderBlockBE;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

public class CustomRenderBlock extends Block implements EntityBlock {
    public CustomRenderBlock(Properties p_309186_) {
        super(p_309186_);
    }

    @Override
    public @Nullable BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new CustomRenderBlockBE(pos, state);
    }

    public void render(Level lvl, BlockPos pos, BlockState state, PoseStack stack) {}
}
