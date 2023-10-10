package ua.com.springschool.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.com.springschool.entity.Course;

import java.util.UUID;

public interface CourseRepository extends JpaRepository<Course, Long> {
}
