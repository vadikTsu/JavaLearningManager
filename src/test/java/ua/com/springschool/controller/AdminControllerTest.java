package ua.com.springschool.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.OverrideAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import ua.com.springschool.model.StudentDTO;
import ua.com.springschool.service.StudentService;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;


@WebMvcTest(AdminController.class)
@OverrideAutoConfiguration(enabled=true)
class AdminControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private StudentService studentService;

    @Test
    public void testStudentByIdForm() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/students/find"))
            .andExpect(status().isOk())
            .andExpect(view().name("admin/students/find-by-id"));
    }

    @Test
    public void testStudentById() throws Exception {
        when(studentService.getStudentById(1L)).thenReturn(Optional.of(StudentDTO.builder().build()));

        mockMvc.perform(MockMvcRequestBuilders.post("/students/find/1"))
            .andExpect(status().isOk())
            .andExpect(view().name("admin/students/information"));
    }

    @Test
    public void testSaveStudentForm() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/students/form"))
            .andExpect(status().isOk())
            .andExpect(view().name("admin/students/save-new"))
            .andExpect(MockMvcResultMatchers.model().attributeExists("student"));
    }

    @Test
    public void testSaveStudent() throws Exception {
        StudentDTO student = StudentDTO.builder().build();
        when(studentService.saveNewStudent(any(StudentDTO.class))).thenReturn(student);

        mockMvc.perform(MockMvcRequestBuilders.post("/students/save")
                .flashAttr("student", student))
            .andExpect(status().isOk())
            .andExpect(view().name("admin/students/information"))
            .andExpect(MockMvcResultMatchers.model().attributeExists("student"));
    }

    @Test
    public void testDeleteStudentById() throws Exception {
        when(studentService.deleteById(1L)).thenReturn(true);

        mockMvc.perform(MockMvcRequestBuilders.post("/students/delete/1"))
            .andExpect(status().is3xxRedirection())
            .andExpect(view().name("redirect:/admin"));
    }

}
