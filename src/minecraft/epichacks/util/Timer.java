package epichacks.util;

/**
 * Timer class for measuring time intervals and checking if a specific time has elapsed.
 */
public class Timer {
	public long lastMS;
	

    /**
     * Resets the timer to the current system time in milliseconds.
     */
	public void reset() {
		lastMS = System.currentTimeMillis();
	}
	
    /**
     * Checks if the specified time has elapsed since the last reset.
     *
     * @param time   The time in milliseconds to check against.
     * @param reset  Whether to reset the timer if the time has elapsed.
     * @return       True if the specified time has elapsed, false otherwise.
     */
	public boolean hasTimeElapsed(long time, boolean reset) {
		if (System.currentTimeMillis() - lastMS > time) {
			if (reset)
				reset();
			return true;
		}
		return false;
	}
}
