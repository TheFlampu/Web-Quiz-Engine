package engine.quiz;

import engine.user.User;
import org.springframework.data.domain.Page;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
public interface CompletedQuizRepository extends PagingAndSortingRepository<CompletedQuiz, Integer> {
    Page<CompletedQuiz> findAllByAuthor(User author, org.springframework.data.domain.Pageable pageable);
    @Transactional
    void deleteAllByQuiz(Quiz quiz);
}
