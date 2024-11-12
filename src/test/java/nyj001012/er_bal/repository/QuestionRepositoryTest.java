package nyj001012.er_bal.repository;

import jakarta.transaction.Transactional;
import nyj001012.er_bal.domain.Question;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.test.annotation.Rollback;

import java.util.Date;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class QuestionRepositoryTest {
    @Autowired
    QuestionRepository questionRepository;

    // TODO: 데이터베이스 초기화하는 Teardown 추가

    @Test
    void 질문_등록_테스트() {
        Date dateTime = new Date();
        Question question = new Question();
        question.setQuestionA("질문1");
        question.setQuestionB("질문2");
        question.setCreatedDate(dateTime);
        question.setUpdatedDate(dateTime);
        question.setAChoiceCount(0L);
        question.setBChoiceCount(0L);

        questionRepository.save(question);

        Question savedQuestion = questionRepository.findById(question.getId()).orElse(null);
        assertThat(savedQuestion).isNotNull();
        assertThat(savedQuestion.getQuestionA()).isEqualTo("질문1");
        assertThat(savedQuestion.getQuestionB()).isEqualTo("질문2");
    }

    @Test
    public void 질문_조회_테스트() {
        // 저장된 질문이 3개일 때
        for (int i = 0; i < 3; i++) {
            Date dateTime = new Date();
            Question question = new Question();
            question.setQuestionA("질문 A" + i);
            question.setQuestionB("질문 B" + (i + 1));
            question.setCreatedDate(dateTime);
            question.setUpdatedDate(dateTime);
            question.setAChoiceCount(0L);
            question.setBChoiceCount(0L);
            questionRepository.save(question);
        }

        // 3개의 랜덤한 질문이 조회되고
        Set<Long> selectedQuestionIds = new HashSet<>();
        for (int i = 0; i < 3; i++) {
            Optional<Question> question = questionRepository.findOneRandom();
            assertThat(question).isPresent();
            assertThat(selectedQuestionIds).doesNotContain(question.get().getId());
        }
        // 더 이상 조회되는 질문이 없어야 한다.
        assertThat(questionRepository.findOneRandom()).isEmpty();
    }
}
