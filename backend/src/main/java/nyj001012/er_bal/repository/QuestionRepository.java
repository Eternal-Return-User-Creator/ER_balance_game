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

    /**
     * 질문을 저장한다.
     *
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
     *
     * @param id 질문 id
     * @return id로 조회된 질문
     */
    @Override
    public Optional<Question> findById(Long id) {
        Question question = entityManager.find(Question.class, id);
        return Optional.ofNullable(question);
    }

    /**
     * 질문 내용과 선택지 A, B를 동시에 만족하는 질문을 조회한다.
     * 즉, 질문 내용이 questionText이고, 선택지 A가 choiceA이고 선택지 B가 choiceB 이거나,
     * 질문 내용이 questionText이고, 선택지 A가 choiceB이고 선택지 B가 choiceA인 질문을 조회한다.
     * @param questionText 질문 내용
     * @param choiceA 선택지 A
     * @param choiceB 선택지 B
     * @return 질문 내용과 선택지 A, B를 동시에 만족하는 질문
     * @apiNote 두 질문이 동시에 만족하는 질문이 없을 경우 Optional.empty()를 반환한다.
     */
    @Override
    public Optional<Question> findByQuestionTextAndChoiceAB(String questionText, String choiceA, String choiceB) {
        String jpql = "SELECT q FROM Question q WHERE q.questionText = :questionText AND ((q.choiceA = :choiceA AND q.choiceB = :choiceB) OR (q.choiceA = :choiceB AND q.choiceB = :choiceA))";
        List<Question> question = entityManager.createQuery(jpql, Question.class)
                .setParameter("questionText", questionText)
                .setParameter("choiceA", choiceA)
                .setParameter("choiceB", choiceB)
                .getResultList();
        return question.stream().findAny();
    }

    /**
     * 랜덤으로 하나의 질문을 조회한다.
     *
     * @return 랜덤으로 조회된 질문
     * @apiNote 중복 조회되지 않도록 selectedQuestionIds에 조회된 질문의 id를 저장한다.
     */
    @Override
    public Question findOneRandom() {
        String jpql = "SELECT q FROM Question q ORDER BY FUNCTION('RAND')";
        return entityManager.createQuery(jpql, Question.class)
                .setMaxResults(1)
                .getSingleResult();
    }

    /**
     * id에 해당하는 질문의 선택지 카운트를 조회한다.
     * @param id 질문 id
     * @param flag 선택지 (A 또는 B)
     * @return id에 해당하는 질문의 선택지 카운트
     */
    @Override
    public int countChoiceOfQuestion(Long id, char flag) {
        String jpql = "SELECT SUM(CASE WHEN :flag = 'A' THEN q.choiceACount ELSE q.choiceBCount END) FROM Question q WHERE q.id = :id";
        return entityManager.createQuery(jpql, Long.class)
                .setParameter("flag", String.valueOf(flag))
                .setParameter("id", id)
                .getSingleResult()
                .intValue();
    }

    /**
     * id에 해당하는 질문의 선택지 카운트를 증가시킨다.
     * flag가 'A'이면 A 선택지 카운트를 증가시키고, 'B'이면 B 선택지 카운트를 증가시킨다.
     * @param id 질문 id
     * @param flag 선택지
     */
    @Override
    @Transactional
    public void updateChoiceCount(Long id, char flag) {
        Question question = entityManager.find(Question.class, id);
        if (flag == 'A') {
            question.setChoiceACount(question.getChoiceACount() + 1);
        } else {
            question.setChoiceBCount(question.getChoiceBCount() + 1);
        }
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
