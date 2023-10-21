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

import java.util.*;

@Component
@RequiredArgsConstructor
public class BootstrapDataService implements ApplicationRunner {

    private final StudentRepository studentRepository;
    private final GroupRepository groupRepository;
    private final CourseRepository courseRepository;

    @Override
    public void run(ApplicationArguments args) {
        Group group1 = Group.builder().id(1L).name("Group 1").build();
        Group group2 = Group.builder().id(2L).name("Group 2").build();
        Group group3 = Group.builder().id(3L).name("Group 3").build();
        groupRepository.saveAll(Arrays.asList(group1, group2, group3));

        Course course1 = Course.builder().id(1L).name("Mathematics").build();
        Course course2 = Course.builder().id(2L).name("Physics").build();
        Course course3 = Course.builder().id(3L).name("Programming").build();
        Course course4 = Course.builder().id(4L).name("RandomCourse))").build();
        courseRepository.saveAll(Arrays.asList(course1, course2, course3, course4));

        List<Group> groups = Arrays.asList(group1, group2, group3);
        List<Course> courses = Arrays.asList(course1, course2, course3, course4);

        Random random = new Random();

        for (long i = 1; i <= 6; i++) {
            Group randomGroup = groups.get(random.nextInt(groups.size()));

            Course randomCourse = courses.get(random.nextInt(courses.size()));

            Student student = Student.builder()
                .id(i)
                .name("Student " + i)
                .group(randomGroup)
                .courses(Set.of(randomCourse))
                .build();

            studentRepository.save(student);
        }
    }
}
