package net.ennway.farworld.item.accessory;

import net.ennway.farworld.item.AccessoryItem;
import net.ennway.farworld.registries.ModBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.CropBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.common.CommonHooks;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;
import net.neoforged.neoforge.event.tick.EntityTickEvent;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;

import java.util.Objects;
import java.util.function.Consumer;

public class MagicSpur extends AccessoryItem {

    public MagicSpur(Properties properties) {
        super(properties);
    }

    int distance = 3;

    @Override
    public void postTick(Player player, ItemStack stack, PlayerTickEvent.Post event) {

        {
            Level level = event.getEntity().level();
            for (int i = -distance; i <= distance; i++)
            {
                for (int j = -distance; j <= distance; j++)
                {
                    for (int k = -distance; k <= distance; k++)
                    {
                        BlockPos pos = new BlockPos(player.getOnPos().getX() + i,
                                player.getOnPos().getY() + j,
                                player.getOnPos().getZ() + k);


                        if (level.isAreaLoaded(pos, 1)) {

                            if (level.getRawBrightness(pos, 0) >= 9) {

                                BlockState state = level.getBlockState(pos);
                                Block block = state.getBlock();
                                if ((player.getRandom().nextFloat() % 1f) < 0.05f) {
                                    if (block instanceof CropBlock crop) {
                                        int i1 = state.getValue(CropBlock.AGE);
                                        if (i1 < crop.getMaxAge()) {
                                            level.setBlock(pos, crop.getStateForAge(i1 + 1), 2);
                                            CommonHooks.fireCropGrowPost(level, pos, state);

                                            player.playSound(SoundEvents.BONE_MEAL_USE);
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
