package epichacks.modules.render;

import org.lwjgl.input.Keyboard;

import epichacks.events.Event;
import epichacks.events.listeners.EventUpdate;
import epichacks.modules.Module;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityOtherPlayerMP;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.network.play.client.C16PacketClientStatus;
import net.minecraft.network.play.client.C18PacketSpectate;
import net.minecraft.network.play.server.S2BPacketChangeGameState;
import net.minecraft.world.WorldSettings;

/**
 * The {@code Freecam} class represents a hack that allows the player to enter a spectator-like mode,
 * enabling them to freely navigate and observe the game world without any physical constraints or collision with terrain.
 * It extends the {@link epichacks.modules.Module} class and provides specific behavior for enabling and disabling freecam.
 */
public class Freecam extends Module {
    private EntityOtherPlayerMP fakePlayer;
    private double originalPlayerX;
    private double originalPlayerY;
    private double originalPlayerZ;
    private float originalPlayerYaw;
    private float originalPlayerPitch;
    private boolean originalPlayerFlying; 
    private WorldSettings.GameType originalGameMode; 

    /**
     * Constructs a new {@code Freecam} hack with the default name, key code, and category.
     * The default key code is {@link org.lwjgl.input.Keyboard#KEY_F}.
     */
    public Freecam() {
        super("freecam", Keyboard.KEY_F, Category.RENDER);
    }

    /**
     * Called when the hack is enabled.
     * Sets the player's gamemode to spectator mode, and creates
     * a temporary fake entity to replace the player.
     */
    @Override
    public void onEnable() {
        // Store the original game mode and flying status
        originalGameMode = mc.thePlayer.capabilities.isCreativeMode ? WorldSettings.GameType.CREATIVE : WorldSettings.GameType.SURVIVAL;
        originalPlayerFlying = mc.thePlayer.capabilities.isFlying; 

        // store the original player's position
        originalPlayerX = mc.thePlayer.posX;
        originalPlayerY = mc.thePlayer.posY;
        originalPlayerZ = mc.thePlayer.posZ;
        originalPlayerYaw = mc.thePlayer.rotationYaw;
        originalPlayerPitch = mc.thePlayer.rotationPitch;

        // Set the game mode to SPECTATOR
        mc.thePlayer.sendChatMessage("/gamemode spectator");
        mc.thePlayer.setGameType(WorldSettings.GameType.SPECTATOR);
        mc.playerController.setGameType(WorldSettings.GameType.SPECTATOR);
        
        // create fake clone of the player
        fakePlayer = new EntityOtherPlayerMP(mc.theWorld, mc.thePlayer.getGameProfile());
        fakePlayer.clonePlayer(mc.thePlayer, true);
        fakePlayer.copyLocationAndAnglesFrom(mc.thePlayer);
        fakePlayer.rotationYawHead = mc.thePlayer.rotationYawHead;
        
        // add fake player to world
        mc.theWorld.addEntityToWorld(fakePlayer.getEntityId(), fakePlayer);
        
        // Send a packet to the server to change the game mode
        mc.thePlayer.sendQueue.addToSendQueue(new C16PacketClientStatus(C16PacketClientStatus.EnumState.PERFORM_RESPAWN));
        mc.thePlayer.sendQueue.addToSendQueue(new C18PacketSpectate());
    }

    /**
     * Called when the hack is disabled.
     * Resets the player's gamemode back to their original gamemode, and
     * teleports them back to their initial position.
     */
    @Override
    public void onDisable() {
        // Set the player's flying status to the original state
        mc.thePlayer.capabilities.isFlying = originalPlayerFlying;

        // teleport the player back to the initial position
        mc.thePlayer.setPositionAndRotation(originalPlayerX, originalPlayerY, originalPlayerZ, originalPlayerYaw, originalPlayerPitch);
        
        // Restore the original game mode
        mc.thePlayer.sendChatMessage("/gamemode " + originalGameMode.getName());
        mc.getNetHandler().addToSendQueue(new C18PacketSpectate());

        // Remove the fake player
        mc.theWorld.removeEntityFromWorld(fakePlayer.getEntityId());
    }
}