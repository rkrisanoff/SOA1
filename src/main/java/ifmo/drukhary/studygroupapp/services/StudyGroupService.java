package ifmo.drukhary.studygroupapp.services;

import ifmo.drukhary.studygroupapp.DTO.StudyGroupBase;
import ifmo.drukhary.studygroupapp.DTO.StudyGroupResponse;
import ifmo.drukhary.studygroupapp.entities.StudyGroupEntity;
import ifmo.drukhary.studygroupapp.exceptions.StudyGroupDoesNotExistException;
import ifmo.drukhary.studygroupapp.exceptions.WrongFilterException;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;

import java.util.List;
import java.util.Optional;
public interface StudyGroupService {

    Optional<StudyGroupEntity> getById(int id) throws NotFoundException;

    StudyGroupEntity updateById(int id, StudyGroupBase studyGroup) throws NotFoundException,StudyGroupDoesNotExistException;

    void deleteById(int id) throws NotFoundException;

    Optional<StudyGroupEntity> create(StudyGroupBase studyGroup);

    List<StudyGroupEntity> getAll(List<String> filters, List<String> sorts, int size, int page) throws WrongFilterException;

    long getAllStudentCount();

    List<StudyGroupEntity> getAllWithNameStartWith(String name);


    int deleteAllByStudentCount(long studentCount);
}
