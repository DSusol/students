package ru.finplatforms.interview.students.controllers;

import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ru.finplatforms.interview.students.domain.Student;
import ru.finplatforms.interview.students.services.StudentService;

@Controller
@Slf4j
@RequiredArgsConstructor
public class StudentController {

    private final StudentService studentService;

    @GetMapping({"", "/", "/index", "/index.html"})
    public String studentList(Model model) {
        model.addAttribute("students", studentService.findAllStudents());
        return "index";
    }

    @GetMapping("/students/new")
    public String newStudent(Model model) {
        model.addAttribute("student", new Student());
        return "createOrUpdateForm";
    }

    @GetMapping("students/{studentId}/update")
    public String updateStudent(@PathVariable Long studentId, Model model) {
        model.addAttribute("student", studentService.findStudentById(studentId));
        return "createOrUpdateForm";
    }


    @PostMapping({"students/new", "/students/{studentId}/update"})
    public String saveOrUpdateStudent(@Valid @ModelAttribute("student") Student student, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            bindingResult.getAllErrors().forEach(objectError -> log.debug(objectError.toString()));
            return "createOrUpdateForm";
        }
        studentService.saveStudent(student);
        return "redirect:/";
    }
}