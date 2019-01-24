package textReader;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.web.HTMLEditor;
import javafx.stage.Stage;
import utils.TextUtil;

import java.io.File;

public class MainText extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/text.fxml"));
        Scene scene = new Scene(root);
        scene.getStylesheets().add
                (getClass().getResource("/css/text.css").toExternalForm());
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}