package az.javafx;

import az.javafx.dao.TeacherDao;
import az.javafx.dao.impl.TeacherDAOImpl;
import az.javafx.model.Student;
import az.javafx.model.Teacher;
import az.javafx.service.TeacherService;
import az.javafx.service.impl.TeacherServiceImpl;
import az.javafx.util.ControllerUtil;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class TeacherPageController extends GeneralTeacherController {
    TeacherDao teacherDao = new TeacherDAOImpl();
    TeacherService teacherService = new TeacherServiceImpl(teacherDao);

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TableView<Teacher> teacherTable;

    @FXML
    private TableColumn<?, ?> idCol;

    @FXML
    private TableColumn<?, ?> nameCol;

    @FXML
    private TableColumn<?, ?> surnameCol;

    @FXML
    private TableColumn<?, ?> ageCol;

    @FXML
    private TableColumn<?, ?> serianumCol;

    @FXML
    private TableColumn<?, ?> emailCol;

    @FXML
    private TableColumn<?, ?> phoneCol;

    @FXML
    private Button addBtn;

    @FXML
    private Button updateBtn;

    @FXML
    private Button deleteBtn;

    @FXML
    private Button refreshBtn;

    @FXML
    private Label errorMessage;

    @FXML
    void initialize() {
        printTeachers();
        addBtn.setOnAction(event -> {
            operation = addBtn.getText().toLowerCase();
            System.out.println(operation);
            openTeacherAddForm();
        });
        refreshBtn.setOnAction(event -> {
            printTeachers();
        });
        updateBtn.setOnAction(event -> {
            operation = updateBtn.getText().toLowerCase();
            openTeacherUpdateForm();
        });
        deleteBtn.setOnAction(event -> {
            deleteTeacherById();
        });
        homeBtn.setOnAction(event -> {
            homeBtn.getScene().getWindow().hide();
            ControllerUtil.openNewScene(getClass().getResource("main.fxml"));
        });
        logoutBtn.setOnAction(event -> {
            logoutBtn.getScene().getWindow().hide();
            ControllerUtil.openNewScene(getClass().getResource("login.fxml"));
        });

    }

    private void deleteTeacherById() {
        Long teacherId = teacherTable.getSelectionModel().getSelectedItem().getId();
        teacherService.deleteTeacher(teacherId);
    }

    private void openTeacherAddForm() {
        selectedTeacher = null;
        ControllerUtil.openNewScene(getClass().getResource("teacherForm.fxml"));
    }

    private void printTeachers() {
        List<Teacher> teachersFromDb = teacherService.getAllTeachers();
        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        surnameCol.setCellValueFactory(new PropertyValueFactory<>("surname"));
        ageCol.setCellValueFactory(new PropertyValueFactory<>("DOB"));
        serianumCol.setCellValueFactory(new PropertyValueFactory<>("seriaNum"));
        emailCol.setCellValueFactory(new PropertyValueFactory<>("email"));
        phoneCol.setCellValueFactory(new PropertyValueFactory<>("phone"));
        teacherTable.setItems(FXCollections.observableArrayList(teachersFromDb));
    }

    private void openTeacherUpdateForm() {
        try {
            Long teacherId = teacherTable.getSelectionModel().getSelectedItem().getId();
            Teacher teacher = teacherService.getTeacherById(teacherId);
            selectedTeacher = teacher;
            errorMessage.setText("");
            ControllerUtil.openNewScene(getClass().getResource("teacherForm.fxml"));

        } catch (NullPointerException ex) {
            errorMessage.setText("Dəyişiklik etmək üçün istifadəçi seçilməyib !");
        }
    }
}

