package controller;

import data.BookMark;
import data.Const;
import data.Setting;
import data.dto.FileDto;
import extractor.FileParser;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.NodeOrientation;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import utils.ModalUtil;
import utils.SceneManager;

import java.awt.*;
import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

public class ViewerController implements Initializable {
    @FXML
    private javafx.scene.control.Button changeOrient = new javafx.scene.control.Button();
    @FXML
    private ImageView imgView = new ImageView();
    @FXML
    private Label pageNum = new Label();
    @FXML
    private Slider slider = new Slider();

    private void openFile(final File file) {
        FileDto.onClickFile(file);
        FileParser.refreash(file);
        jumpToPage(FileParser.currentPage);
    }

    @FXML
    private void doLeft() {
        if (Setting.orient == Const.R2L) {
            nextPage();
        } else {
            prePage();
        }
    }

    @FXML
    private void doRight() {
        if (Setting.orient == Const.L2R) {
            nextPage();
        } else {
            prePage();
        }
    }

    @FXML
    private void doPageClick(MouseEvent mouseEvent) {
        //翻页
        double x = mouseEvent.getX();
        double width = Toolkit.getDefaultToolkit().getScreenSize().width;
        if (x >= width * 0.6) {
            doRight();
        } else if (x < width * 0.4) {
            doLeft();
        } else {
            showSlider();//点击中间方显示进度条
        }
        mouseEvent.consume();
    }

    //显示进度条
    private void showSlider() {
        if (slider.visibleProperty().getValue()) {//进度条已显示就隐藏
            ModalUtil.hide(slider, imgView);
            return;
        }
        if (Setting.orient == Const.R2L) {
            slider.setNodeOrientation(NodeOrientation.RIGHT_TO_LEFT);
        }
        slider.setMax(FileParser.totalPage);
        //固定在下方
        double y = Toolkit.getDefaultToolkit().getScreenSize().height;
        slider.setTranslateY(y * 0.45);//在1/3和1/2之间
        ModalUtil.show(slider, imgView);
    }

    @FXML
    private void onSliderChanging() {
        //滚动时页码变化
        pageNum.setText((int) slider.getValue() + "/" + FileParser.totalPage);
    }

    @FXML
    private void onSliderChanged() {
        int page = (int) slider.getValue();
        jumpToPage(page);
        ModalUtil.hide(slider, imgView);
    }

    @FXML
    private void keyPressed(KeyEvent keyEvent) {
        if (keyEvent.getCode().equals(KeyCode.LEFT)) {
            doLeft();
        } else if (keyEvent.getCode().equals(KeyCode.RIGHT)) {
            doRight();
        } else if (keyEvent.getCode().equals(KeyCode.SHIFT)) {
            //Shift键显示进度条
            showSlider();
        }
        keyEvent.consume();
    }

    //更改翻页方式
    @FXML
    private void changeOrient(MouseEvent mouseEvent) {
        if (Setting.orient == Const.L2R) {
            Setting.orient = Const.R2L;
            changeOrient.setText("右->左");
        } else {
            Setting.orient = Const.L2R;
            changeOrient.setText("左->右");
        }
        mouseEvent.consume();
    }

    //返回目录
    @FXML
    private void returnDir() {
        BookMark.saveCurrent();
        SceneManager.toExplorer();
    }

    private void nextPage() {
        jumpToPage(FileParser.currentPage + 1);
    }

    private void prePage() {
        jumpToPage(FileParser.currentPage - 1);
    }

    private void jumpToPage(int page) {
        if (0 >= FileParser.totalPage) return;
        if (page > FileParser.totalPage) return;
        if (page < 0) return;

        //1 前后的文件可能是文本类型的
        // 2 文件列表没按默认方式排序
//        if (page > FileParser.totalPage) { //打开文件夹中下一文件
//            openFile(FileDto.nextFile());
//        }
//        if (page < 1) {//打开文件夹中上一文件
//            openFile(FileDto.preFile());
//        }
        imgView.setImage(
                FileParser.getImage(page)
        );
        FileParser.currentPage = page;
        pageNum.setText(FileParser.currentPage + "/" + FileParser.totalPage);
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

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        if (Setting.isFullScreen) {
            Platform.runLater(() -> {
                setFullScreen();
            });
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

