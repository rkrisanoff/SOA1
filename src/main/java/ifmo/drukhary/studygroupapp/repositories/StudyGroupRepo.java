package ifmo.drukhary.studygroupapp.repositories;

import ifmo.drukhary.studygroupapp.DTO.Filter;
import ifmo.drukhary.studygroupapp.entities.CoordinatesEntity;
import ifmo.drukhary.studygroupapp.entities.StudyGroupEntity;
import ifmo.drukhary.studygroupapp.exceptions.StudyGroupDoesNotExistException;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;

import java.util.List;
import java.util.Optional;

public interface StudyGroupRepo {

    List<StudyGroupEntity> getAll(List<Filter> filters, List<String> sorts, int offset, int limit);
    Optional<StudyGroupEntity> getById(int id) throws StudyGroupDoesNotExistException, NotFoundException;
    Optional<StudyGroupEntity> create(StudyGroupEntity studyGroup);

    int updateById(int id, StudyGroupEntity studyGroup)throws StudyGroupDoesNotExistException,NotFoundException;

    void deleteById(int id) throws NotFoundException;

    // Extra functions

    Long getAllStudentCount();

    List<StudyGroupEntity> getAllWithNameStartWith(String name);


    int deleteAllByStudentCount(long studentCount);




}
