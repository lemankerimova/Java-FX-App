package az.javafx;

import az.javafx.dao.TeacherDao;
import az.javafx.dao.impl.TeacherDAOImpl;
import az.javafx.exceptions.*;
import az.javafx.model.Teacher;
import az.javafx.service.TeacherService;
import az.javafx.service.impl.TeacherServiceImpl;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class TeacherFormController extends GeneralTeacherController {
    TeacherDao teacherDao = new TeacherDAOImpl();
    TeacherService teacherService = new TeacherServiceImpl(teacherDao);

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button saveBtn;

    @FXML
    private Button ignoreBtn;

    @FXML
    private TextField firstnameField;

    @FXML
    private TextField phoneField;

    @FXML
    private TextField surnameField;

    @FXML
    private TextField emailField;

    @FXML
    private TextField seriaNumField;

    @FXML
    private DatePicker dobField;

    @FXML
    private Label successMessage;

    @FXML
    private RadioButton femaleRadio;

    @FXML
    private RadioButton maleRadio;

    @FXML
    void initialize() {
        saveBtn.setOnAction(event -> {
            if (operation.equals("add teacher")) {
                setTeacher(operation);
            } else if (operation.equals("update teacher")) {
                setTeacher(operation);
            }
        });
        printSelectedTeacher();
    }

    private void setTeacher(String operation) {
        Teacher teacher = new Teacher();
        boolean errorNotFound = true;
        String gender = "";
        try {
            if (femaleRadio.selectedProperty().getValue() && maleRadio.selectedProperty().getValue()){
                System.out.println("error");
                throw new GenderNotValidException("GenderNotValidException");
            } else if (femaleRadio.selectedProperty().getValue() == true) {
                gender = "Q";
            } else if (maleRadio.selectedProperty().getValue() == true) {
                gender = "K";
            } else {
                throw new GenderNotValidException("GenderNotValidException");
            }
        }catch (Exception ex) {
            errorNotFound = false;
            successMessage.setText("GenderNotValidException");
        }
        try {
            teacher.setName(firstnameField.getText());
            teacher.setSurname(surnameField.getText());
            teacher.setDOB(String.valueOf(dobField.getValue()));
            teacher.setPhone(phoneField.getText());
            teacher.setEmail(emailField.getText());
            teacher.setGender(gender);
        }catch (SeriaNumException ex){
            errorNotFound = false;
            seriaNumField.clear();
            seriaNumField.setPromptText(ex.getMessage());
        }catch (NameException ex){
            errorNotFound = false;
            firstnameField.clear();
            firstnameField.setPromptText(ex.getMessage());
        }catch (SurnameException ex){
            errorNotFound = false;
            surnameField.clear();
            surnameField.setPromptText(ex.getMessage());
        }catch (PhoneException ex){
            errorNotFound = false;
            phoneField.clear();
            phoneField.setPromptText(ex.getMessage());
        }catch (EmailException ex){
            errorNotFound = false;
            emailField.clear();
            emailField.setPromptText(ex.getMessage());
        }catch (DOBException ex){
            errorNotFound = false;
            dobField.setPromptText(ex.getMessage());
        }
        if (errorNotFound) {
            if (operation.equals("add teacher")) {
                teacherService.addTeacher(teacher);
                successMessage.setText("Müəllim uğurla əlavə edildi");
            } else if (operation.equals("update teacher")) {
                teacher.setId(selectedTeacher.getId());
                teacherService.updateTeacherById(teacher);
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

    private void printSelectedTeacher() {
        if (selectedTeacher != null) {
            firstnameField.setText(selectedTeacher.getName());
            surnameField.setText(selectedTeacher.getSurname());
            seriaNumField.setText(selectedTeacher.getSeriaNum());
            emailField.setText(selectedTeacher.getEmail());
            dobField.setValue(LocalDate.parse(selectedTeacher.getDOB()));
            phoneField.setText(selectedTeacher.getPhone());
        }

    }
}

