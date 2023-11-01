package epichacks.modules.player;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import org.lwjgl.input.Keyboard;

import epichacks.events.Event;
import epichacks.events.listeners.EventUpdate;
import epichacks.modules.Module;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.network.play.client.C07PacketPlayerDigging;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.Vec3;
import epichacks.settings.ModeSetting;
import epichacks.settings.NumberSetting;

/**
 * The {@code Nuker} class represents a hack that allows players to rapidly destroy blocks in a specified radius around them.
 * It extends the {@link epichacks.modules.Module} class and provides specific behavior for enabling and disabling nuker.
 */
public class Nuker extends Module {
    public ModeSetting mode = new ModeSetting("Mode", "Normal", "Normal", "Flat", "Smash");
    public NumberSetting radius = new NumberSetting("Radius", 3.0, 1.0, 8.0, 1.0);

    /**
     * Constructs a new {@code Nuker} hack with the default name, key code, and category.
     * The default key code is {@link org.lwjgl.input.Keyboard#KEY_N}.
     */
    public Nuker() {
        super("nuker", Keyboard.KEY_N, Category.PLAYER);
        this.addSettings(mode, radius);
    }
    
    /**
     * Handles an event, specifically the {@link epichacks.events.listeners.EventUpdate} event.
     * When the event is "PRE," this method enables the nuker effect, destroying all blocks within 
     * a specified radius of the player.
     *
     * @param e The event to be handled.
     */
    @Override
    public void onEvent(Event e) {
        if (e instanceof EventUpdate && e.isPre()) {
        	nuker(radius.getValue(), mode.getMode(), mc);
        }
    }

    /**
     * Destroys blocks in a specified radius around the player's position based on the selected destruction mode.
     *
     * @param rad  The radius within which blocks should be destroyed.
     * @param mode The destruction mode (e.g., "Normal," "Flat," "Smash") that determines which blocks to destroy.
     * @param mc   The Minecraft instance used to interact with the game world.
     */
    private void nuker(double rad, String mode, Minecraft mc) {
        // Get the player's eye position in the game world.
        Vec3 playerEyePos = getPlayerEyePos();
        // Create a list to store the positions of blocks that should be destroyed.
        List<BlockPos> blocksToDestroy = new ArrayList<>();
        
        // Get the player's feet position.
        BlockPos playerFeetPos = mc.thePlayer.getPosition();

        for (int x = (int) -rad; x <= rad; x++) {
            for (int z = (int) -rad; z <= rad; z++) {
                int minY = mode.equals("Flat") ? 0 : (int) -rad;
                int maxY = (int) rad;

                for (int y = minY; y <= maxY; y++) {
                    // Calculate the position of the block to check for destruction based on player's feet.
                    BlockPos blockPos = playerFeetPos.add(x, y, z); 
                    // Get the block type at the calculated position.
                    Block block = mc.theWorld.getBlockState(blockPos).getBlock();

                    boolean shouldDestroy = false;
                    if (block != null && !mc.theWorld.isAirBlock(blockPos)) {
                        // Check the hardness of the block to determine if it can be destroyed.
                        float blockHardness = block.getBlockHardness(mc.theWorld, blockPos);
                        if (blockHardness != -1.0F) {
                            // Check the block hardness based on the selected destruction mode.
                            if ((mode.equals("Normal") && blockHardness >= 0.0F)
                                    || (mode.equals("Flat") && blockHardness >= 0.0F && y >= 0) // Exclude blocks below player's level
                                    || (mode.equals("Smash") && blockHardness >= 0.0F && blockHardness <= 1.0F)) {
                                shouldDestroy = true;
                            }
                        }
                    }
                    // If the block should be destroyed, add its position to the list.
                    if (shouldDestroy) {
                        blocksToDestroy.add(blockPos);
                    }
                }
            }
        }

        // Sort the list of blocks to destroy based on their distance from the player's eye position.
        blocksToDestroy.sort(Comparator.comparingDouble(blockPos -> playerEyePos.squareDistanceTo(new Vec3(blockPos.getX() + 0.5, blockPos.getY() + 0.5, blockPos.getZ() + 0.5))));

        // Iterate through the sorted list and send dig requests to the server to destroy the blocks.
        for (BlockPos blockPos : blocksToDestroy) {
            mc.thePlayer.sendQueue.addToSendQueue(new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.START_DESTROY_BLOCK, blockPos, EnumFacing.DOWN));
            mc.thePlayer.sendQueue.addToSendQueue(new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.STOP_DESTROY_BLOCK, blockPos, EnumFacing.DOWN));
        }
    }

    /**
     * Retrieves the current eye position of the player in the game world.
     *
     * @return A Vec3 object representing the player's eye position.
     */
    private Vec3 getPlayerEyePos() {
        // Get the Minecraft instance.
        Minecraft mc = Minecraft.getMinecraft();
        // Get the player's X, Y, and Z coordinates and add the eye height to the Y position.
        double posX = mc.thePlayer.posX;
        double posY = mc.thePlayer.posY + mc.thePlayer.getEyeHeight();
        double posZ = mc.thePlayer.posZ;
        // Create and return a Vec3 object representing the player's eye position.
        return new Vec3(posX, posY, posZ);
    }

}
