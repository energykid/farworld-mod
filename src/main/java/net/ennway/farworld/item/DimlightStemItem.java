package net.ennway.farworld.item;

import net.ennway.farworld.block.MilkBerryCropBlock;
import net.ennway.farworld.registries.ModBlocks;
import net.minecraft.core.Direction;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

public class DimlightStemItem extends BlockItem {
    public DimlightStemItem(Block block, Properties properties) {
        super(ModBlocks.DIMLIGHT_STEM.get(), properties);
    }

    @Override
    protected boolean canPlace(BlockPlaceContext context, BlockState state) {
        BlockState state2 = context.getLevel().getBlockState(context.getClickedPos());
        if (context.getClickedFace().getAxis() != Direction.Axis.Y) return false;
        return MilkBerryCropBlock.canGrowOn(state2) || state2.is(ModBlocks.DIMLIGHT);
    }
}
