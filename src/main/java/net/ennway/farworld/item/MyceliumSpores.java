//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package net.ennway.farworld.item;

import net.ennway.farworld.particle.SporeParticle;
import net.ennway.farworld.registries.ModParticles;
import net.ennway.farworld.registries.ModTags;
import net.ennway.farworld.utils.MathUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Direction.Plane;
import net.minecraft.core.Holder;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.BiomeTags;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.util.Mth;
import net.minecraft.util.ParticleUtils;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.BaseCoralWallFanBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.event.EventHooks;
import net.neoforged.neoforge.event.entity.player.BonemealEvent;

import javax.annotation.Nullable;

public class MyceliumSpores extends Item {

    public MyceliumSpores(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        Level level = context.getLevel();
        BlockPos pos = context.getClickedPos();

        if (level.getBlockState(pos).is(Blocks.GRASS_BLOCK))
        {
            level.playSound(context.getPlayer(), pos, SoundEvents.COMPOSTER_FILL, SoundSource.BLOCKS);
            level.setBlockAndUpdate(pos, Blocks.MYCELIUM.defaultBlockState());

            for (int i = 0; i < 5; i++) {
                level.addParticle(ModParticles.SPORE.get(), pos.getX() + MathUtils.randomDouble(level.random, 0, 1f), pos.getY() + 1, pos.getZ() + MathUtils.randomDouble(level.random, 0, 1f), 0, 0, 0);
            }

            context.getPlayer().swing(context.getHand());
            context.getItemInHand().consume(1, context.getPlayer());

            level.updateNeighborsAt(pos, Blocks.GRASS_BLOCK);

            BlockPos.MutableBlockPos moved = pos.mutable().move(0, 1, 0);
            if (level.getBlockState(moved).is(ModTags.TALL_GRASSES))
            {
                level.setBlockAndUpdate(moved, (Math.random() % 1.0d >= 0.5d) ? Blocks.RED_MUSHROOM.defaultBlockState() : Blocks.BROWN_MUSHROOM.defaultBlockState());
            }

            return InteractionResult.CONSUME;
        }

        return InteractionResult.PASS;
    }
}
