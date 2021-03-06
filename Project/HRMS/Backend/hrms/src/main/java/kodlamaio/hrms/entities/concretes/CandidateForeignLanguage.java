package kodlamaio.hrms.entities.concretes;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name="candidate_foreign_languages")
@EqualsAndHashCode(exclude="cv")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler","cv"})
public class CandidateForeignLanguage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private int id;

    @Min(1)
    @Max(5)
    @NotEmpty
    @Column(name="level")
    private int level;

    @ManyToOne()
    @JoinColumn(name="foreign_language_id")
    private ForeignLanguage foreignLanguage;

    @ManyToOne()
    @JoinColumn(name = "cv_id")
    private Cv cv;
}
