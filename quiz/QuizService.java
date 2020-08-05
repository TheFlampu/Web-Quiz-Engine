package engine.quiz;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class QuizService {
    QuizRepository quizRepository;

    @Autowired
    public QuizService(QuizRepository quizRepository) {
        this.quizRepository = quizRepository;
    }

    public Page<Quiz> getAllQuiz(Integer pageNo, Integer pageSize, String sortBy)
    {
        PageRequest paging = PageRequest.of(pageNo, pageSize, Sort.by(sortBy));
        return quizRepository.findAll(paging);
    }
}
