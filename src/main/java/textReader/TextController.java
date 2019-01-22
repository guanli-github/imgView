package textReader;

import com.sun.javafx.robot.impl.FXRobotHelper;
import data.Const;
import data.Status;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
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
    private int page = 1;
    private String fullContent = "";
    private int total = 1;
    private final static FileChooser fileChooser = new FileChooser();

    public void doLeft(SwipeEvent s){
        if(Status.orient == Const.L2R){
            nextPage();
        }else{
            prePage();
        }
    }
    public void doRight(SwipeEvent s){
        if(Status.orient == Const.R2L){
            nextPage();
        }else{
            prePage();
        }
    }

    private void nextPage(){
        page+=1;
        jumpToPage(page);
    }
    private void prePage(){
        page-=1;
        jumpToPage(page);
    }
    @FXML
    private void jumpToPage(int page){
        if(page<1 || page>total){
            return;
        }
        int start = page * Const.wordPerPage+1;
        int end = start + Const.wordPerPage+1;
        if(end>fullContent.length()){
            end = fullContent.length();
        }
        String content = fullContent.substring(start,end);
        text.setText(content);
    }
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

        text.setBackground(background);
    }
    @FXML
    private void doPageClick(MouseEvent mouseEvent) {
        double x = mouseEvent.getX();
        double middle = Toolkit.getDefaultToolkit().getScreenSize().width / 2;
        if((x >= middle && Status.orient== Const.L2R)
                || (x<middle && Status.orient== Const.R2L)){
            nextPage();
        }else{
            prePage();
        }
        mouseEvent.consume();
    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        fullContent = TextUtil.readTxt(Status.getCurrentFile());
        total = (int)Math.ceil((double) fullContent.length() / Const.wordPerPage);
        jumpToPage(page);
    }
}
