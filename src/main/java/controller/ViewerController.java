package controller;

import data.BookMark;
import data.Const;
import data.Setting;
import data.dto.FileDto;
import extractor.FileParser;
import javafx.application.Platform;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.NodeOrientation;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.TouchEvent;
import javafx.scene.layout.VBox;
import utils.FileTypeHandler;
import utils.ModalUtil;
import utils.SceneManager;

import java.awt.*;
import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

public class ViewerController implements Initializable {
    @FXML
    private ImageView imgView = new ImageView();
    @FXML
    private Label title = new Label();
    @FXML
    private Label pageNum = new Label();
    @FXML
    private Slider slider = new Slider();
    @FXML
    private VBox menu = new VBox();
    private static Alert alert = new Alert(Alert.AlertType.INFORMATION,"");
    static {
        alert.setTitle("");
        alert.setHeaderText("");
        alert.initOwner(SceneManager.getStage());
    }
    private long touchPressTime;
    private int touchId;
//手势事件
    @FXML
    private void slide() {
        int page = (int) slider.getValue();
        pageNum.setText(page+"/"+FileParser.totalPage);
        jumpToPage(page);
    }

    @FXML
    private void keyPress(KeyEvent keyEvent) {
        if (keyEvent.getCode().equals(KeyCode.LEFT)) {
            doLeft();
        } else if (keyEvent.getCode().equals(KeyCode.RIGHT)) {
            doRight();
        } else if (keyEvent.getCode().equals(KeyCode.SHIFT)) {
            //Shift键显示隐藏操作层
            if(menu.isVisible() || slider.isVisible()){
                ModalUtil.hide(imgView, menu, slider);
            }else{
                showModal();
            }
        } else if (keyEvent.getCode().equals(KeyCode.ESCAPE)) {
            returnDir();
        }
        keyEvent.consume();
    }

    @FXML
    private void touchPress(TouchEvent touchEvent) {
        touchId =touchEvent.getTouchPoint().getId();
        touchPressTime =System.currentTimeMillis();
        //touchEvent.consume();
    }

    @FXML
    private void touchRelease(TouchEvent touchEvent) {
        if(touchId != touchEvent.getTouchPoint().getId()){
            touchEvent.consume();
            return;
        }
        //长按唤出操作层
        if (System.currentTimeMillis() - touchPressTime >=Setting.longTouchInterval){
            touchEvent.consume();
            showModal();
            return;
        }
        //翻页
        double x = touchEvent.getTouchPoint().getX();
        double width = Toolkit.getDefaultToolkit().getScreenSize().width;
        if (x >= width * 0.5) {
            doRight();
        } else{
            doLeft();
        }
        touchEvent.consume();
    }

//菜单按钮事件

    //更改翻页方式
    @FXML
    private void changeOrient(Event event) {
        if (Setting.orient == Const.L2R) {
            Setting.orient = Const.R2L;
        } else {
            Setting.orient = Const.L2R;
        }
        event.consume();
    }
    //打开下一个文件
    @FXML
    private void preFile(Event event) {
        BookMark.saveCurrent();

        ModalUtil.hide(imgView, menu, slider);
        File preFile = FileDto.preFile();
        if(null != preFile){
            openFile(preFile);
        }else{
            alert.setContentText("已经是第一本了");
            alert.show();
        }
        event.consume();
    }
    //打开下一个文件
    @FXML
    private void nextFile(Event event) {
        BookMark.saveCurrent();

        ModalUtil.hide(imgView, menu, slider);
        File nextFile = FileDto.nextFile();
        if(null != nextFile){
            openFile(nextFile);
        }else{
            alert.setContentText("已经是最后一本了");
            alert.show();
        }
        event.consume();
    }
    //返回目录
    @FXML
    private void returnDir() {
        BookMark.saveCurrent();
        //关闭资源
        FileParser.closeFile();
        SceneManager.toExplorer();
    }

    private void nextPage() {
        jumpToPage(FileParser.currentPage.getValue() + 1);
    }

    private void prePage() {
        jumpToPage(FileParser.currentPage.getValue() - 1);
    }

    private void jumpToPage(int page) {
        Image image = FileParser.getImage(page);
        if (null == image) {
            showModal();
            return;
        }
        imgView.setImage(image);
    }


    private void openFile(final File file) {
        //非相关类型，不做处理
        if(!FileTypeHandler.docFilter.accept(file)){
            alert.setContentText("不支持的文件类型");
            alert.show();
            return;
        }
        FileDto.onClickFile(file);
        FileParser.closeFile();
        FileParser.refreash(file);
        jumpToPage(FileParser.currentPage.getValue());
        String fileTitle = file.getName().substring(0,
                file.getName().lastIndexOf("."));
        title.setText(fileTitle);
    }

    //显示菜单和进度条
    private void showModal() {
        pageNum.setText(FileParser.currentPage.getValue()+"/"+FileParser.totalPage);
        if (Setting.orient == Const.R2L) {
            slider.setNodeOrientation(NodeOrientation.RIGHT_TO_LEFT);
        }
        slider.setMax(FileParser.totalPage);
        slider.setValue(FileParser.currentPage.getValue());

        //进度条固定在下方
        double y = Toolkit.getDefaultToolkit().getScreenSize().height;
        slider.setTranslateY(y * 0.45);//在1/3和1/2之间
        ModalUtil.show(imgView,menu,slider);
    }

    @FXML
    private void hideModal(Event event) {
        ModalUtil.hide(imgView, menu, slider);
        event.consume();
    }

    private void setFullScreen() {
        double width = SceneManager.getStage().getWidth();
        double height = SceneManager.getStage().getHeight();

        if (width > height) {
            imgView.setFitHeight(height);
            imgView.setFitWidth(0);
        } else {
            imgView.setFitWidth(width);
            imgView.setFitHeight(0);
        }
    }
    private void doLeft() {
        if (Setting.orient == Const.R2L) {
            nextPage();
        } else {
            prePage();
        }
    }
    private void doRight() {
        if (Setting.orient == Const.L2R) {
            nextPage();
        } else {
            prePage();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        if (Setting.isFullScreen) {
            Platform.runLater(() -> setFullScreen());
            SceneManager.getStage().widthProperty().addListener((observable) -> {//屏幕旋转
                Platform.runLater(() -> {
                            setFullScreen();
                        }
                );
            });
        }

        openFile(FileDto.getCurrentFile());
    }

}

