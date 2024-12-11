package nyj001012.er_bal.domain;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Builder
@Setter
@Getter
@Entity
@NoArgsConstructor
@AllArgsConstructor
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
    @Builder.Default
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdDate = new Date();

    @Column(name="updated_date")
    @Builder.Default
    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedDate = new Date();

    @Column(name="choice_a_count")
    @Builder.Default
    private Long choiceACount = 0L;

    @Column(name="choice_b_count")
    private Long choiceBCount = 0L;
}
