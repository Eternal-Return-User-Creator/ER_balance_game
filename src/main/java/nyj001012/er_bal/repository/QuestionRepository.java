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

    /**
     * 질문을 저장한다.
     * @param question 저장할 질문
     * @return 저장된 질문
     */
    @Override
    @Transactional
    public Question save(Question question) {
        entityManager.persist(question);
        return question;
    }

    /**
     * id로 질문을 조회한다.
     * @param id 질문 id
     * @return id로 조회된 질문
     */
    @Override
    public Optional<Question> findById(Long id) {
        Question question = entityManager.find(Question.class, id);
        return Optional.ofNullable(question);
    }

    /**
     * 질문을 포함하는 질문을 조회한다. 즉, 질문A 또는 질문B가 question인 질문을 조회한다.
     * @param question 질문
     * @return 질문을 포함하는 질문
     */
    @Override
    public List<Question> findByQuestion(String question) {
        String jpql = "SELECT q FROM Question q WHERE q.questionA = :question OR q.questionB = :question";
        return entityManager.createQuery(jpql, Question.class)
                .setParameter("question", question)
                .getResultList();
    }

    /**
     * 두 질문을 동시에 만족하는 질문을 조회한다.
     * 즉, 질문A가 questionA이고 질문B가 questionB 이거나, 질문A가 questionB이고 질문B가 questionA인 질문을 조회한다.
     * @apiNote 두 질문이 동시에 만족하는 질문이 없을 경우 Optional.empty()를 반환한다.
     * @param questionA 질문A
     * @param questionB 질문B
     * @return 두 질문을 동시에 만족하는 질문
     */
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

    /**
     * 모든 질문을 삭제한다.
     */
    @Override
    @Transactional
    public void deleteAll() {
        entityManager.createQuery("DELETE FROM Question").executeUpdate();
    }
}
