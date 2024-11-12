package nyj001012.er_bal.repository;

import nyj001012.er_bal.domain.Question;

import java.util.List;
import java.util.Optional;

public interface IQuestionRepository {
    Question save(Question question);
    Optional<Question> findById(Long id);
    List<Question> findByQuestion(String question);
    Optional<Question> findByQuestionAB(String questionA, String questionB);
    Question findOneRandom();
    void deleteAll();
}
