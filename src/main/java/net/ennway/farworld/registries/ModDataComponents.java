package net.ennway.farworld.registries;

import com.mojang.serialization.Codec;
import net.ennway.farworld.Farworld;
import net.minecraft.core.BlockPos;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.Registries;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.UnaryOperator;

public class ModDataComponents {
    public static final DeferredRegister<DataComponentType<?>> DATA_COMPONENT_TYPES =
            DeferredRegister.create(Registries.DATA_COMPONENT_TYPE, Farworld.MOD_ID);

    public static final DeferredHolder<DataComponentType<?>, DataComponentType<Integer>> FRAME = register(
            "frame",
            builder -> builder.persistent(Codec.INT)
    );

    public static final DeferredHolder<DataComponentType<?>, DataComponentType<Float>> BOW_DRAW_SPEED = register(
            "bow_draw_speed",
            builder -> builder.persistent(Codec.FLOAT)
    );

    public static final DeferredHolder<DataComponentType<?>, DataComponentType<Integer>> ACCESSORY_VALUE = register(
            "accessory_value",
            builder -> builder.persistent(Codec.INT)
    );

    private static <T>DeferredHolder<DataComponentType<?>, DataComponentType<T>> register(String name, UnaryOperator<DataComponentType.Builder<T>> builderOperator)
    {
        return DATA_COMPONENT_TYPES.register(name, () -> builderOperator.apply(DataComponentType.builder()).build());
    }

    public static void register(IEventBus eventBus)
    {
        DATA_COMPONENT_TYPES.register(eventBus);
    }
}
