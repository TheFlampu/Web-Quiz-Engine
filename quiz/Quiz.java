package engine.quiz;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import engine.user.User;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Entity
public class Quiz {
    @Id
    @GeneratedValue
    private int id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String text;

    @ElementCollection
    @NotNull
    @Size(min = 2)
    private List<String> options;

    @ElementCollection
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private List<Integer> answer;

    @JsonIgnore
    @ManyToOne(cascade = CascadeType.DETACH)
    private User author;

    public Quiz() {
    }

    public Quiz(int id, String title, String text) {
        this.id = id;
        this.title = title;
        this.text = text;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public List<String> getOptions() {
        return options;
    }

    public void setOptions(List<String> options) {
        this.options = options;
    }

    public List<Integer> getAnswer() {
        return answer;
    }

    public void setAnswer(List<Integer> answer) {
        this.answer = answer;
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }
}
