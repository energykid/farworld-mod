package net.ennway.farworld.block;

import com.mojang.logging.LogUtils;
import net.ennway.farworld.particle.BystonePortalParticleProvider;
import net.ennway.farworld.particle.ParalysisParticleProvider;
import net.ennway.farworld.registries.ModBlocks;
import net.ennway.farworld.registries.ModDimensions;
import net.ennway.farworld.registries.ModParticles;
import net.ennway.farworld.registries.ModSounds;
import net.minecraft.BlockUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LanternBlock;
import net.minecraft.world.level.block.Portal;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.level.border.WorldBorder;
import net.minecraft.world.level.dimension.DimensionType;
import net.minecraft.world.level.portal.DimensionTransition;
import net.minecraft.world.level.portal.PortalShape;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.slf4j.Logger;

import javax.annotation.Nullable;
import java.awt.*;
import java.util.Optional;

public class BystonePortalBlock extends Block {

    public static final EnumProperty<Direction.Axis> AXIS;
    public static final BooleanProperty SHORT;
    private static final Logger LOGGER;
    protected static final int AABB_OFFSET = 2;
    protected static final VoxelShape X_AXIS_AABB;
    protected static final VoxelShape Z_AXIS_AABB;

    static {
        AXIS = BlockStateProperties.HORIZONTAL_AXIS;
        SHORT = BlockStateProperties.SHORT;
        LOGGER = LogUtils.getLogger();
        X_AXIS_AABB = Block.box(0.0, 0.0, 6.0, 16.0, 16.0, 10.0);
        Z_AXIS_AABB = Block.box(6.0, 0.0, 0.0, 10.0, 16.0, 16.0);
    }

    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(new Property[]{AXIS, SHORT});
    }

    public BystonePortalBlock(Properties properties) {
        super(properties);
        this.registerDefaultState((BlockState)((BlockState)this.stateDefinition.any()).setValue(AXIS, Direction.Axis.X).setValue(SHORT, false));
    }

    protected VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return switch (state.getValue(AXIS)) {
            case Z -> Z_AXIS_AABB;
            default -> X_AXIS_AABB;
        };
    }

    @Override
    public void destroy(LevelAccessor level, BlockPos pos, BlockState state) {
        super.destroy(level, pos, state);
        cascadingPortalConversion(level, pos);
    }

    @Override
    public void animateTick(BlockState state, Level level, BlockPos pos, RandomSource random) {
        if (random.nextInt(40) > 38)
        {
            level.playLocalSound(pos.getX() + 0.5,pos.getY() + 0.5,pos.getZ() + 0.5, ModSounds.BYSTONE_PORTAL_IDLE.get(), SoundSource.AMBIENT, (float)0.8f + (random.nextFloat() % 0.4f), 1f, false);
        }
        level.addParticle(ModParticles.BYSTONE_PORTAL.get(), pos.getX() + random.nextDouble(), pos.getY() + random.nextDouble(), pos.getZ() + random.nextDouble(), 0, 0, 0);
    }

    public static void cascadingPortalConversion(LevelAccessor level, BlockPos pos)
    {
        for (int i = -1; i <= 1; i++)
        {
            for (int j = -1; j <= 1; j++)
            {
                for (int k = -1; k <= 1; k++)
                {
                    if (i != 0 || j != 0 || k != 0)
                    {
                        BlockPos pos2 = new BlockPos(pos.getX() + i, pos.getY() + j, pos.getZ() + k);
                        if (level.getBlockState(pos2).is(ModBlocks.BYSTONE_PORTAL))
                        {
                            level.removeBlock(pos2, false);
                            cascadingPortalConversion(level, pos2);
                        }
                        if (level.getBlockState(pos2).is(ModBlocks.ECHO_LANTERN))
                        {
                            level.setBlock(pos2, Blocks.LANTERN.defaultBlockState().setValue(LanternBlock.HANGING, true), Block.UPDATE_ALL);
                            cascadingPortalConversion(level, pos2);
                        }
                    }
                }
            }
        }
    }
}
