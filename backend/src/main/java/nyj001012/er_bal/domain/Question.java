package nyj001012.er_bal.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Setter
@Getter
@Entity
public class Question {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="question_text")
    private String questionText;

    @Column(name="choice_a")
    private String choiceA;

    @Column(name="choice_b")
    private String choiceB;

    @Column(name="created_date")
    private Date createdDate;

    @Column(name="updated_date")
    private Date updatedDate;

    @Column(name="choice_a_count")
    private Long choiceACount;

    @Column(name="choice_b_count")
    private Long choiceBCount;

}
