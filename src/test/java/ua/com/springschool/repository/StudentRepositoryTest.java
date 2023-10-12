package ua.com.springschool.repository;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import ua.com.springschool.entity.Student;
import ua.com.springschool.mapper.StudentMapper;
import ua.com.springschool.model.StudentDTO;

import java.util.List;
import java.util.Optional;

class StudentRepositoryTest extends AbstractTestContainer {

    @Autowired
    StudentRepository studentRepository;

    @Autowired
    GroupRepository groupRepository;

    @Autowired
    StudentMapper studentMapper;

    @Test
    public void studentRepository_saveStudent_ReturnSavedPokemon() {
        StudentDTO studentDTO = StudentDTO.builder().id(7L)
                .name("pikachu")
                .groupId(1L)
                .build();

        Student student = studentMapper.studentDtoToStudent(studentDTO);

        student.setGroup(groupRepository.findById(studentDTO.getGroupId()).orElseThrow(() -> new  RuntimeException()));
        Student savedStudent = studentRepository.save(student);

        assertThat(savedStudent).isNotNull();
        assertThat(savedStudent.getId()).isGreaterThan(0);

        System.out.println(studentRepository.findById(7L).orElse(null).getGroup().getName());
    }

    @Test
    public void studentRepository_GetAll_ReturnMoreThenOnePokemon() {

        List<Student> studentsList = studentRepository.findAll();

        assertThat(studentsList).isNotNull();
        assertThat(studentsList.size()).isEqualTo(6);
    }

    @Test
    public void studentRepository_FindById_ReturnPokemon() {
        Student student = studentRepository.findById(1L).orElseThrow(()-> new RuntimeException());

        Assertions.assertEquals("Student 1", student.getName());

        assertThat(student).isNotNull();
    }

    @Test
    public void PokemonRepository_PokemonDelete_ReturnPokemonIsEmpty() {
        studentRepository.deleteById(1L);

        Optional<Student> student = studentRepository.findById(1L);

        assertThat(student).isEmpty();
    }

    @Test
    public void studentRepository_shouldFindAllStudentsAssignedToCourse_whenValidName(){

        List<Student> students = (List<Student>) studentRepository.findAllStudentsAssignedToCourse("Mathematics");
        assertThat(students.size()).isGreaterThan(0);

        List<Student> studentsNotExistingCourse = (List<Student>) studentRepository.findAllStudentsAssignedToCourse("RandomCourse))");
        Assertions.assertTrue(studentsNotExistingCourse.isEmpty());
    }

}