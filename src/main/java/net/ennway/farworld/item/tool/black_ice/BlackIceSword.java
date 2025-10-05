package net.ennway.farworld.item.tool.black_ice;

import net.ennway.farworld.entity.projectile.BlackIceImplosionProjectile;
import net.ennway.farworld.registries.ModEntities;
import net.ennway.farworld.registries.sets.SetTiers;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.component.ItemAttributeModifiers;
import net.minecraft.world.phys.Vec3;

public class BlackIceSword extends SwordItem {
    public BlackIceSword(Properties properties) {
        super(SetTiers.BLACK_ICE_TIER, properties
                .attributes(ItemAttributeModifiers.builder()
                        .add(Attributes.ATTACK_DAMAGE,
                                new AttributeModifier(BASE_ATTACK_DAMAGE_ID, 7.5, AttributeModifier.Operation.ADD_VALUE),
                                EquipmentSlotGroup.MAINHAND)
                        .add(Attributes.ATTACK_SPEED,
                                new AttributeModifier(BASE_ATTACK_SPEED_ID, -2.4, AttributeModifier.Operation.ADD_VALUE),
                                EquipmentSlotGroup.MAINHAND)
                .build())
                .rarity(Rarity.UNCOMMON));
    }

    @Override
    public void postHurtEnemy(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        super.postHurtEnemy(stack, target, attacker);

        if (!attacker.swinging) {
            BlackIceImplosionProjectile proj = new BlackIceImplosionProjectile(ModEntities.BLACK_ICE_AOE_ENTITY.get(), target.level());
            proj.setPos(target.position().add(new Vec3(0.0, target.getBbHeight() / 2.0, 0.0)));

            target.level().addFreshEntity(proj);
        }
    }
}