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

    public static final Supplier<SoundEvent> GOLIATH_AMBIENT = createSoundEvent("goliath_ambient");
    public static final Supplier<SoundEvent> GOLIATH_STEP = createSoundEvent("goliath_step");
    public static final Supplier<SoundEvent> GOLIATH_ATTACK = createSoundEvent("goliath_attack");
    public static final Supplier<SoundEvent> GOLIATH_DEATH = createSoundEvent("goliath_death");

    public static final Supplier<SoundEvent> AMETHYST_CONSTRUCT_IDLE = createSoundEvent("amethyst_construct_idle");
    public static final Supplier<SoundEvent> AMETHYST_CONSTRUCT_WINDUP = createSoundEvent("amethyst_construct_windup");
    public static final Supplier<SoundEvent> AMETHYST_CONSTRUCT_SMASH = createSoundEvent("amethyst_construct_smash");
    public static final Supplier<SoundEvent> AMETHYST_CONSTRUCT_HURT = createSoundEvent("amethyst_construct_hurt");
    public static final Supplier<SoundEvent> AMETHYST_CONSTRUCT_DEATH = createSoundEvent("amethyst_construct_death");
    public static final Supplier<SoundEvent> AMETHYST_CONSTRUCT_CRUNCH = createSoundEvent("amethyst_construct_crunch");
    public static final Supplier<SoundEvent> AMETHYST_CONSTRUCT_SPIT = createSoundEvent("amethyst_construct_spit");

    public static final Supplier<SoundEvent> REDSTONE_CURIOSITY_ZIP = createSoundEvent("redstone_curiosity_zip");
    public static final Supplier<SoundEvent> REDSTONE_CURIOSITY_SPAWN = createSoundEvent("redstone_curiosity_spawn");
    public static final Supplier<SoundEvent> REDSTONE_CURIOSITY_HURT = createSoundEvent("redstone_curiosity_hurt");
    public static final Supplier<SoundEvent> REDSTONE_CURIOSITY_SHOOT_SMALL = createSoundEvent("redstone_curiosity_shoot_small");
    public static final Supplier<SoundEvent> REDSTONE_CURIOSITY_CHARGE = createSoundEvent("redstone_curiosity_chargeup");
    public static final Supplier<SoundEvent> REDSTONE_CURIOSITY_CHARGE_GATLING = createSoundEvent("redstone_curiosity_chargeup_gatling");
    public static final Supplier<SoundEvent> REDSTONE_CURIOSITY_SHOOT_LARGE = createSoundEvent("redstone_curiosity_shoot_large");
    public static final Supplier<SoundEvent> REDSTONE_CURIOSITY_TELEGRAPH = createSoundEvent("redstone_curiosity_telegraph");
    public static final Supplier<SoundEvent> REDSTONE_CURIOSITY_RAIN_LAND = createSoundEvent("redstone_curiosity_rain_land");
    public static final Supplier<SoundEvent> REDSTONE_CURIOSITY_KILL = createSoundEvent("redstone_curiosity_kill");

    public static final Supplier<SoundEvent> BATTLE_STANCE = createSoundEvent("battle_stance");
    public static final Supplier<SoundEvent> BLAZE_STANCE_SLASH = createSoundEvent("blaze_stance_slash");

    public static final Supplier<SoundEvent> GLOOMSTONE_PICKUP = createSoundEvent("gloomstone_pickup");

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
    public static final Supplier<SoundEvent> MUSIC_REDSTONE_CURIOSITY_BATTLE = createSoundEvent("music.redstone_curiosity_battle");

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
