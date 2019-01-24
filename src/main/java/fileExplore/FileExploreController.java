package fileExplore;

import com.sun.javafx.robot.impl.FXRobotHelper;
import data.FileTypeHandler;
import data.Status;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import utils.ThumbnailUtil;

import java.io.File;
import java.io.IOException;
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
        openDir(par);
    }
    //进入目录，显示文件视图
    private void openDir(File dir) {
        Status.onChangeDir(dir);
        files.setItems(Status.currentFileList);
        files.setCellFactory((ListView<File> listView)->new FileCell());
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
        ObservableList<Stage> stage = FXRobotHelper.getStages();
        Scene scene = null;
        try {
            if(FileTypeHandler.docFilter.accept(f)){
                scene = new Scene(FXMLLoader.load(getClass().getResource("/viewer.fxml")));
            }else{
                scene = new Scene(FXMLLoader.load(getClass().getResource("/text.fxml")));
                scene.getStylesheets().add
                        (getClass().getResource("/css/text.css").toExternalForm());
            }
            stage.get(0).setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        openDir(Status.deafultDir);
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

