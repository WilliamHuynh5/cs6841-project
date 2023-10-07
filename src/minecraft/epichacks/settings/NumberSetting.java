package epichacks.settings;

/**
 * The {@code NumberSetting} class represents a setting that holds a numerical value.
 * It extends the {@link epichacks.settings.Setting} class.
 */
public class NumberSetting extends Setting {

	public double value, minimum, maximum, increment;

	/**
	 * Constructor to create a NumberSetting with a specified name and initial values.
	 *
	 * @param name The name of the numerical setting.
	 * @param value The initial value of the numerical setting.
	 * @param minimum The minimum value the numerical setting can take.
	 * @param maximum The maximum value the numerical setting can take.
	 * @param increment The increment step for changing the numerical value.
	 */
	public NumberSetting(String name, double value, double minimum, double maximum, double increment) {
		this.name = name;
		this.value = value;
		this.minimum = minimum;
		this.maximum = maximum;
		this.increment = increment;
	}

	public double getValue() {
		return value;
	}

	/**
	 * Sets the value of the numerical setting, ensuring it falls within the defined range.
	 *
	 * @param value The new value to set for the numerical setting.
	 */
	public void setValue(double value) {
		// Round the value to the nearest increment and ensure it's within the defined range
		double precision = 1 / increment;
		this.value = Math.round(Math.max(minimum, Math.min(maximum, value)) * precision) / precision;
	}
	
	/**
	 * Increments or decrements the numerical setting value by the defined increment.
	 *
	 * @param positive True to increment, false to decrement.
	 */
	public void increment (boolean positive) {
		setValue(getValue() + (positive ? 1 : -1) * increment);
	}

	public double getMinimum() {
		return minimum;
	}

	public void setMinimum(double minimum) {
		this.minimum = minimum;
	}

	public double getMaximum() {
		return maximum;
	}

	public void setMaximum(double maximum) {
		this.maximum = maximum;
	}

	public double getIncrement() {
		return increment;
	}

	public void setIncrement(double increment) {
		this.increment = increment;
	}
	
}
