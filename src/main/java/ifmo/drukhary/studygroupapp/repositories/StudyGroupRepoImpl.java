package ifmo.drukhary.studygroupapp.repositories;

import ifmo.drukhary.studygroupapp.DTO.Filter;
import ifmo.drukhary.studygroupapp.entities.StudyGroupEntity;
import ifmo.drukhary.studygroupapp.exceptions.StudyGroupDoesNotExistException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.*;
import jakarta.transaction.Transactional;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
public class StudyGroupRepoImpl implements StudyGroupRepo {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    @Transactional
    public List<StudyGroupEntity> getAll(List<Filter> filters, List<String> sorts, int offset, int limit) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<StudyGroupEntity> criteriaQuery
                = criteriaBuilder.createQuery(StudyGroupEntity.class);

        Root<StudyGroupEntity> root
                = criteriaQuery.from(StudyGroupEntity.class);
        Predicate[] predicates = filters.stream()
                .collect(Collectors.groupingBy(Filter::attributeName))
                .entrySet().stream()
                .map(filtersByAttribute -> criteriaBuilder.or(
                                filtersByAttribute.getValue().stream()
                                        .map(filter -> criteriaBuilder.equal(
                                                getExpressionByFieldName(root, filtersByAttribute.getKey()),
                                                filter.attributeValue()
                                        )).toArray(Predicate[]::new)
                        )
                ).toArray(Predicate[]::new);

        if (sorts == null || sorts.isEmpty()) {
            sorts = List.of("id");
        }

        criteriaQuery.select(root).orderBy(
                sorts.stream().toList().stream().map(sort -> {
                    if (sort.startsWith("-")) {
                        return criteriaBuilder.desc(getExpressionByFieldName(root, sort.substring(1)));
                    } else {
                        return criteriaBuilder.asc(getExpressionByFieldName(root, sort));
                    }
                }).toArray(Order[]::new)
        ).where(criteriaBuilder.and(predicates));

        return entityManager
                .createQuery(criteriaQuery)
                .setFirstResult(offset)
                .setMaxResults(limit)
                .getResultList();

    }

    private static <T> Expression<?> getExpressionByFieldName(Root<T> root, String attributeName) {
        if (attributeName.contains(".")) {
            String[] parts = attributeName.split("[.]");

            Path<?> childrenRoot = root;

            for (String attr : parts) {
                if (attr.isBlank())
                    throw new RuntimeException("Attribute cannot be blank string");
                else
                    childrenRoot = childrenRoot.get(attr);
            }

            return childrenRoot;
        }

        return root.get(attributeName);
    }

    @Override
    @Transactional
    public Optional<StudyGroupEntity> getById(int id) throws StudyGroupDoesNotExistException, ChangeSetPersister.NotFoundException {
        return Optional.ofNullable(entityManager.find(StudyGroupEntity.class, id));
    }

    @Override
    @Transactional
    public Optional<StudyGroupEntity> create(StudyGroupEntity studyGroup) {
        if (studyGroup != null) {
            if (studyGroup.getCreationDate() == null)
                studyGroup.setCreationDate(java.time.LocalDate.now());

            entityManager.persist(studyGroup);

            StudyGroupEntity result = entityManager.merge(studyGroup);
            return Optional.ofNullable(result);
        }
        return Optional.empty();
    }

    @Override
    @Transactional
    public int updateById(int id, StudyGroupEntity studyGroup) {
        if (studyGroup != null) {
            if (entityManager.find(StudyGroupEntity.class, studyGroup.getId()) != null) {
                if (studyGroup.getCreationDate() == null)
                    studyGroup.setCreationDate(LocalDate.now());
                entityManager.merge(studyGroup);
            }
        }
        return 1;
    }

    @Override
    @Transactional
    public void deleteById(int id) throws ChangeSetPersister.NotFoundException {
        Optional<StudyGroupEntity> maybeStudyGroup = getById(id);
        if (maybeStudyGroup.isEmpty()) {
            throw new StudyGroupDoesNotExistException(id);
        }
        entityManager.remove(maybeStudyGroup.get());

    }


    @Override
    @Transactional
    public Long getAllStudentCount() {

        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> criteriaQuery = cb.createQuery(Long.class);
        Root<StudyGroupEntity> root = criteriaQuery.from(StudyGroupEntity.class);

        Expression<Long> sumExpression = cb.sum(root.get("studentsCount"));
        criteriaQuery.select(sumExpression);

        return entityManager.createQuery(criteriaQuery).getSingleResult();
    }

    @Override
    @Transactional
    public List<StudyGroupEntity> getAllWithNameStartWith(String name) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<StudyGroupEntity> criteriaQuery
                = criteriaBuilder.createQuery(StudyGroupEntity.class);

        Root<StudyGroupEntity> root
                = criteriaQuery.from(StudyGroupEntity.class);

        criteriaQuery.select(root)
                .where(criteriaBuilder.like(root.get("name"), name + "%"))
                .orderBy(criteriaBuilder.asc(root.get("id")));


        return entityManager
                .createQuery(criteriaQuery)
                .getResultList();

    }

    @Override
    @Transactional
    public int deleteAllByStudentCount(long studentCount) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaDelete<StudyGroupEntity> query = criteriaBuilder.createCriteriaDelete(StudyGroupEntity.class);
        Root<StudyGroupEntity> root = query.from(StudyGroupEntity.class);

        query.where(criteriaBuilder.equal(root.get("studentsCount"), studentCount));

        return entityManager.createQuery(query).executeUpdate();
    }

}
