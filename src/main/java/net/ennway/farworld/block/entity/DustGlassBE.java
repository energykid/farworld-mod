package net.ennway.farworld.block.entity;

import net.ennway.farworld.registries.ModBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

public class DustGlassBE extends BlockEntity {

    public DustGlassBE(BlockPos pos, BlockState state) {
        super(ModBlockEntities.DUST_GLASS_BE.get(), pos, state);
    }
}
