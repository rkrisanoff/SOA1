package ifmo.drukhary.studygroupapp.controllers;

import ifmo.drukhary.studygroupapp.DTO.*;
import ifmo.drukhary.studygroupapp.DTO.responses.DeleteAllByStudentCountResponse;
import ifmo.drukhary.studygroupapp.DTO.responses.GetAllStudentsCountResponse;
import ifmo.drukhary.studygroupapp.entities.StudyGroupEntity;
import ifmo.drukhary.studygroupapp.exceptions.StudyGroupDoesNotExistException;
import ifmo.drukhary.studygroupapp.exceptions.WrongFilterException;
import ifmo.drukhary.studygroupapp.services.StudyGroupService;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.springframework.http.HttpStatus.BAD_REQUEST;


@RestController
@RequestMapping("/study-groups")
@Validated
public class StudyGroupsController {

    @Autowired
    private StudyGroupService studyGroupService;

    @GetMapping("/ping")
    @ResponseBody
    public String ping() throws WrongFilterException {
        return "PONG";
    }


    @GetMapping("/{id}")
    public ResponseEntity<StudyGroupResponse> GetStudyGroup(@PathVariable("id") @Positive(message = "id must be positive") int id)
            throws NotFoundException {
        Optional<StudyGroupEntity> maybeStudyGroup = studyGroupService.getById(id);
        if (maybeStudyGroup.isEmpty()) {
            throw new StudyGroupDoesNotExistException(id);
        }
        return ResponseEntity.ok(fromEntity(maybeStudyGroup.get()));
    }

    @GetMapping
    public ResponseEntity<List<StudyGroupResponse>> getStudyGroups(
            @RequestParam(name = "filter", required = false) List<String> filters,
            @RequestParam(name = "sort", required = false) List<String> sorts,
            @RequestParam(name = "page", required = false, defaultValue = "1") @Validated @Positive(message = "page must be positive") int page,
            @RequestParam(name = "size", required = false, defaultValue = "25") @Validated @Positive(message = "size must be positive") int size
    ) throws WrongFilterException {
        if (filters == null) {
            filters = List.of();
        }
        if (sorts == null) {
            sorts = List.of();
        }

        List<StudyGroupResponse> studyGroups = studyGroupService.getAll(filters, sorts, page, size)
                .stream().map(StudyGroupsController::fromEntity).toList();
        return ResponseEntity.ok().body(studyGroups);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteStudyGroup(
            @PathVariable("id") @Validated @Positive(message = "id must be positive") @Max(2147483647) int id)
            throws NotFoundException {
        studyGroupService.deleteById(id);
        return ResponseEntity.ok().body("successful");
    }

    @PutMapping("/{id}")
    public ResponseEntity<StudyGroupResponse> updateStudyGroup(
            @PathVariable("id") @Validated @Positive(message = "id must be positive") int id,
            @RequestBody @NotNull(message = "Study group must not be null") @Validated StudyGroupBase studyGroup)

            throws NotFoundException {
        StudyGroupEntity updatedStudyGroup = studyGroupService.updateById(id, studyGroup);
        return ResponseEntity.ok().body(fromEntity(updatedStudyGroup));
    }

    @PostMapping
    public ResponseEntity<String> createStudyGroup(
            @RequestBody @NotNull(message = "Study group must not be null")
            @Validated StudyGroupBase studyGroup) {
        Optional<StudyGroupEntity> maybeCreatedStudyGroup = studyGroupService.create(studyGroup);
        if (maybeCreatedStudyGroup.isEmpty()) {
            return ResponseEntity.status(BAD_REQUEST).build();
        }
        return ResponseEntity.ok().body("successful");
    }

    @GetMapping("/all-students-count")
    public ResponseEntity<GetAllStudentsCountResponse> getAllStudentsCount() {
        long count = studyGroupService.getAllStudentCount();
        return ResponseEntity.ok().body(new GetAllStudentsCountResponse(count));
    }

    @GetMapping("/start-with")
    public ResponseEntity<List<StudyGroupResponse>> getAllWithNameStartWith(
            @NotNull(message = "name must not be null") @Validated @RequestParam("name") String name) {
        List<StudyGroupResponse> studyGroups = studyGroupService.getAllWithNameStartWith(name)
                .stream().map(StudyGroupsController::fromEntity).toList();
        return ResponseEntity.ok().body(studyGroups);
    }

    @DeleteMapping("/student-count/{studentCount}")
    public ResponseEntity<DeleteAllByStudentCountResponse> deleteAllByStudentCount(
            @NotNull(message = "studentCount must not be null")
            @PathVariable("studentCount") Long studentCount
    ) {
        int countDeleted = studyGroupService.deleteAllByStudentCount(studentCount);
        return ResponseEntity.ok().body(new DeleteAllByStudentCountResponse(countDeleted));
    }


    public static StudyGroupResponse fromEntity(StudyGroupEntity studyGroup) {
        StudyGroupResponse response = new StudyGroupResponse();

        Location location = new Location();
        Coordinates coordinates = new Coordinates();
        Person person = new Person();

        person.setLocation(location);
        response.setGroupAdmin(person);
        response.setCoordinates(coordinates);

        response.setId(studyGroup.getId());

        response.setName(studyGroup.getName());
        response.setSemesterEnum(studyGroup.getSemesterEnum());
        response.setStudentsCount(BigInteger.valueOf(studyGroup.getStudentsCount()));
        response.setFormOfEducation(studyGroup.getFormOfEducation());
        response.setShouldBeExpelled(BigInteger.valueOf(studyGroup.getShouldBeExpelled()));
        response.setCreationDate(LocalDate.now().atStartOfDay().toString());


        person.setName(studyGroup.getGroupAdmin().getName());
        person.setNationality(studyGroup.getGroupAdmin().getNationality());
        person.setPassportID(studyGroup.getGroupAdmin().getPassportID());

        location.setX(BigDecimal.valueOf(studyGroup.getGroupAdmin().getLocation().getX()));
        location.setY(BigInteger.valueOf(studyGroup.getGroupAdmin().getLocation().getY()));
        location.setZ(BigDecimal.valueOf(studyGroup.getGroupAdmin().getLocation().getZ()));

        coordinates.setX(BigDecimal.valueOf(studyGroup.getCoordinates().getX()));
        coordinates.setY(BigDecimal.valueOf(studyGroup.getCoordinates().getY()));

        return response;
    }
}
