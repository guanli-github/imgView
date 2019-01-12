package viewer.ImageParser;

import data.Const;
import data.Glo_Dto;
import javafx.scene.image.Image;

import java.io.File;

public class Img {
    static void reInitImg(File file){
        FileParser.fileType = Const.TYPE_IMG;
        FileParser.totalPage = Glo_Dto.curre.length;
    }

    static Image getImageFromImg(int page){
        String path = "file:" + Glo_Dto.currentFileList[Glo_Dto.currentFileIndex].getAbsolutePath();
        return new Image(path);
    }
}
