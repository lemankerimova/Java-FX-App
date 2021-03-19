package az.javafx;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

import az.javafx.dao.StudentDao;
import az.javafx.dao.impl.StudentDaoImpl;
import az.javafx.exceptions.*;
import az.javafx.model.Student;
import az.javafx.service.StudentService;
import az.javafx.service.impl.StudentServiceImpl;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class StudentFormController extends GeneralStudentController {
    StudentDao studentDao = new StudentDaoImpl();
    StudentService studentService = new StudentServiceImpl(studentDao);

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField phoneField;

    @FXML
    private TextField firstnameField;

    @FXML
    private Button ignoreBtn;

    @FXML
    private TextField seriaNumField;

    @FXML
    private TextField emailField;

    @FXML
    private TextField surnameField;

    @FXML
    private DatePicker dobField;

    @FXML
    private Button saveBtn;

    @FXML
    private Label successMessage;

    @FXML
    private RadioButton femaleRadio;

    @FXML
    private RadioButton maleRadio;


    @FXML
    void initialize() {
        saveBtn.setOnAction(event -> {
            if (operation.equals("add student")) {
                setStudent(operation);
            } else if (operation.equals("update student")) {
                setStudent(operation);
            }
        });
        printSelectedStudent();
    }

    private void setStudent(String operation) {
        Student student = new Student();
        boolean errorNotFound = true;
        String gender = "";
        try {
            if (femaleRadio.selectedProperty().getValue() && maleRadio.selectedProperty().getValue()) {
                System.out.println("error");
                throw new GenderNotValidException("GenderNotValidException");
            } else if (femaleRadio.selectedProperty().getValue() == true) {
                gender = "Q";
            } else if (maleRadio.selectedProperty().getValue() == true) {
                gender = "K";
            } else {
                throw new GenderNotValidException("GenderNotValidException");
            }
        } catch (Exception ex) {
            errorNotFound = false;
            successMessage.setText("GenderNotValidException");
        }

        try {
            student.setName(firstnameField.getText());
            student.setSurname(surnameField.getText());
            student.setDOB(String.valueOf(dobField.getValue()));
            student.setPhone(phoneField.getText());
            student.setSeriaNum(seriaNumField.getText());
            student.setEmail(emailField.getText());
            student.setGender(gender);
        } catch (SeriaNumException ex) {
            errorNotFound = false;
            seriaNumField.clear();
            seriaNumField.setPromptText(ex.getMessage());
        } catch (NameException ex) {
            errorNotFound = false;
            firstnameField.clear();
            firstnameField.setPromptText(ex.getMessage());
        } catch (SurnameException ex) {
            errorNotFound = false;
            surnameField.clear();
            surnameField.setPromptText(ex.getMessage());
        } catch (PhoneException ex) {
            phoneField.clear();
            phoneField.setPromptText(ex.getMessage());
        } catch (EmailException ex) {
            errorNotFound = false;
            emailField.clear();
            emailField.setPromptText(ex.getMessage());
        } catch (DOBException ex) {
            errorNotFound = false;
            dobField.setPromptText(ex.getMessage());
        }

        if (errorNotFound) {
            if (operation.equals("add student")) {
                studentService.addStudent(student);
                successMessage.setText("Tələbə uğurla əlavə edildi");
            } else if (operation.equals("update student")) {
                student.setId(selectedStudent.getId());
                studentService.updateStudentById(student);
                successMessage.setText("Dəyişiklik uğurla yerinə yetirildi");
            }
            seriaNumField.clear();
            firstnameField.clear();
            surnameField.clear();
            dobField.getEditor().clear();
            emailField.clear();
            phoneField.clear();
        }

    }

    private void printSelectedStudent() {
        if (selectedStudent != null) {
            firstnameField.setText(selectedStudent.getName());
            surnameField.setText(selectedStudent.getSurname());
            seriaNumField.setText(selectedStudent.getSeriaNum());
            emailField.setText(selectedStudent.getEmail());
            dobField.setValue(LocalDate.parse(selectedStudent.getDOB()));
            phoneField.setText(selectedStudent.getPhone());

        }
    }

}







