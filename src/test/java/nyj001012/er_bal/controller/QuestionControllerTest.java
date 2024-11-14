package nyj001012.er_bal.controller;

import jakarta.transaction.Transactional;
import nyj001012.er_bal.domain.Question;
import nyj001012.er_bal.repository.QuestionRepository;
import nyj001012.er_bal.service.QuestionService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.test.annotation.Commit;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import javax.sound.midi.SysexMessage;
import java.util.Date;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@WebMvcTest(QuestionController.class)
@ExtendWith(RestDocumentationExtension.class)
@AutoConfigureRestDocs
public class QuestionControllerTest {
    @MockBean
    QuestionRepository questionRepository;
    @MockBean
    QuestionService questionService;
    @Autowired
    MockMvc mockMvc;
    @Autowired
    private WebApplicationContext context;

    @BeforeEach
    public void setUp(RestDocumentationContextProvider restDocumentationContextProvider) {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.context)
                .apply(documentationConfiguration(restDocumentationContextProvider))
                .alwaysDo(document("{method-name}"))
                .build();
    }

    @Test
    public void 질문_등록_성공() throws Exception {
        String questionJson = "{\n" +
                "  \"questionA\": \"질문A 입니다.\",\n" +
                "  \"questionB\": \"질문B 입니다.\"\n" +
                "}";
        given(questionService.post(any(Question.class)))
                .willReturn(12L);

        mockMvc.perform(post("/api/question")
                        .contentType("application/json")
                        .content(questionJson))
                .andExpect(result -> {
                    assertThat(result.getResponse().getStatus()).isEqualTo(201);
                });
    }

    @Test
    public void 랜덤_질문_조회_성공() throws Exception {
        Question question = new Question();
        for (long i = 0; i < 3; i++) {
            question.setId((long) i);
            question.setQuestionA("질문A" + i);
            question.setQuestionB("질문B" + i);
            question.setCreatedDate(new Date());
            question.setUpdatedDate(new Date());
            question.setAChoiceCount(0L);
            question.setBChoiceCount(0L);
            questionRepository.save(question);
        }
        questionRepository.save(question);
        given(questionService.getRandom())
                .willReturn(Optional.of(question));

        mockMvc.perform(get("/api/question"))
                .andExpect(result -> {
                    assertThat(result.getResponse().getStatus()).isEqualTo(200);
                    assertThat(result.getResponse().getContentAsString()).isNotEmpty();
                });
    }

    @Nested
    class 질문_선택_테스트 {

        @Test
        public void 질문_선택_성공() throws Exception {
            doNothing().when(questionService).updateChoiceCount(1L, 'A');
            mockMvc.perform(patch("/api/question/1/choice-count?flag=A"))
                    .andExpect(result -> {
                        assertThat(result.getResponse().getStatus()).isEqualTo(200);
                    });
            verify(questionService, times(1)).updateChoiceCount(1L, 'A');
        }

        @Test
        public void 질문_선택_플래그가_잘못된_경우() throws Exception {
            doThrow(new IllegalArgumentException()).when(questionService).updateChoiceCount(1L, 'C');
            mockMvc.perform(patch("/api/question/1/choice-count?flag=C"))
                    .andExpect(result -> {
                        assertThat(result.getResponse().getStatus()).isEqualTo(400);
                    });
            verify(questionService, times(1)).updateChoiceCount(1L, 'C');
        }

        @Test
        public void 질문_선택_시_존재하지_않는_질문() throws Exception {
            doThrow(new IllegalArgumentException()).when(questionService).updateChoiceCount(1L, 'A');
            mockMvc.perform(patch("/api/question/1/choice-count?flag=A"))
                    .andExpect(result -> {
                        assertThat(result.getResponse().getStatus()).isEqualTo(400);
                    });
            verify(questionService, times(1)).updateChoiceCount(1L, 'A');
        }
    }
}
