package nyj001012.er_bal.repository;

import nyj001012.er_bal.domain.Question;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class QuestionRepositoryTest {
    @Autowired
    QuestionRepository questionRepository;

    @AfterEach
    public void tearDown() {
        questionRepository.deleteAll();
    }

    @Test
    public void 질문_등록_테스트() {
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
    public void 질문_랜덤_조회_테스트() {
        for (int i = 0; i < 3; i++) {
            Question question = new Question();
            question.setQuestionA("질문A" + i);
            question.setQuestionB("질문B" + (i + 1));
            question.setAChoiceCount(0L);
            question.setBChoiceCount(0L);
            question.setCreatedDate(new Date());
            question.setUpdatedDate(new Date());
            questionRepository.save(question);
        }

        Question randomQuestion = questionRepository.findOneRandom();
        assertThat(randomQuestion).isNotNull();
    }

    @Test
    public void 질문_선택지_카운트_증가_성공() {
        Question question = new Question();
        question.setQuestionA("질문A");
        question.setQuestionB("질문B");
        question.setAChoiceCount(0L);
        question.setBChoiceCount(0L);
        question.setCreatedDate(new Date());
        question.setUpdatedDate(new Date());
        questionRepository.save(question);

        // A 선택지 카운트 증가
        questionRepository.updateChoiceCount(question.getId(), 'A');
        Question updatedQuestion = questionRepository.findById(question.getId()).orElse(null);
        assertThat(updatedQuestion).isNotNull();
        assertThat(updatedQuestion.getAChoiceCount()).isEqualTo(1L);

        // B 선택지 카운트 증가
        questionRepository.updateChoiceCount(question.getId(), 'B');
        updatedQuestion = questionRepository.findById(question.getId()).orElse(null);
        assertThat(updatedQuestion).isNotNull();
        assertThat(updatedQuestion.getBChoiceCount()).isEqualTo(1L);
    }

    @Test
    public void 질문_선택지_선택_수_조회() {
        Question question = new Question();
        question.setQuestionA("질문A");
        question.setQuestionB("질문B");
        question.setAChoiceCount(2L);
        question.setBChoiceCount(1L);
        question.setCreatedDate(new Date());
        question.setUpdatedDate(new Date());
        questionRepository.save(question);

        int aChoiceCount = questionRepository.countChoiceOfQuestion(question.getId(), 'A');
        int bChoiceCount = questionRepository.countChoiceOfQuestion(question.getId(), 'B');

        assertThat(aChoiceCount).isEqualTo(2L);
        assertThat(bChoiceCount).isEqualTo(1L);
    }
}
