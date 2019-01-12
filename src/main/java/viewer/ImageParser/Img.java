package viewer.ImageParser;

import data.Const;
import data.Status;
import javafx.scene.image.Image;

import java.io.File;

public class Img {
    static void reInitImg(File file){
        FileParser.fileType = Const.TYPE_IMG;
        FileParser.totalPage = Status.currentFileList.length;
    }

    static Image getImageFromImg(int page){
        Status.currentFileIndex = page;
        String path = "file:" + Status.currentFileList[Status.currentFileIndex].getAbsolutePath();
        return new Image(path);
    }
}
