package fIleExplore;

import com.sun.javafx.robot.impl.FXRobotHelper;
import data.Status;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Arrays;
import java.util.Collections;
import java.util.ResourceBundle;

public class FIleExploreController implements Initializable {
    @FXML
    private Button parentDir = new Button();
    @FXML
    private ListView<File> files = new ListView();

    private static ObservableList<Label> fileLabels;
    //返回上一级目录
    public void returnParDir(){
        File par = Status.currentDir.getParentFile();
        if (null == par) return;
        showDir(par);
    }
    //进入目录，显示文件视图
    private void showDir(File dir) {
        if (!Status.onChangeDir(dir)) {
            clickFile(dir);
        }
        files.setItems(FXCollections.observableList(
                Arrays.asList(
                        Status.currentFileList)));
        files.setCellFactory((ListView<File> listView)->new FileCell());
        //双击打开文件
        files.setOnMouseClicked((click) -> {
                    if (click.getClickCount() == 2) {
                        //Use ListView's getSelected Item
                        File f = files.getSelectionModel()
                                .getSelectedItem();
                        clickFile(f);
                        //use this to do whatever you want to. Open Link etc.
                    }
                }
                );
    }
    //点击跳转文件页面
    private void clickFile(File f){
        Status.onClickFile(f);
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
        showDir(Status.deafultDir);
    }

    static class FileCell extends ListCell<File> {
        @Override
        public void updateItem(File item, boolean empty) {
            super.updateItem(item, empty);
            if (item != null) {
                this.setText(item.getName());
            }
        }
    }
}

