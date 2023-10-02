package ua.com.springschool.service;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.com.springschool.entity.Course;
import ua.com.springschool.entity.Student;
import ua.com.springschool.mapper.CourseMapper;
import ua.com.springschool.mapper.StudentMapper;
import ua.com.springschool.model.StudentDTO;
import ua.com.springschool.repository.CourseRepository;
import ua.com.springschool.repository.StudentRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class StudentServiceJpa implements StudentService{

    private final StudentRepository studentRepository;
    private final CourseRepository courseRepository;
    private final StudentMapper studentMapper;
    private final CourseMapper courseMapper;

    @Autowired
    public StudentServiceJpa(StudentRepository studentRepository,
                             CourseRepository courseRepository,
                             StudentMapper studentMapper, CourseMapper courseMapper) {
        this.studentRepository = studentRepository;
        this.courseRepository = courseRepository;
        this.studentMapper = studentMapper;
        this.courseMapper = courseMapper;
    }

    @Override
    public List<StudentDTO> listStudents() {
        return studentRepository.findAll()
                .stream()
                .map(studentMapper::studentToStudentDto)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<StudentDTO> getStudentById(UUID id) {
        return Optional.ofNullable(
                studentMapper.studentToStudentDto(
                        studentRepository
                                .findById(id)
                                .orElse(null)));
    }

    @Override
    public StudentDTO saveNewStudent(StudentDTO studentDTO) {
        return studentMapper.studentToStudentDto(studentRepository.
                save(studentMapper.studentDtoToStudent(studentDTO)));
    }

    @Transactional
    public void addStudentToCourse(UUID studentId, UUID courseId) {
        Optional<Student> studentOptional = studentRepository.findById(studentId);
        Optional<Course> courseOptional = courseRepository.findById(courseId);

        if (studentOptional.isPresent() && courseOptional.isPresent()) {
            Course course = courseOptional.get();
            Student student = studentOptional.get();

            course.getStudents().add(student);
            student.getCourses().add(course);

            courseRepository.save(course);
            studentRepository.save(student);
        } else {
            throw new EntityNotFoundException("Invalid Id given!");
        }
    }

    @Override
    public Boolean deleteById(UUID studentId) {
        if (studentRepository.existsById(studentId)) {
            studentRepository.deleteById(studentId);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public Optional<Iterable<Course>> getCoursesByStudentsId(UUID studentId) {
        if(studentRepository.existsById(studentId)){
            Optional<Student> student = studentRepository.findById(studentId);
            if(student.isPresent()){
                return Optional.of(student.get().getCourses().stream().peek(courseMapper::courseToCourseDto).collect(Collectors.toList()));
            }
        }
        return Optional.empty();
    }

}
