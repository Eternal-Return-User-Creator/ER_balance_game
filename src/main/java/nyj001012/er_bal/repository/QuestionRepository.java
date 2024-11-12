package nyj001012.er_bal.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import nyj001012.er_bal.domain.Question;
import org.springframework.stereotype.Repository;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public class QuestionRepository implements IQuestionRepository {

    @PersistenceContext
    private final EntityManager entityManager;

    private final Set<Long> selectedQuestionIds = new HashSet<>();

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

    /**
     * 랜덤으로 하나의 질문을 조회한다.
     * @apiNote 중복 조회되지 않도록 selectedQuestionIds에 조회된 질문의 id를 저장한다.
     * @return 랜덤으로 조회된 질문
     */
    @Override
    public Optional<Question> findOneRandom() {
        int totalSize = entityManager.createQuery("SELECT COUNT(q) FROM Question q", Long.class)
                .getSingleResult()
                .intValue();
        while (selectedQuestionIds.size() < totalSize) {
            String jpql = "SELECT q FROM Question q ORDER BY FUNCTION('RAND')";
            Question question = entityManager.createQuery(jpql, Question.class)
                    .setMaxResults(1)
                    .getSingleResult();
            if (!selectedQuestionIds.contains(question.getId())) {
                selectedQuestionIds.add(question.getId());
                return Optional.of(question);
            }
        }
        return Optional.empty();
    }

    @Override
    @Transactional
    public void deleteAll() {
        entityManager.createQuery("DELETE FROM Question").executeUpdate();
    }
}
