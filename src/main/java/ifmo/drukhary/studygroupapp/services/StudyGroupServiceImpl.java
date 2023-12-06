package ifmo.drukhary.studygroupapp.services;

import ifmo.drukhary.studygroupapp.DTO.Filter;
import ifmo.drukhary.studygroupapp.DTO.StudyGroupBase;
import ifmo.drukhary.studygroupapp.entities.CoordinatesEntity;
import ifmo.drukhary.studygroupapp.entities.LocationEntity;
import ifmo.drukhary.studygroupapp.entities.PersonEntity;
import ifmo.drukhary.studygroupapp.entities.StudyGroupEntity;
import ifmo.drukhary.studygroupapp.exceptions.StudyGroupDoesNotExistException;
import ifmo.drukhary.studygroupapp.exceptions.WrongFilterException;
import ifmo.drukhary.studygroupapp.repositories.StudyGroupRepo;
import jakarta.annotation.PostConstruct;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.stereotype.Service;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static ifmo.drukhary.studygroupapp.utils.Constants.STUDY_GROUP_VALIDATION_EXPRESSION;

@Service
public class StudyGroupServiceImpl implements StudyGroupService,Serializable {

    @Serial
    private static final long serialVersionUID = 1L;


    @Autowired
    private StudyGroupRepo studyGroupRepo;

    @PostConstruct
    public void init() {
    }


    @PersistenceContext
    private EntityManager entityManager;

    @Override
    @Transactional
    public Optional<StudyGroupEntity> getById(int id) throws NotFoundException {
        return studyGroupRepo.getById(id);
    }

    @Override
    @Transactional
    public StudyGroupEntity updateById(int id, StudyGroupBase studyGroup) throws NotFoundException, StudyGroupDoesNotExistException {
        Optional<StudyGroupEntity> maybeStudyGroup = studyGroupRepo.getById(id);
        if (maybeStudyGroup.isPresent()) {
            StudyGroupEntity studyGroupEntity = this.dataToEntity(studyGroup);
            studyGroupEntity.setId(id);
            studyGroupEntity.setCreationDate(maybeStudyGroup.get().getCreationDate());
            entityManager.merge(studyGroupEntity);
            return studyGroupEntity;
        }
        throw new NotFoundException();
    }

    @Override
    @Transactional
    public void deleteById(int id) throws NotFoundException {
        studyGroupRepo.deleteById(id);
    }

    @Override
    @Transactional
    public Optional<StudyGroupEntity> create(StudyGroupBase studyGroup) {

        StudyGroupEntity studyGroupEntity = this.dataToEntity(studyGroup);


        return studyGroupRepo.create(studyGroupEntity);
    }

    @Override
    @Transactional
    public List<StudyGroupEntity> getAll(List<String> rawFilters, List<String> sorts, int page, int size) throws WrongFilterException {

        for (String rawFilter : rawFilters) {
            if (!rawFilter.matches(STUDY_GROUP_VALIDATION_EXPRESSION)) {
                throw new WrongFilterException(rawFilter);
            }
        }

        List<Filter> filters = rawFilters.stream().map(filter -> {
            String[] parts = filter.split(":");
            return new Filter(parts[0], parts[1]);
        }).toList();

        return studyGroupRepo.getAll(filters, sorts, (page - 1) * size, size);
    }


    @Override
    @Transactional
    public long getAllStudentCount() {
        Long allStudentCount = studyGroupRepo.getAllStudentCount();
        if (allStudentCount == null) {
            return 0L;
        }

        return allStudentCount;
    }

    @Override
    @Transactional
    public List<StudyGroupEntity> getAllWithNameStartWith(String name) {
        return studyGroupRepo.getAllWithNameStartWith(name);
    }

    @Override
    @Transactional
    public int deleteAllByStudentCount(long studentCount) {
        return studyGroupRepo.deleteAllByStudentCount(studentCount);
    }


    private StudyGroupEntity dataToEntity(StudyGroupBase studyGroup) {
        LocationEntity locationEntity = new LocationEntity();
        CoordinatesEntity coordinatesEntity = new CoordinatesEntity();
        PersonEntity personEntity = new PersonEntity();
        StudyGroupEntity studyGroupEntity = new StudyGroupEntity();

        personEntity.setLocation(locationEntity);
        studyGroupEntity.setGroupAdmin(personEntity);
        studyGroupEntity.setCoordinates(coordinatesEntity);

        studyGroupEntity.setName(studyGroup.getName());
        studyGroupEntity.setSemesterEnum(studyGroup.getSemesterEnum());
        studyGroupEntity.setStudentsCount(studyGroup.getStudentsCount().longValue());
        studyGroupEntity.setFormOfEducation(studyGroup.getFormOfEducation());
        studyGroupEntity.setShouldBeExpelled(studyGroup.getShouldBeExpelled().intValue());
        studyGroupEntity.setCreationDate(LocalDate.now());


        personEntity.setName(studyGroup.getGroupAdmin().getName());
        personEntity.setNationality(studyGroup.getGroupAdmin().getNationality());
        personEntity.setPassportID(studyGroup.getGroupAdmin().getPassportID());

        locationEntity.setX(studyGroup.getGroupAdmin().getLocation().getX().doubleValue());
        locationEntity.setY(studyGroup.getGroupAdmin().getLocation().getY().longValue());
        locationEntity.setZ(studyGroup.getGroupAdmin().getLocation().getZ().doubleValue());

        coordinatesEntity.setX(studyGroup.getCoordinates().getX().floatValue());
        coordinatesEntity.setY(studyGroup.getCoordinates().getY().floatValue());

        return studyGroupEntity;
    }
}
