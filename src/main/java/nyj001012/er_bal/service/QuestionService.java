package nyj001012.er_bal.service;

import com.vane.badwordfiltering.BadWordFiltering;
import nyj001012.er_bal.domain.Question;
import nyj001012.er_bal.repository.QuestionRepository;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class QuestionService {
    private final QuestionRepository questionRepository;

    public QuestionService(QuestionRepository questionRepository) {
        this.questionRepository = questionRepository;
    }

    /**
     * 질문 등록
     * @param question 질문 객체
     * @return 질문 ID
     */
    public Long post(Question question) {
        validateQuestion(question);
        questionRepository.save(question);
        return question.getId();
    }

    /**
     * 질문 검증
     * 1. 질문 길이 검증
     * 2. 비속어 검증
     * 3. 질문 중복 검증
     * @param question 검증할 질문 객체
     */
    public void validateQuestion(Question question) {
        validateQuestionLength(question);
        validateQuestionProfanity(question);
        validateQuestionDuplicate(question);
    }

    /**
     * 질문 길이 검증 (100자 이하)
     * @param question 중복 검증할 질문 객체
     */
    public void validateQuestionLength(Question question) {
        if (question.getQuestionA().isEmpty() || question.getQuestionB().isEmpty()) {
            throw new IllegalArgumentException("질문은 비어있을 수 없습니다.");
        }
        if (question.getQuestionA().length() > 100 || question.getQuestionB().length() > 100) {
            throw new IllegalArgumentException("질문은 100자 이하이어야 합니다.");
        }
    }

    /**
     * 질문 비속어 포함 여부 검증
     * @param question 검증할 질문 객체
     */
    public void validateQuestionProfanity(Question question) {
        BadWordFiltering badWordFiltering = new BadWordFiltering();

        if (badWordFiltering.blankCheck(question.getQuestionA())
                || badWordFiltering.blankCheck(question.getQuestionB())) {
            throw new IllegalArgumentException("욕설은 사용할 수 없습니다.");
        }
    }

    /**
     * 질문 중복 검증
     * 1. 질문 A와 B가 같은 질문인지 검증
     * 2. 이미 등록된 질문인지 검증
     * @param question 중복 검증할 질문 객체
     */
    public void validateQuestionDuplicate(Question question) {
        String questionA = question.getQuestionA();
        String questionB = question.getQuestionB();

        // question A와 B가 같은 질문인지 검증
        if (Objects.equals(questionA, questionB)) {
            throw new IllegalArgumentException("같은 질문을 입력할 수 없습니다.");
        }
        // 이미 등록된 질문인지 검증
        if (questionRepository.findByQuestionAB(questionA, questionB).isPresent()
            || questionRepository.findByQuestionAB(questionB, questionA).isPresent()) {
            throw new IllegalArgumentException("이미 등록된 질문입니다.");
        }
    }
}
