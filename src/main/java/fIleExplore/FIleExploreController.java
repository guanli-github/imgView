package fIleExplore;

import data.Status;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListCell;
import javafx.scene.layout.FlowPane;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

public class FIleExploreController implements Initializable {
    @FXML
    private FlowPane fileListPane = new FlowPane();
    @FXML
    private Button parentDir = new Button();



    final javafx.stage.FileChooser fileChooser = new javafx.stage.FileChooser();
    //返回上一级目录
    private void returnParDir(){
        File par = Status.currentDir.getParentFile();
        if (null == par) return;
        clickDir(par);
    }
    //点击进入目录，显示文件视图
    private void clickDir(File dir){
        if(!Status.onChangeDir(dir)){
            clickFile(dir);
        }
        for(File f: Status.currentFileList){

        }
    }
    //点击跳转文件页面
    private void clickFile(File f){
//        ObservableList<Stage> stage = FXRobotHelper.getStages();
//        Scene scene = null;
//        try {
//            scene = new Scene(FXMLLoader.load(getClass().getResource("viewer/viewer.fxml")));
//            stage.get(0).setScene(scene);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        if(null != Status.currentDir)
        clickDir(Status.deafultDir);
    }
    private static class FileCell extends ListCell<String> {
        @Override
        public void updateItem(String item, boolean empty) {
            super.updateItem(item, empty);

        }
    }
}

