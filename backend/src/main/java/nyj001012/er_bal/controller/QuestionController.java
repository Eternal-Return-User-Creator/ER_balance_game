package nyj001012.er_bal.controller;

import nyj001012.er_bal.domain.Question;
import nyj001012.er_bal.dto.QuestionPostRequestDTO;
import nyj001012.er_bal.service.QuestionService;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class QuestionController {

    private final QuestionService questionService;

    public QuestionController(QuestionService questionService) {
        this.questionService = questionService;
    }

    @PostMapping("question")
    public ResponseEntity<?> post(@RequestBody QuestionPostRequestDTO questionPostRequestDTO, HttpEntity<Object> httpEntity) {
        Date date = new Date();
        Question question = new Question();
        question.setChoiceA(questionPostRequestDTO.getchoiceA());
        question.setChoiceB(questionPostRequestDTO.getchoiceB());
        question.setCreatedDate(date);
        question.setUpdatedDate(date);
        question.setChoiceACount(0L);
        question.setChoiceBCount(0L);
        Long id = questionService.post(question);
        return ResponseEntity.status(201).body(id);
    }

    @GetMapping("question")
    public ResponseEntity<?> get() {
        return questionService.getRandom().map(
                ResponseEntity::ok
        ).orElseGet(
                () -> ResponseEntity.noContent().build()
        );
    }

    @PatchMapping("question/{id}/choice-count")
    public ResponseEntity<String> patch(@PathVariable("id") Long id, @RequestParam("flag") char flag) {
        questionService.updateChoiceCount(id, flag);
        return ResponseEntity.ok().build();
    }

    @GetMapping("question/{id}/choice-result")
    public ResponseEntity<?> getChoiceResult(@PathVariable("id") Long id) {
        Map<String, String> result = questionService.getChoiceResult(id);
        return ResponseEntity.ok(result);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> handleIllegalArgumentException(IllegalArgumentException e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<String> handleInternalError(RuntimeException e) {
        return ResponseEntity.internalServerError().body(e.getMessage());
    }
}
