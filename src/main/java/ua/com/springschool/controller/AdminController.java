package ua.com.springschool.controller;

import com.fasterxml.jackson.databind.introspect.TypeResolutionContext;
import jakarta.websocket.server.PathParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ua.com.springschool.exceptions.StudentNotFoundException;
import ua.com.springschool.model.GroupDTO;
import ua.com.springschool.model.StudentDTO;
import ua.com.springschool.service.StudentService;

import java.util.List;

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
        Iterable<GroupDTO> gruops =  studentService.listGroups().orElse(null);
        return (List<GroupDTO>) gruops;
    }

    @GetMapping("/find")
    public String studentByIdForm(){
        return "admin/students/find-by-id";
    }

    @PostMapping("/find/{id}")
    public String studentById(@PathVariable(value = "id") Long id, Model model){
        StudentDTO student = studentService.getStudentById(id).orElse(null);
        if (student != null) {
            model.addAttribute("student", student);
            return "admin/students/information";
        } else {
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
        StudentDTO persistedStuednt = studentService.saveNewStudent(student);
        model.addAttribute("student", persistedStuednt);
        return "admin/students/information";
    }

    @PostMapping("/delete/{id}")
    public String deleteStudentById(@PathVariable(value = "id") Long id) {
        boolean isDeleted = studentService.deleteById(id);
        if (isDeleted) {
            return "redirect:/admin";
        } else {
            return "admin/students/not-found";
        }
    }
}
