package controller;

import data.Setting;
import data.dto.FileDto;
import data.dto.TextSearchDto;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
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
    ListView<String> text = new ListView<>();
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
    private void changeMode(MouseEvent click) {
        if(click.getClickCount() != 2)
            return;
        if(TextSearchDto.highlightMode){
            TextSearchDto.highlightMode = false;
            refreshTextListView();
        }else{
            showMenu();
        }
    }
    private void showMenu() {
        ModalUtil.show(text,menu);
    }
    @FXML
    private void hideMenu() {
        ModalUtil.hide(text,menu);
    }
    @FXML
    private void returnDir() {
        SceneManager.toExplorer();
    }

    @FXML
    private void setBgImg() {
        File f = fileChooser.showOpenDialog(null);
        TextSearchDto.bgImg = new Image("file:" + f.getAbsolutePath());
        BackgroundSize backgroundSize = new BackgroundSize(100, 100, true, true, true, false);
        BackgroundImage backgroundImage = new BackgroundImage(TextSearchDto.bgImg, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, backgroundSize);
        Background background = new Background(backgroundImage);
        root.setBackground(background);
        hideMenu();
    }

    @FXML
    private void toSearch() {
        SceneManager.toSearch();
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
                Platform.runLater(this::setFullScreen
                );
            });
        }
        //初始化文档
        // OR后面是文件改变的情况
        if(null == TextSearchDto.document
        || !filename.equals(FileDto.getCurrentFile().getName())){
            TextSearchDto.document = TextUtil.read(
                    FileDto.getCurrentFile());
            filename = FileDto.getCurrentFile().getName();
        }
        refreshTextListView();

        if (Setting.isFullScreen) {
            setFullScreen();
        }
        Platform.runLater(() ->
        {
            //backgroud image
            if (null != TextSearchDto.bgImg) {
                BackgroundSize backgroundSize = new BackgroundSize(100, 100, true, true, true, false);
                BackgroundImage backgroundImage = new BackgroundImage(TextSearchDto.bgImg, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, backgroundSize);
                Background background = new Background(backgroundImage);
                root.setBackground(background);
            }

        });
        //choose bg image
        fileChooser.setInitialDirectory(new File(Setting.bgImgDir));
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter(
                "image filter", "*.jpg", "*.jpeg", "*.gif", "*.png", "*.bmp"
        ));
    }

    private void refreshTextListView(){
        text.setItems(TextSearchDto.document);
        text.setCellFactory((e)-> new SearchHighlightedTextCell());
        //read process
        if(0 != TextSearchDto.searchIndex){
            text.scrollTo(TextSearchDto.searchIndex);
        }
    }
}

//highlight searched word
class SearchHighlightedTextCell extends ListCell<String> {

    @Override
    protected void updateItem(String text, boolean empty) {
        super.updateItem(text, empty);

        setWrapText(true);
        setPrefWidth(SceneManager.getStage().getWidth());
        setText(text == null ? "" : text);

        if(TextSearchDto.highlightMode){
            //if this is the choosed
            if(text.equals(TextSearchDto.searchResultList.get(TextSearchDto.searchIndex).line)){
                //setHighLight(this,text,TextSearchDto.searchWord);
                this.setTextFill(Setting.highLightPaint);
            }
        }

    }
//
//    private void setHighLight(Pane node,String text, String hlWord){
//
//        String[] split = text.replace(hlWord,"\n"+hlWord+"\n")split("\n");
//        List<Text> list = new ArrayList<>();
//        for(String s:split){
//            if(null == s || s.isEmpty()){
//                continue;
//            }
//            Text t = new Text(s);
//            if(s.equals(hlWord)){
//                t.setFill(Setting.highLightPaint);
//            }
//            list.add(t);
//        }
//        node.getChildren()
//    }

}