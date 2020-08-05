package engine.quiz;

import com.fasterxml.jackson.annotation.*;
import engine.user.User;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
public class CompletedQuiz {
    @Id
    @GeneratedValue
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private int id;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @ManyToOne(cascade = CascadeType.DETACH)
    private Quiz quiz;

    @JsonProperty("id")
    public int getQuizId() {
        return quiz.getId();
    }

    private LocalDateTime completedAt;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @ManyToOne(cascade = CascadeType.DETACH)
    private User author;

    public CompletedQuiz() {
    }

    public CompletedQuiz(User user, Quiz quiz) {
        this.author = user;
        this.quiz = quiz;
        completedAt = LocalDateTime.now();

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalDateTime getCompletedAt() {
        return completedAt;
    }

    public void setCompletedAt(LocalDateTime completedAt) {
        this.completedAt = completedAt;
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public Quiz getQuiz() {
        return quiz;
    }

    public void setQuiz(Quiz quiz) {
        this.quiz = quiz;
    }
}
