package ua.com.springschool.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ua.com.springschool.entity.Group;

import java.util.Collection;
import java.util.List;

@Repository
public class GroupRepository  extends  AbstractJpaRepository<Group> {

    public GroupRepository() {
        super();
        setEntity(Group.class);
    }

public Collection<Group> findAllGroupsWithLessStudents(int number) {
    return entityManager.createQuery("SELECT g FROM Group g " +
            "LEFT JOIN g.students s " +
            "GROUP BY g.id, g.name " +
            "HAVING COUNT(s.id) <= :number")
        .setParameter("number", number)
        .getResultList();
    }
}

