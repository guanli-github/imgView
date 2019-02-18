package controller;

import data.Const;
import data.Setting;
import data.dto.FileDto;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import utils.FileTypeHandler;
import utils.FileUtil;
import utils.SceneManager;
import utils.ThumbnailUtil;

import java.awt.*;
import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

public class FileExploreController implements Initializable {
    @FXML
    private ListView<File> files = new ListView();

    private static final FileChooser delFileChooser = new FileChooser();

    @FXML
    private void keyPress(KeyEvent keyEvent) {
        if (keyEvent.getCode().equals(KeyCode.ESCAPE)) {
            returnParDir();
        } else if(keyEvent.getCode().equals(KeyCode.ENTER)){
            File choosed = files.getSelectionModel()
                    .getSelectedItem();
            if(null == choosed) return;
            if(choosed.isDirectory()){
                openDir(choosed);
            }else{
                openFile(choosed);
            }
        }
        //keyEvent.consume();
    }
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
        openDir(FileDto.currentDir);
        return;
    }

    private File[] getSelectedFiles() {
        delFileChooser.setInitialDirectory(FileDto.currentDir);
        delFileChooser.setTitle("选择要删除的文件");
        File[] choosed = delFileChooser.showOpenMultipleDialog(null)
                .toArray(new File[0]);
        return choosed;
    }
//    @FXML
//    private void hideDialog(){
//        ModalUtil.hide(files,dialog);
//    }
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

