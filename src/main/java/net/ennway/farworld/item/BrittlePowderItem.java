//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package net.ennway.farworld.item;

import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Holder;
import net.minecraft.core.Direction.Plane;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.BiomeTags;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
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
import net.neoforged.neoforge.event.EventHooks;
import net.neoforged.neoforge.event.entity.player.BonemealEvent;

public class BrittlePowderItem extends Item {
    public static final int GRASS_SPREAD_WIDTH = 3;
    public static final int GRASS_SPREAD_HEIGHT = 1;
    public static final int GRASS_COUNT_MULTIPLIER = 3;

    public static final TagKey<Block> CROPS = TagKey.create(
            BuiltInRegistries.BLOCK.key(),
            ResourceLocation.withDefaultNamespace("crops"));

    public BrittlePowderItem(Item.Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        Level level = context.getLevel();
        BlockPos blockpos = context.getClickedPos();
        BlockPos blockpos1 = blockpos.relative(context.getClickedFace());
        BlockState blockstate = level.getBlockState(blockpos);

        if (!blockstate.is((CROPS)))
            return InteractionResult.PASS;

        if (applyBonemeal(context.getItemInHand(), level, blockpos, context.getPlayer())) {
            if (!level.isClientSide && blockstate.is(CROPS)) {
                context.getPlayer().gameEvent(GameEvent.ITEM_INTERACT_FINISH);
                level.levelEvent(1505, blockpos, 15);
                context.getPlayer().gameEvent(GameEvent.ITEM_INTERACT_FINISH);
                level.levelEvent(1505, blockpos, 15);
                context.getPlayer().gameEvent(GameEvent.ITEM_INTERACT_FINISH);
                level.levelEvent(1505, blockpos, 15);
            }

            return InteractionResult.sidedSuccess(level.isClientSide);
        }

        return InteractionResult.PASS;
    }

    public static boolean applyBonemeal(ItemStack p_40628_, Level p_40629_, BlockPos p_40630_, @Nullable Player player) {
        BlockState blockstate = p_40629_.getBlockState(p_40630_);

        boolean ret = false;

        {
            BonemealEvent event = EventHooks.fireBonemealEvent(player, p_40629_, p_40630_, blockstate, p_40628_);
            if (event.isCanceled()) {
                return event.isSuccessful();
            } else {
                Block var7 = blockstate.getBlock();
                if (var7 instanceof BonemealableBlock) {
                    BonemealableBlock bonemealableblock = (BonemealableBlock)var7;
                    if (bonemealableblock.isValidBonemealTarget(p_40629_, p_40630_, blockstate)) {
                        if (p_40629_ instanceof ServerLevel) {
                            if (bonemealableblock.isBonemealSuccess(p_40629_, p_40629_.random, p_40630_, blockstate)) {
                                bonemealableblock.performBonemeal((ServerLevel)p_40629_, p_40629_.random, p_40630_, blockstate);
                            }
                        }

                        if (player.getRandom().nextFloat() < 0.66f)
                            p_40628_.consume(1, player);

                        ret = true;
                    }
                }
            }
        }

        return ret;
    }

    public static boolean growWaterPlant(ItemStack stack, Level level, BlockPos pos, @Nullable Direction clickedSide) {
        if (level.getBlockState(pos).is(Blocks.WATER) && level.getFluidState(pos).getAmount() == 8) {
            if (!(level instanceof ServerLevel)) {
                return true;
            } else {
                RandomSource randomsource = level.getRandom();

                label77:
                for(int i = 0; i < 128; ++i) {
                    BlockPos blockpos = pos;
                    BlockState blockstate = Blocks.SEAGRASS.defaultBlockState();

                    for(int j = 0; j < i / 16; ++j) {
                        blockpos = blockpos.offset(randomsource.nextInt(3) - 1, (randomsource.nextInt(3) - 1) * randomsource.nextInt(3) / 2, randomsource.nextInt(3) - 1);
                        if (level.getBlockState(blockpos).isCollisionShapeFullBlock(level, blockpos)) {
                            continue label77;
                        }
                    }

                    Holder<Biome> holder = level.getBiome(blockpos);
                    if (holder.is(BiomeTags.PRODUCES_CORALS_FROM_BONEMEAL)) {
                        if (i == 0 && clickedSide != null && clickedSide.getAxis().isHorizontal()) {
                            blockstate = (BlockState)BuiltInRegistries.BLOCK.getRandomElementOf(BlockTags.WALL_CORALS, level.random).map((p_204100_) -> {
                                return ((Block)p_204100_.value()).defaultBlockState();
                            }).orElse(blockstate);
                            if (blockstate.hasProperty(BaseCoralWallFanBlock.FACING)) {
                                blockstate = (BlockState)blockstate.setValue(BaseCoralWallFanBlock.FACING, clickedSide);
                            }
                        } else if (randomsource.nextInt(4) == 0) {
                            blockstate = (BlockState)BuiltInRegistries.BLOCK.getRandomElementOf(BlockTags.UNDERWATER_BONEMEALS, level.random).map((p_204095_) -> {
                                return ((Block)p_204095_.value()).defaultBlockState();
                            }).orElse(blockstate);
                        }
                    }

                    if (blockstate.is(BlockTags.WALL_CORALS, (p_204093_) -> {
                        return p_204093_.hasProperty(BaseCoralWallFanBlock.FACING);
                    })) {
                        for(int k = 0; !blockstate.canSurvive(level, blockpos) && k < 4; ++k) {
                            blockstate = (BlockState)blockstate.setValue(BaseCoralWallFanBlock.FACING, Plane.HORIZONTAL.getRandomDirection(randomsource));
                        }
                    }

                    if (blockstate.canSurvive(level, blockpos)) {
                        BlockState blockstate1 = level.getBlockState(blockpos);
                        if (blockstate1.is(Blocks.WATER) && level.getFluidState(blockpos).getAmount() == 8) {
                            level.setBlock(blockpos, blockstate, 3);
                        } else if (blockstate1.is(Blocks.SEAGRASS) && randomsource.nextInt(10) == 0) {
                            ((BonemealableBlock)Blocks.SEAGRASS).performBonemeal((ServerLevel)level, randomsource, blockpos, blockstate1);
                        }
                    }
                }

                stack.shrink(1);
                return true;
            }
        } else {
            return false;
        }
    }

    public static void addGrowthParticles(LevelAccessor level, BlockPos pos, int data) {
        BlockState blockstate = level.getBlockState(pos);
        Block var5 = blockstate.getBlock();
        if (var5 instanceof BonemealableBlock) {
            BonemealableBlock bonemealableblock = (BonemealableBlock)var5;
            BlockPos blockpos = bonemealableblock.getParticlePos(pos);
            switch (bonemealableblock.getType()) {
                case NEIGHBOR_SPREADER -> ParticleUtils.spawnParticles(level, blockpos, data * 3, 3.0, 1.0, false, ParticleTypes.HAPPY_VILLAGER);
                case GROWER -> ParticleUtils.spawnParticleInBlock(level, blockpos, data, ParticleTypes.HAPPY_VILLAGER);
            }
        } else if (blockstate.is(Blocks.WATER)) {
            ParticleUtils.spawnParticles(level, pos, data * 3, 3.0, 1.0, false, ParticleTypes.HAPPY_VILLAGER);
        }

    }
}
