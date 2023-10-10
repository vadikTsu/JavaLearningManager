package ua.com.springschool.service;

import jakarta.persistence.EntityNotFoundException;
import net.bytebuddy.dynamic.DynamicType;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.when;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ua.com.springschool.entity.Course;
import ua.com.springschool.entity.Group;
import ua.com.springschool.entity.Student;
import ua.com.springschool.mapper.CourseMapper;
import ua.com.springschool.mapper.StudentMapper;
import ua.com.springschool.model.CourseDTO;
import ua.com.springschool.model.StudentDTO;
import ua.com.springschool.repository.CourseRepository;
import ua.com.springschool.repository.GroupRepository;
import ua.com.springschool.repository.StudentRepository;

import java.util.*;


@ExtendWith(MockitoExtension.class)
class StudentServiceJpaTest {

    @InjectMocks
    private StudentServiceJpa studentServiceJpa;

    @Mock
    private StudentRepository studentRepository;

    @Mock
    private CourseRepository courseRepository;

    @Mock
    private GroupRepository groupRepository;

    @Mock
    private CourseMapper courseMapper;

    @Mock
    private StudentMapper studentMapper;


    @Test
    void listStudents_shouldReturnOptionalIterableOfStudentsDto_whenNotEmpltyRepository() {
        Student s = Student.builder().group(null).id(1L).name("alex").build();
        StudentDTO dto1 = StudentDTO.builder().groupId(null).id(1L).name("alex").build();

        when(studentRepository.findAll()).thenReturn(List.of(s));
        when(studentMapper.studentToStudentDto(s)).thenReturn(dto1);

        assertTrue(studentServiceJpa.listStudents() instanceof Optional<Iterable<StudentDTO>>);
        assertEquals(studentServiceJpa.listStudents().get(), List.of(dto1));
    }

    @Test
    void getStudentById_shouldReturnOptioanlOfStuudentDTO_whenExistingStusdentId() {
        Student s = Student.builder().group(null).id(1L).name("alex").build();
        StudentDTO dto = StudentDTO.builder().groupId(null).id(1L).name("alex").build();

        when(studentRepository.findById(1L)).thenReturn(Optional.ofNullable(s));
        when(studentMapper.studentToStudentDto(s)).thenReturn(dto);

        assertTrue(studentServiceJpa.getStudentById(1L) instanceof Optional<StudentDTO>);
        assertEquals(studentServiceJpa.getStudentById(1L).get(), dto);
    }

    @Test
    void getStudentById_shouldReturnEmptyOptioanl_whenNonExistingStudentId() {
        when(studentRepository.findById(anyLong())).thenReturn(Optional.empty());

        Optional<StudentDTO> result = studentServiceJpa.getStudentById(2L);

        assertEquals(Optional.empty(), result);
    }

//    @Test
//    void saveNewStudent_shouldReturnSavedStudentDTO_whenValidStudentDTO() {
//        var studentDTO =  StudentDTO.builder().build();
//        var student = Student.builder().build();
//        when(studentMapper.studentDtoToStudent(studentDTO)).thenReturn(student);
//        when(studentRepository.save(student)).thenReturn(student);
//        when(studentMapper.studentToStudentDto(student)).thenReturn(studentDTO);
//
//        StudentDTO studentSaved = studentServiceJpa.saveNewStudent(studentDTO);
//
//        assertEquals(studentSaved, studentDTO);
//    }

    @Test
    void assignStudentToCourse_shouldAssignStuedntToCourse_whenStudentAndCourseExists() {
        var student = spy(Student.builder().courses(new HashSet<>()).build());
        when(studentRepository.findById(anyLong())).thenReturn(Optional.of(student));
        var course = spy(Course.builder().students(new HashSet<>()).build());
        when(courseRepository.findById(anyLong())).thenReturn(Optional.of(course));

        studentServiceJpa.assignStudentToCourse(1L, 2L);

        verify(course,times(1)).getStudents();
        verify(student, times(1)).getCourses();
    }

    @Test
    void deleteById_shouldReturnTrueAndDeleteStudent_whenStudentExists() {
        Long studentId = 1L;

        when(studentRepository.existsById(studentId)).thenReturn(true);

        boolean result = studentServiceJpa.deleteById(studentId);

        assertTrue(result);
        verify(studentRepository, times(1)).deleteById(studentId);
    }

    @Test
    void deleteById_shouldReturnFalse_whenStudentDoesNotExist() {
        Long studentId = 1L;

        when(studentRepository.existsById(1L)).thenReturn(false);

        boolean result = studentServiceJpa.deleteById(studentId);

        assertFalse(result);
        verify(studentRepository, never()).deleteById(studentId);
    }

