package controller;

import data.dto.Status;
import data.dto.TextSearchDto;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
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
    @FXML
    private Button toSearch = new Button();

    private final static FileChooser fileChooser = new FileChooser();

    @FXML
    private void returnDir(){
        SceneManager.toExplorer();
        return;
    }
    @FXML
    private void setBgImg(){
        File f = fileChooser.showOpenDialog(null);
        Image image = new Image("file:" + f.getAbsolutePath());
        BackgroundSize backgroundSize = new BackgroundSize(100, 100, true, true, true, false);
        BackgroundImage backgroundImage = new BackgroundImage(image, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, backgroundSize);
        Background background = new Background(backgroundImage);
        root.setBackground(background);

        //text.setPrefHeight(backgroundSize.getHeight());
    }
    @FXML
    private void toSearch(MouseEvent mouseEvent) {
        TextSearchDto.readIndex = text.getScrollTop();//保存阅读进度
        SceneManager.toSearch();
        return;
    }
    //https://stackoverflow.com/questions/19121486/how-to-scroll-javafx-textarea-after-settext?r=SearchResults
    //https://stackoverflow.com/questions/14206692/javafx-textarea-hiding-scroll-bars?r=SearchResults
    @FXML
    private void scrollTo(double location){
        text.setScrollTop(location * 100);
    }
    @FXML
    private void resizeText(){
        int base = 760;
        text.setPrefWidth(base);
        text.setPrefHeight(130);
    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        resizeText();
        TextSearchDto.fullContent = TextUtil.readTxt(Status.getCurrentFile());
        text.setText(TextSearchDto.fullContent);
        text.setFont(Font.font (16));
        scrollTo(TextSearchDto.hitLocation);
    }
}
