package az.javafx.service;

import az.javafx.model.Teacher;

import java.util.List;

public interface TeacherService {
    boolean addTeacher(Teacher teacher);

    List<Teacher> getAllTeachers();

    Teacher getTeacherById(Long id);

    boolean deleteTeacher(Long id);

    boolean updateTeacherById(Teacher teacher);
}
