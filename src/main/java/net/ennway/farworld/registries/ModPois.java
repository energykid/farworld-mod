package net.ennway.farworld.registries;

import net.ennway.farworld.Farworld;
import net.ennway.farworld.block.BystonePortalBlock;
import net.ennway.farworld.block.RedstoneTeleporterBlock;
import net.ennway.farworld.item.tool.Wishbone;
import net.minecraft.core.Direction;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.Resource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.ai.village.poi.PoiType;
import net.minecraft.world.entity.ai.village.poi.PoiTypes;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.predicate.BlockStatePredicate;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.dimension.DimensionType;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.Set;

public class ModPois {
    public static final DeferredRegister<PoiType> POIS = DeferredRegister.create(BuiltInRegistries.POINT_OF_INTEREST_TYPE, Farworld.MOD_ID);

    public static final Holder<PoiType> BYSTONE_PORTAL = POIS.register("bystone_portal",
            () -> new PoiType(Set.of(ModBlocks.BYSTONE_PORTAL.get().defaultBlockState(),
                    ModBlocks.BYSTONE_PORTAL.get().defaultBlockState().setValue(BystonePortalBlock.AXIS, Direction.Axis.Z),
                    ModBlocks.BYSTONE_PORTAL.get().defaultBlockState().setValue(BystonePortalBlock.AXIS, Direction.Axis.Z).setValue(BystonePortalBlock.SHORT, true),
                    ModBlocks.BYSTONE_PORTAL.get().defaultBlockState().setValue(BystonePortalBlock.SHORT, true)),
                    1,
                    1000));

    public static final Holder<PoiType> REDSTONE_TELEPORTER = POIS.register("redstone_teleporter",
            () -> new PoiType(Set.of(ModBlocks.REDSTONE_TELEPORTER_BLOCK.get().defaultBlockState()),
                    1,
                    1000));
}