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

    @Column(name="question_a")
    private String questionA;

    @Column(name="question_b")
    private String questionB;

    @Column(name="created_date")
    private Date createdDate;

    @Column(name="updated_date")
    private Date updatedDate;

    @Column(name="a_choice_count")
    private Long aChoiceCount;

    @Column(name="b_choice_count")
    private Long bChoiceCount;

}
