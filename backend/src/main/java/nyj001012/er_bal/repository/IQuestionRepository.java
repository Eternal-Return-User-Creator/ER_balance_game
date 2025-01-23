package nyj001012.er_bal.repository;

import nyj001012.er_bal.domain.Question;

import java.util.List;
import java.util.Optional;

public interface IQuestionRepository {
    Question save(Question question);
    Optional<Question> findById(Long id);
    Optional<Question> findByQuestionTextAndChoiceAB(String questionText, String choiceA, String choiceB);
    Question findOneRandom();
    int countChoiceOfQuestion(Long id, char flag);
    void updateChoiceCount(Long id, char flag);
    void deleteAll();
}
