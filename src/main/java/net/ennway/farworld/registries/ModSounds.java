package net.ennway.farworld.registries;

import net.ennway.farworld.Farworld;
import net.minecraft.client.resources.sounds.Sound;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.Resource;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.item.JukeboxSong;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class ModSounds {
    public static final DeferredRegister<SoundEvent> SOUND_EVENTS =
        DeferredRegister.create(BuiltInRegistries.SOUND_EVENT, "farworld");

    public static final Supplier<SoundEvent> ALLSAW_SWITCH_FROM_PRECISION = createSoundEvent("allsawswitchfromprecision");
    public static final Supplier<SoundEvent> ALLSAW_SWITCH_TO_PRECISION = createSoundEvent("allsawswitchtoprecision");

    public static final Supplier<SoundEvent> BLOOMED_HURT = createSoundEvent("bloomedhurt");
    public static final Supplier<SoundEvent> BLOOMED_DEATH = createSoundEvent("bloomeddeath");

    public static final Supplier<SoundEvent> SOUL_GOLEM_ACTIVATE = createSoundEvent("soulgolemactivate");
    public static final Supplier<SoundEvent> SOUL_GOLEM_DEACTIVATE = createSoundEvent("soulgolemdeactivate");

    public static final Supplier<SoundEvent> DUSTBUG_SCREECH = createSoundEvent("dustbug_screech");

    public static final Supplier<SoundEvent> WISHBONE_CRACK = createSoundEvent("wishbonecrack");
    public static final Supplier<SoundEvent> WISHBONE_SHATTER = createSoundEvent("wishboneshatter");

    public static final Supplier<SoundEvent> WHIRLING_WORLD = createSoundEvent("whirlingworld");
    public static final ResourceKey<JukeboxSong> WHIRLING_WORLD_KEY = createSong("whirlingworld");

    public static final Supplier<SoundEvent> DUSTY_SHELVES_AMBIANCE = createSoundEvent("dusty_shelves_ambiance");
    public static final Supplier<SoundEvent> LUSH_SHALLOWS_AMBIANCE = createSoundEvent("lush_shallows_ambiance");
    public static final Supplier<SoundEvent> CHARGED_CAVES_AMBIANCE = createSoundEvent("charged_caves_ambiance");
    public static final Supplier<SoundEvent> GEODIC_COLUMNS_AMBIANCE = createSoundEvent("geodic_columns_ambiance");

    public static final Supplier<SoundEvent> DIAMOND_HIT = createSoundEvent("diamond_hit");
    public static final Supplier<SoundEvent> COBALT_HIT = createSoundEvent("cobalt_hit");

    public static final Supplier<SoundEvent> BLACK_ICE_OUTWARDS = createSoundEvent("black_ice_outwards");
    public static final Supplier<SoundEvent> BLACK_ICE_INWARDS = createSoundEvent("black_ice_inwards");

    public static final Supplier<SoundEvent> VOID_BOW_RELEASE = createSoundEvent("void_bow_release");
    public static final Supplier<SoundEvent> VOID_CROSSBOW_INWARDS = createSoundEvent("void_crossbow_inwards");

    public static final Supplier<SoundEvent> BYSTONE_PORTAL_IDLE = createSoundEvent("bystone_portal_idle");
    public static final Supplier<SoundEvent> BYSTONE_PORTAL_ENTER = createSoundEvent("bystone_portal_enter");
    public static final Supplier<SoundEvent> BYSTONE_PORTAL_EXIT = createSoundEvent("bystone_portal_exit");

    public static final Supplier<SoundEvent> MUSIC_LUSH_SHALLOWS = createSoundEvent("music.lush_shallows");
    public static final Supplier<SoundEvent> MUSIC_ARMAGEDDON_SIMPLE_THINGS = createSoundEvent("music.armageddon_simple_things");
    public static final Supplier<SoundEvent> MUSIC_DUSTY_SHELVES = createSoundEvent("music.dusty_shelves");

    private static ResourceKey<JukeboxSong> createSong(String name)
    {
        return ResourceKey.create(Registries.JUKEBOX_SONG, ResourceLocation.fromNamespaceAndPath(Farworld.MOD_ID, name));
    }

    private static Supplier<SoundEvent> createSoundEvent(String name)
    {
        return SOUND_EVENTS.register(
                name,
            () -> SoundEvent.createVariableRangeEvent(ResourceLocation.fromNamespaceAndPath(Farworld.MOD_ID, name))
        );
    }
}
