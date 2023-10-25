package ua.com.springschool.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;
import ua.com.springschool.entity.Course;
import ua.com.springschool.entity.Group;
import ua.com.springschool.entity.Student;
import ua.com.springschool.model.StudentDTO;
import ua.com.springschool.repository.CourseRepository;
import ua.com.springschool.repository.GroupRepository;
//import ua.com.springschool.repository.GroupRepository;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public abstract class StudentMapper {

    @Autowired
    protected GroupRepository groupRepository;

    @Autowired
    protected CourseRepository courseRepository;

    @Mapping(target = "group", expression = "java(mapGroupIdToGroup(studentDTO.getGroupId()))")
    @Mapping(target = "courses", expression = "java(mapCourseIdsToCourses(studentDTO.getCourseIds()))")
    public abstract Student studentDtoToStudent(StudentDTO studentDTO);

    @Mapping(source = "group.id", target = "groupId")
    @Mapping(target = "courseIds", expression = "java(mapCoursesToIds(student.getCourses()))")
    public abstract StudentDTO studentToStudentDto(Student student);

    Group mapGroupIdToGroup(Long groupId) {
        return groupId != null ? groupRepository.findById(groupId).orElse(null) : null;
    }

    Set<Course> mapCourseIdsToCourses(Set<Long> courseIds) {
        return courseIds != null ? new HashSet<>(courseRepository.findAllById(courseIds)) : null;
    }

    Set<Long> mapCoursesToIds(Set<Course> courses) {
        return courses.stream().map(Course::getId).collect(Collectors.toSet());
    }

}
