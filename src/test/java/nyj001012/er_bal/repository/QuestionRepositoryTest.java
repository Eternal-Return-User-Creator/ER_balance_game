package nyj001012.er_bal.repository;

import jakarta.transaction.Transactional;
import nyj001012.er_bal.domain.Question;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.test.annotation.Rollback;

import java.util.Date;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class QuestionRepositoryTest {
    @Autowired
    QuestionRepository questionRepository;

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
}
