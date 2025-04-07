package net.ennway.farworld.registries;

import com.mojang.serialization.Codec;
import net.ennway.farworld.Farworld;
import net.minecraft.network.syncher.SynchedEntityData;
import net.neoforged.neoforge.attachment.AttachmentType;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

import java.util.function.Supplier;

public class ModAttachments {
    public static final DeferredRegister<AttachmentType<?>> ATTACHMENT_TYPES = DeferredRegister.create(NeoForgeRegistries.ATTACHMENT_TYPES, Farworld.MOD_ID);

    public static final Supplier<AttachmentType<Float>> DIMENSION_TRANSITION = ATTACHMENT_TYPES.register(
            "dimension_transition", () -> AttachmentType.builder(() -> 0f).serialize(Codec.FLOAT).build()
    );
    public static final Supplier<AttachmentType<Float>> DIMENSION_TRANSITION_VISUAL = ATTACHMENT_TYPES.register(
            "dimension_transition_visual", () -> AttachmentType.builder(() -> 0f).serialize(Codec.FLOAT).build()
    );
    public static final Supplier<AttachmentType<String>> DIMENSION_TRANSITION_RESOURCE = ATTACHMENT_TYPES.register(
            "dimension_transition_resource", () -> AttachmentType.builder(() -> "overlay/bystone").serialize(Codec.STRING).build()
    );
}
