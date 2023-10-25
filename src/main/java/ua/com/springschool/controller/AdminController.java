package ua.com.springschool.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ua.com.springschool.model.GroupDTO;
import ua.com.springschool.model.StudentDTO;
import ua.com.springschool.service.StudentService;

import java.util.List;

@Slf4j
@Controller
@RequestMapping("/students")
public class AdminController {

    private final StudentService studentService;

    @Autowired
    public AdminController(StudentService studentService) {
        this.studentService = studentService;
    }

    @ModelAttribute("groups")
    public List<GroupDTO> groups(){
        return (List<GroupDTO>) studentService.listGroups();
    }

    @GetMapping("/find")
    public String studentByIdForm(){
        return "admin/students/find-by-id";
    }

    @PostMapping("/find/{id}")
    public String studentById(@PathVariable(value = "id") Long id, Model model){
        try{
            StudentDTO student = studentService.getStudentById(id);
            log.info("/find/{id} -- fetched student from DB");
            model.addAttribute("student", student);
            return "admin/students/information";
        } catch(RuntimeException exception){
            log.warn(exception.getMessage());
            return "admin/students/not-found";
        }
    }

    @GetMapping("/form")
    public String saveStudentForm(Model model) {
        model.addAttribute("student", StudentDTO.builder().build());
        return "admin/students/save-new";
    }

    @PostMapping("/save")
    public String saveStudent(@ModelAttribute("student") StudentDTO student, Model model) {
        StudentDTO persistedStudent = studentService.saveNewStudent(student);
        model.addAttribute("student", persistedStudent);
        return "admin/students/information";
    }

    @PostMapping("/delete/{id}")
    public String deleteStudentById(@PathVariable(value = "id") Long id) {
        boolean isDeleted = studentService.deleteStudentById(id);
        if (isDeleted) {
            log.info("Deleted student with ID: " + id);
            return "redirect:/admin";
        } else {
            log.warn("Failed to delete student with ID: " + id);
            return "admin/students/not-found";
        }
    }

}
