package epichacks.ui;

import java.util.Map;
import java.util.Map.Entry;

import epichacks.Client;
import epichacks.modules.Module;
import epichacks.events.listeners.EventRenderGUI;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;

/**
 * The {@code HUD} class represents a heads-up display (HUD) for displaying information and status of various modules.
 * It is responsible for rendering module names on the screen when those modules are enabled.
 */
public class HUD {
    /**
     * The instance of the Minecraft client for game-related operations.
     */
    public Minecraft minecraft = Minecraft.getMinecraft();

    /**
     * The default X-coordinate for rendering module names.
     */
    final Integer defaultXCoord = 4;

    /**
     * The default Y-coordinate for rendering the first module name.
     */
    final Integer defaultYCoord = 4;

    /**
     * The default Z-coordinate for rendering module names.
     */
    final Integer defaultZCoord = -1;

    /**
     * The vertical displacement between each module name when rendering.
     */
    final Integer yDisplacement = 10;

    /**
     * Draws the heads-up display by rendering the names of enabled modules on the screen.
     */
    public void draw() {
        ScaledResolution scaledResolution = new ScaledResolution(minecraft, minecraft.displayWidth, minecraft.displayHeight);
        Integer yCoord = defaultYCoord;

        for (Entry<Integer, Module> entry : Client.modules.entrySet()) {
            Module hack = entry.getValue();
            if (hack.hackName.equals("TabGUI")) continue;
            if (hack.isEnabled()) {
                // Render the name of the enabled module at the specified coordinates.
                minecraft.fontRendererObj.drawString(entry.getValue().hackName, defaultXCoord, yCoord, defaultZCoord);
                yCoord += yDisplacement; // Move to the next Y-coordinate for the next module.
            }
        }
        Client.onEvent(new EventRenderGUI());
    }
}
