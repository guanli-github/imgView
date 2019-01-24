package textReader;

import data.Status;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.stage.FileChooser;
import utils.SceneManager;
import utils.TextUtil;

import java.io.File;
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
    }
    @FXML
    private void scrollTo(int index){

    }
    @FXML
    private void resizeText(){
        int base = 760;
        text.setPrefWidth(base);

        //text.setPrefHeight();
    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        resizeText();
        fullContent = TextUtil.readTxt(Status.getCurrentFile());
        text.setText(fullContent);

    }
}
