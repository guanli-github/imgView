package fIleExplore;

import com.sun.javafx.robot.impl.FXRobotHelper;
import data.Status;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class FIleExploreController implements Initializable {
    @FXML
    private Button parentDir = new Button();
    @FXML
    private HBox fileBox = new HBox();

    private static ObservableList<Label> fileLabels;
    //返回上一级目录
    public void returnParDir(){
        File par = Status.currentDir.getParentFile();
        if (null == par) return;
        showDir(par);
    }
    //进入目录，显示文件视图
    private void showDir(File dir){
        if(!Status.onChangeDir(dir)){
            clickFile(dir);
        }

        for(File f: Status.currentFileList){

        }
    }
    //点击跳转文件页面
    private void clickFile(File f){
        ObservableList<Stage> stage = FXRobotHelper.getStages();
        Scene scene = null;
        try {
            scene = new Scene(FXMLLoader.load(getClass().getResource("/viewer.fxml")));
            stage.get(0).setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
//        if(null != Status.currentDir){
//            showDir(Status.currentDir);
//        }
        showDir(Status.deafultDir);
    }
}

