package nyj001012.er_bal.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class QuestionPostRequestDTO {
    private String questionText;
    private String choiceA;
    private String choiceB;
}
