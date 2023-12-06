package ifmo.drukhary.studygroupapp.DTO;

import ifmo.drukhary.studygroupapp.entities.StudyGroupEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StudyGroupResponse extends StudyGroupBase {

    private String creationDate;
    private int id;
}
