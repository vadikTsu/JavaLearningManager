package ua.com.springschool.service;

import jakarta.transaction.Transactional;
import ua.com.springschool.model.CourseDTO;
import ua.com.springschool.model.StudentDTO;

import java.util.Optional;
import java.util.UUID;

public interface StudentService {

    Optional<Iterable<StudentDTO>> listStudents();

    Optional<StudentDTO> getStudentById(Long id);

    StudentDTO saveNewStudent(StudentDTO studentDTO);

    @Transactional
    void assignStudentToCourse(Long studentId, Long courseId);

    Boolean deleteById(Long studentId);

    Optional<Iterable<CourseDTO>> getCoursesByStudentsId(Long id);


    @Transactional
    void moveStuentToGroup(Long studentId, Long groupId);

    @Transactional
    void removeStudentFromCourse(Long studentId, Long courseId);
}
