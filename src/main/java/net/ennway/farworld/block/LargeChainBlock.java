package net.ennway.farworld.block;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;

import java.util.function.Function;

public class LargeChainBlock extends RotatedPillarBlock {

    public LargeChainBlock(Properties p_52591_) {
        super(p_52591_);
    }

    @Override
    protected VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        if (state.getValue(RotatedPillarBlock.AXIS) == Direction.Axis.Y)
        {
            return Block.box(3.0, 0.0, 3.0, 14.0, 16.0, 14.0);
        }
        else
        if (state.getValue(RotatedPillarBlock.AXIS) == Direction.Axis.X)
        {
            return Block.box(0, 3.0, 3.0, 16.0, 14.0, 14.0);
        }
        else
        {
            return Block.box(3.0, 3.0, 0, 14.0, 14.0, 16.0);
        }
    }

    @Override
    protected BlockState updateShape(BlockState state, Direction facing, BlockState facingState, LevelAccessor level, BlockPos currentPos, BlockPos facingPos)
    {
        return super.updateShape(state, facing, facingState, level, currentPos, facingPos);
    }

    public static <B extends Block> MapCodec<B> simpleCodec(@NotNull Function<Properties, B> factory) {
    return RecordCodecBuilder.mapCodec((p_304392_) -> {
        return p_304392_.group(propertiesCodec()).apply(p_304392_, factory);
    });
    }
}