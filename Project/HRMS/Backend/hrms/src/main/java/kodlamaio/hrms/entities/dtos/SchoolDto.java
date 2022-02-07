package kodlamaio.hrms.entities.dtos;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class SchoolDto {
    private int id;

    private String name;

    private String department;

    private LocalDate startedDate;

    private LocalDate graduatedDate;

    private boolean isGraduated;

    private int candidateId;
    private int cvId;

}
