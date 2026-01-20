package net.ennway.farworld.item.accessory;

import net.ennway.farworld.Farworld;
import net.ennway.farworld.registries.ModItems;
import net.ennway.farworld.utils.AccessoryUtils;
import net.ennway.farworld.utils.BehaviorUtils;
import net.ennway.farworld.utils.MathUtils;
import net.ennway.farworld.utils.ServerUtils;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.CropBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.event.level.BlockDropsEvent;
import net.neoforged.neoforge.event.level.BlockEvent;

@EventBusSubscriber(modid = Farworld.MOD_ID)
public class MagicSpurLootEvent {

    @SubscribeEvent
    public static void lootStuff(BlockDropsEvent evt)
    {
        if (evt.getBreaker() instanceof Player plr)
        {
            if (AccessoryUtils.playerHasAccessory(plr, ModItems.MAGIC_SPUR.get()))
            {
                BlockState state = evt.getState();
                if (state.getBlock() instanceof CropBlock block)
                {
                    if (block.isMaxAge(state))
                    {
                        evt.getDrops().forEach(i -> {
                            ItemStack stack = i.getItem();
                            double rand = MathUtils.randomDouble(evt.getLevel().getRandom(), 1, 2);
                            stack.setCount(stack.getCount() + (int)rand);
                            i.setItem(stack);
                        });
                    }
                }
            }
        }
    }
}
