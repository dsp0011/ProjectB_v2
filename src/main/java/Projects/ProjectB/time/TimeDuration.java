package Projects.ProjectB.time;

import java.time.ZonedDateTime;
import java.util.ArrayList;

public class TimeDuration implements ITimeDuration {

    /**
     * A List of time units, that together comprises the TimeDuration:<br>
     * First element is the number of days.<br>
     * Second element is the number of hours.<br>
     * Third element is the number of minutes.<br>
     * Fourth element is the number of seconds.
     */
    private final ArrayList<Integer> timeDuration;

    public TimeDuration(Long timeInSeconds) {
        this.timeDuration = ITimeDuration.secondsToTimeUnits(timeInSeconds);
    }

    @Override
    public Integer getDays() {
        return this.timeDuration.get(0);
    }

    @Override
    public Integer getHours() {
        return this.timeDuration.get(1);
    }

    @Override
    public Integer getMinutes() {
        return this.timeDuration.get(2);
    }

    @Override
    public Integer getSeconds() {
        return this.timeDuration.get(3);
    }

    @Override
    public void setDays(Integer newValue) {
            this.timeDuration.set(0, newValue);
    }

    @Override
    public void setHours(Integer newValue) {
        if (newValue < 0) {
            reduceHoursBy(newValue * (-1));
        } else if (newValue >= NUMBER_OF_HOURS_IN_A_DAY) {
            increaseHoursBy(newValue);
        } else {
            this.timeDuration.set(1, newValue);
        }
    }

    @Override
    public void setMinutes(Integer newValue) {
        if (newValue < 0) {
            reduceMinutesBy(newValue * (-1));
        } else if (newValue >= NUMBER_OF_MINUTES_IN_AN_HOUR) {
            increaseMinutesBy(newValue);
        } else {
            this.timeDuration.set(2, newValue);
        }
    }

    @Override
    public void setSeconds(Integer newValue) {
        if (newValue < 0) {
            reduceSecondsBy(newValue * (-1));
        } else if (newValue >= NUMBER_OF_SECONDS_IN_A_MINUTE) {
            increaseSecondsBy(newValue);
        } else {
            this.timeDuration.set(3, newValue);
        }
    }

    @Override
    public void reduceDaysBy(Integer daysToSubtract) {
        int days = this.getDays();
        int newValueOfDays = days - daysToSubtract;
        if (daysToSubtract < 0) {
            increaseDaysBy(daysToSubtract * (-1)); // Someone called the wrong function...
        } else {
            setDays(newValueOfDays);
        }
    }

    @Override
    public void reduceHoursBy(Integer hoursToSubtract) {
        int hours = this.getHours();
        int newValueOfHours = hours - hoursToSubtract;
        if (hoursToSubtract < 0) {
            increaseHoursBy(hoursToSubtract * (-1)); // Someone called the wrong function...
        } else if (newValueOfHours < 0) {
            int daysToSubtract = 1 + (Math.abs(newValueOfHours) / NUMBER_OF_HOURS_IN_A_DAY);
            reduceDaysBy(daysToSubtract);
            int remainingHours = newValueOfHours + (daysToSubtract * NUMBER_OF_HOURS_IN_A_DAY);
            setHours(remainingHours);
        } else {
            setHours(newValueOfHours);
        }
    }

    @Override
    public void reduceMinutesBy(Integer minutesToSubtract) {
        int minutes = this.getMinutes();
        int newValueOfMinutes = minutes - minutesToSubtract;
        if (minutesToSubtract < 0) {
            increaseMinutesBy(minutesToSubtract * (-1)); // Someone called the wrong function...
        } else if (newValueOfMinutes < 0) {
            int hoursToSubtract = 1 + (Math.abs(newValueOfMinutes) / NUMBER_OF_MINUTES_IN_AN_HOUR);
            reduceHoursBy(hoursToSubtract);
            int remainingMinutes = newValueOfMinutes + (hoursToSubtract * NUMBER_OF_MINUTES_IN_AN_HOUR);
            setMinutes(remainingMinutes);
        } else {
            setMinutes(newValueOfMinutes);
        }
    }

    @Override
    public void reduceSecondsBy(Integer secondsToSubtract) {
        int seconds = this.getSeconds();
        int newValueOfSeconds = seconds - secondsToSubtract;
        if (secondsToSubtract < 0) {
            increaseSecondsBy(secondsToSubtract * (-1)); // Someone called the wrong function...
        } else if (newValueOfSeconds < 0) {
            int minutesToSubtract = 1 + (Math.abs(newValueOfSeconds) / NUMBER_OF_SECONDS_IN_A_MINUTE);
            reduceMinutesBy(minutesToSubtract);
            int remainingSeconds = newValueOfSeconds + (minutesToSubtract * NUMBER_OF_SECONDS_IN_A_MINUTE);
            setSeconds(remainingSeconds);
        } else {
            setSeconds(newValueOfSeconds);
        }
    }

