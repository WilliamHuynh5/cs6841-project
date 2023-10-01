package epichacks.events;

/**
 * The {@code EventType} enum represents the type of an event, which can be either "PRE" or "POST".
 * Event types are used to categorize when an event occurs in a sequence of actions or operations.
 */
public enum EventType {
    /**
     * Represents an event that occurs before the main action or operation.
     * Typically, "PRE" events are used for preparation or setup actions.
     */
    PRE,

    /**
     * Represents an event that occurs after the main action or operation.
     * Typically, "POST" events are used for cleanup or follow-up actions.
     */
    POST
}

