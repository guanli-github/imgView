package viewer.Extractor;

import data.Const;
import data.Status;
import javafx.scene.image.Image;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class Img implements IExtractor {
    private static List<File> imgFiles = new ArrayList<>();

    static void reInit(File file){
        FileParser.fileType = Const.TYPE_IMG;
        for(File f:Status.currentFileList){
            if(FileParser.imgFilter.accept(f)){
                imgFiles.add(f);
            }
        }
        FileParser.totalPage = imgFiles.size();
        FileParser.currentPage = imgFiles.indexOf(file);
    }

    static Image getImage(int page){
        String path = "file:" + imgFiles.get(page).getAbsolutePath();
        return new Image(path);
    }
}
