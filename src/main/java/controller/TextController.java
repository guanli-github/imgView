package controller;

import data.ChapteredText;
import data.Setting;
import data.dto.FileDto;
import data.dto.TextSearchDto;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
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

    private final static FileChooser fileChooser = new FileChooser();

    @FXML
    private void returnDir() {
        SceneManager.toExplorer();
        return;
    }

    @FXML
    private void setBgImg() {
        File f = fileChooser.showOpenDialog(null);
        TextSearchDto.bgImg = new Image("file:" + f.getAbsolutePath());
        BackgroundSize backgroundSize = new BackgroundSize(100, 100, true, true, true, false);
        BackgroundImage backgroundImage = new BackgroundImage(TextSearchDto.bgImg, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, backgroundSize);
        Background background = new Background(backgroundImage);
        root.setBackground(background);

        //text.setPrefHeight(backgroundSize.getHeight());
    }

    @FXML
    private void toSearch() {
        //TextSearchDto.readIndex = text.getScrollTop();//保存阅读进度
        SceneManager.toSearch();
        return;
    }
    @FXML
    private void nextChapter() {
        String chapter = TextSearchDto.nextChapter();
        if(!"".equals(chapter)){
            text.setText(chapter);
        }
        return;
    }
    @FXML
    private void preChapter() {
        String chapter = TextSearchDto.preChapter();
        if(!"".equals(chapter)){
            text.setText(chapter);
        }
        return;
    }

    @FXML
    private void scrollTo(double location) {
        Node text1 = text.lookup(".content");
        Node scrollPane = text.lookup(".scroll-pane");

        if (text1 == null || scrollPane == null) {
            return;
        }

        double textHeight = text1.getLayoutBounds().getHeight();
        double textAreaHeight = ((ScrollPane) scrollPane).getViewportBounds().getHeight();

        text.setScrollTop(location * (textHeight-textAreaHeight)  * 10);
    }

    private void resize() {

        double width = SceneManager.getStage().getWidth();
        double height = SceneManager.getStage().getHeight();

        text.setPrefHeight(height);
        text.setPrefWidth(width);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        SceneManager.getStage().widthProperty().addListener((observable) -> {//屏幕旋转
            Platform.runLater(() -> {
                        resize();
                    }
            );
        });
        TextSearchDto.document = new ChapteredText(
                TextUtil.readTxt(FileDto.getCurrentFile()));
        text.setText(TextSearchDto.document.getChapter(1));
        text.setFont(Font.font(16));
        Platform.runLater(() ->
        {
            resize();
            scrollTo(TextSearchDto.presentScroll);
            //背景图片
            if (null != TextSearchDto.bgImg){
                BackgroundSize backgroundSize = new BackgroundSize(100, 100, true, true, true, false);
                BackgroundImage backgroundImage = new BackgroundImage(TextSearchDto.bgImg, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, backgroundSize);
                Background background = new Background(backgroundImage);
                root.setBackground(background);
            }
        });
//        //进度显示
//        DoubleProperty percentScrolled = new SimpleDoubleProperty();
//        percentScrolled.bind(Bindings.createDoubleBinding(() -> {
//
//            Node text1 = text.lookup(".content");
//            Node scrollPane = text.lookup(".scroll-pane");
//
//            if (text1 == null || scrollPane == null) {
//                return 0.0 ;
//            }
//
//            double textHeight = text1.getLayoutBounds().getHeight();
//            double textAreaHeight = ((ScrollPane) scrollPane).getViewportBounds().getHeight();
//
//            if (textHeight <= textAreaHeight) {
//                return 100.0 ;
//            }
//
//            return 100.0 * text.getScrollTop() / (textHeight - textAreaHeight) ;
//
//
//        }, text.scrollTopProperty()));
//        pageNum.textProperty().bind(percentScrolled.asString("%.2f"));
        //背景图片选择
        fileChooser.setInitialDirectory(Setting.bgImgDir);
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter(
                "image filter","*.jpg","*.jpeg","*.gif","*.png","*.bmp"
        ));
    }

}
