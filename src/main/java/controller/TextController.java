package controller;

import data.Const;
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
    @FXML
    private void scrollTo(int index){
        int topIndex = index-Const.textBias;
        double top = (topIndex / TextSearchDto.fullContent.length() * text.getHeight());
        //int pixel = text.getFont().getSize().
        if(top<=1){
            text.setScrollTop(1);
        }
        text.setScrollTop(top);
    }
    @FXML
    private void resizeText(){
        int base = 760;
        text.setPrefWidth(base);
        text.setPrefHeight(1300);
    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        resizeText();
        TextSearchDto.fullContent = TextUtil.readTxt(Status.getCurrentFile());
        text.setText(TextSearchDto.fullContent);
        text.setFont(Font.font (16));
    }
}
