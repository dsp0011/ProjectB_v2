package Projects.ProjectB.time;

import java.time.ZonedDateTime;
import java.util.ArrayList;

public interface ITimeDuration {

    int HOURS_IN_A_FRESH_DAY = 23;
    int MINUTES_IN_A_FRESH_HOUR = 59;
    int SECONDS_IN_A_FRESH_MINUTE = 59;

    int NUMBER_OF_SECONDS_IN_A_MINUTE = 60;
    int NUMBER_OF_MINUTES_IN_AN_HOUR = 60;
    int NUMBER_OF_HOURS_IN_A_DAY = 24;

    /**
     * Retrieves the number of days from the TimeDuration
     *
     * @return The number of days
     */
    Integer getDays();

    /**
     * Retrieves the number of hours from the TimeDuration
     *
     * @return The number of hours
     */
    Integer getHours();

    /**
     * Retrieves the number of minutes from the TimeDuration
     *
     * @return The number of minutes
     */
    Integer getMinutes();

    /**
     * Retrieves the number of seconds from the TimeDuration
     *
     * @return The number of seconds
     */
    Integer getSeconds();

    /**
     * Update the value of the time unit days, of the TimeDuration.
     *
     * @param newValue The new value for the time unit days.
     */
    void setDays(Integer newValue);

    /**
     * Update the value of the time unit hours, of the TimeDuration.
     *
     * @param newValue The new value for the time unit hours.
     */
    void setHours(Integer newValue);

    /**
     * Update the value of the time unit minutes, of the TimeDuration.
     *
     * @param newValue The new value for the time unit minutes.
     */
    void setMinutes(Integer newValue);

    /**
     * Update the value of the time unit seconds, of the TimeDuration.
     *
     * @param newValue The new value for the time unit seconds.
     */
    void setSeconds(Integer newValue);

    /**
     * Subtract the given number of days from the TimeDuration.
     *
     * @param daysToSubtract The number of days to subtract
     */
    void reduceDaysBy(Integer daysToSubtract);

    /**
     * Subtract the given number of hours from the TimeDuration.
     *
     * @param hoursToSubtract The number of hours to subtract
     */
    void reduceHoursBy(Integer hoursToSubtract);

    /**
     * Subtract the given number of minutes from the TimeDuration.
     *
     * @param minutesToSubtract The number of minutes to subtract
     */
    void reduceMinutesBy(Integer minutesToSubtract);

    /**
     * Subtract the given number of seconds from the TimeDuration.
     *
     * @param secondsToSubtract The number of seconds to subtract
     */
    void reduceSecondsBy(Integer secondsToSubtract);

    /**
     * Add the given number of days to the TimeDuration.
     *
     * @param daysToAdd The number of days to add
     */
    void increaseDaysBy(Integer daysToAdd);

    /**
     * Add the given number of hours to the TimeDuration.
     *
     * @param hoursToAdd The number of hours to add
     */
    void increaseHoursBy(Integer hoursToAdd);

    /**
     * Add the given number of minutes to the TimeDuration.
     *
     * @param minutesToAdd The number of minutes to add
     */
    void increaseMinutesBy(Integer minutesToAdd);

    /**
     * Add the given number of seconds to the TimeDuration.
     *
     * @param secondsToAdd The number of seconds to add
     */
    void increaseSecondsBy(Integer secondsToAdd);

    /**
     * Prints the time units of the TimeDuration.
     */
    void printTimeDuration();

    /**
     * Updates the TimeDuration to be one second less than it was.
     */
    void countDownOneSecond();

    /**
     * Get the ZonedDateTime for today, plus the added TimeDuration.
     *
     * @return A ZonedDateTime with the day, hour, minute and seconds of
     * the TimeDuration added to the current system time.
     */
    ZonedDateTime futureZonedDateTimeFromTimeDuration();

    /**
     * Takes a number of seconds, and converts it to a
     * list of time units that comprises the TimeDuration
     * (days, hours, minutes and seconds).
     *
     * @param timeInSeconds The time in seconds to be converted to time units.
     * @return A List of time units, that together comprises the TimeDuration:<br>
     * First element is the number of days.<br>
     * Second element is the number of hours.<br>
     * Third element is the number of minutes.<br>
     * Fourth element is the number of seconds.
     * @throws ArithmeticException if the provided number of seconds  is too large<br>
     *                             (timeInSeconds > 185.542.587.187.199)
     */
    static ArrayList<Integer> secondsToTimeUnits(long timeInSeconds) {
        long days, hours, minutes, seconds;
        minutes = timeInSeconds / NUMBER_OF_SECONDS_IN_A_MINUTE;
        hours = minutes / NUMBER_OF_MINUTES_IN_AN_HOUR;
        days = hours / NUMBER_OF_HOURS_IN_A_DAY;
        seconds = timeInSeconds - (minutes * NUMBER_OF_SECONDS_IN_A_MINUTE);
        minutes = minutes % NUMBER_OF_MINUTES_IN_AN_HOUR;
        hours = hours % NUMBER_OF_HOURS_IN_A_DAY;
        ArrayList<Integer> timeUnits = new ArrayList<>();
        timeUnits.add(Math.toIntExact(days)); // Fails if number of days exceed size of Integer
        timeUnits.add(Math.toIntExact(hours));
        timeUnits.add(Math.toIntExact(minutes));
        timeUnits.add(Math.toIntExact(seconds));
        return timeUnits;
    }

    /**
     * Takes a String of time units, and turns it into a TimeDuration.
     *
     * @param timeInTimeUnits A String of time units of the format<br>
     *                        "Days:x,Hours:y,Minutes:z,Seconds:a"
     * @return The TimeDuration representation of the given time units.
     */
    static ITimeDuration timeDurationFromStringOfTimeUnits(String timeInTimeUnits) {
        String[] timeUnits = timeInTimeUnits.split(",");
        for (int i = 0; i < timeUnits.length; i++) {
            timeUnits[i] = timeUnits[i].replaceAll("[^\\d-]", ""); // Remove all characters not digits
        }
        int days, hours, minutes, seconds;
        days = Integer.parseInt(timeUnits[0]);
        hours = Integer.parseInt(timeUnits[1]);
        minutes = Integer.parseInt(timeUnits[2]);
        seconds = Integer.parseInt(timeUnits[3]);
        ITimeDuration timeFromString = new TimeDuration(0L);
        timeFromString.setDays(days);
        timeFromString.setHours(hours);
        timeFromString.setMinutes(minutes);
        timeFromString.setSeconds(seconds);
        return timeFromString;
    }

}
