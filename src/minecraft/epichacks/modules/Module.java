package epichacks.modules;

// Superclass of what all hacks will build off
public class Module {
	// Name of the hack
	public String hackName;
	// Whether the hack is active or not
	public boolean hackToggle;
	// They key (on a keyboard) the activates the hack
	public int hackKeycode;
	public Category hackCategory;
	
	// Categories of Hacks
	public enum Category {
		COMBAT,
		MOVEMENT,
		PLAYER,
		RENDER
	}
	
	public Module(String name, int keycode, Category category) {
		this.hackName = name;
		this.hackKeycode = keycode;
		this.hackCategory = category;
	}
	
	public boolean isEnabled() {
		return hackToggle;
	}
	
	public int getKey() {
		return hackKeycode;
	}
	
	public void toggle() {
		hackToggle = !hackToggle;
		if (hackToggle) {
			onEnable();
		} else {
			onDisable();
		}
	}
	
	public void onEnable() {
		
	}
	
	public void onDisable() {
		
	}
}
