package epichacks.settings;

/**
 * The {@code BooleanSetting} class represents a setting that holds a boolean value.
 * It extends the {@link epichacks.settings.Setting} class.
 */
public class BooleanSetting extends Setting {

	// Variable to store the state of the boolean setting (true or false)
	public boolean enabled;

	/**
	 * Constructor to create a BooleanSetting with a specified name and initial enabled state.
	 *
	 * @param name The name of the boolean setting.
	 * @param enabled The initial state of the boolean setting (true if enabled, false otherwise).
	 */
	public BooleanSetting(String name, boolean enabled) {
		this.name = name;
		this.enabled = enabled;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}
	
	public void toggle() {
		enabled = !enabled;
	}
		
}

