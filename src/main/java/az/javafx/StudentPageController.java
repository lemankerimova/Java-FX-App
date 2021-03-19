package az.javafx;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import az.javafx.dao.StudentDao;
import az.javafx.dao.impl.StudentDaoImpl;
import az.javafx.model.Student;
import az.javafx.service.StudentService;
import az.javafx.service.impl.StudentServiceImpl;
import az.javafx.util.ControllerUtil;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class StudentPageController  extends GeneralStudentController{
    StudentDao studentDao = new StudentDaoImpl();
    StudentService studentService = new StudentServiceImpl(studentDao);

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TableView<Student> studentTable;

    @FXML
    private TableColumn<?, ?> idCol;

    @FXML
    private TableColumn<?, ?> nameCol;

    @FXML
    private TableColumn<?, ?> surnameCol;

    @FXML
    private TableColumn<?, ?> ageCol;

    @FXML
    private TableColumn<?, ?> seriaNumCol;

    @FXML
    private TableColumn<?, ?> phoneCol;

    @FXML
    private TableColumn<?, ?> emailCol;

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
        printStudents();
        addBtn.setOnAction(event -> {
            operation = addBtn.getText().toLowerCase();
            System.out.println(operation);
            openStudentAddForm();
        });
        refreshBtn.setOnAction(event -> {
            printStudents();
        });
        updateBtn.setOnAction(event ->{
            operation = updateBtn.getText().toLowerCase();
            openStudentUpdateForm();
        });
        deleteBtn.setOnAction(event -> {
            deleteStudentById();
        });
        homeBtn.setOnAction(event ->{
            homeBtn.getScene().getWindow().hide();
            ControllerUtil.openNewScene(getClass().getResource("main.fxml"));
        });
        logoutBtn.setOnAction(event -> {
            logoutBtn.getScene().getWindow().hide();
            ControllerUtil.openNewScene(getClass().getResource("login.fxml"));
        });

    }

    private void deleteStudentById() {
        Long studentId = studentTable.getSelectionModel().getSelectedItem().getId();
        studentService.deleteStudent(studentId);
    }

    private void openStudentAddForm() {
        selectedStudent = null;
        ControllerUtil.openNewScene(getClass().getResource("studentForm.fxml"));
    }

    private void openStudentUpdateForm() {
        try {
            Long studentId = studentTable.getSelectionModel().getSelectedItem().getId();
            Student student = studentService.getStudentById(studentId);
            selectedStudent = student;
            errorMessage.setText("");
            ControllerUtil.openNewScene(getClass().getResource("studentForm.fxml"));

        }catch (NullPointerException ex) {
            errorMessage.setText("Dəyişiklik etmək üçün istifadəçi seçilməyib !");
        }
    }

    private void printStudents() {
        List<Student> studentsFromDb = studentService.getAllStudents();
        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        surnameCol.setCellValueFactory(new PropertyValueFactory<>("surname"));
        ageCol.setCellValueFactory(new PropertyValueFactory<>("DOB"));
        seriaNumCol.setCellValueFactory(new PropertyValueFactory<>("seriaNum"));
        emailCol.setCellValueFactory(new PropertyValueFactory<>("email"));
        phoneCol.setCellValueFactory(new PropertyValueFactory<>("phone"));
        studentTable.setItems(FXCollections.observableArrayList(studentsFromDb));
    }
}

