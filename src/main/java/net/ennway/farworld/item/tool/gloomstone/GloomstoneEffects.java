package net.ennway.farworld.item.tool.gloomstone;

import net.ennway.farworld.entity.projectile.GloomstonePickup;
import net.ennway.farworld.registries.ModEffects;
import net.ennway.farworld.registries.ModEntities;
import net.ennway.farworld.utils.MathUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;

public class GloomstoneEffects {
    public static void doCombatEffect(LivingEntity target, LivingEntity attacker)
    {
        if (attacker instanceof Player plr)
        {
            target.addEffect(
                    new MobEffectInstance(ModEffects.PARALYSIS, 12)
            );
            int chance = 20;
            if (attacker.getRandom().nextInt(100) < chance)
            {
                GloomstonePickup proj = new GloomstonePickup(ModEntities.GLOOMSTONE_PICKUP.get(), target.level());
                proj.setPos(target.position().add(new Vec3(0.0, target.getBbHeight() / 2.0, 0.0)));
                proj.setDeltaMovement(new Vec3(MathUtils.randomDouble(attacker.getRandom(), -0.2, 0.2), MathUtils.randomDouble(attacker.getRandom(), 0.2, 0.6), MathUtils.randomDouble(attacker.getRandom(), -0.2, 0.2)));
                proj.getEntityData().set(GloomstonePickup.PLAYER_NAME, plr.getName().getString());
                target.level().addFreshEntity(proj);
            }
        }
    }
    public static void doMiningEffect(Player miner, BlockPos pos)
    {
        if (!miner.isCreative() && miner.getItemInHand(InteractionHand.MAIN_HAND).isCorrectToolForDrops(miner.level().getBlockState(pos)))
        {
            int chance = 15;
            if (miner.getRandom().nextInt(100) < chance) {
                GloomstonePickup proj = new GloomstonePickup(ModEntities.GLOOMSTONE_PICKUP.get(), miner.level());
                proj.setPos(pos.getCenter());
                proj.setDeltaMovement(new Vec3(MathUtils.randomDouble(miner.getRandom(), -0.2, 0.2), MathUtils.randomDouble(miner.getRandom(), 0.2, 0.6), MathUtils.randomDouble(miner.getRandom(), -0.2, 0.2)));
                proj.getEntityData().set(GloomstonePickup.PLAYER_NAME, miner.getName().getString());
                miner.level().addFreshEntity(proj);
            }
        }
    }
}
