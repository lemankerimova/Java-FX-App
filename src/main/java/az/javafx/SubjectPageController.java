package az.javafx;

import az.javafx.dao.SubjectDao;
import az.javafx.dao.impl.SubjectDaoImpl;
import az.javafx.service.SubjectService;
import az.javafx.service.impl.SubjectServiceImpl;

public class SubjectPageController extends GeneralSubjectController{
    SubjectDao subjectDao = new SubjectDaoImpl();
    SubjectService subjectService = new SubjectServiceImpl(subjectDao);



}
