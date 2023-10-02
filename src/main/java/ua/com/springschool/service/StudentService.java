package ua.com.springschool.service;

import ua.com.springschool.entity.Course;
import ua.com.springschool.model.StudentDTO;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface StudentService {

    List<StudentDTO> listStudents();

    Optional<StudentDTO> getStudentById(UUID id);

    StudentDTO saveNewStudent(StudentDTO studentDTO);

    Boolean deleteById(UUID beerId);

    Optional<Iterable<Course>> getCoursesByStudentsId(UUID id);
}
