package nyj001012.er_bal.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import nyj001012.er_bal.domain.Question;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class QuestionRepository implements IQuestionRepository {

    @PersistenceContext
    private final EntityManager entityManager;

    public QuestionRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    @Transactional
    public Question save(Question question) {
        entityManager.persist(question);
        return question;
    }

    @Override
    public Optional<Question> findById(Long id) {
        Question question = entityManager.find(Question.class, id);
        return Optional.ofNullable(question);
    }

    @Override
    public List<Question> findByQuestion(String question) {
        String jpql = "SELECT q FROM Question q WHERE q.questionA = :question OR q.questionB = :question";
        return entityManager.createQuery(jpql, Question.class)
                .setParameter("question", question)
                .getResultList();
    }

    @Override
    public Optional<Question> findByQuestionAB(String questionA, String questionB) {
        String jpql = "SELECT q FROM Question q WHERE (q.questionA = :questionA AND q.questionB = :questionB) OR (q.questionA = :questionB AND q.questionB = :questionA)";
        List<Question> question = entityManager.createQuery(jpql, Question.class)
                .setParameter("questionA", questionA)
                .setParameter("questionB", questionB)
                .getResultList();
        return question.stream().findAny();
    }

    @Override
    @Transactional
    public void deleteAll() {
        entityManager.createQuery("DELETE FROM Question").executeUpdate();
    }
}
