package ua.com.springschool.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ua.com.springschool.entity.Student;

import java.util.Collection;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {
    @Query("SELECT s FROM Student s JOIN s.courses c WHERE c.name = :courseName")
    Collection<Student> findAllStudentsAssignedToCourse(String courseName);
}

