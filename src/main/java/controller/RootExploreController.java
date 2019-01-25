package controller;

import data.dto.Status;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import utils.SceneManager;
import utils.ThumbnailUtil;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

public class RootExploreController implements Initializable {

    @FXML
    private ListView<File> files = new ListView();

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
        Status.onChangeDir(f);
        SceneManager.toExplorer();
        return;
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Status.changeToRoot();
        files.setItems(Status.currentFileList);
        files.setCellFactory((ListView<File> listView)->new RootExploreController.RootCell());
    }

    static class RootCell extends ListCell<File> {
        @Override
        public void updateItem(File item, boolean empty) {
            super.updateItem(item, empty);
            if (item != null) {
                ImageView iconView = new ImageView();
                iconView.setImage(ThumbnailUtil.getFileThumbnail(item));
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

