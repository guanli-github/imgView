package viewer;

import data.BookMark;
import data.Const;
import data.Setting;
import data.Status;
import extractor.FileParser;
import javafx.beans.value.ChangeListener;
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
    private void doLeft(SwipeEvent s){
        if(Setting.orient == Const.L2R){
            nextPage();
        }else{
            prePage();
        }
    }
    @FXML
    private void doRight(SwipeEvent s){
        if(Setting.orient == Const.R2L){
            nextPage();
        }else{
            prePage();
        }
    }
    @FXML
    private void doPageClick(MouseEvent mouseEvent) {
        double x = mouseEvent.getX();
        double middle = Toolkit.getDefaultToolkit().getScreenSize().width / 2;
        if((x >= middle && Setting.orient== Const.L2R)
                || (x<middle && Setting.orient== Const.R2L)){
            nextPage();
        }else{
            prePage();
        }
        mouseEvent.consume();
    }
    //更改翻页方式
    @FXML
    private void changeOrient(MouseEvent mouseEvent) {
        if(Setting.orient == Const.L2R){
            Setting.orient = Const.R2L;
        }else{
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

    private void nextPage(){
        FileParser.currentPage += 1;
        jumpToPage(FileParser.currentPage);
    }
    private void prePage(){
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
        pageNum.setText(FileParser.currentPage+"/"+FileParser.totalPage);
    }

    /**
     * 只判断两种情况
     * 横屏时限制高度，宽度足够就放两张
     * 竖屏时根据宽度限制，只放一张
     * */
    @FXML
    private void resizeImgView(){
//        double width = Toolkit.getDefaultToolkit().getScreenSize().width;
//        double height = Toolkit.getDefaultToolkit().getScreenSize().height;
//
//        double radio = imgView.getImage().getWidth() / imgView.getImage().getHeight();
//        System.out.println("wight:"+width+"; height:"+height);
//        if(width>height){
//            imgView.setFitHeight(height);
//        }else{
//            imgView.setFitHeight(width);
//        }
//        imgView.setFitWidth(height * radio);
        imgView.setFitWidth(766.0);
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        resizeImgView();
        jumpSlider.valueProperty().addListener(new ChangeListener<Number>() {
            public void changed(ObservableValue<? extends Number> ov,
                                Number old_val, Number new_val) {
                int page = (int)Math.round(new_val.doubleValue() * FileParser.totalPage);
                jumpToPage(page);
                pageNum.setText(FileParser.currentPage+"/"+FileParser.totalPage);
            }
        });
        openFile(Status.getCurrentFile());
    }

}

