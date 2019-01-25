package controller;

import data.FileTypeHandler;
import data.Setting;
import data.dto.Status;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import utils.SceneManager;
import utils.ThumbnailUtil;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

public class FileExploreController implements Initializable {
    @FXML
    private Button parentDir = new Button();
    @FXML
    private ListView<File> files = new ListView();

    //返回上一级目录
    @FXML
    private void returnParDir(){
        File par = Status.currentDir.getParentFile();
        if(null == par){
            SceneManager.toRoot();
            return;
        }
        openDir(par);
    }
    //进入目录，显示文件视图
    private void openDir(File dir) {
        Status.onChangeDir(dir);
        files.setItems(Status.currentFileList);
        files.setCellFactory((files)->new FileCell());
    }
    //双击打开文件
    @FXML
    private void clickFile(MouseEvent click){
        if (click.getClickCount() == 2) {
            File choosed = files.getSelectionModel()
                    .getSelectedItem();
            if(null == choosed) return;
            if(choosed.isDirectory()){
                openDir(choosed);
            }else{
                openFile(choosed);
            }
        }
    }

    private void openFile(File f){
        Status.onClickFile(f);
        if(FileTypeHandler.docFilter.accept(f)){
            SceneManager.toViewer();
            return;
        }else if(FileTypeHandler.txtFilter.accept(f)){
            SceneManager.toText();
            return;
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        if(null != Status.currentDir){
            openDir(Status.currentDir);
        }
        openDir(Setting.deafultDir);
    }

    static class FileCell extends ListCell<File> {
        @Override
        public void updateItem(File item, boolean empty) {
            super.updateItem(item, empty);
            if (item != null) {
                ImageView iconView = new ImageView();
                iconView.setImage(ThumbnailUtil.getFileThumbnail(item));
                iconView.setFitWidth(20);
                iconView.setFitHeight(20);
                this.setGraphic(iconView);
                this.setText(item.getName());
            }else{
                this.setText("");
                this.setGraphic(null);
            }
        }
    }
}

