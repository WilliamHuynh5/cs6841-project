/**
 * The {@code Autosprint} class represents a hack that automatically sprints in the game.
 * It extends the {@link epichacks.modules.Module} class and provides specific behavior for enabling and disabling autosprint.
 */
public class Autosprint extends Module {
    /**
     * Constructs a new {@code Autosprint} hack with the default name, key code, and category.
     * The default key code is {@link org.lwjgl.input.Keyboard#KEY_N}.
     */
    public Autosprint() {
        super("autosprint", Keyboard.KEY_N, Category.MOVEMENT);
    }

    /**
     * Called when the hack is enabled.
     * This method is empty because autosprint does not require specific setup actions when enabled.
     */
    @Override
    public void onEnable() {
        // No specific actions needed when enabling autosprint.
    }

    /**
     * Called when the hack is disabled.
     * This method stops the player from sprinting when autosprint is disabled.
     */
    @Override
    public void onDisable() {
        mc.thePlayer.setSprinting(false);
    }

    /**
     * Handles an event, specifically the {@link epichacks.events.listeners.EventUpdate} event.
     * When the event is "PRE," this method sets the player to sprint.
     *
     * @param e The event to be handled.
     */
    @Override
    public void onEvent(Event e) {
        if (e instanceof EventUpdate) {
            if (e.isPre()) {
                mc.thePlayer.setSprinting(true);
            }
        }
    }
}
