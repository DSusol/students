package ru.finplatforms.interview.students.controllers;

import java.util.List;
import java.util.stream.Collectors;

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
import ru.finplatforms.interview.students.services.StudentService;

@Controller
@Slf4j
@RequiredArgsConstructor
public class StudentController {

    private final UIStudentDTOMapper dtoMapper;
    private final StudentService studentService;

    @GetMapping({"", "/", "/index", "/index.html"})
    public String studentList(Model model) {
        List<UIStudentDTO> studentDTOs = studentService.findAllStudents().stream()
                .map(dtoMapper::studentToUiStudentDto)
                .collect(Collectors.toList());
        model.addAttribute("students", studentDTOs);
        return "index";
    }

    @GetMapping("/students/new")
    public String newStudent(Model model) {
        model.addAttribute("student", new UIStudentDTO());
        return "createOrUpdateForm";
    }

    @GetMapping("students/{studentId}/update")
    public String updateStudent(@PathVariable Long studentId, Model model) {
        UIStudentDTO studentDTO = dtoMapper.studentToUiStudentDto(
                studentService.findStudentById(studentId));
        model.addAttribute("student", studentDTO);
        return "createOrUpdateForm";
    }


    @PostMapping({"students/new", "/students/{studentId}/update"})
    public String saveOrUpdateStudent(@Valid @ModelAttribute("student") UIStudentDTO studentDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            bindingResult.getAllErrors().forEach(objectError -> log.debug(objectError.toString()));
            return "createOrUpdateForm";
        }
        studentService.saveStudent(dtoMapper.uiStudentDtoToStudent(studentDTO));
        return "redirect:/";
    }

    @GetMapping("/students/{studentId}/delete")
    public String deleteStudent(@PathVariable Long studentId) {
        studentService.deleteStudentById(studentId);
        return "redirect:/";
    }
}