    @Test
    void getCoursesByStudentsId_shouldReturnCourses_whenStudentExists() {
        Course course1 = Course.builder().build();
        Course course2 = Course.builder().build();
        HashSet<Course> courses = new HashSet<>();
        courses.add(course1);
        courses.add(course2);

        CourseDTO courseDto1 = CourseDTO.builder().build();
        CourseDTO courseDto2 = CourseDTO.builder().build();
        HashSet<CourseDTO> courseDtos = new HashSet<>();
        courseDtos.add(courseDto1);
        courseDtos.add(courseDto2);

        Student student = new Student();
        student.setCourses(courses);

        when(studentRepository.findById(1L)).thenReturn(Optional.of(student));
        when(courseMapper.courseToCourseDto(course1)).thenReturn(courseDto1);
        when(courseMapper.courseToCourseDto(course2)).thenReturn(courseDto2);

        Optional<Iterable<CourseDTO>> result = studentServiceJpa.getCoursesByStudentsId(1L);

        assertTrue(result.isPresent());
        Iterable<CourseDTO> resultCourses = result.get();
        assertNotNull(resultCourses);
        assertEquals(2, ((List<CourseDTO>) resultCourses).size());
        assertTrue(((List<CourseDTO>) resultCourses).contains(courseDto1));
        assertTrue(((List<CourseDTO>) resultCourses).contains(courseDto2));
    }

    @Test
    void getCoursesByStudentsId_shouldThrowEntityNotFoundException_whenStudentDoesNotExist() {
        when(studentRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> studentServiceJpa.getCoursesByStudentsId(1L));
    }
    @Test
    void moveStudentToGroup_shouldMoveStudentToNewGroup() {
        Long studentId = 1L;
        Long newGroupId = 2L;

        Student student = new Student();
        student.setId(studentId);

        Group newGroup = new Group();
        newGroup.setId(newGroupId);

        when(studentRepository.findById(studentId)).thenReturn(Optional.of(student));
        when(groupRepository.findById(newGroupId)).thenReturn(Optional.of(newGroup));

        studentServiceJpa.moveStuentToGroup(studentId, newGroupId);

        assertEquals(newGroup, student.getGroup());

        verify(studentRepository, times(1)).findById(studentId);
        verify(groupRepository, times(1)).findById(newGroupId);
    }

    @Test
    void moveStudentToGroup_shouldThrowEntityNotFoundException_whenStudentNotFound() {
        Long studentId = 1L;
        Long newGroupId = 2L;

        when(studentRepository.findById(studentId)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> studentServiceJpa.moveStuentToGroup(studentId, newGroupId));
    }

    @Test
    void moveStudentToGroup_shouldThrowEntityNotFoundException_whenNewGroupNotFound() {
        Long studentId = 1L;
        Long newGroupId = 2L;

        Student student = Student.builder().id(studentId).build();
        student.setId(studentId);

        when(studentRepository.findById(studentId)).thenReturn(Optional.of(student));
        when(groupRepository.findById(newGroupId)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> studentServiceJpa.moveStuentToGroup(studentId, newGroupId));
    }

    @Test
    void removeStudentFromCourse_shouldThrowEntityNotFoundException_whenStudentNotFound() {
        Long studentId = 1L;
        Long courseId = 2L;

        when(studentRepository.findById(studentId)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> studentServiceJpa.removeStudentFromCourse(studentId, courseId));
    }

    @Test
    void removeStudentFromCourse_shouldThrowEntityNotFoundException_whenCourseNotFound() {
        Long studentId = 1L;
        Long courseId = 2L;

        Student student = Student.builder().id(studentId).build();
        student.setId(studentId);

        when(studentRepository.findById(studentId)).thenReturn(Optional.of(student));
        when(courseRepository.findById(courseId)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> studentServiceJpa.removeStudentFromCourse(studentId, courseId));
    }

    @Test
    void removeStudentFromCourse_shouldThrowRuntimeException_whenStudentNotEnrolledInCourse() {
        Long studentId = 1L;
        Long courseId = 2L;

        Student student = Student.builder().id(studentId).build();
        student.setId(studentId);

        Course course = Course.builder().id(courseId).build();

        course.setId(courseId);

        when(studentRepository.findById(studentId)).thenReturn(Optional.of(student));
        when(courseRepository.findById(courseId)).thenReturn(Optional.of(course));

        assertThrows(RuntimeException.class, () -> studentServiceJpa.removeStudentFromCourse(studentId, courseId));
    }
}