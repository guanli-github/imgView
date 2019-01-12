package viewer;

import data.Const;
import data.Status;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
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
    @FXML
    private Button returnDir = new Button();

    private static final FileChooser fileChooser = new FileChooser();
    static{
        fileChooser.setSelectedExtensionFilter(new FileChooser.ExtensionFilter(
                "default filter",Const.file_types.toString()));
        fileChooser.setInitialDirectory(Status.deafultDir);
    }

    private void openFile(final File file) {
        Status.onClickFile(file);
        FileParser.resetSetting(file);
        jumpToPage(FileParser.currentPage);
    }
    public void doLeft(){
        if(Status.orient == Const.L2R){
            nextPage();
        }else{
            prePage();
        }
    }
    public void doRight(){
        if(Status.orient == Const.R2L){
            nextPage();
        }else{
            prePage();
        }
    }
    public void doPageClick(MouseEvent mouseEvent) {
        double x = mouseEvent.getX();
        double middle = Toolkit.getDefaultToolkit().getScreenSize().width / 2;
        if((x >= middle && Status.orient== Const.L2R)
                || (x<middle && Status.orient== Const.R2L)){
            nextPage();
        }else{
            prePage();
        }
        mouseEvent.consume();
    }
    //更改翻页方式
    public void changeOrient(MouseEvent mouseEvent) {
        if(Status.orient == Const.L2R){
            Status.orient = Const.R2L;
        }else{
            Status.orient = Const.L2R;
        }
        mouseEvent.consume();
    }
    //返回目录
    public void returnDir(MouseEvent mouseEvent) {
//        ObservableList<Stage> stage = FXRobotHelper.getStages();
//        Scene scene = null;
//        try {
//            scene = new Scene(FXMLLoader.load(getClass().getResource("fIleExplore/FIleExplore.fxml")));
//            stage.get(0).setScene(scene);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
        fileChooser.setInitialDirectory(Status.currentDir);
        openFile(fileChooser.showOpenDialog(null));
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
            if(Status.currentFileIndex < Status.currentFileList.length){
                Status.currentFileIndex += 1;
                openFile(Status.currentFileList[Status.currentFileIndex]);
            }else{
                return;
            }
        }
        if (page < 1) //打开文件夹中上一文件
            if(Status.currentFileIndex > 0){
                Status.currentFileIndex -= 1;
                openFile(Status.currentFileList[Status.currentFileIndex]);
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
        imgView.getNodeOrientation();
        imgView.setFitWidth(766.0);
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        File f = new FileChooser().showOpenDialog(null);
        resizeImgView();
        openFile(f);
    }

}

