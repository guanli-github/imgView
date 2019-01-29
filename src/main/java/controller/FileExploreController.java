package controller;

import data.Const;
import data.FileTypeHandler;
import data.Setting;
import data.dto.FileDto;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import utils.SceneManager;
import utils.ThumbnailUtil;

import java.awt.*;
import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

public class FileExploreController implements Initializable {
    @FXML
    private ListView<File> files = new ListView();

    //返回上一级目录
    @FXML
    private void returnParDir(){
        File par = FileDto.currentDir.getParentFile();
        if(null == par){
            SceneManager.toRoot();
            return;
        }
        openDir(par);
    }
    @FXML
    private void exit(MouseEvent mouseEvent) {
        System.exit(0);
    }
    //进入目录，显示文件视图
    private void openDir(File dir) {
        FileDto.onChangeDir(dir);
        files.setItems(null);
        files.setItems(FileDto.currentFileList);
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
        FileDto.onClickFile(f);
        if(FileTypeHandler.docFilter.accept(f)){
            SceneManager.toViewer();
            return;
        }else if(FileTypeHandler.txtFilter.accept(f)){
            SceneManager.toText();
            return;
        }
    }
    @FXML
    private void resize(){
        double width = Toolkit.getDefaultToolkit().getScreenSize().width;
        double height = Toolkit.getDefaultToolkit().getScreenSize().height;

        files.setPrefWidth(width);
        files.setPrefHeight(height);
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        resize();
        if(null != FileDto.currentDir){
            openDir(FileDto.currentDir);
        }else{
            openDir(Setting.deafultDir);
        }
    }

    static class FileCell extends ListCell<File> {
        @Override
        public void updateItem(File item, boolean empty) {
            super.updateItem(item, empty);
            if (item != null) {
                ImageView iconView = new ImageView();
                iconView.setImage(ThumbnailUtil.getFileThumbnail(item));
                iconView.setFitWidth(Const.iconSize);
                iconView.setFitHeight(Const.iconSize);
                this.setGraphic(iconView);
                this.setText(item.getName());
            }else{
                this.setText("");
                this.setGraphic(null);
            }
        }
    }
}

