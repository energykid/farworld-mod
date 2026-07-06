package net.ennway.farworld.particle.base;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.netty.buffer.ByteBuf;
import net.ennway.farworld.registries.ModParticles;
import net.minecraft.core.particles.ColorParticleOption;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.world.phys.Vec3;
import org.joml.Vector3f;

public class OrientedParticleOptions implements ParticleOptions {

    public static MapCodec<OrientedParticleOptions> codec(ParticleType<OrientedParticleOptions> particleType) {
        return ExtraCodecs.VECTOR3F.xmap((p_333828_) -> new OrientedParticleOptions(particleType, p_333828_), (p_333908_) -> p_333908_.orientation).fieldOf("orientation");
    }

    public static StreamCodec<? super ByteBuf, OrientedParticleOptions> streamCodec(ParticleType<OrientedParticleOptions> type) {
        return ByteBufCodecs.VECTOR3F.map((p_333912_) -> new OrientedParticleOptions(type, p_333912_), (p_334072_) -> p_334072_.orientation);
    }

    public ParticleType<OrientedParticleOptions> type;
    public final Vector3f orientation;

    public OrientedParticleOptions(ParticleType<OrientedParticleOptions> type, Vector3f orientation) {
        this.type = type;
        this.orientation = orientation;
    }

    @Override
    public ParticleType<?> getType() {
        return this.type;
    }
}
