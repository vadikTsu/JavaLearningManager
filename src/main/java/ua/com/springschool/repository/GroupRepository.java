package ua.com.springschool.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ua.com.springschool.entity.Group;

import java.util.Collection;

@Repository
public class GroupRepository  extends  AbstractJpaRepository<Group> {

    public GroupRepository() {
        super();

        setEntity(Group.class);
    }

//    @Query("SELECT g FROM Group g " +
//        "LEFT JOIN g.students s " +
//        "GROUP BY g.id, g.name " +
//        "HAVING COUNT(s.id) <= :lessThanStudents")
//    Collection<Group> findAllGroupsWithLessStudents(int lessThanStudents);
}

