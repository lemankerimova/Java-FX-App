package az.javafx.dao.impl;

import az.javafx.dao.SubjectDao;
import az.javafx.model.Subject;

import java.util.List;

public class SubjectDaoImpl implements SubjectDao {
    @Override
    public boolean saveSubject(Subject subject) {
        return false;
    }

    @Override
    public List<Subject> getAllSubjects() {
        return null;
    }

    @Override
    public boolean softDeleteSubject(Long id) {
        return false;
    }

    @Override
    public boolean hardDeleteSubject(Long id) {
        return false;
    }

    @Override
    public Subject getSubjectById(Long id) {
        return null;
    }

    @Override
    public boolean updateSubjectById(Subject subject) {
        return false;
    }
}
