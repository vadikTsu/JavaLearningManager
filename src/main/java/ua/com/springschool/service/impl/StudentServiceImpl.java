package ua.com.springschool.service.impl;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.com.springschool.entity.Course;
import ua.com.springschool.entity.Group;
import ua.com.springschool.entity.Student;
import ua.com.springschool.exceptions.CourseNotFoundException;
import ua.com.springschool.exceptions.GroupNotFoundException;
import ua.com.springschool.exceptions.StudentNotFoundException;
import ua.com.springschool.mapper.CourseMapper;
import ua.com.springschool.mapper.GroupMapper;
import ua.com.springschool.mapper.StudentMapper;
import ua.com.springschool.model.CourseDTO;
import ua.com.springschool.model.GroupDTO;
import ua.com.springschool.model.StudentDTO;
import ua.com.springschool.repository.CourseRepository;
import ua.com.springschool.repository.GroupRepository;
import ua.com.springschool.repository.StudentRepository;
import ua.com.springschool.service.StudentService;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class StudentServiceImpl implements StudentService {

    private final StudentRepository studentRepository;
    private final CourseRepository courseRepository;
    private final GroupRepository groupRepository;
    private final StudentMapper studentMapper;
    private final CourseMapper courseMapper;
    private final GroupMapper groupMapper;

    @Autowired
    public StudentServiceImpl(StudentRepository studentRepository,
                              CourseRepository courseRepository,
                              GroupRepository groupRepository,
                              StudentMapper studentMapper,
                              CourseMapper courseMapper,
                              GroupMapper groupMapper) {
        this.studentRepository = studentRepository;
        this.courseRepository = courseRepository;
        this.groupRepository = groupRepository;
        this.studentMapper = studentMapper;
        this.courseMapper = courseMapper;
        this.groupMapper = groupMapper;
    }

    @Override
    public Iterable<StudentDTO> listStudents() {
        return studentRepository.findAll()
            .stream()
            .map(studentMapper::studentToStudentDto)
            .collect(Collectors.toList());
    }

    @Override
    public Iterable<GroupDTO> listGroups() {
        return groupRepository.findAll()
            .stream()
            .map(groupMapper::groupToGroupDto)
            .collect(Collectors.toList());
    }

    @Override
    public StudentDTO getStudentById(Long id) {
        return studentMapper.studentToStudentDto(
            studentRepository.findById(id)
            .orElseThrow(()-> new StudentNotFoundException("Not existing student with ID: " + id)));
    }

    @Transactional
    @Override
    public StudentDTO saveNewStudent(StudentDTO studentDTO) {
        Student student = studentMapper.studentDtoToStudent(studentDTO);
        Group group = groupRepository.findById(studentDTO.getGroupId())
            .orElseThrow(() -> new GroupNotFoundException("Not existing group with ID: " + studentDTO.getGroupId()));

        Set<Course> courses = new HashSet<>();
        if(student.getCourses() != null){
            courses.addAll(student.getCourses());
        }
        student.setGroup(group);
        student.setCourses(courses);
        return studentMapper.studentToStudentDto(studentRepository.save(student));
    }

    @Transactional
    @Override
    public void assignStudentToCourse(Long studentId, Long courseId) {
        Student student = studentRepository.findById(studentId)
            .orElseThrow(() -> new StudentNotFoundException("Student not found with ID: " + studentId));

        Course course = courseRepository.findById(courseId)
            .orElseThrow(() -> new CourseNotFoundException("Course not found with ID: " + courseId));

        course.getStudents().add(student);
        student.getCourses().add(course);
    }

    @Override
    public Boolean deleteById(Long studentId) {
        if (studentRepository.existsById(studentId)) {
            studentRepository.deleteById(studentId);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public Iterable<CourseDTO> getCoursesByStudentsId(Long studentId) {
        Student student = studentRepository.findById(studentId)
            .orElseThrow(() -> new StudentNotFoundException("Student not found with ID: " + studentId));
        return student
            .getCourses()
            .stream()
            .map(courseMapper::courseToCourseDto)
            .collect(Collectors.toList());
    }

    @Transactional
    @Override
    public void moveStuentToGroup(Long studentId, Long newGroupId) {
        Student student = studentRepository.findById(studentId)
            .orElseThrow(() -> new StudentNotFoundException("Student not found with ID: " + studentId));

        Group newGroup = groupRepository.findById(newGroupId)
            .orElseThrow(() -> new GroupNotFoundException("Group not found with ID: " + newGroupId));

        Group oldGroup = student.getGroup();
        if (oldGroup != null && !oldGroup.getId().equals(newGroup.getId())) {
            oldGroup.getStudents().remove(student);
        }
        student.setGroup(newGroup);
    }

    @Transactional
    @Override
    public void removeStudentFromCourse(Long studentId, Long courseId) {
        Student student = studentRepository.findById(studentId)
            .orElseThrow(() -> new StudentNotFoundException("Student not found with ID: " + studentId));

        Course course = courseRepository.findById(courseId)
            .orElseThrow(() -> new CourseNotFoundException("Course not found with ID: " + courseId));

        if (course.getStudents().contains(student) && student.getCourses().contains(course)) {
            course.getStudents().remove(student);
            student.getCourses().remove(course);
        } else {
            throw new RuntimeException("Student is not enrolled to course with ID: " + courseId);
        }
    }
}