    @Override
    public void increaseDaysBy(Integer daysToAdd) {
        int days = this.getDays();
        int newValueOfDays = days + daysToAdd;
        if (daysToAdd < 0) {
            reduceDaysBy(daysToAdd * (-1)); // Someone called the wrong function...
        } else {
            setDays(newValueOfDays);
        }
    }

    @Override
    public void increaseHoursBy(Integer hoursToAdd) {
        int hours = this.getHours();
        int newValueOfHours = hours + hoursToAdd;
        if (hoursToAdd < 0) {
            reduceHoursBy(hoursToAdd * (-1)); // Someone called the wrong function...
        } else if (newValueOfHours >= NUMBER_OF_HOURS_IN_A_DAY) {
            int daysToAdd = newValueOfHours / NUMBER_OF_HOURS_IN_A_DAY;
            increaseDaysBy(daysToAdd);
            int remainingHours = newValueOfHours - (daysToAdd * NUMBER_OF_HOURS_IN_A_DAY);
            setHours(remainingHours);
        } else {
            setHours(newValueOfHours);
        }
    }

    @Override
    public void increaseMinutesBy(Integer minutesToAdd) {
        int minutes = this.getMinutes();
        int newValueOfMinutes = minutes + minutesToAdd;
        if (minutesToAdd < 0) {
            reduceMinutesBy(minutesToAdd * (-1)); // Someone called the wrong function...
        } else if (newValueOfMinutes >= NUMBER_OF_MINUTES_IN_AN_HOUR) {
            int hoursToAdd = newValueOfMinutes / NUMBER_OF_MINUTES_IN_AN_HOUR;
            increaseHoursBy(hoursToAdd);
            int remainingMinutes = newValueOfMinutes - (hoursToAdd * NUMBER_OF_MINUTES_IN_AN_HOUR);
            setMinutes(remainingMinutes);
        } else {
            setMinutes(newValueOfMinutes);
        }
    }

    @Override
    public void increaseSecondsBy(Integer secondsToAdd) {
        int seconds = this.getSeconds();
        int newValueOfSeconds = seconds + secondsToAdd;
        if (secondsToAdd < 0) {
            reduceSecondsBy(secondsToAdd * (-1)); // Someone called the wrong function...
        } else if (newValueOfSeconds >= NUMBER_OF_SECONDS_IN_A_MINUTE) {
            int minutesToAdd = newValueOfSeconds / NUMBER_OF_SECONDS_IN_A_MINUTE;
            increaseMinutesBy(minutesToAdd);
            int remainingSeconds = newValueOfSeconds - (minutesToAdd * NUMBER_OF_SECONDS_IN_A_MINUTE);
            setSeconds(remainingSeconds);
        } else {
            setSeconds(newValueOfSeconds);
        }
    }

    @Override
    public void printTimeDuration() {
        System.out.println(this.toString());
    }

    @Override
    public String toString() {
        return "Days: " + this.getDays()
                + ", Hours: " + this.getHours()
                + ", Minutes: " + this.getMinutes()
                + ", Seconds: " + this.getSeconds();
    }

    @Override
    public void countDownOneSecond() {
        Integer seconds = this.getSeconds();
        if (seconds - 1 < 0) {
            Integer minutes = getMinutes();
            if (minutes - 1 < 0) {
                Integer hours = getHours();
                if (hours - 1 < 0) {
                    Integer days = getDays();
                    setDays(days - 1);
                    setHours(HOURS_IN_A_FRESH_DAY);
                } else {
                    setHours(hours - 1);
                }
                setMinutes(MINUTES_IN_A_FRESH_HOUR);
            } else {
                setMinutes(minutes - 1);
            }
            setSeconds(SECONDS_IN_A_FRESH_MINUTE);
        } else {
            setSeconds(seconds - 1);
        }
    }

    @Override
    public ZonedDateTime futureZonedDateTimeFromTimeDuration() {
        long days, hours, minutes, seconds;
        days = this.getDays().longValue();
        hours = this.getHours().longValue();
        minutes = this.getMinutes().longValue();
        seconds = this.getSeconds().longValue();
        ZonedDateTime zonedDateTime = ZonedDateTime.now();
        zonedDateTime = zonedDateTime.plusDays(days);
        zonedDateTime = zonedDateTime.plusHours(hours);
        zonedDateTime = zonedDateTime.plusMinutes(minutes);
        zonedDateTime = zonedDateTime.plusSeconds(seconds);
        return zonedDateTime;
    }


}
