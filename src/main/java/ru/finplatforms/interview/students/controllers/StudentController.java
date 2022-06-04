package ru.finplatforms.interview.students.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import lombok.RequiredArgsConstructor;
import ru.finplatforms.interview.students.services.StudentService;

@Controller
@RequiredArgsConstructor
public class StudentController {

    private final StudentService studentService;

    @GetMapping({"", "/", "/index", "/index.html"})
    public String studentList(Model model) {
        model.addAttribute("students", studentService.findAllStudents());
        return "index";
    }
}
