package kodlamaio.hrms.entities.concretes;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import kodlamaio.hrms.core.entities.Candidate;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.time.LocalDate;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name="schools")
//@JsonIgnoreProperties({"hibernateLazyInitializer", "handler","cv"})
public class School {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private int id;

    @NotEmpty
    @Column(name="name")
    private String name;

    @NotEmpty
    @Column(name="department")
    private String department;

    @NotEmpty
    @DateTimeFormat(pattern = "yyyy-mm-dd")
    @Column(name="startedDate")
    private LocalDate startedDate;

    @DateTimeFormat(pattern = "yyyy-mm-dd")
    @Column(name="graduatedDate")
    private LocalDate graduatedDate;

    @ManyToOne()
    @JoinColumn(name="cv_id")
    private Cv cv;

    @JoinColumn(name="is_graduated")
    private boolean isGraduated;
}
