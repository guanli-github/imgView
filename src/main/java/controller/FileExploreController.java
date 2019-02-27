package controller;

import data.Const;
import data.Setting;
import data.dto.FileDto;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.cell.CheckBoxListCell;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.util.Callback;
import utils.FileTypeHandler;
import utils.FileUtil;
import utils.SceneManager;
import utils.ThumbnailUtil;

import java.awt.*;
import java.io.File;
import java.net.URL;
import java.util.List;
import java.util.*;

public class FileExploreController implements Initializable {
    @FXML
    private ListView<File> files = new ListView();
    private Map<File, ObservableValue<Boolean>> chooseFileMap = new HashMap<>();

    @FXML
    private void keyPress(KeyEvent keyEvent) {
        if (keyEvent.getCode().equals(KeyCode.ESCAPE)) {
            returnParDir();
        } else if (keyEvent.getCode().equals(KeyCode.UP)) {
            files.getSelectionModel().selectPrevious();
            files.scrollTo(files.getSelectionModel().getSelectedIndex());
        } else if (keyEvent.getCode().equals(KeyCode.DOWN)) {
            files.getSelectionModel().selectNext();
            files.scrollTo(files.getSelectionModel().getSelectedIndex());
        } else if (keyEvent.getCode().equals(KeyCode.ENTER)) {
            File choosed = files.getSelectionModel()
                    .getSelectedItem();
            if (null == choosed) return;
            if (choosed.isDirectory()) {
                openDir(choosed);
            } else {
                openFile(choosed);
            }
        }
        keyEvent.consume();
    }

    //返回上一级目录
    @FXML
    private void returnParDir() {
        File par = FileDto.currentDir.getParentFile();
        if (null == par) {
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
        showFileView();
    }

    //双击打开文件
    @FXML
    private void clickFile(MouseEvent click) {
        if (click.getClickCount() == 2) {
            File choosed = files.getSelectionModel()
                    .getSelectedItem();
            if (null == choosed) return;
            if (choosed.isDirectory()) {
                openDir(choosed);
            } else {
                openFile(choosed);
            }
        }
    }

    int sta = 0;

    //把文件移到回收站
    @FXML
    private void moveFileToTrash() {
        if (sta == 0) {
            showChooseFileView();
            sta = 1;
            return;
        }
        showFileView();
        sta = 0;
        File[] choosed = getSelectedFiles();
        String str = "";
        if (0 == choosed.length) {
            return;
        }
        String info = choosed[0].getName() + "等" + choosed.length + "个文件";

        boolean result = FileUtil.moveFileToTrash(choosed);
        String resultStr = result ? "已移至回收站" : "删除失败";

        Alert alert = new Alert(Alert.AlertType.INFORMATION, info + resultStr);
        alert.setTitle("");
        alert.setHeaderText("");
        alert.initOwner(SceneManager.getStage());
        alert.show();
        openDir(FileDto.currentDir);
        return;
    }

    //选择要删除的文件
    private File[] getSelectedFiles() {
        List<File> list = new ArrayList<>();
        for (File key : chooseFileMap.keySet()) {
            if (chooseFileMap.get(key).getValue()) {
                list.add(key);
            }
        }
        chooseFileMap.clear();
        File[] choosed = list
                .toArray(new File[0]);
        return choosed;
    }

    private void openFile(File f) {
        FileDto.onClickFile(f);
        if (FileTypeHandler.docFilter.accept(f)) {
            SceneManager.toViewer();
            return;
        } else if (FileTypeHandler.txtFilter.accept(f)) {
            SceneManager.toText();
            return;
        }
    }

    @FXML
    private void setFullScreen() {
        double width = Toolkit.getDefaultToolkit().getScreenSize().width;
        double height = Toolkit.getDefaultToolkit().getScreenSize().height;

        files.setPrefWidth(width);
        files.setPrefHeight(height);
    }

    //显示选择文件视图
    private void showChooseFileView() {
        Callback<File, ObservableValue<Boolean>> itemToBoolean = (File item) -> chooseFileMap.get(item);
        files.setItems(null);
        files.refresh();
        files.setItems(FileDto.currentFileList);
        files.setCellFactory(lv -> new ChooseFileCell(itemToBoolean));

        for (File f : FileDto.currentFileList) {
            chooseFileMap.put(f, new SimpleBooleanProperty(false));
        }
    }

    private void showFileView() {
        chooseFileMap.clear();
        files.setItems(null);
        files.setItems(FileDto.currentFileList);
        files.setCellFactory((files) -> new FileCell());
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        if (Setting.isFullScreen) {
            setFullScreen();
        }
        if (null != FileDto.currentDir) {
            openDir(FileDto.currentDir);
        } else {
            openDir(Setting.deafultDir);
        }
        //从其他面板返回时，保留焦点
        if (null != FileDto.getCurrentFile()) {
            files.getSelectionModel().select(FileDto.getCurrentFile());
            files.scrollTo(files.getSelectionModel().getSelectedIndex());
        }
    }

    static class FileCell extends ListCell<File> {
        @Override
        public void updateItem(File item, boolean empty) {
            super.updateItem(item, empty);
            if (empty) {
                this.setText(null);
                this.setGraphic(null);
            } else {
                ImageView iconView = new ImageView();
                iconView.setImage(ThumbnailUtil.getFileThumbnail(item));
                iconView.setFitWidth(Const.iconSize);
                iconView.setFitHeight(Const.iconSize);
                this.setGraphic(iconView);
                this.setText(item.getName());
            }
        }
    }

    public class ChooseFileCell extends CheckBoxListCell<File> {
        public ChooseFileCell(Callback<File, ObservableValue<Boolean>> getSelectedProperty) {
            super(getSelectedProperty);
        }

        @Override
        public void updateItem(File item, boolean empty) {
            super.updateItem(item, empty);
            if (empty) {
                this.setText(null);
                this.setGraphic(null);
            } else {
//                ImageView iconView = new ImageView();
//                iconView.setImage(ThumbnailUtil.getFileThumbnail(item));
//                iconView.setFitWidth(Const.iconSize);
//                iconView.setFitHeight(Const.iconSize);
//                CheckBox checkBox = (CheckBox) this.getGraphic();
//
//                HBox hBox = new HBox();
//                hBox.getChildren().addAll(checkBox,iconView);
//                this.setGraphic(hBox);
                this.setText(item.getName());
            }
        }
    }
}

