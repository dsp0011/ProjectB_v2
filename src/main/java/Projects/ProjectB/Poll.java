package Projects.ProjectB;

import Projects.ProjectB.time.ITimeDuration;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Poll {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String question;
    private String alternative1;
    private String alternative2;
    private String timeLimit; // The duration for how long the poll should remain active.
    private String pollClosingDate; // The target time for when the poll should close.
    private Boolean isPublic;
    private Boolean isActive;
    private Boolean canEdit; // Specify if the poll is still editable.

    @OneToOne(cascade = CascadeType.ALL)
    private Vote vote = new Vote();

    @ManyToOne
    private User creator;

    @OneToMany(mappedBy = "poll", cascade = CascadeType.ALL)
    private List<IotDevice> iotDevices;

    public Poll() {
    }

    public Poll(String question, String alternative1, String alternative2,
                String timeLimit, Boolean isPublic, Boolean isActive, Boolean canEdit, User creator) {
        this.question = question;
        this.alternative1 = alternative1;
        this.alternative2 = alternative2;
        this.timeLimit = timeLimit;
        if (timeLimit == null || timeLimit.isEmpty() || timeLimit.toLowerCase().equals("inf")) {
            this.pollClosingDate = "inf";   // No time limitation
        } else {
            String closingDate = ITimeDuration.timeDurationFromStringOfTimeUnits(timeLimit)
                    .futureZonedDateTimeFromTimeDuration()
                    .toString();
            System.out.println("closingDate = " + closingDate);
            this.pollClosingDate = closingDate;
        }
        this.isPublic = isPublic;
        this.isActive = isActive;
        this.canEdit = canEdit;
        this.creator = creator;
        this.vote = new Vote();
        this.iotDevices = new ArrayList<>();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getAlternative1() {
        return alternative1;
    }

    public void setAlternative1(String alternative1) {
        this.alternative1 = alternative1;
    }

    public String getAlternative2() {
        return alternative2;
    }

    public void setAlternative2(String alternative2) {
        this.alternative2 = alternative2;
    }

    public String getTimeLimit() {
        return timeLimit;
    }

    public void setTimeLimit(String timeLimit) {
        System.out.println("trying to set time limit");
        if (timeLimit == null || timeLimit.isEmpty()) {
            this.timeLimit = "inf";
            System.out.println("timelimit was inf");
        } else {
            this.timeLimit = timeLimit;
            System.out.println("set the timelimit regular");
        }
    }

    public String getPollClosingDate() {
        return this.pollClosingDate;
    }

    public void setPollClosingDate(String pollClosingDate) {
        System.out.println("pollClosingDate value = " + pollClosingDate);
        System.out.println("canEdit = " + canEdit);
        System.out.println("isActive = " + isActive);
        if (pollClosingDate.equals("inf")) {
            this.pollClosingDate = pollClosingDate;
            System.out.println("pollClosingDate was inf");
        } else if (canEdit && !isActive) {
            System.out.println("setting poll closing date based on time limit: " + timeLimit);
            this.pollClosingDate = ITimeDuration
                    .timeDurationFromStringOfTimeUnits(this.timeLimit)
                    .futureZonedDateTimeFromTimeDuration()
                    .toString();
        } else {
            this.pollClosingDate = pollClosingDate;
            System.out.println("none of the above");
        }
        System.out.println("set the closing date to: " + this.pollClosingDate);
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
    }

    public Boolean getPublic() {
        return isPublic;
    }

    public void setPublic(Boolean isPublic) {
        this.isPublic = isPublic;
    }

    public Boolean getActive() {
        return isActive;
    }

    public void setActive(Boolean isActive) {
        this.isActive = isActive;
    }

    public Vote getVote() {
        return vote;
    }

    public void setVote(Vote vote) {
        this.vote = vote;
    }

    public User getCreator() {
        return creator;
    }

    public void setCreator(User creator) {
        this.creator = creator;
    }

    public List<IotDevice> getIotDevices() {
        return iotDevices;
    }

    public void setIotDevices(List<IotDevice> iotDevices) {
        this.iotDevices = iotDevices;
    }

    public Boolean getCanEdit() {
        return canEdit;
    }

    public void setCanEdit(Boolean canEdit) {
        this.canEdit = canEdit;
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
}
