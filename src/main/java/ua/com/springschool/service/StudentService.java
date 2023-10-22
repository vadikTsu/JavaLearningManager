package ua.com.springschool.service;

import jakarta.transaction.Transactional;
import ua.com.springschool.model.CourseDTO;
import ua.com.springschool.model.GroupDTO;
import ua.com.springschool.model.StudentDTO;

public interface StudentService {

    Iterable<StudentDTO> listStudents();

    Iterable<GroupDTO> listGroups();

    StudentDTO getStudentById(Long id);

    StudentDTO saveNewStudent(StudentDTO studentDTO);

    @Transactional
    void assignStudentToCourse(Long studentId, Long courseId);

    Boolean deleteById(Long studentId);

    Iterable<CourseDTO> getCoursesByStudentsId(Long id);

    @Transactional
    void moveStuentToGroup(Long studentId, Long groupId);

    @Transactional
    void removeStudentFromCourse(Long studentId, Long courseId);
}
