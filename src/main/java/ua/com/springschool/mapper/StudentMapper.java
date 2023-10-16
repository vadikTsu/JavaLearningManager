package ua.com.springschool.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ua.com.springschool.entity.Course;
import ua.com.springschool.entity.Student;
import ua.com.springschool.model.StudentDTO;

import java.util.Set;
import java.util.stream.Collectors;

@Mapper
public interface StudentMapper {

    //todo add reverse mapping for student dto
    //todo service refactor required

    Student studentDtoToStudent(StudentDTO studentDTO);

    /**
     *  Mapping Student entity to StudentDTO
     */
    @Mapping(source = "group.id", target = "groupId")
    @Mapping(target = "courseIds", expression = "java(mapCoursesToIds(student.getCourses()))")
    StudentDTO studentToStudentDto(Student student);

    default Set<Long> mapCoursesToIds(Set<Course> courses) {
        return courses.stream()
            .map(Course::getId)
            .collect(Collectors.toSet());
    }
}
