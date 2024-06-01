package org.example.loginpage.gameLoader;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.io.IOException;

public class ChessPageController {
    private Stage stage;
    @FXML
    void Start_button(ActionEvent event)  {
        GamePanel gamePanelFX = new GamePanel();
        gamePanelFX.launchGame();

        StackPane root = new StackPane();
        root.getChildren().add(gamePanelFX.getCanvas());

        Scene scene = new Scene(root, GamePanel.WIDTH, GamePanel.HEIGHT);
        stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        stage.setResizable(false);
        stage.setTitle("Chess Game");
        stage.setScene(scene);
        stage.show();
    }

}