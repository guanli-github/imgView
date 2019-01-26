package controller;

import data.BookMark;
import data.Const;
import data.Setting;
import data.dto.Status;
import extractor.FileParser;
import javafx.application.Platform;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.SwipeEvent;
import utils.SceneManager;

import java.awt.*;
import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

public class ViewerController implements Initializable {
    @FXML
    private ImageView imgView = new ImageView();
    @FXML
    private Label pageNum = new Label();
    @FXML
    private Button changeOrient = new Button();
    @FXML
    private Button returnDir = new Button();
    @FXML
    private Slider jumpSlider = new Slider();

    private void openFile(final File file) {
        Status.onClickFile(file);
        FileParser.refreash(file);
        jumpToPage(FileParser.currentPage);
    }

    @FXML
    private void doLeft(SwipeEvent s) {
        if (Setting.orient == Const.L2R) {
            nextPage();
        } else {
            prePage();
        }
    }

    @FXML
    private void doRight(SwipeEvent s) {
        if (Setting.orient == Const.R2L) {
            nextPage();
        } else {
            prePage();
        }
    }

    @FXML
    private void doPageClick(MouseEvent mouseEvent) {
        double x = mouseEvent.getX();
        double middle = Toolkit.getDefaultToolkit().getScreenSize().width / 2;
        if ((x >= middle && Setting.orient == Const.L2R)
                || (x < middle && Setting.orient == Const.R2L)) {
            nextPage();
        } else {
            prePage();
        }
        mouseEvent.consume();
    }

    //更改翻页方式
    @FXML
    private void changeOrient(MouseEvent mouseEvent) {
        if (Setting.orient == Const.L2R) {
            Setting.orient = Const.R2L;
        } else {
            Setting.orient = Const.L2R;
        }
        mouseEvent.consume();
    }

    //返回目录
    @FXML
    private void returnDir(MouseEvent mouseEvent) {
        BookMark.saveCurrent();
        SceneManager.toExplorer();
    }

    private void nextPage() {
        FileParser.currentPage += 1;
        jumpToPage(FileParser.currentPage);
    }

    private void prePage() {
        FileParser.currentPage -= 1;
        jumpToPage(FileParser.currentPage);
    }

    private void jumpToPage(int page) {
        if (0 >= FileParser.totalPage) return;
        if (page > FileParser.totalPage) return;
        if (page < 1) return;

//        if (page > FileParser.totalPage) { //打开文件夹中下一文件
//            if(Status.currentFileIndex < Status.currentFileList.length){
//                Status.currentFileIndex += 1;
//                openFile(Status.currentFileList[Status.currentFileIndex]);
//            }else{
//                return;
//            }
//        }
//        if (page < 1) //打开文件夹中上一文件
//            if(Status.currentFileIndex > 0){
//                Status.currentFileIndex -= 1;
//                openFile(Status.currentFileList[Status.currentFileIndex]);
//            }else{
//                return;
//            }

        imgView.setImage(
                FileParser.getImage(page)
        );
        FileParser.currentPage = page;
        pageNum.setText(FileParser.currentPage + "/" + FileParser.totalPage);
    }

    @FXML
    private void resize() {
        double width = SceneManager.getStage().getWidth();
        double height = SceneManager.getStage().getHeight();

        //System.out.println("width:" + width + "; height:" + height);
        if (width > height) {
            imgView.setFitHeight(height);
            imgView.setFitWidth(0);
        } else {
            imgView.setFitWidth(width);
            imgView.setFitHeight(0);

        }
        //System.out.println("imagewidth:" + imgView.getFitWidth() + "; imageheight:" + imgView.getFitHeight());

    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        resize();
        SceneManager.getStage().widthProperty().addListener((observable) -> {//屏幕旋转
            Platform.runLater(() -> {
                        resize();
                    }
            );
        });
        //滚动条
        jumpSlider.valueProperty().addListener((ObservableValue<? extends Number> ov,
                                                Number old_val, Number new_val) -> {
            int page = (int) Math.round(new_val.doubleValue() * FileParser.totalPage);
            jumpToPage(page);
            pageNum.setText(FileParser.currentPage + "/" + FileParser.totalPage);
        });
        openFile(Status.getCurrentFile());

    }

}

