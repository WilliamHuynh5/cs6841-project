package epichacks.events;

/**
 * The {@code Event} class represents a generic event that can be used in the EpicHacks framework.
 * It includes fields and methods for managing event properties such as cancellation, type, and direction.
 *
 * @param <T> The type of data associated with the event (generic).
 */
public class Event<T> {
    /**
     * A flag indicating whether the event has been cancelled.
     */
    public boolean cancelled;

    /**
     * The type of the event, which can be {@link EventType#PRE} or {@link EventType#POST}.
     */
    public EventType type;

    /**
     * The direction of the event, which can be INCOMING or OUTGOING.
     */
    public EventDirection direction;

    /**
     * Checks if the event has been cancelled.
     *
     * @return {@code true} if the event is cancelled, {@code false} otherwise.
     */
    public boolean isCancelled() {
        return cancelled;
    }

    /**
     * Sets the cancellation status of the event.
     *
     * @param cancelled {@code true} to cancel the event, {@code false} to leave it uncanceled.
     */
    public void setCancelled(boolean cancelled) {
        this.cancelled = cancelled;
    }

    /**
     * Gets the type of the event (PRE or POST).
     *
     * @return The event type.
     */
    public EventType getType() {
        return type;
    }

    /**
     * Sets the type of the event.
     *
     * @param type The event type to set.
     */
    public void setType(EventType type) {
        this.type = type;
    }

    /**
     * Checks if the event type is PRE.
     *
     * @return {@code true} if the event type is PRE, {@code false} otherwise.
     */
    public boolean isPre() {
        if (type == null) {
            return false;
        }
        return type == EventType.PRE;
    }

    /**
     * Checks if the event type is POST.
     *
     * @return {@code true} if the event type is POST, {@code false} otherwise.
     */
    public boolean isPost() {
        if (type == null) {
            return false;
        }
        return type == EventType.POST;
    }

    /**
     * Gets the direction of the event (INCOMING or OUTGOING).
     *
     * @return The event direction.
     */
    public EventDirection getDirection() {
        return direction;
    }

    /**
     * Sets the direction of the event.
     *
     * @param direction The event direction to set.
     */
    public void setDirection(EventDirection direction) {
        this.direction = direction;
    }

    /**
     * Checks if the event direction is INCOMING.
     *
     * @return {@code true} if the event direction is INCOMING, {@code false} otherwise.
     */
    public boolean isIncoming() {
        if (direction == null) {
            return false;
        }
        return direction == EventDirection.INCOMING;
    }

    /**
     * Checks if the event direction is OUTGOING.
     *
     * @return {@code true} if the event direction is OUTGOING, {@code false} otherwise.
     */
    public boolean isOutgoing() {
        if (direction == null) {
            return false;
        }
        return direction == EventDirection.OUTGOING;
    }
}
