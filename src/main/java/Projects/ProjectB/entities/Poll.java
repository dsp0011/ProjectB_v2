package Projects.ProjectB.entities;

import Projects.ProjectB.time.ITimeDuration;
import Projects.ProjectB.time.TimeDuration;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Poll {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String question;
    private String alternative1;
    private String alternative2;
    @Setter(AccessLevel.NONE)private String timeLimit; // The duration for how long the poll should remain active.
    @Setter(AccessLevel.NONE)private String pollClosingDate; // The target time for when the poll should close.
    private boolean isPublic;
    private boolean isActive;
    private boolean canEdit; // Specify if the poll is still editable.


    @OneToOne(cascade = CascadeType.ALL)
    private Vote vote = new Vote();

    @ManyToOne
    private User creator;

    @ManyToMany
    private List<User> usersVoted;

    @OneToMany(mappedBy = "poll", cascade = CascadeType.ALL)
    private List<IotDevice> iotDevices;

    public Poll(String question, String alternative1, String alternative2,
                String timeLimit, boolean isPublic, boolean isActive, boolean canEdit, User creator) {
        this.question = question;
        this.alternative1 = alternative1;
        this.alternative2 = alternative2;
        this.isPublic = isPublic;
        this.isActive = isActive;
        this.canEdit = canEdit;
        this.usersVoted = new ArrayList<>();
        setTimeLimit(timeLimit);
        this.creator = creator;
        this.vote = new Vote();
        this.iotDevices = new ArrayList<>();
    }
/*
        1. Poll does not exist.

        2. Poll is created:
           canEdit = true && isActive = false;
           The poll can be edited, so the poll closing date should
           be auto renewed based on timeLimit, unless no timeLimit
           is specified (timeLimit = "inf").

        3. Poll is finished (published to the masses) and can now be voted on:
           canEdit = false && isActive = true;
           Users can vote on the poll, and it is no longer possible
           to make changes to the poll.
           Poll closing date should have been updated when the poll was published,
           and users should somehow see the time left until that date.
           Poll closing date should no longer be changed.

       4. Poll is closed:
          canEdit = false && isActive = false;
          Poll has ended, either because it reached pollClosingDate,
          or the creator manually closed the poll.
*/
    /**
     * Mark the poll as published.
     */
    public void publishPoll() {
        if (this.canEdit && !this.isActive) {
            updatePollClosingDate();
            this.canEdit = false;
            this.isActive = true;
        } else {
            throw new IllegalStateException("Poll has already been published");
        }
    }

    /**
     * Mark an active poll as closed.
     */
    public void closePoll() {
        if (this.isActive && !this.canEdit) {
            this.isActive = false;
        } else {
            throw new IllegalStateException("Poll is not active and can therefore not be closed");
        }
    }

    /**
     * Compute the time between now and pollClosingDate,
     * and return the TimeDuration.
     *
     * @return The time left before the poll closes as time units or "inf".
     */
    public String computeTimeRemaining() {
        if (pollClosingDate.toLowerCase().equals("inf")) {
            return "inf";
        }
        if (!canEdit && !isActive) {
            return "Poll is closed";
        }
        ZonedDateTime closingDate = ZonedDateTime.parse(pollClosingDate);
        ZonedDateTime now = ZonedDateTime.now();
        long seconds = ChronoUnit.SECONDS.between(now, closingDate);
        return new TimeDuration(seconds).toString();
    }

    public void setTimeLimit(String timeLimit) {
        if (timeLimit == null
                || timeLimit.isEmpty()
                || timeLimit.toLowerCase().equals("inf")) {
            this.timeLimit = "inf";
        } else {
            this.timeLimit = timeLimit;
        }
        updatePollClosingDate();
    }

    private void updatePollClosingDate() {
        if (this.canEdit) {
            if (this.timeLimit == null
                    || this.timeLimit.isEmpty()
                    || this.timeLimit.toLowerCase().equals("inf")) {
                this.pollClosingDate = "inf";   // No time limitation
            } else {
                this.pollClosingDate = ITimeDuration
                        .timeDurationFromStringOfTimeUnits(this.timeLimit)
                        .futureZonedDateTimeFromTimeDuration()
                        .toString();
            }
        }
    }

    public void setPollClosingDate(String pollClosingDate) {
        if (this.canEdit) {
            updatePollClosingDate();
        } else {
            this.pollClosingDate = pollClosingDate;
        }
    }

    @Override
    public String toString() {
        return String.format(
                "Poll[Id='%d', " +
                        "Question 1='%s', " +
                        "Alternative 1='%s', " +
                        "Alternative 2='%s', " +
                        "Public='%s', " +
                        "Active='%s', " +
                        "Can edit poll='%s', " +
                        "Time Limit='%s', " +
                        "Poll Closing Date='%s', " +
                        "Creator='%s', " +
                        "Vote='%s']",
                id, question, alternative1, alternative2,
                isPublic, isActive, canEdit, timeLimit,
                pollClosingDate, creator, vote
        );
    }

    public void updateUsersVoted(User user) {
        if (this.usersVoted == null) {
            this.usersVoted = new ArrayList<>();
            this.usersVoted.add(user);
        } else {
            this.usersVoted.add(user);
        }
    }
}
