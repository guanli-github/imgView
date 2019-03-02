package controller;

import data.Setting;
import data.dto.FileDto;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import utils.SceneManager;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

public class RootExploreController implements Initializable {

    @FXML
    private ListView<File> files = new ListView();

    @FXML
    private void exit() {
        System.exit(0);
    }
    @FXML
    private void keyPress(KeyEvent keyEvent) {
        if(keyEvent.getCode().equals(KeyCode.ENTER)){
            File choosed = files.getSelectionModel()
                    .getSelectedItem();
            if(null == choosed) return;

            openFile(choosed);
        }
        //keyEvent.consume();
    }
    //双击打开文件
    @FXML
    private void clickFile(MouseEvent click) {
        if (click.getClickCount() == 2) {
            File choosed = files.getSelectionModel()
                    .getSelectedItem();
            if (null == choosed) return;

            openFile(choosed);
        }
    }

    private void openFile(File f) {
        FileDto.onChangeDir(f);
        SceneManager.toExplorer();
        return;
    }
    private void setFullScreen() {

        double width = SceneManager.getStage().getWidth();
        double height = SceneManager.getStage().getHeight();

        files.setPrefHeight(height);
        files.setPrefWidth(width);
    }
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        if (Setting.isFullScreen) {
            SceneManager.getStage().widthProperty().addListener((observable) -> {//屏幕旋转
                Platform.runLater(() -> {
                            setFullScreen();
                        }
                );
            });
        }
        FileDto.changeToRoot();
        files.setItems(FileDto.currentFileList);
        files.setCellFactory((ListView<File> listView)->new RootExploreController.RootCell());
    }

    static class RootCell extends ListCell<File> {
        @Override
        public void updateItem(File item, boolean empty) {
            super.updateItem(item, empty);
            if (item != null) {
                ImageView iconView = new ImageView();
                iconView.setImage(new Image("/icons/dir.png"));
                iconView.setFitWidth(20);
                iconView.setFitHeight(20);
                this.setGraphic(iconView);
                this.setText(item.getAbsolutePath());
            } else {
                this.setText("");
                this.setGraphic(null);
            }
        }
    }
}

