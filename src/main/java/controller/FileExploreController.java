package controller;

import data.Const;
import data.Setting;
import data.dto.FileDto;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import utils.*;

import java.awt.*;
import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

public class FileExploreController implements Initializable {
    @FXML
    private ListView<File> files = new ListView();
    @FXML
    private VBox dialog = new VBox();
    @FXML
    private Label dialogInfo = new Label();
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
    //显示删除确认框
    @FXML
    private void deleteDialog() {
        File[] choosed = getSelectedFiles();
        if(0 == choosed.length){
            return;
        }
        String info = choosed[0].getName()+"等"+choosed.length+"个文件";

        dialogInfo.setText("确认删除"+info+"吗?");
        //设置对话框位置到正中
        double width = Toolkit.getDefaultToolkit().getScreenSize().width;
        double dialogWidth = dialog.getWidth();
        dialog.setTranslateX(0.5 * (width-dialogWidth) );

//        double height = Toolkit.getDefaultToolkit().getScreenSize().height;
//        double dialogHeight = dialog.getHeight();
//        dialog.setTranslateY(0.5 * (height-dialogHeight) );
        ModalUtil.show(files,dialog);
    }
    //把文件移到回收站
    @FXML
    private void moveFileToTrash() {
        File[] choosed = getSelectedFiles();
        if(0 == choosed.length){
            return;
        }
        String info = choosed[0].getName()+"等"+choosed.length+"个文件";
        boolean result = FileUtil.moveFileToTrash(choosed);
        String resultStr = result?"已移至回收站":"删除失败";
        Alert alert = new Alert(Alert.AlertType.INFORMATION,info+resultStr);
        alert.setTitle("");
        alert.setHeaderText("");
        alert.initOwner(SceneManager.getStage());
        alert.show();
        hideDialog();
        openDir(FileDto.currentDir);
        return;
    }
    private File[] getSelectedFiles(){
        return files.getSelectionModel()
                .getSelectedItems().toArray(new File[0]) ;
    }
    @FXML
    private void hideDialog(){
        ModalUtil.hide(files,dialog);
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
    private void setFullScreen(){
        double width = Toolkit.getDefaultToolkit().getScreenSize().width;
        double height = Toolkit.getDefaultToolkit().getScreenSize().height;

        files.setPrefWidth(width);
        files.setPrefHeight(height);
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        if(Setting.isFullScreen) {
            setFullScreen();
        }
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

