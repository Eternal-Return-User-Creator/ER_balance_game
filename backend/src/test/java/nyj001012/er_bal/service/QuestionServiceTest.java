package nyj001012.er_bal.service;

import nyj001012.er_bal.domain.Question;
import nyj001012.er_bal.repository.QuestionRepository;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;

@SpringBootTest
public class QuestionServiceTest {
    @Autowired
    QuestionService questionService;
    @Autowired
    QuestionRepository questionRepository;
    private Question question = new Question();

    @BeforeEach
    public void setUp() {
        this.question = new Question();
        this.question.setQuestionA("질문A");
        this.question.setQuestionB("질문B");
        this.question.setAChoiceCount(0L);
        this.question.setBChoiceCount(0L);
        this.question.setCreatedDate(new Date());
        this.question.setUpdatedDate(new Date());
    }

    @AfterEach
    public void tearDown() {
        questionRepository.deleteAll();
    }

    @Nested
    class 질문_길이_검증_테스트 {

        @Test
        public void 질문_길이_검증_통과() {
            questionService.validateQuestionLength(question);
        }

        @Test
        public void 질문_길이가_100자를_넘는_경우() {
            // questionA가 101자인 경우
            question.setQuestionA("a".repeat(101));
            IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> questionService.validateQuestionLength(question));
            assertThat(e.getMessage()).isEqualTo("질문은 100자 이하이어야 합니다.");

            // questionA, questionB가 101자인 경우
            question.setQuestionA("질문A");
            question.setQuestionB("b".repeat(101));
            e = assertThrows(IllegalArgumentException.class, () -> questionService.validateQuestionLength(question));
            assertThat(e.getMessage()).isEqualTo("질문은 100자 이하이어야 합니다.");

            // questionB가 101자인 경우
            question.setQuestionA("a");
            e = assertThrows(IllegalArgumentException.class, () -> questionService.validateQuestionLength(question));
            assertThat(e.getMessage()).isEqualTo("질문은 100자 이하이어야 합니다.");
        }

        @Test
        public void 질문이_비어있을_경우() {
            // questionA가 비어있는 경우
            question.setQuestionA("");
            IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> questionService.validateQuestionLength(question));
            assertThat(e.getMessage()).isEqualTo("질문은 비어있을 수 없습니다.");

            // questionA, questionB가 비어있는 경우
            question.setQuestionB("");
            e = assertThrows(IllegalArgumentException.class, () -> questionService.validateQuestionLength(question));
            assertThat(e.getMessage()).isEqualTo("질문은 비어있을 수 없습니다.");

