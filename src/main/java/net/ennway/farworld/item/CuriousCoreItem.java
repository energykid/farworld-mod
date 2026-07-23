package net.ennway.farworld.item;

import net.ennway.farworld.registries.ModDataComponents;
import net.ennway.farworld.registries.ModItems;
import net.ennway.farworld.registries.ModSounds;
import net.ennway.farworld.registries.ModTags;
import net.ennway.farworld.utils.ItemUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.SlotAccess;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ClickAction;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.CustomModelData;
import net.minecraft.world.level.Level;

import java.util.List;

public class CuriousCoreItem extends Item {

    public static final int MAX_SATURATION = 5;

    public CuriousCoreItem(Properties properties) {
        super(properties
                .craftRemainder(ModItems.CURIOUS_CORE_OFF.asItem())
                .stacksTo(1)
                .component(ModDataComponents.EXP_SATURATION, 5)
                .component(DataComponents.CUSTOM_MODEL_DATA, new CustomModelData(1)));
    }

    public static void setModelStuff(ItemStack stack)
    {
        stack.set(DataComponents.CUSTOM_MODEL_DATA, new CustomModelData(stack.get(ModDataComponents.EXP_SATURATION) == 0 ? 1 : 0));

        if (stack.get(ModDataComponents.EXP_SATURATION) < MAX_SATURATION)
            stack.setDamageValue(5 - stack.get(ModDataComponents.EXP_SATURATION));
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
        ItemUtils.addUsesLeft(tooltipComponents, stack.get(ModDataComponents.EXP_SATURATION));
    }

    @Override
    public void inventoryTick(ItemStack stack, Level level, Entity entity, int slotId, boolean isSelected) {
        setModelStuff(stack);
    }

    @Override
    public int getMaxDamage(ItemStack stack) {
        return MAX_SATURATION;
    }

    @Override
    public boolean hasCraftingRemainingItem(ItemStack stack) {
        return true;
    }

    @Override
    public ItemStack getCraftingRemainingItem(ItemStack itemStack) {
        ItemStack s = new ItemStack(ModItems.CURIOUS_CORE.asItem());
        s.set(ModDataComponents.EXP_SATURATION, itemStack.get(ModDataComponents.EXP_SATURATION) - 1);

        setModelStuff(s);

        if (Minecraft.getInstance().player != null)
            Minecraft.getInstance().player.playNotifySound(ModSounds.CURIOUS_CORE_POWER_DOWN.get(), SoundSource.PLAYERS, 1, 1);

        if (s.get(ModDataComponents.EXP_SATURATION) <= 0) s = new ItemStack(ModItems.CURIOUS_CORE_OFF.asItem());

        return s;
    }

    @Override
    public boolean isBarVisible(ItemStack stack) {
        return stack.get(ModDataComponents.EXP_SATURATION) < 5;
    }

    @Override
    public int getBarColor(ItemStack stack) {
        return 12728890;
    }
}
