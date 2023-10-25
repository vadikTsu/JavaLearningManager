package ua.com.springschool.repository;


import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import ua.com.springschool.entity.Course;
import ua.com.springschool.entity.Group;
import ua.com.springschool.entity.Student;


import java.util.Optional;

@Repository
public class CourseRepository extends AbstractJpaRepository<Course>{

    public CourseRepository () {
        super();
        setEntity(Course.class);
    }

}
