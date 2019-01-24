package textReader;

import com.sun.javafx.robot.impl.FXRobotHelper;
import data.Status;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import utils.SceneManager;
import utils.TextUtil;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class TextController implements Initializable {
    @FXML
    private GridPane root = new GridPane();
    @FXML
    private TextArea text = new TextArea();
    @FXML
    private Label pageNum = new Label();
    @FXML
    private Button changeOrient = new Button();
    @FXML
    private Button returnDir = new Button();
    @FXML
    private Button bgImg = new Button();
    private final static FileChooser fileChooser = new FileChooser();

    private static String fullContent;
    @FXML
    private void returnDir(){
        SceneManager.toExplorer();
    }
    @FXML
    private void setBgImg(){
        File f = fileChooser.showOpenDialog(null);
        Image image = new Image("file:" + f.getAbsolutePath());
        BackgroundSize backgroundSize = new BackgroundSize(100, 100, true, true, true, false);
        BackgroundImage backgroundImage = new BackgroundImage(image, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, backgroundSize);
        Background background = new Background(backgroundImage);
        root.setBackground(background);
        text.setPrefWidth(image.getWidth());
        text.setPrefHeight(image.getHeight());
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
         fullContent = TextUtil.readTxt(Status.getCurrentFile());
        text.setText(fullContent);
    }
}
