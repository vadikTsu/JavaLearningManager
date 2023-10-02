package ua.com.springschool.bootstrap;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import ua.com.springschool.repository.CourseRepository;
import ua.com.springschool.repository.GroupRepository;
import ua.com.springschool.repository.StudentRepository;

@Component
@RequiredArgsConstructor
public class BootstrapData implements CommandLineRunner {

    private final StudentRepository studentRepository;
    private final GroupRepository groupRepository;
    private final CourseRepository courseRepository;

    @Override
    public void run(String... args) throws Exception {
        //todo add data
    }
}
