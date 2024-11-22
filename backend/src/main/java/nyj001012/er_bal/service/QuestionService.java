package nyj001012.er_bal.service;

import com.vane.badwordfiltering.BadWordFiltering;
import nyj001012.er_bal.domain.Question;
import nyj001012.er_bal.repository.QuestionRepository;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class QuestionService {
    private final QuestionRepository questionRepository;
    private final Set<Long> selectedQuestionIds = new HashSet<>();

    public QuestionService(QuestionRepository questionRepository) {
        this.questionRepository = questionRepository;
    }

    /**
     * 질문 등록
     *
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
     *
     * @param question 검증할 질문 객체
     */
    public void validateQuestion(Question question) {
        validateQuestionLength(question);
        validateQuestionProfanity(question);
        validateQuestionDuplicate(question);
    }

    /**
     * 질문 길이 검증 (100자 이하)
     *
     * @param question 중복 검증할 질문 객체
     */
    public void validateQuestionLength(Question question) {
        if (question.getQuestionText().isEmpty()
                || question.getChoiceA().isEmpty()
                || question.getChoiceB().isEmpty()) {
            throw new IllegalArgumentException("질문은 비어있을 수 없습니다.");
        }
        if (question.getQuestionText().length() > 100
                || question.getChoiceA().length() > 100
                || question.getChoiceB().length() > 100) {
            throw new IllegalArgumentException("질문은 100자 이하이어야 합니다.");
        }
    }

    /**
     * 질문 비속어 포함 여부 검증
     *
     * @param question 검증할 질문 객체
     */
    public void validateQuestionProfanity(Question question) {
        BadWordFiltering badWordFiltering = new BadWordFiltering();

        if (badWordFiltering.blankCheck(question.getQuestionText())
                || badWordFiltering.blankCheck(question.getChoiceA())
                || badWordFiltering.blankCheck(question.getChoiceB())) {
            throw new IllegalArgumentException("욕설은 사용할 수 없습니다.");
        }
    }

    /**
     * 질문 중복 검증
     * 1. 질문 A와 B가 같은 선택지인지 검증
     * 2. 이미 등록된 질문인지 검증
     *
     * @param question 중복 검증할 질문 객체
     */
    public void validateQuestionDuplicate(Question question) {
        String questionText = question.getQuestionText();
        String choiceA = question.getChoiceA();
        String choiceB = question.getChoiceB();

        // question A와 B가 같은 질문인지 검증
        if (Objects.equals(choiceA, choiceB)) {
            throw new IllegalArgumentException("같은 선택지를 입력할 수 없습니다.");
        }
        // 이미 등록된 질문인지 검증
        if (questionRepository.findByQuestionTextAndChoiceAB(questionText, choiceA, choiceB).isPresent()) {
            throw new IllegalArgumentException("이미 등록된 질문입니다.");
        }
    }

    /**
     * 랜덤으로 하나의 질문 조회
     * 중복 조회되지 않도록 selectedQuestionIds에 조회된 질문의 id를 저장
     *
     * @return 랜덤으로 조회된 질문
     */
    public Optional<Question> getRandom() {
        int totalSize = questionRepository.count();
        while (selectedQuestionIds.size() < totalSize) {
            Question question = questionRepository.findOneRandom();
            if (!selectedQuestionIds.contains(question.getId())) {
                selectedQuestionIds.add(question.getId());
                return Optional.of(question);
            }
        }
        return Optional.empty();
    }

    /**
     * 선택한 질문의 카운트 증가 (A, B 중 하나)
     *
     * @param questionId 질문 ID
     * @param flag       선택한 질문의 A, B 여부
     */
    public void updateChoiceCount(Long questionId, char flag) {
        if (flag != 'A' && flag != 'B') {
            throw new IllegalArgumentException("flag는 A 또는 B여야 합니다.");
        }
        if (questionRepository.findById(questionId).isEmpty()) {
            throw new IllegalArgumentException("존재하지 않는 질문입니다.");
        }
        questionRepository.updateChoiceCount(questionId, flag);
    }

    /**
     * 질문의 선택 결과 조회
     *
     * @param questionId 질문 ID
     * @return 질문의 선택 결과 (A, B 선택 비율)
     */
    public Map<String, String> getChoiceResult(Long questionId) {
        if (questionRepository.findById(questionId).isEmpty()) {
            throw new IllegalArgumentException("존재하지 않는 질문입니다.");
        }
        int choiceACount = questionRepository.countChoiceOfQuestion(questionId, 'A');
        int choiceBCount = questionRepository.countChoiceOfQuestion(questionId, 'B');
        int totalCount = choiceACount + choiceBCount;

        String choiceAPercent = String.format("%.2f", (double) choiceACount / totalCount * 100);
        String choiceBPercent = String.format("%.2f", (double) choiceBCount / totalCount * 100);

        Map<String, String> result = new HashMap<>();
        result.put("A", choiceAPercent);
        result.put("B", choiceBPercent);
        return result;
    }
}
