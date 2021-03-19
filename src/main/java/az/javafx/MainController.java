package az.javafx;

import az.javafx.util.ControllerUtil;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

import java.net.URL;
import java.util.ResourceBundle;

public class MainController {

    @FXML
    protected Button logoutBtn;

    @FXML
    protected Button studentOppBtn;

    @FXML
    protected Button teacherOppBtn;

    @FXML
    protected Button subjectOppBtn;

    @FXML
    protected Button groupOppBtn;

    @FXML
    protected Button homeBtn;

    @FXML
    void initialize() {
        studentOppBtn.setOnAction(event ->{
            studentOppBtn.getScene().getWindow().hide();
            ControllerUtil.openNewScene(getClass().getResource("studentPage.fxml"));
        });
        logoutBtn.setOnAction(event -> {
            logoutBtn.getScene().getWindow().hide();
            ControllerUtil.openNewScene(getClass().getResource("login.fxml"));
        });
        teacherOppBtn.setOnAction(event ->{
            teacherOppBtn.getScene().getWindow().hide();
            ControllerUtil.openNewScene(getClass().getResource("teacherPage.fxml"));
        });

    }
}