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

    // Test data
    private final int DAYS = 40;
    private final int HOURS = 20;
    private final int MINUTES = 0;
    private final int SECONDS = 26;
    private final String TIME_UNITS = "Days: " + DAYS
            + ", Hours: " + HOURS
            + ", Minutes: " + MINUTES
            + ", Seconds: " + SECONDS;

    @BeforeEach
    void setUp() {
        this.timeDuration = ITimeDuration.timeDurationFromStringOfTimeUnits(TIME_UNITS);
    }

    @Test
    void oneSecondToTimeUnitsHasTheRightTimeUnits() {
        ITimeDuration time = new TimeDuration(ONE_SECOND);
        assertEquals(0, time.getDays());
        assertEquals(0, time.getHours());
        assertEquals(0, time.getMinutes());
        assertEquals(1, time.getSeconds());
        String correctTimeUnits = "Days: 0, Hours: 0, Minutes: 0, Seconds: 1";
        assertEquals(correctTimeUnits, time.toString());
    }

    @Test
    void oneMinuteToTimeUnitsHasTheRightTimeUnits() {
        ITimeDuration time = new TimeDuration(ONE_MINUTE);
        assertEquals(0, time.getDays());
        assertEquals(0, time.getHours());
        assertEquals(1, time.getMinutes());
        assertEquals(0, time.getSeconds());
        String correctTimeUnits = "Days: 0, Hours: 0, Minutes: 1, Seconds: 0";
        assertEquals(correctTimeUnits, time.toString());
    }

    @Test
    void oneHourToTimeUnitsHasTheRightTimeUnits() {
        ITimeDuration time = new TimeDuration(ONE_HOUR);
        assertEquals(0, time.getDays());
        assertEquals(1, time.getHours());
        assertEquals(0, time.getMinutes());
        assertEquals(0, time.getSeconds());
        String correctTimeUnits = "Days: 0, Hours: 1, Minutes: 0, Seconds: 0";
        assertEquals(correctTimeUnits, time.toString());
    }

    @Test
    void oneDayToTimeUnitsHasTheRightTimeUnits() {
        ITimeDuration time = new TimeDuration(ONE_DAY);
        assertEquals(1, time.getDays());
        assertEquals(0, time.getHours());
        assertEquals(0, time.getMinutes());
        assertEquals(0, time.getSeconds());
        String correctTimeUnits = "Days: 1, Hours: 0, Minutes: 0, Seconds: 0";
        assertEquals(correctTimeUnits, time.toString());
    }

    @Test
    void timeDurationFromStringOfTimeUnits() {
        ITimeDuration time = ITimeDuration.timeDurationFromStringOfTimeUnits(TIME_UNITS);
        assertEquals(DAYS, time.getDays());
        assertEquals(HOURS, time.getHours());
        assertEquals(MINUTES, time.getMinutes());
        assertEquals(SECONDS, time.getSeconds());
        assertEquals(TIME_UNITS, time.toString());
    }

    @Test
    void timeDurationIsCreatedWithCorrectTimeUnitSeconds() {
        long zeroSeconds = 0L;
        ITimeDuration time1 = new TimeDuration(zeroSeconds);
        assertEquals(0, time1.getSeconds());

        ITimeDuration time2 = new TimeDuration(ONE_SECOND);
        assertEquals(1, time2.getSeconds());

        long fiftyNineSeconds = 59L;
        ITimeDuration time3 = new TimeDuration(fiftyNineSeconds);
        assertEquals(0, time3.getMinutes());
        assertEquals(59, time3.getSeconds());

        long sixtySeconds = 60L;
        ITimeDuration time4 = new TimeDuration(sixtySeconds);
        assertEquals(1, time4.getMinutes());
        assertEquals(0, time4.getSeconds());

        long minusOneSecond = -ONE_SECOND;
        ITimeDuration time5 = new TimeDuration(minusOneSecond);
        assertEquals(0, time5.getDays());
        assertEquals(0, time5.getHours());
        assertEquals(0, time5.getMinutes());
        assertEquals(-1, time5.getSeconds());

        long minusFiftyNineSeconds = 59 * (-ONE_SECOND);
        ITimeDuration time6 = new TimeDuration(minusFiftyNineSeconds);
        assertEquals(0, time6.getDays());
        assertEquals(0, time6.getHours());
        assertEquals(0, time6.getMinutes());
        assertEquals(-59, time6.getSeconds());

        long minusSixtySeconds = 60 * (-ONE_SECOND);
        ITimeDuration time7 = new TimeDuration(minusSixtySeconds);
        assertEquals(0, time7.getDays());
        assertEquals(0, time7.getHours());
        assertEquals(-1, time7.getMinutes());
        assertEquals(0, time7.getSeconds());
    }

    @Test
    void timeDurationIsCreatedWithCorrectTimeUnitMinutes() {
        long zeroMinutes = ONE_MINUTE - 1;
        ITimeDuration time1 = new TimeDuration(zeroMinutes);
        assertEquals(0, time1.getMinutes());
        assertEquals(59, time1.getSeconds());

        ITimeDuration time2 = new TimeDuration(ONE_MINUTE);
        assertEquals(1, time2.getMinutes());
        assertEquals(0, time2.getSeconds());

        long fiftyNineMinutes = 59 * ONE_MINUTE;
        ITimeDuration time3 = new TimeDuration(fiftyNineMinutes);
        assertEquals(0, time3.getHours());
        assertEquals(59, time3.getMinutes());

        long sixtyMinutes = 60 * ONE_MINUTE;
        ITimeDuration time4 = new TimeDuration(sixtyMinutes);
        assertEquals(1, time4.getHours());
        assertEquals(0, time4.getMinutes());

        long minusOneMinute = -ONE_MINUTE;
        ITimeDuration time5 = new TimeDuration(minusOneMinute);
        assertEquals(0, time5.getDays());
        assertEquals(0, time5.getHours());
        assertEquals(-1, time5.getMinutes());
        assertEquals(0, time5.getSeconds());

        long minusFiftyNineMinutes = 59 * (-ONE_MINUTE);
        ITimeDuration time6 = new TimeDuration(minusFiftyNineMinutes);
        assertEquals(0, time6.getDays());
        assertEquals(0, time6.getHours());
        assertEquals(-59, time6.getMinutes());
        assertEquals(0, time6.getSeconds());

        long minusSixtyMinutes = 60 * (-ONE_MINUTE);
        ITimeDuration time7 = new TimeDuration(minusSixtyMinutes);
        assertEquals(0, time7.getDays());
        assertEquals(-1, time7.getHours());
        assertEquals(0, time7.getMinutes());
        assertEquals(0, time7.getSeconds());
    }

    @Test
    void timeDurationIsCreatedWithCorrectTimeUnitHours() {
        long zeroHours = ONE_HOUR - 1;
        ITimeDuration time1 = new TimeDuration(zeroHours);
        assertEquals(0, time1.getHours());
        assertEquals(59, time1.getMinutes());

        ITimeDuration time2 = new TimeDuration(ONE_HOUR);
        assertEquals(1, time2.getHours());
        assertEquals(0, time2.getMinutes());

        long twentyThreeHours = 23 * ONE_HOUR;
        ITimeDuration time3 = new TimeDuration(twentyThreeHours);
        assertEquals(0, time3.getDays());
        assertEquals(23, time3.getHours());

        long twentyFourHours = 24 * ONE_HOUR;
        ITimeDuration time4 = new TimeDuration(twentyFourHours);
        assertEquals(1, time4.getDays());
        assertEquals(0, time4.getHours());

        long minusOneHour = -ONE_HOUR;
        ITimeDuration time5 = new TimeDuration(minusOneHour);
        assertEquals(0, time5.getDays());
        assertEquals(-1, time5.getHours());
        assertEquals(0, time5.getMinutes());
        assertEquals(0, time5.getSeconds());

        long minusTwentyThreeHours = 23 * (-ONE_HOUR);
        ITimeDuration time6 = new TimeDuration(minusTwentyThreeHours);
        assertEquals(0, time6.getDays());
        assertEquals(-23, time6.getHours());
        assertEquals(0, time6.getMinutes());
        assertEquals(0, time6.getSeconds());

        long minusTwentyFourHours = 24 * (-ONE_HOUR);
        ITimeDuration time7 = new TimeDuration(minusTwentyFourHours);
        assertEquals(-1, time7.getDays());
        assertEquals(0, time7.getHours());
        assertEquals(0, time7.getMinutes());
        assertEquals(0, time7.getSeconds());
    }

    @Test
    void timeDurationIsCreatedWithCorrectTimeUnitDays() {
        long zeroDays = ONE_DAY - 1;
        ITimeDuration time1 = new TimeDuration(zeroDays);
        assertEquals(0, time1.getDays());
        assertEquals(23, time1.getHours());

        ITimeDuration time2 = new TimeDuration(ONE_DAY);
        assertEquals(1, time2.getDays());
        assertEquals(0, time2.getHours());

        long maxNumberOfDays = Integer.MAX_VALUE * ONE_DAY;
        ITimeDuration time3 = new TimeDuration(maxNumberOfDays);
        assertEquals(Integer.MAX_VALUE, time3.getDays());
        assertEquals(0, time3.getHours());

        long minusOneDay = -ONE_DAY;
        ITimeDuration time4 = new TimeDuration(minusOneDay);
        assertEquals(-1, time4.getDays());
        assertEquals(0, time4.getHours());
        assertEquals(0, time4.getMinutes());
        assertEquals(0, time4.getSeconds());

        long minusMaxNumberOfDays = Integer.MIN_VALUE * ONE_DAY;
        ITimeDuration time5 = new TimeDuration(minusMaxNumberOfDays);
        assertEquals(Integer.MIN_VALUE, time5.getDays());
        assertEquals(0, time5.getHours());
        assertEquals(0, time5.getMinutes());
        assertEquals(0, time5.getSeconds());
    }
    
    @Test
    void getDays() {
        assertEquals(DAYS, timeDuration.getDays());
    }

    @Test
    void getHours() {
        assertEquals(HOURS, timeDuration.getHours());
    }

    @Test
    void getMinutes() {
        assertEquals(MINUTES, timeDuration.getMinutes());
    }

    @Test
    void getSeconds() {
        assertEquals(SECONDS, timeDuration.getSeconds());
    }

    /*
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

     */
}