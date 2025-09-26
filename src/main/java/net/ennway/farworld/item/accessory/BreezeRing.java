package net.ennway.farworld.item.accessory;

import net.ennway.farworld.item.AccessoryItem;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.Mth;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;

public class BreezeRing extends AccessoryItem {

    public BreezeRing(Properties properties) {
        super(properties);
    }

    @Override
    public void onDamageEnemy(Entity player, Entity enemy, ItemStack stack, LivingDamageEvent.Pre event) {
        Player pl = (Player)player;
        if (player.getWeaponItem().is(ItemTags.SWORDS) && pl.swingTime == 0)
        {
            ((Mob)enemy).addEffect(new MobEffectInstance(MobEffects.WIND_CHARGED, 20));
        }
    }
}
