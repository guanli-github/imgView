package extractor;

import data.Const;
import data.dto.FileDto;
import javafx.scene.image.Image;
import utils.FileTypeHandler;
import utils.FileUtil;
import utils.ThumbnailUtil;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

public class Img {
    private static List<File> imgFiles = new ArrayList<>();

    static void reInit(File file) {
        FileParser.fileType = Const.TYPE_IMG;
        for (File f : FileDto.currentFileList) {
            if (FileTypeHandler.imgFilter.accept(f)) {
                imgFiles.add(f);
            }
        }
        imgFiles.sort((o1, o2) -> {
            String o1Name = o1.getName().substring(
                    o1.getName().lastIndexOf("/") + 1,
                    o1.getName().lastIndexOf(".")
            );
            String o2Name = o2.getName().substring(
                    o2.getName().lastIndexOf("/") + 1,
                    o2.getName().lastIndexOf(".")
            );
            return FileUtil.sortByName(o1Name, o2Name);
        });
        FileParser.totalPage = imgFiles.size();
        FileParser.currentPage.setValue(imgFiles.indexOf(file) + 1);
    }

    public static Image getImage(int page) {
        String path = "file:" + imgFiles.get(page).getAbsolutePath();
        return new Image(path);
    }

    public static Image getThumnbnail(File file) {
        String path = "file:" + file.getAbsolutePath();
        try {
            return ThumbnailUtil.newThumbnail(new FileInputStream(file));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }
}
