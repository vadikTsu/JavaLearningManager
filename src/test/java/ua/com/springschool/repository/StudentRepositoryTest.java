package ua.com.springschool.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class StudentRepositoryTest extends AbstractTestContainer {


    StudentRepository studentRepository;

    @Autowired
    public StudentRepositoryTest(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    @Test
    public void showStudents() {
        System.out.println(studentRepository.findById(1L));
    }
}
