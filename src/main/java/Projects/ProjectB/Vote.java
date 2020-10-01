package Projects.ProjectB;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Vote {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String question;
    private String alternative1;
    private String alternative2;
    private int response1;
    private int response2;

    public Vote() {

    }

    public Vote(String question, String alternative1, String alternative2) {
        this.question = question;
        this.alternative1 = alternative1;
        this.alternative2 = alternative2;
        this.response1 = 0;
        this.response2 = 0;
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

    public int getResponse1() {
        return response1;
    }

    public void setResponse1(int response1) {
        this.response1 = response1;
    }

    public int getResponse2() {
        return response2;
    }

    public void setResponse2(int response2) {
        this.response2 = response2;
    }
}
