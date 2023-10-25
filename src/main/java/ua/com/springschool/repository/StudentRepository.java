package ua.com.springschool.repository;

import org.springframework.data.jpa.repository.Query;
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

    public Collection<Student> findAllStudentsAssignedToCourse(String courseName){
        return entityManager.createQuery("SELECT s FROM Student s JOIN s.courses c WHERE c.name = :courseName")
            .setParameter("courseName", courseName)
            .getResultList();
    }
}

