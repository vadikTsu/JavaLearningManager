package ua.com.springschool.service;

import ua.com.springschool.model.CourseDTO;
import ua.com.springschool.model.GroupDTO;
import ua.com.springschool.model.StudentDTO;

public interface StudentService {

    Iterable<StudentDTO> listStudents();

    Iterable<GroupDTO> listGroups();

    StudentDTO getStudentById(Long id);

    StudentDTO saveNewStudent(StudentDTO studentDTO);

    void assignStudentToCourse(Long studentId, Long courseId);

    Boolean deleteStudentById(Long studentId);

    Iterable<CourseDTO> getCoursesByStudentsId(Long id);

    void moveStudentToGroup(Long studentId, Long groupId);

    void removeStudentFromCourse(Long studentId, Long courseId);
}
