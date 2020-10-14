package Projects.ProjectB.time;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TimeDurationTest {

    private ITimeDuration timeDuration;
    private final Long ONE_MILLION_SECONDS = 1000000L;
    private final Long ONE_SECOND = 1L;
    private final Long ONE_MINUTE = 60 * ONE_SECOND;
    private final Long ONE_HOUR = 60 * ONE_MINUTE;
    private final Long ONE_DAY = 24 * ONE_HOUR;


    @BeforeEach
    void setUp() {
        this.timeDuration = new TimeDuration(ONE_SECOND);
    }

    @Test
    void secondsToTimeUnits() {

    }

    @Test
    void timeDurationFromStringOfTimeUnits() {
        int days = 1;
        int hours = 4;
        int minutes = 50;
        int seconds = 0;
        String oneDayFourHoursFiftyMinutesZeroSeconds = "Days: " + days + ", Hours: " + hours
                + ", Minutes: " + minutes
                + ", Seconds: " + seconds;
        ITimeDuration time = ITimeDuration.timeDurationFromStringOfTimeUnits(
                oneDayFourHoursFiftyMinutesZeroSeconds);
        assertEquals(days, time.getDays());
        assertEquals(hours, time.getHours());
        assertEquals(minutes, time.getMinutes());
        assertEquals(seconds, time.getSeconds());
        assertEquals(oneDayFourHoursFiftyMinutesZeroSeconds, time.toString());
    }

    @Test
    void getDays() {






    }

    @Test
    void getHours() {
    }

    @Test
    void getMinutes() {
    }

    @Test
    void getSeconds() {
    }

    @Test
    void setDays() {
    }

    @Test
    void setHours() {
    }

    @Test
    void setMinutes() {
    }

    @Test
    void setSeconds() {
    }

    @Test
    void reduceDaysBy() {
    }

    @Test
    void reduceHoursBy() {
    }

    @Test
    void reduceMinutesBy() {
    }

    @Test
    void reduceSecondsBy() {
    }

    @Test
    void increaseDaysBy() {
    }

    @Test
    void increaseHoursBy() {
    }

    @Test
    void increaseMinutesBy() {
    }

    @Test
    void increaseSecondsBy() {
    }

    @Test
    void printTimeDuration() {
    }

    @Test
    void testToString() {
    }

    @Test
    void countDownOneSecond() {
    }

    @Test
    void futureZonedDateTimeFromTimeDuration() {
    }
}