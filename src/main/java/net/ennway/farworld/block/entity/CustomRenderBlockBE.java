package net.ennway.farworld.block.entity;

import net.ennway.farworld.registries.ModBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class CustomRenderBlockBE extends BlockEntity {
    public CustomRenderBlockBE(BlockPos pos, BlockState state) {
        super(ModBlockEntities.CUSTOM_RENDER_BE.get(), pos, state);
    }
}
