package ua.com.springschool.bootstrap;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import ua.com.springschool.entity.Course;
import ua.com.springschool.entity.Group;
import ua.com.springschool.entity.Student;
import ua.com.springschool.repository.CourseRepository;
import ua.com.springschool.repository.GroupRepository;
import ua.com.springschool.repository.StudentRepository;

import java.util.Set;

@Component
@RequiredArgsConstructor
public class BootstrapDataService implements ApplicationRunner {

    private final StudentRepository studentRepository;
    private final GroupRepository groupRepository;
    private final CourseRepository courseRepository;

    @Override
    public void run(ApplicationArguments args) {
        Group group1 = Group.builder().id(1L).name("Group 1").students(null).build();
        Group group2 = Group.builder().id(2L).name("Group 2").students(null).build();
        groupRepository.save(group1);
        groupRepository.save(group2);

        Course course1 = Course.builder().id(1L).name("Mathematics").build();
        Course course2 = Course.builder().id(2L).name("Physics").build();
        Course course3 = Course.builder().id(3L).name("Programming").build();
        Course course4 = Course.builder().id(4L).name("RandomCourse))").build();
        courseRepository.save(course1);
        courseRepository.save(course2);
        courseRepository.save(course3);
        courseRepository.save(course4);

        Student student1 = Student.builder().id(1L).name("Student 1").group(group1).courses(Set.of(course1, course2, course3)).build();
        Student student2 = Student.builder().id(2L).name("Student 2").group(group1).build();
        Student student3 = Student.builder().id(3L).name("Student 3").group(group1).build();
        Student student4 = Student.builder().id(4L).name("Student 4").group(group1).build();
        Student student5 = Student.builder().id(5L).name("Student 5").group(group2).build();
        Student student6 = Student.builder().id(6L).name("Student 6").group(group2).build();
        studentRepository.save(student1);
        studentRepository.save(student2);
        studentRepository.save(student3);
        studentRepository.save(student4);
        studentRepository.save(student5);
        studentRepository.save(student6);
    }
}
