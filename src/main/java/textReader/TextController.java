package textReader;

import com.sun.javafx.robot.impl.FXRobotHelper;
import data.Const;
import data.Status;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Slider;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.SwipeEvent;
import javafx.scene.layout.*;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import utils.TextUtil;
import javafx.scene.image.Image;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class TextController implements Initializable {
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
    private int page = 1;
    private String fullContent = "";
    private int total = 1;
    private final static FileChooser fileChooser = new FileChooser();

    @FXML
    private void returnDir(){
        ObservableList<Stage> stage = FXRobotHelper.getStages();
        Scene scene = null;
        try {
            scene = new Scene(FXMLLoader.load(getClass().getResource("/FileExplore.fxml")));
            stage.get(0).setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @FXML
    private void setBgImg(){
        File f = fileChooser.showOpenDialog(null);
        Image image = new Image("file:" + f.getAbsolutePath());
        BackgroundSize backgroundSize = new BackgroundSize(100, 100, true, true, true, false);
        BackgroundImage backgroundImage = new BackgroundImage(image, BackgroundRepeat.REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, backgroundSize);
        Background background = new Background(backgroundImage);

        //text.setBackground(background);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        fullContent = TextUtil.readTxt(Status.getCurrentFile());
    }
}
