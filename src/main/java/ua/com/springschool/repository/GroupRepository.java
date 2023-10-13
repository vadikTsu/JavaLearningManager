package ua.com.springschool.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ua.com.springschool.entity.Group;

import java.util.Collection;

public interface GroupRepository extends JpaRepository<Group, Long> {

    @Query("SELECT g FROM Group g " +
        "LEFT JOIN g.students s " +
        "GROUP BY g.id, g.name " +
        "HAVING COUNT(s.id) <= :lessThanStudents")
    Collection<Group> findAllGroupsWithLessStudents(int lessThanStudents);
}

