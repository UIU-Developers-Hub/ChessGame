package org.example.loginpage;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import org.example.loginpage.DBMS_connection.MyJDBC;

import java.io.IOException;


public class LogRegController {
    @FXML

    private Stage stage;
    private Scene scene;
    private Parent root;
    @FXML
    private TextField UserID;
    @FXML
    private PasswordField loginPass;
    @FXML
    private PasswordField Re_RegisterPass;

    @FXML
    private PasswordField RegisterPass;

    @FXML
    private TextField Register_user;

    @FXML
    void loginPage(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("login-page.fxml"));
        stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        // stage.getIcons().add(new Image("src/main/resources/image/login.jpg"));
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    void signupPage(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("registerpage.fxml"));
        stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        // stage.getIcons().add(new Image("src/main/resources/image/login.jpg"));
        stage.setScene(scene);
        stage.show();
    }
    @FXML
    void createAccountButton(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("registerpage.fxml"));
        stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        // stage.getIcons().add(new Image("src/main/resources/image/login.jpg"));
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    void gotoLoginpage(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("login-page.fxml"));
        stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        // stage.getIcons().add(new Image("src/main/resources/image/login.jpg"));
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    void signupButton(ActionEvent event) throws IOException {
        String username = Register_user.getText();
        String password = RegisterPass.getText();
        String re_password = Re_RegisterPass.getText();
//        System.out.println("PasswordField Content: " + password);
//        System.out.println("rePasswordField Content: " + re_password);
//        System.out.println("user Content: " + username);
        if(RegisterValid_Input_check(username,password,re_password)){
            if(MyJDBC.register(username,password)){
                // System.out.println("success");
                root = FXMLLoader.load(getClass().getResource("login-page.fxml"));
                stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
                scene = new Scene(root);
                //  stage.getIcons().add(new Image("src/main/resources/image/login.jpg"));
                stage.setScene(scene);
                stage.show();

            }
            else {
                System.out.println("user already here");
            }
        }
        else {
            System.out.println("error");
        }
    }
    @FXML
    void loginButton(ActionEvent event) throws IOException {

        String username = UserID.getText();
        String password = loginPass.getText();

        if(MyJDBC.login(username,password)){
            //login success
            //pulok add your code here
            System.out.println("login success");
            root = FXMLLoader.load(getClass().getResource("chessStartPage.fxml"));
            stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();

        }
        else {
            System.out.println("login failed");
        }
    }
    private boolean RegisterValid_Input_check(String username, String password, String RE_pass){
        if(username.length()==0 || password.length()==0 || RE_pass.length()==0)return false;
        if(username.length()<5)return false;
        if(!password.equals(RE_pass))return false;
        return true;
    }
}