package controller;

import data.Setting;
import data.dto.FileDto;
import data.dto.TextSearchDto;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.stage.FileChooser;
import utils.ModalUtil;
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
    private VBox menu = new VBox();

    private static String filename = "";

    private final static FileChooser fileChooser = new FileChooser();

    @FXML
    private void keyPress(KeyEvent keyEvent) {
       if (keyEvent.getCode().equals(KeyCode.SHIFT)) {
            //Shift键显示隐藏操作层
            if(menu.isVisible()){
                ModalUtil.hide(text,menu);
            }else{
                ModalUtil.show(text,menu);
            }
        } else if (keyEvent.getCode().equals(KeyCode.ESCAPE)) {
            returnDir();
        }
        keyEvent.consume();
    }
    @FXML
    private void showMenu(MouseEvent click) {
        if(click.getClickCount() != 2)
            return;
        ModalUtil.show(text,menu);
    }
    @FXML
    private void hideMenu(MouseEvent click) {
        if(click.getClickCount() != 2)
            return;
        ModalUtil.hide(text,menu);
    }
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
        ModalUtil.hide(text,menu);
    }

    @FXML
    private void toSearch() {
        SceneManager.toSearch();
        return;
    }
    @FXML
    private void toChapter() {
        SceneManager.toChapter();
        return;
    }

    @FXML
    private void nextChapter() {
        String chapter = TextSearchDto.nextChapter();
        if (!"".equals(chapter)) {
            text.setText(chapter);
        }
        return;
    }

    @FXML
    private void preChapter() {
        String chapter = TextSearchDto.preChapter();
        if (!"".equals(chapter)) {
            text.setText(chapter);
        }
        return;
    }

    @FXML
    private void scrollTo(double location) {
        ScrollPane scrollPane = (ScrollPane)text.lookup(".scroll-pane");

        scrollPane.setVvalue(location);
    }

    private void setFullScreen() {

        double width = SceneManager.getStage().getWidth();
        double height = SceneManager.getStage().getHeight();

        text.setPrefHeight(height);
        text.setPrefWidth(width);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        if (Setting.isFullScreen) {
            SceneManager.getStage().widthProperty().addListener((observable) -> {//屏幕旋转
                Platform.runLater(() -> {
                            setFullScreen();
                        }
                );
            });
        }
        //初始化文档
        // OR后面是文件改变的情况
        if(null == TextSearchDto.document
        || !filename.equals(FileDto.getCurrentFile().getName())){
            TextSearchDto.document = TextUtil.splitChapter(
                    FileDto.getCurrentFile());
            filename = FileDto.getCurrentFile().getName();
        }

        text.setText(TextSearchDto.getPresentChapterStr());
        text.setFont(Font.font(16));
        Platform.runLater(() ->
        {
            if (Setting.isFullScreen) {
                setFullScreen();
            }
            scrollTo(TextSearchDto.presentScroll);
            //背景图片
            if (null != TextSearchDto.bgImg) {
                BackgroundSize backgroundSize = new BackgroundSize(100, 100, true, true, true, false);
                BackgroundImage backgroundImage = new BackgroundImage(TextSearchDto.bgImg, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, backgroundSize);
                Background background = new Background(backgroundImage);
                root.setBackground(background);
            }
        });
        //背景图片选择
        fileChooser.setInitialDirectory(new File(Setting.bgImgDir));
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter(
                "image filter", "*.jpg", "*.jpeg", "*.gif", "*.png", "*.bmp"
        ));
    }

}
