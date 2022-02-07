package kodlamaio.hrms.entities.concretes;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name="working_times")

public class WorkingTime {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private int id;

    @NotNull
    @Column(name="name", unique = true)
    private String name;

    @JsonIgnore
    @OneToMany(mappedBy = "workingTimes",
            fetch = FetchType.LAZY,
            cascade = CascadeType.ALL,
            orphanRemoval = true )
    private List<JobAdvert> jobAdvert;

}
