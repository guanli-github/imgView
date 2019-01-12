package viewer;

import data.Const;
import data.Glo_Dto;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import viewer.ImageParser.FileParser;

import java.awt.*;
import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

public class ViewerController implements Initializable {
    @FXML
    private ImageView imgView = new ImageView();
    @FXML
    private Button changeOrient = new Button();

    private void openFile(final File file) {
        FileParser.resetSetting(file);
        jumpToPage(FileParser.currentPage);
    }
    public void doLeft(){
        if(Glo_Dto.orient == Const.L2R){
            nextPage();
        }else{
            prePage();
        }
    }
    public void doRight(){
        if(Glo_Dto.orient == Const.R2L){
            nextPage();
        }else{
            prePage();
        }
    }
    public void doPageClick(MouseEvent mouseEvent) {
        double x = mouseEvent.getX();
        double middle = Toolkit.getDefaultToolkit().getScreenSize().width / 2;
        if((x >= middle && Glo_Dto.orient== Const.L2R)
                || (x<middle && Glo_Dto.orient== Const.R2L)){
            nextPage();
        }else{
            prePage();
        }
        mouseEvent.consume();
    }

    public void changeOrient(MouseEvent mouseEvent) {
        if(Glo_Dto.orient == Const.L2R){
            Glo_Dto.orient = Const.R2L;
        }else{
            Glo_Dto.orient = Const.L2R;
        }
        mouseEvent.consume();
    }
    //刷新属性值
    public void refreshSetting(){
        FileParser.currentPage = 1;
        Glo_Dto.orient = 1;
        Glo_Dto.renderDpi = 800;
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

        if (page > FileParser.totalPage) { //打开文件夹中下一文件
            if(Glo_Dto.currentFileIndex < Glo_Dto.currentFileList.length){
                Glo_Dto.currentFileIndex += 1;
                openFile(Glo_Dto.currentFileList[Glo_Dto.currentFileIndex]);
            }else{
                return;
            }
        }
        if (page < 1) //打开文件夹中上一文件
            if(Glo_Dto.currentFileIndex > 0){
                Glo_Dto.currentFileIndex -= 1;
                openFile(Glo_Dto.currentFileList[Glo_Dto.currentFileIndex]);
            }else{
                return;
            }
        imgView.setImage(
                FileParser.getImage(page)
        );
        FileParser.currentPage = page;
    }

    /**
     * 只判断两种情况
     * 横屏时限制高度，宽度足够就放两张
     * 竖屏时根据宽度限制，只放一张
     * */
    public void resizeImgView(){
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
        imgView.setFitWidth(400.0);
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        resizeImgView();
        openFile(Glo_Dto.currentFileList[Glo_Dto.currentFileIndex]);
    }

}

