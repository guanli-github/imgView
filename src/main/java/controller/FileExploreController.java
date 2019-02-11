package controller;

import data.Const;
import data.FileTypeHandler;
import data.Setting;
import data.dto.FileDto;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import utils.FileUtil;
import utils.SceneManager;
import utils.ThumbnailUtil;

import java.awt.*;
import java.io.File;
import java.net.URL;
import java.util.Optional;
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
    private void exit() {
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
    //把文件移到回收站
    @FXML
    private void moveFileToTrash() {
        File choosed = files.getSelectionModel()
                .getSelectedItem();
        String filename = choosed.getName();
        Alert isDelete = new Alert(Alert.AlertType.CONFIRMATION,"确认删除"+filename+"?");
        isDelete.setTitle("");
        isDelete.setHeaderText("");
        Optional<ButtonType> confirm = isDelete.showAndWait();
        if (confirm.get() == ButtonType.OK){
            boolean result = FileUtil.moveFileToTrash(
                    new File[]{choosed}
            );
            String resultStr = result?"已移至回收站":"删除失败";
            Alert alert = new Alert(Alert.AlertType.INFORMATION,filename+resultStr);
            alert.setTitle("");
            alert.setHeaderText("");
            alert.show();
            openDir(FileDto.currentDir);
        }
        return;
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

