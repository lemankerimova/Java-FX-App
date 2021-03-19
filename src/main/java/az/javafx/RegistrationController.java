package az.javafx;
import java.net.URL;
import java.util.ResourceBundle;

import az.javafx.config.DBConfig;
import az.javafx.dao.UserDao;
import az.javafx.dao.impl.UserDaoImpl;
import az.javafx.model.User;
import az.javafx.service.RegistrationService;
import az.javafx.service.impl.RegistrationServiceImpl;
import az.javafx.util.ControllerUtil;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;

public class RegistrationController {
    UserDao userDao = new UserDaoImpl();
    RegistrationService registrationService = new RegistrationServiceImpl(userDao);

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField usernameField;

    @FXML
    private TextField firstnameField;

    @FXML
    private Button registratioBtn;

    @FXML
    private TextField passwordField;

    @FXML
    private TextField surnameField;

    @FXML
    private CheckBox genderField;

    @FXML
    void initialize() {
        registratioBtn.setOnAction(event ->  {
            User user = new User();
            user.setFirstname(firstnameField.getText());
            user.setUsername(usernameField.getText());
            user.setPassword(passwordField.getText());
            user.setSurname(surnameField.getText());
           if (registrationService.addUser(user) == true) {
               registratioBtn.getScene().getWindow().hide();
               ControllerUtil.openNewScene(getClass().getResource("login.fxml"));
           }else {
               System.out.println("error");
           }
        });
    }
}

