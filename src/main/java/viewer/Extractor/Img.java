package viewer.Extractor;

import data.Const;
import data.Status;
import javafx.scene.image.Image;

import java.io.File;

public class Img implements IExtractor {
    static void reInit(File file){
        FileParser.fileType = Const.TYPE_IMG;
        FileParser.totalPage = Status.currentFileList.length;
    }

    static Image getImage(int page){
        Status.currentFileIndex = page;
        String path = "file:" + Status.currentFileList[Status.currentFileIndex].getAbsolutePath();
        return new Image(path);
    }
}
