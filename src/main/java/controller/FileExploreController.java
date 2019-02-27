package controller;

import data.BookMark;
import data.Const;
import data.Setting;
import data.dto.FileDto;
import javafx.application.Platform;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;
import javafx.scene.text.Text;
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
    private FlowPane files = new FlowPane();
    private Map<File, ObservableValue<Boolean>> chooseFileMap = new HashMap<>();
    private static int chooseStatus = 0;//0 is in choose mode;1 not
    @FXML
    private Button delFileBtn = new Button();

    @FXML
    private void keyPress(KeyEvent keyEvent) {
        if (keyEvent.getCode().equals(KeyCode.ESCAPE)) {
            returnParDir();
            keyEvent.consume();
        }
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
//            File choosed = files.getSelectionModel()
//                    .getSelectedItem();
//            if (null == choosed) return;
//            if (choosed.isDirectory()) {
//                openDir(choosed);
//            } else {
//                openFile(choosed);
//            }
        }
    }

    @FXML
    private void toggleChoose() {
        if (chooseStatus == 0) {
            showChooseFileView();
            chooseStatus = 1;
            delFileBtn.setDisable(false);
            return;
        }
        showFileView();
        chooseStatus = 0;
        delFileBtn.setDisable(true);
    }

    //把文件移到回收站
    @FXML
    private void moveFileToTrash() {

        File[] choosed = getSelectedFiles();

        if (0 == choosed.length) {
            toggleChoose();//恢复到普通的文件页面
            return;
        }
        toggleChoose();//恢复到普通的文件页面
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
        //选择文件列表，初始默认都不选中
        for (File f : FileDto.currentFileList) {
            chooseFileMap.put(f, new SimpleBooleanProperty(false));
        }
        List<VBox> vbs = new ArrayList<>();
        Insets insets = new Insets(10, 10, 10, 10);
        for (File f : FileDto.currentFileList) {
            CheckBox checkBox = new CheckBox();
            checkBox.setOnMouseClicked((e)->{
                if(e.getClickCount() != 1){//只允许单击
                    e.consume();
                    return;
                }
                //根据checkBox有否选中更新选择文件列表的值
                chooseFileMap.put(f, new SimpleBooleanProperty(checkBox.isSelected()));
            });
            ImageView iconView = new ImageView();
            iconView.setImage(ThumbnailUtil.getFileThumbnail(f));
            iconView.setFitWidth(Const.iconSize);
            iconView.setFitHeight(Const.iconSize);

            HBox hBox =new HBox(checkBox,iconView);
            Text title = new Text(f.getName());
            title.setFill(Paint.valueOf("orange"));
            title.setWrappingWidth(Const.iconSize);

            VBox vb = new VBox(hBox, title);
            vb.setPrefWidth(Const.iconSize);
            vb.setPadding(insets);
            vbs.add(vb);
        }
        files.getChildren().setAll(vbs);
    }

    private void showFileView() {
        chooseFileMap.clear();
        List<VBox> vbs = new ArrayList<>();
        Insets insets = new Insets(10, 10, 10, 10);
        for (File f : FileDto.currentFileList) {
            ImageView iconView = new ImageView();
            Platform.runLater(()->{//加载文件缩略图
                iconView.setImage(ThumbnailUtil.getFileThumbnail(f));
            });
            iconView.setFitWidth(Const.iconSize);
            iconView.setFitHeight(Const.iconSize);

            Text title = new Text(f.getName());
            title.setFill(Paint.valueOf("white"));
            if(BookMark.read(f) != 1){
                if(BookMark.isReaded(f)){ //已读
                    title.setFill(Paint.valueOf("blue"));
                }else{//正在读
                    title.setFill(Paint.valueOf("orange"));
                }
            }
            title.setWrappingWidth(Const.iconSize);

            VBox vb = new VBox(iconView, title);
            vb.setPrefWidth(Const.iconSize);
            vb.setPadding(insets);
            vb.setOnMouseClicked((e) -> {
                if (e.getClickCount() != 2) {
                    e.consume();
                    return;
                }
                if (f.isDirectory()) {
                    openDir(f);
                } else {
                    openFile(f);
                }
                e.consume();
            });

            vbs.add(vb);
        }
        files.getChildren().setAll(vbs);
//        Platform.runLater(()->{//设置文件图片
//            for(VBox vb:vbs){
//                vb.
//            }
//        });
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        if (Setting.isFullScreen) {
            setFullScreen();
        }
        if (null != FileDto.currentDir) {
            openDir(FileDto.currentDir);
        } else {
            openDir(new File(Setting.deafultDir));
        }
    }

}

