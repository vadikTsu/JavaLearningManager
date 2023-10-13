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
        Group group1 = Group.builder().id(1L).name("Group 1").students(null).build();
        Group group2 = Group.builder().id(2L).name("Group 2").students(null).build();
        groupRepository.save(group1);
        groupRepository.save(group2);

        Course course1 = Course.builder().id(1L).name("Mathematics").build();
        Course course2 = Course.builder().id(2L).name("Physics").build();
        Course course3 = Course.builder().id(3L).name("Programming").build();
        Course course4 = Course.builder().id(4L).name("RandomCourse)").build();
        courseRepository.save(course1);
        courseRepository.save(course2);
        courseRepository.save(course3);
        courseRepository.save(course4);

        List<Course> availableCourses = new ArrayList<>();
        availableCourses.addAll(List.of(course1, course2, course3, course4));

        Random random = new Random();

        for (long i = 1L; i <= 6; i++) {
            Group randomGroup = (random.nextInt(2) + 1) == 1 ? group1 : group2;

            int numCourses = random.nextInt(2) + 1;
            Set<Course> randomCourses = new HashSet<>();

            for (int j = 0; j < numCourses; j++) {
                Course randomCourse = availableCourses.get(random.nextInt(availableCourses.size()));
                randomCourses.add(randomCourse);
            }

            Student student = Student.builder().id(i).name("Student " + i)
                .group(randomGroup)
                .courses(randomCourses).build();
            studentRepository.save(student);
        }
    }
}
