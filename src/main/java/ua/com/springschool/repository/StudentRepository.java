package ua.com.springschool.repository;

import org.springframework.stereotype.Repository;
import ua.com.springschool.entity.Group;
import ua.com.springschool.entity.Student;

import java.util.Collection;
import java.util.Optional;

@Repository
public class StudentRepository extends AbstractJpaRepository<Student>{

    public StudentRepository() {
        super();
        setEntity(Student.class);
    }

}

