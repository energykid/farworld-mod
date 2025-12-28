package net.ennway.farworld.item.tool.gloomstone;

import net.ennway.farworld.entity.projectile.BlackIceImplosionProjectile;
import net.ennway.farworld.entity.projectile.GloomstonePickup;
import net.ennway.farworld.registries.ModEffects;
import net.ennway.farworld.registries.ModEntities;
import net.ennway.farworld.registries.sets.SetTiers;
import net.ennway.farworld.utils.MathUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.AxeItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tiers;
import net.minecraft.world.item.component.ItemAttributeModifiers;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;

public class GloomstoneAxe extends AxeItem {
    public GloomstoneAxe(Properties properties) {
        super(SetTiers.GLOOMSTONE_TIER, properties
                .attributes(ItemAttributeModifiers.builder()
                        .add(Attributes.ATTACK_DAMAGE,
                                new AttributeModifier(BASE_ATTACK_DAMAGE_ID, 8, AttributeModifier.Operation.ADD_VALUE),
                                EquipmentSlotGroup.MAINHAND)
                        .add(Attributes.ATTACK_SPEED,
                                new AttributeModifier(BASE_ATTACK_SPEED_ID, -3.2, AttributeModifier.Operation.ADD_VALUE),
                                EquipmentSlotGroup.MAINHAND)
                .build()));
    }

    @Override
    public void postHurtEnemy(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        GloomstoneEffects.doCombatEffect(target, attacker);

        super.postHurtEnemy(stack, target, attacker);
    }

    @Override
    public boolean mineBlock(ItemStack stack, Level level, BlockState state, BlockPos pos, LivingEntity miningEntity) {
        if (miningEntity instanceof Player plr)
        {
            GloomstoneEffects.doMiningEffect(plr, pos);
        }

        return super.mineBlock(stack, level, state, pos, miningEntity);
    }
}