package engine.quiz;

import engine.user.User;
import engine.user.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;

@RestController
public class QuizController {
    private final QuizRepository quizList;
    private final UserRepository users;
    private final PasswordEncoder passwordEncoder;
    private final QuizService quizService;
    private final CompletedQuizRepository completedQuizRepository;
    private final CompletedQuizService completedQuizService;

    public QuizController(QuizRepository quizList, UserRepository users, PasswordEncoder passwordEncoder, QuizService quizService, CompletedQuizRepository completedQuizRepository, CompletedQuizService completedQuizService) {
        this.quizList = quizList;
        this.users = users;
        this.passwordEncoder = passwordEncoder;
        this.quizService = quizService;
        this.completedQuizRepository = completedQuizRepository;
        this.completedQuizService = completedQuizService;
    }

    @RequestMapping("/api/quizzes")
    public Page<Quiz> getQuizzes(@RequestParam(defaultValue = "0") Integer page,
                                 @RequestParam(defaultValue = "10") Integer pageSize,
                                 @RequestParam(defaultValue = "id") String sortBy) {

        return quizService.getAllQuiz(page, pageSize, sortBy);
    }

    @RequestMapping("/api/quizzes/{quizId}")
    public Quiz getQuiz(@PathVariable int quizId) {
        return quizList.findById(quizId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping("/api/quizzes/{quizId}")
    public ResponseEntity<Object> delete(@PathVariable Integer quizId, @AuthenticationPrincipal User user) {
        var quiz = quizList.findById(quizId);
        if (quiz.isPresent()) {
            if (quiz.get().getAuthor().getEmail().equals(user.getEmail())) {
                completedQuizRepository.deleteAllByQuiz(quiz.get());
                quizList.delete(quiz.get());
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            } else {
                return new ResponseEntity<>(HttpStatus.FORBIDDEN);
            }
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping(value = "/api/quizzes", consumes = "application/json")
    public Quiz greet(@RequestBody @Valid Quiz newQuiz, @AuthenticationPrincipal User user) {
        newQuiz.setAuthor(user);
        return quizList.save(newQuiz);
    }

    @PostMapping(value = "/api/quizzes/{quizId}/solve", consumes = "application/json")
    public FeedBack solveQuiz(@PathVariable int quizId, @RequestBody Answer answer, @AuthenticationPrincipal User user) {
        Quiz quiz = quizList.findById(quizId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        if (quiz.getAnswer().toString().equals(answer.getAnswer().toString())) {
            completedQuizRepository.save(new CompletedQuiz(user, quiz));
            return new FeedBack(true);
        }
        return new FeedBack(false);
    }

    @PostMapping(value = "/api/register", consumes = "application/json")
    public ResponseEntity<User> post(@Valid @RequestBody User user) {
        if (users.findByEmail(user.getEmail()).isPresent()) {
            return ResponseEntity.badRequest().build();
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return ResponseEntity.ok(users.save(user));
    }

    @RequestMapping("/api/quizzes/completed")
    public Page<CompletedQuiz> getComplete(@AuthenticationPrincipal User user, @RequestParam(defaultValue = "0") Integer page) {
        System.out.println(user);
         return completedQuizService.getAllCompletedQuiz(user, page);
    }
}
