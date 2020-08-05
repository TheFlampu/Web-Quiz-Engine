package engine.quiz;

import engine.user.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class CompletedQuizService {
    CompletedQuizRepository completedQuizRepository;

    public CompletedQuizService(CompletedQuizRepository completedQuizRepository) {
        this.completedQuizRepository = completedQuizRepository;
    }

    public Page<CompletedQuiz> getAllCompletedQuiz(User author, int page)
    {
        Pageable paging = PageRequest.of(page, 10, Sort.by("completedAt").descending());
        return completedQuizRepository.findAllByAuthor(author, paging);
    }
}
