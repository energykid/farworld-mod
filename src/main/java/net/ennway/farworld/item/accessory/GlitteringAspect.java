package net.ennway.farworld.item.accessory;

import net.ennway.farworld.item.AccessoryItem;
import net.ennway.farworld.registries.ModDataComponents;
import net.ennway.farworld.registries.ModItems;
import net.ennway.farworld.registries.ModParticles;
import net.minecraft.core.component.DataComponents;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.Mth;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.ItemAttributeModifiers;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;
import net.neoforged.neoforge.event.entity.living.LivingHealEvent;
import net.neoforged.neoforge.event.tick.EntityTickEvent;

public class GlitteringAspect extends AccessoryItem {

    public GlitteringAspect(Properties properties) {
        super(properties);
    }

    @Override
    public void preTick(Player player, ItemStack stack, EntityTickEvent.Pre event) {

        if (player.getFoodData().getSaturationLevel() > 0)
        {
            // Speed 1
            player.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 10));

            // Speed 3, if Breeze Boots are equipped
            for (ItemStack stack2 : player.getArmorSlots())
            {
                if (stack2.is(ModItems.BREEZE_BOOTS))
                {
                    player.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 10, 1));
                }
            }

            // Particles

            if (player.isSprinting())
            {
                if (player.onGround() && (player.getRandom().nextFloat() % 1f) > 0.7f)
                {
                    player.level().addParticle(ModParticles.GLITTERING_PARTICLE.get(),
                            player.getX(), player.getY() + 0.05, player.getZ(),
                            0, 0, 0);
                }

                player.level().addParticle(ModParticles.DIAMOND_SPARKLE.get(),
                        player.getX(), player.getY() + 1, player.getZ(),
                        player.getRandomX(0.1), player.getRandom().nextFloat() % 0.4, player.getRandomZ(0.1));
            }
        }
    }
}