            // questionB가 비어있는 경우
            question.setQuestionA("질문A");
            e = assertThrows(IllegalArgumentException.class, () -> questionService.validateQuestionLength(question));
            assertThat(e.getMessage()).isEqualTo("질문은 비어있을 수 없습니다.");
        }
    }

    @Nested
    class 질문_비속어_포함_테스트 {

        @Test
        public void 질문_비속어_포함_통과() {
            questionService.validateQuestionProfanity(question);
        }

        @Test
        public void 질문에_비속어가_포함되어_있을_때() {
            // questionA에 비속어가 포함된 경우
            question.setQuestionA("ㅆㅂ이라고 욕한다.");
            IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> questionService.validateQuestionProfanity(question));
            assertThat(e.getMessage()).isEqualTo("욕설은 사용할 수 없습니다.");

            // questionA, questionB에 비속어가 포함된 경우
            question.setQuestionB("채팅으로 존나 못하네라고 한다.");
            e = assertThrows(IllegalArgumentException.class, () -> questionService.validateQuestionProfanity(question));
            assertThat(e.getMessage()).isEqualTo("욕설은 사용할 수 없습니다.");

            // questionB에 비속어가 포함된 경우
            question.setQuestionA("예쁜말..!");
            e = assertThrows(IllegalArgumentException.class, () -> questionService.validateQuestionProfanity(question));
            assertThat(e.getMessage()).isEqualTo("욕설은 사용할 수 없습니다.");
        }
    }

    @Nested
    class 중복_질문_등록_테스트 {
        @Test
        public void 중복_질문_등록_통과() {
            questionService.validateQuestionDuplicate(question);
        }

        @Test
        public void 질문_A와_B가_같은_경우() {
            question.setQuestionA("같은 질문");
            question.setQuestionB("같은 질문");
            IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> questionService.validateQuestionDuplicate(question));
            assertThat(e.getMessage()).isEqualTo("같은 질문을 입력할 수 없습니다.");
        }

        @Test
        public void 이미_등록된_질문인_경우() {
            question.setQuestionA("질문A");
            question.setQuestionB("질문B");
            questionRepository.save(question);

            IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> questionService.validateQuestionDuplicate(question));
            assertThat(e.getMessage()).isEqualTo("이미 등록된 질문입니다.");

            // 질문 내용은 같은데, A와 B가 반대인 경우
            question.setQuestionA("질문B");
            question.setQuestionB("질문A");

            e = assertThrows(IllegalArgumentException.class, () -> questionService.validateQuestionDuplicate(question));
            assertThat(e.getMessage()).isEqualTo("이미 등록된 질문입니다.");
        }
    }

    @Test
    public void 질문_등록_테스트() {
        questionService.post(question);
        Optional<Question> savedQuestion = questionRepository.findById(question.getId());
        savedQuestion.ifPresentOrElse(q -> {
            assertThat(q.getQuestionA()).isEqualTo(question.getQuestionA());
            assertThat(q.getQuestionB()).isEqualTo(question.getQuestionB());
        }, Assertions::fail);
    }

    @Test
    public void 질문을_무작위로_조회() {
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
            Optional<Question> question = questionService.getRandom();
            assertThat(question).isPresent();
            assertThat(selectedQuestionIds).doesNotContain(question.get().getId());
        }
        // 더 이상 조회되는 질문이 없어야 한다.
        assertThat(questionService.getRandom()).isEmpty();
    }

    @Nested
    class 질문_선택_시_카운트_증가_테스트 {
        @Test
        public void 질문_A_선택_카운트_증가() {
            questionService.post(question);
            questionService.updateChoiceCount(question.getId(), 'A');
            Optional<Question> savedQuestion = questionRepository.findById(question.getId());
            savedQuestion.ifPresentOrElse(q -> {
                assertThat(q.getAChoiceCount()).isEqualTo(1L);
                assertThat(q.getBChoiceCount()).isEqualTo(0L);
            }, Assertions::fail);
        }

        @Test
        public void 질문_B_선택_카운트_증가() {
            questionService.post(question);
            questionService.updateChoiceCount(question.getId(), 'B');
            Optional<Question> savedQuestion = questionRepository.findById(question.getId());
            savedQuestion.ifPresentOrElse(q -> {
                assertThat(q.getAChoiceCount()).isEqualTo(0L);
                assertThat(q.getBChoiceCount()).isEqualTo(1L);
            }, Assertions::fail);
        }

        @Test
        public void 선택지가_올바르지_않은_경우() {
            questionService.post(question);
            assertThrows(IllegalArgumentException.class, () -> questionService.updateChoiceCount(question.getId(), 'C'));
        }

        @Test
        public void 존재하지_않는_질문인_경우() {
            assertThrows(IllegalArgumentException.class, () -> questionService.updateChoiceCount(1L, 'A'));
        }
    }

    @Nested
    class 질문_선택_결과_조회_테스트 {
        @Test
        public void 질문_선택_결과_조회_소수점_없음() {
            questionService.post(question);
            for (int i = 0; i < 4; i++) {
                questionService.updateChoiceCount(question.getId(), 'A');
            }
            questionService.updateChoiceCount(question.getId(), 'B');

            Optional<Question> savedQuestion = questionRepository.findById(question.getId());
            savedQuestion.ifPresentOrElse(q -> {
                questionService.getChoiceResult(q.getId()).forEach((k, v) -> {
                    if (k.equals("A")) {
                        assertThat(v).isEqualTo("80.00");
                    } else if (k.equals("B")) {
                        assertThat(v).isEqualTo("20.00");
                    } else {
                        fail();
                    }
                });
            }, Assertions::fail);
        }

        @Test
        public void 존재하지_않는_질문인_경우() {
            assertThrows(IllegalArgumentException.class, () -> questionService.getChoiceResult(1L));
        }
    }
}
