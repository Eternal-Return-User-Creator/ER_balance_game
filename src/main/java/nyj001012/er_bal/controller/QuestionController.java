package nyj001012.er_bal.controller;

import nyj001012.er_bal.domain.Question;
import nyj001012.er_bal.dto.QuestionPostRequestDTO;
import nyj001012.er_bal.service.QuestionService;
import org.apache.coyote.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@RestController
@RequestMapping("/api")
public class QuestionController {

    private final QuestionService questionService;

    public QuestionController(QuestionService questionService) {
        this.questionService = questionService;
    }

    @PostMapping("question")
    public ResponseEntity<?> post(@RequestBody QuestionPostRequestDTO questionPostRequestDTO) {
        Date date = new Date();
        Question question = new Question();
        question.setQuestionA(questionPostRequestDTO.getQuestionA());
        question.setQuestionB(questionPostRequestDTO.getQuestionB());
        question.setCreatedDate(date);
        question.setUpdatedDate(date);
        question.setAChoiceCount(0L);
        question.setBChoiceCount(0L);
        try {
            question = questionService.post(question);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
        return ResponseEntity.ok(question);
    }
}
