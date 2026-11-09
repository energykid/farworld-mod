package net.ennway.farworld.item.accessory;

import net.ennway.farworld.entity.projectile.ApocalypseBreathProjectile;
import net.ennway.farworld.item.AccessoryItem;
import net.ennway.farworld.network.ApocalypseCoreData;
import net.ennway.farworld.network.TechnicLaunchersData;
import net.ennway.farworld.registries.*;
import net.ennway.farworld.utils.AccessoryUtils;
import net.ennway.farworld.utils.BehaviorUtils;
import net.ennway.farworld.utils.MathUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.ai.behavior.Behavior;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.ClientTickEvent;
import net.neoforged.neoforge.client.event.InputEvent;
import net.neoforged.neoforge.event.tick.EntityTickEvent;
import net.neoforged.neoforge.network.PacketDistributor;
import org.joml.Vector2f;

public class TechnicLaunchers extends AccessoryItem {

    public TechnicLaunchers(Properties properties) {
        super(properties.rarity(Rarity.RARE));
    }

    public static void runZip(Player player, int direction)
    {
        var dir = new Vector2f(-direction, 0);
        var d = MathUtils.rotateVector2f(dir, Math.toRadians(player.getViewYRot(0)));
        player.addDeltaMovement(new Vec3(d.x, 0, d.y));
        player.playSound(ModSounds.REDSTONE_CURIOSITY_ZIP.get());
    }

    @EventBusSubscriber(value = Dist.CLIENT)
    public class Event
    {
        public static float dashInputTiming = 0f;
        public static float dashCooldown = 0f;
        public static float dashDirection = 0f;
        public static boolean oldInputLeft = false;
        public static boolean oldInputRight = false;
        @SubscribeEvent
        public static void runInput(ClientTickEvent.Post evt)
        {
            if (!Minecraft.getInstance().isPaused())
            {
                LocalPlayer player = Minecraft.getInstance().player;
                if (player != null)
                {
                    dashCooldown--;
                    if (dashCooldown == 0)
                    {
                        dashDirection = 0;
                        player.playSound(ModSounds.TECHNIC_LAUNCHER_COOLDOWN_END.get());
                    }
                    if (AccessoryUtils.playerHasAccessory(player, ModItems.TECHNIC_LAUNCHERS.get()))
                    {
                        if (dashCooldown < 0)
                        {
                            dashInputTiming--;
                            if ((player.input.left || player.input.right) && !oldInputLeft && !oldInputRight)
                            {
                                if (dashInputTiming > 0 && dashDirection != 0)
                                {
                                    var dir = player.input.left ? -1 : 1;

                                    if (dir == dashDirection)
                                    {
                                        dashCooldown = 40;
                                        PacketDistributor.sendToServer(new TechnicLaunchersData(dir));
                                    }
                                }
                                else
                                {
                                    dashInputTiming = 6;
                                    if (player.input.left)
                                    {
                                        dashDirection = -1;
                                    }
                                    else if (player.input.right)
                                    {
                                        dashDirection = 1;
                                    }
                                }
                            }
                        }
                        oldInputLeft = player.input.left;
                        oldInputRight = player.input.right;
                    }
                }
            }
        }
    }
}
