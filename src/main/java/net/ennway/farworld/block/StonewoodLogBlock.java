package net.ennway.farworld.block;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.ennway.farworld.registries.ModBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.neoforged.neoforge.common.ItemAbility;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.function.Function;

public class StonewoodLogBlock extends RotatedPillarBlock {
    public static Block strippedVersion;

    public StonewoodLogBlock(Block stripped, Properties p_52591_) {
        super(p_52591_);
        strippedVersion = stripped;
    }

    public static <B extends Block> MapCodec<B> simpleCodec(@NotNull Function<Properties, B> factory) {
    return RecordCodecBuilder.mapCodec((p_304392_) -> {
        return p_304392_.group(propertiesCodec()).apply(p_304392_, factory);
    });
    }

    @Override
    protected ItemInteractionResult useItemOn(ItemStack stack, BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hitResult) {
        if (stack.is(ItemTags.AXES))
        {
            var ax = level.getBlockState(pos).getValue(RotatedPillarBlock.AXIS);

            level.playSound(player, pos, SoundEvents.AXE_STRIP, SoundSource.BLOCKS);
            level.setBlock(pos, strippedVersion.defaultBlockState().setValue(RotatedPillarBlock.AXIS, ax), 0);

            return ItemInteractionResult.SUCCESS;
        }

        return super.useItemOn(stack, state, level, pos, player, hand, hitResult);
    }
}