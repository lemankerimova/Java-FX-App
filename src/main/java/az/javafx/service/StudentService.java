package az.javafx.service;

import az.javafx.model.Student;

import java.util.List;

public interface StudentService {

    boolean addStudent(Student student);

    List<Student> getAllStudents();

    Student getStudentById(Long id);

    boolean deleteStudent(Long id);

    boolean updateStudentById(Student student);
}
