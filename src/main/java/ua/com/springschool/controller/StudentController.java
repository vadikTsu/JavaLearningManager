package ua.com.springschool.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ua.com.springschool.model.GroupDTO;
import ua.com.springschool.model.StudentDTO;
import ua.com.springschool.service.StudentService;

import java.util.List;

@Controller
@RequestMapping("/students")
public class StudentController {

    private final StudentService studentService;

    @Autowired
    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @ModelAttribute("groups")
    public List<GroupDTO> groups(){
        Iterable<GroupDTO> gruops =  studentService.listGroups().orElse(null);
        return (List<GroupDTO>) gruops;
    }

    @GetMapping
    public String studentByIdForm(){
        return "student-by-id";
    }

    @GetMapping("/")
    public String studentById(@RequestParam(value = "id") Long id, Model model){
        StudentDTO student = studentService.getStudentById(id).orElse(null);
        if (student != null) {
            model.addAttribute("student", student);
            return "student-information";
        } else {
            return "student-not-found";
        }
    }
    @GetMapping("/form")
    public String saveStudentForm(Model model) {
        model.addAttribute("student", StudentDTO.builder().build());
        return "student-save-new";
    }

    @PostMapping("/save")
    public String saveStudent(@ModelAttribute("student") StudentDTO student, Model model) {
        StudentDTO persistedStuednt = studentService.saveNewStudent(student);
        model.addAttribute("student", persistedStuednt);
        return "student-information";
    }
}
