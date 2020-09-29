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
    private String alternativ1;
    private String alternativ2;
    private int respons1;
    private int respons2;

    public Vote() {

    }

    public Vote(String question, String alternativ1, String alternativ2) {
        this.question = question;
        this.alternativ1 = alternativ1;
        this.alternativ2 = alternativ2;
        this.respons1 = 0;
        this.respons2 = 0;
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

    public String getAlternativ1() {
        return alternativ1;
    }

    public void setAlternativ1(String alternativ1) {
        this.alternativ1 = alternativ1;
    }

    public String getAlternativ2() {
        return alternativ2;
    }

    public void setAlternativ2(String alternativ2) {
        this.alternativ2 = alternativ2;
    }

    public int getRespons1() {
        return respons1;
    }

    public void setRespons1(int respons1) {
        this.respons1 = respons1;
    }

    public int getRespons2() {
        return respons2;
    }

    public void setRespons2(int respons2) {
        this.respons2 = respons2;
    }
}
