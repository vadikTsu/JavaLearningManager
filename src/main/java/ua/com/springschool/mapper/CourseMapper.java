package ua.com.springschool.mapper;

import org.mapstruct.Mapper;
import ua.com.springschool.entity.Course;
import ua.com.springschool.entity.Group;
import ua.com.springschool.model.CourseDTO;
import ua.com.springschool.model.GroupDTO;

@Mapper
public interface CourseMapper {

    Course courseDtoToCourse(CourseDTO courseDTO);
    CourseDTO courseToCourseDto(Course course);
}
