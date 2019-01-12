package fIleExplore;

import data.Glo_Dto;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListCell;
import javafx.scene.layout.FlowPane;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

public class FIleExploreController implements Initializable {
    @FXML
    private FlowPane fileListPane = new FlowPane();

    final javafx.stage.FileChooser fileChooser = new javafx.stage.FileChooser();

    private void showFileList(File dir){
        if(!dir.isDirectory()) return;
        dir.list();

    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        showFileList(Glo_Dto.deafultDir);
    }
    private static class FileCell extends ListCell<String> {
        @Override
        public void updateItem(String item, boolean empty) {
            super.updateItem(item, empty);

        }
    }
}

