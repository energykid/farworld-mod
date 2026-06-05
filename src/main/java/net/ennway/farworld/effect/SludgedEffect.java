package net.ennway.farworld.effect;

import net.ennway.farworld.registries.ModParticles;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;

import java.util.Objects;

public class SludgedEffect extends MobEffect {
    public SludgedEffect()
    {
        super(MobEffectCategory.HARMFUL,0x7D3F3F);
        this.addAttributeModifier(
                Attributes.JUMP_STRENGTH.getDelegate(),
                Objects.requireNonNull(Attributes.JUMP_STRENGTH.getKey()).location(),
                -0.07f,
                AttributeModifier.Operation.ADD_VALUE
        );
        this.addAttributeModifier(
                Attributes.MOVEMENT_SPEED.getDelegate(),
                Objects.requireNonNull(Attributes.MOVEMENT_SPEED.getKey()).location(),
                -0.01f,
                AttributeModifier.Operation.ADD_VALUE
        );
    }

    @Override
    public ParticleOptions createParticleOptions(MobEffectInstance effect) {
        return ModParticles.SLUDGE_DROP.get();
    }
}
