package net.ennway.farworld.event;

import net.ennway.farworld.Farworld;
import net.ennway.farworld.block.BystonePortalBlock;
import net.ennway.farworld.block.EchoLantern;
import net.ennway.farworld.entity.custom.SoulGolemEntity;
import net.ennway.farworld.particle.on_hit.SoulSmokeParticle;
import net.ennway.farworld.particle.on_hit.SoulSmokeParticleProvider;
import net.ennway.farworld.registries.ModBlocks;
import net.ennway.farworld.registries.ModEntities;
import net.ennway.farworld.registries.ModParticles;
import net.ennway.farworld.utils.MathUtils;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.LayeredDraw;
import net.minecraft.commands.arguments.item.ItemParser;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.block.BaseFireBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LanternBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.pattern.BlockInWorld;
import net.minecraft.world.level.block.state.pattern.BlockPattern;
import net.minecraft.world.level.block.state.pattern.BlockPatternBuilder;
import net.minecraft.world.level.block.state.predicate.BlockStatePredicate;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.neoforged.bus.api.Event;
import net.neoforged.neoforge.client.event.RegisterGuiLayersEvent;
import net.neoforged.neoforge.event.entity.player.UseItemOnBlockEvent;
import net.neoforged.neoforge.event.level.BlockEvent;
import net.neoforged.neoforge.event.tick.LevelTickEvent;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;

@EventBusSubscriber(modid = Farworld.MOD_ID, bus = EventBusSubscriber.Bus.GAME)
public class PortalEvents {

    public static BlockPattern bystonePortal = null;

    public static BlockPattern getOrCreatePortalBase() {
        if (bystonePortal == null) {
            bystonePortal = BlockPatternBuilder.start()
                    .aisle("#", "~", "~", "~")
                    .where('#', BlockInWorld.hasState(BlockStatePredicate.forBlock(Blocks.LANTERN)))
                    .where('~', BlockInWorld.hasState(BlockStatePredicate.forBlock(Blocks.AIR)))
                    .build();
        }
        return bystonePortal;
    }

    @SubscribeEvent
    public static void lightPortal(UseItemOnBlockEvent event)
    {
        BlockPattern.BlockPatternMatch blockpatternmatch = getOrCreatePortalBase().find(event.getLevel(), event.getPos());

        if (blockpatternmatch != null)
        {
            int extendDownwards = 0;
            for (int i = 1; i <= 3; i++)
            {
                extendDownwards++;
                if (!event.getLevel().getBlockState(new BlockPos(event.getPos().getX(), event.getPos().getY() - i, event.getPos().getZ())).is(Blocks.AIR))
                {
                    break;
                }
            }
            if (extendDownwards >= 3)
            {
                if (cascadingLanternConversion(event, event.getPos(), 100, null))
                {
                    if (event.getPlayer() != null)
                    {
                        event.getLevel().playSound(event.getPlayer(), event.getPos(), SoundEvents.FIRECHARGE_USE, SoundSource.PLAYERS);
                        event.getPlayer().swinging = true;
                        event.getPlayer().swingTime = 0;
                        event.getPlayer().swingingArm = InteractionHand.MAIN_HAND;
                    }
                }
            }
        }
    }

    public static boolean cascadingLanternConversion(UseItemOnBlockEvent event, BlockPos pos, int num, Direction.Axis axis)
    {
        if (num > 0) {
            if (event.getItemStack().is(Items.ECHO_SHARD)) {
                if (event.getLevel().getBlockState(pos).getBlock() == Blocks.LANTERN) {
                    Direction.Axis ax = null;

                    int x = 0;
                    int z = 0;
                    for (int i = -2; i <= 2; i++) {
                        if (i == 0) i++;

                        int xx = 0;
                        int zz = 0;

                        if (Math.abs(i) <= 1) xx = MathUtils.sign(i);
                        else zz = MathUtils.sign(i);

                        BlockPos pos2 = new BlockPos(event.getPos().getX() + xx, event.getPos().getY(), event.getPos().getZ() + zz);

                        if (event.getLevel().getBlockState(pos2).is(Blocks.LANTERN)) {
                            if (Math.abs(i) <= 1) x += 1;
                            else z += 1;
                        }
                    }

                    if (axis == null) {
                        ax = x > z ? Direction.Axis.X : Direction.Axis.Z;
                    } else {
                        ax = axis;
                    }

                    axis = ax;

                    BlockState hanging = ModBlocks.ECHO_LANTERN.get().defaultBlockState().setValue(LanternBlock.HANGING, true);
                    event.getLevel().setBlockAndUpdate(pos, hanging);

                    for (int j = -1; j > -20; j--) {
                        BlockPos ps = new BlockPos(pos.getX(), pos.getY() + j, pos.getZ());
                        if (event.getLevel().getBlockState(ps).canBeReplaced()) {
                            BlockPlaceContext cont = new BlockPlaceContext(event.getUseOnContext());
                            BlockState portal = ModBlocks.BYSTONE_PORTAL.get().getStateForPlacement(cont)
                                    .setValue(BystonePortalBlock.AXIS, ax)
                                    .setValue(BystonePortalBlock.SHORT, j == -1);

                            event.getLevel().setBlockAndUpdate(ps, portal);
                            event.getLevel().gameEvent(event.getPlayer(), GameEvent.BLOCK_PLACE, event.getPos());
                        } else break;
                    }

                    for (int i = -2; i <= 2; i++) {
                        if (i == 0) i++;

                        int xx = 0;
                        int zz = 0;

                        if (axis == null) {
                            if (Math.abs(i) <= 1) xx = MathUtils.sign(i);
                            else zz = MathUtils.sign(i);
                        } else {
                            if (axis == Direction.Axis.X) xx = MathUtils.sign(i);
                            else zz = MathUtils.sign(i);
                        }

                        BlockPos p = new BlockPos(pos.getX() + xx, pos.getY(), pos.getZ() + zz);

                        if (event.getLevel().getBlockState(p).getBlock() == Blocks.LANTERN) {
                            if (axis == null) {
                                if (Math.abs(i) <= 1) axis = Direction.Axis.X;
                                else axis = Direction.Axis.Z;
                            }

                            cascadingLanternConversion(event, p, num - 1, ax);
                        }
                    }

                    event.getLevel().playSound(event.getPlayer(), pos, SoundEvents.SCULK_CATALYST_BLOOM, SoundSource.PLAYERS);
                    event.getLevel().addParticle(ModParticles.SOUL_SMOKE.get(),
                            pos.getX() + 0.5, pos.getY(), pos.getZ() + 0.5,
                            0, -2, 0);

                    return true;
                }
            }
        }
        return false;
    }
}