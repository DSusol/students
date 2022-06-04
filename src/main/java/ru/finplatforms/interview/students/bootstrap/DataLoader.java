package ru.finplatforms.interview.students.bootstrap;

import java.time.LocalDate;
import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ru.finplatforms.interview.students.database.StudentEntity;
import ru.finplatforms.interview.students.database.StudentRepository;

@Slf4j
@RequiredArgsConstructor
@Component
public class DataLoader implements CommandLineRunner {

    private final StudentRepository studentRepository;

    @Override
    public void run(String... args) throws Exception {
        if (studentRepository.count() == 0) {
            addDataToDatabase();
        }
    }

    private void addDataToDatabase() {

        StudentEntity alexey = StudentEntity.builder()
                .firstName("Alexey")
                .middleName("Alexeevich")
                .lastName("Alexeev")
                .birthDate(LocalDate.of(2005, 2, 15))
                .group("Engineers")
                .build();

        StudentEntity vasiliy = StudentEntity.builder()
                .firstName("Vasiliy")
                .middleName("Vasilievich")
                .lastName("Vasiliev")
                .birthDate(LocalDate.of(2005, 5, 10))
                .group("Engineers")
                .build();

        StudentEntity andrey = StudentEntity.builder()
                .firstName("Andrey")
                .middleName("Andreevich")
                .lastName("Andreev")
                .birthDate(LocalDate.of(2005, 12, 6))
                .group("Finance")
                .build();

        StudentEntity pavel = StudentEntity.builder()
                .firstName("Pavel")
                .middleName("Pavlovich")
                .lastName("Pavelov")
                .birthDate(LocalDate.of(2005, 3, 17))
                .group("Finance")
                .build();

        StudentEntity kirill = StudentEntity.builder()
                .firstName("Kirill")
                .middleName("Kirillovich")
                .lastName("Kirillov")
                .birthDate(LocalDate.of(2005, 6, 1))
                .group("Finance")
                .build();

        studentRepository.saveAll(List.of(alexey, vasiliy, andrey, pavel, kirill));
        log.debug("{} students are added to database.", studentRepository.count());
    }
}