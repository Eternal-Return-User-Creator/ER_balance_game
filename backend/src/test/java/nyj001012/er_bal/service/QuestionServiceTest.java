package nyj001012.er_bal.service;

import nyj001012.er_bal.domain.Question;
import nyj001012.er_bal.repository.QuestionRepository;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.*;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

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
        this.question.setQuestionText("질문");
        this.question.setChoiceA("선택지 A");
        this.question.setChoiceB("선택지 B");
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

        /**
         * 100자를 넘는 질문을 제공하는 테스트 메소드
         *
         * @return 100자를 넘는 질문
         */
        private static Stream<Arguments> provideLongQuestions() {
            return Stream.of(
                    Arguments.of("q".repeat(101), "선택지 A", "선택지 B"), // questionText가 101자인 경우
                    Arguments.of("질문", "a".repeat(101), "선택지 B"), // choiceA가 101자인 경우
                    Arguments.of("질문", "선택지 A", "b".repeat(101)), // choiceB가 101자인 경우
                    Arguments.of("q".repeat(101), "a".repeat(101), "b".repeat(101))
            );
        }

        @ParameterizedTest
        @MethodSource("provideLongQuestions")
        public void 질문_길이가_100자를_넘는_경우(String questionText, String choiceA, String choiceB) {
            question.setQuestionText(questionText);
            question.setChoiceA(choiceA);
            question.setChoiceB(choiceB);
            IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> questionService.validateQuestionLength(question));
            assertThat(e.getMessage()).isEqualTo("질문은 100자 이하이어야 합니다.");
        }

        /**
         * 비어있는 질문을 제공하는 테스트 메소드
         * @return 비어있는 질문
         */
        private static Stream<Arguments> provideEmptyQuestions() {
            return Stream.of(
                    Arguments.of("      ", "선택지 A", "선택지 B"),
                    Arguments.of("\r\n", "선택지 A", "선택지 B"),
                    Arguments.of("질문", "", "선택지 B"),
                    Arguments.of("질문", "\n", "선택지 B"),
                    Arguments.of("질문", "선택지 A", "\t")
            );
        }

        @ParameterizedTest
        @MethodSource("provideEmptyQuestions")
        public void 질문이_비어있을_경우(String questionText, String choiceA, String choiceB) {
            question.setQuestionText(questionText);
            question.setChoiceA(choiceA);
            question.setChoiceB(choiceB);
            IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> questionService.validateQuestionLength(question));
            assertThat(e.getMessage()).isEqualTo("질문은 비어있을 수 없습니다.");
        }
    }

    @Nested
    class 질문_비속어_포함_테스트 {

        @Test
        public void 질문_비속어_포함_통과() {
            questionService.validateQuestionBadWord(question);
        }

        /**
         * 비속어가 포함된 질문을 제공하는 테스트 메소드
         * @return 비속어가 포함된 질문
         */
        private static Stream<Arguments> provideBadWordQuestion() {
            return Stream.of(
                    Arguments.of("ㅆㅂ", "선택지 A", "선택지 B"),
                    Arguments.of("질문", "씨발", "선택지 B"),
                    Arguments.of("질문", "선택지 A", "씨발"),
                    Arguments.of("시발", "씨발", "씨발")
            );
        }

        @ParameterizedTest
        @MethodSource("provideBadWordQuestion")
        public void 질문에_비속어가_포함되어_있을_때(String questionText, String choiceA, String choiceB) {
            question.setQuestionText(questionText);
            question.setChoiceA(choiceA);
            question.setChoiceB(choiceB);
            IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> questionService.validateQuestionBadWord(question));
            assertThat(e.getMessage()).isEqualTo("욕설은 사용할 수 없습니다.");
        }
    }

    @Nested
    class 중복_질문_등록_테스트 {

        /**
         * 중복된 질문을 제공하는 테스트 메소드
         *
         * @return 중복된 질문
         */
        private static Stream<Arguments> provideDuplicateQuestions() {
            Question question1 = new Question();
            question1.setQuestionText("질문"); // setUp()의 question과 같은 질문
            question1.setChoiceA("선택지 A"); // setUp()의 question과 같은 선택지
            question1.setChoiceB("선택지 B"); // setUp()의 question과 같은 선택지

            Question question2 = new Question();
            question2.setQuestionText("질문"); // setUp()의 question과 같은 질문
            question2.setChoiceA("선택지 B"); // setUp()의 question과 순서만 다른 선택지
            question2.setChoiceB("선택지 A"); // setUp()의 question과 순서만 다른 선택지

            return Stream.of(
                    Arguments.of(question1, question1), // 같은 질문, 같은 선택지
                    Arguments.of(question2, question2) // 같은 질문, 순서만 다른 선택지
            );
        }

        @Test
        public void 중복_질문_등록_통과() {
            questionService.validateQuestionDuplicate(question);
        }

        @Test
        public void 질문_A와_B가_같은_경우() {
            question.setChoiceA("같은 선택지");
            question.setChoiceB("같은 선택지");
            IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> questionService.validateQuestionDuplicate(question));
            assertThat(e.getMessage()).isEqualTo("같은 선택지를 입력할 수 없습니다.");
        }

        @ParameterizedTest
        @MethodSource("provideDuplicateQuestions")
        public void 이미_등록된_질문인_경우(Question savedQuestion, Question duplicatedQuestion) {
            questionRepository.save(savedQuestion);
            IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> questionService.validateQuestionDuplicate(duplicatedQuestion));
            assertThat(e.getMessage()).isEqualTo("이미 등록된 질문입니다.");
        }
    }

    @Test
    public void 질문_등록_테스트() {
        questionService.post(question);
        Optional<Question> savedQuestion = questionRepository.findById(question.getId());
        savedQuestion.ifPresentOrElse(q -> {
            assertThat(q.getChoiceA()).isEqualTo(question.getChoiceA());
            assertThat(q.getChoiceB()).isEqualTo(question.getChoiceB());
        }, Assertions::fail);
    }

    @Test
    public void 질문을_무작위로_조회() {
        // 저장된 질문이 3개일 때
        for (int i = 0; i < 3; i++) {
            Question question = new Question();
            question.setQuestionText("질문" + i);
            question.setChoiceA("선택지 A" + i);
            question.setChoiceB("선택지 B" + (i + 1));
            questionRepository.save(question);
        }

        // 조회된 내용은 비어있으면 안 된다
        for (int i = 0; i < 3; i++) {
            Question question = questionService.getRandom();
            assertThat(question).isNotNull();
        }
    }

    @Nested
    class 질문_선택_시_카운트_증가_테스트 {
        @Test
        public void 질문_A_선택_카운트_증가() {
            questionService.post(question);
            questionService.updateChoiceCount(question.getId(), 'A');
            Optional<Question> savedQuestion = questionRepository.findById(question.getId());
            savedQuestion.ifPresentOrElse(q -> {
                assertThat(q.getChoiceACount()).isEqualTo(1L);
                assertThat(q.getChoiceBCount()).isEqualTo(0L);
            }, Assertions::fail);
        }

        @Test
        public void 질문_B_선택_카운트_증가() {
            questionService.post(question);
            questionService.updateChoiceCount(question.getId(), 'B');
            Optional<Question> savedQuestion = questionRepository.findById(question.getId());
            savedQuestion.ifPresentOrElse(q -> {
                assertThat(q.getChoiceACount()).isEqualTo(0L);
                assertThat(q.getChoiceBCount()).isEqualTo(1L);
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
                Map<String, Map<String, String>> result = questionService.getChoiceResult(q.getId());
                assertThat(result.get("A").get("count")).isEqualTo("4");
                assertThat(result.get("A").get("ratio")).isEqualTo("80.00");
                assertThat(result.get("B").get("count")).isEqualTo("1");
                assertThat(result.get("B").get("ratio")).isEqualTo("20.00");
            }, Assertions::fail);
        }

        @Test
        public void 존재하지_않는_질문인_경우() {
            assertThrows(IllegalArgumentException.class, () -> questionService.getChoiceResult(1L));
        }
    }
}
