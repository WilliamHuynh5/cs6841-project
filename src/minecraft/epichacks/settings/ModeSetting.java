package epichacks.settings;

import java.util.Arrays;
import java.util.List;

/**
 * The {@code ModeSetting} class represents a setting that allows cycling through modes.
 * It extends the {@link epichacks.settings.Setting} class.
 */
public class ModeSetting extends Setting {

	// Variable to store the current index representing the selected mode
	public int index;

	// List to store the available modes
	public List<String> modes;

	/**
	 * Constructor to create a ModeSetting with a specified name, default mode, and available modes.
	 *
	 * @param name The name of the mode setting.
	 * @param defaultMode The default mode to start with.
	 * @param modes Array of available modes.
	 */
	public ModeSetting(String name, String defaultMode, String... modes) {
		this.name = name;
		this.modes = Arrays.asList(modes);  // Convert array to a List
		index = this.modes.indexOf(defaultMode);  // Set the default mode index
	}

	/**
	 * Gets the current mode.
	 *
	 * @return The current mode.
	 */
	public String getMode() {
		return modes.get(index);
	}

	/**
	 * Checks if the setting is in a specific mode.
	 *
	 * @param mode The mode to check against.
	 * @return True if the setting is in the specified mode, false otherwise.
	 */
	public boolean is(String mode) {
		return index == modes.indexOf(mode);
	}

	/**
	 * Cycles to the next mode in the list of available modes.
	 * If already at the last mode, cycles back to the first mode.
	 */
	public void cycle() {
		if (index < modes.size() - 1) {
			index++;
		} else {
			index = 0;
		}
	}
}
