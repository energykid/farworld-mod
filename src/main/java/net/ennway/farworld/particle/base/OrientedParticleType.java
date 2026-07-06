package net.ennway.farworld.particle.base;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.util.ExtraCodecs;

public class OrientedParticleType extends ParticleType<OrientedParticleOptions> {
    public OrientedParticleType(boolean overrideLimitter) {
        super(overrideLimitter);
    }

    public OrientedParticleType getType() {
        return this;
    }

    @Override
    public MapCodec<OrientedParticleOptions> codec() {
        return ExtraCodecs.VECTOR3F.xmap((p_333828_) -> new OrientedParticleOptions(getType(), p_333828_), (p_333908_) -> p_333908_.orientation).fieldOf("orientation");
    }

    @Override
    public StreamCodec<? super RegistryFriendlyByteBuf, OrientedParticleOptions> streamCodec() {
        return ByteBufCodecs.VECTOR3F.map((p_333912_) -> new OrientedParticleOptions(getType(), p_333912_), (p_334072_) -> p_334072_.orientation);
    }
}
