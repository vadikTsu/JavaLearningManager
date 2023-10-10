package ua.com.springschool.mapper;

import org.mapstruct.Mapper;
import ua.com.springschool.entity.Student;
import ua.com.springschool.model.StudentDTO;

@Mapper
public interface StudentMapper {

    Student studentDtoToStudent(StudentDTO studentDTO);
    StudentDTO studentToStudentDto(Student student);

}
