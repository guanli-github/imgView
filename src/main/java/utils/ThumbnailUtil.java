package utils;

import data.FileTypeHandler;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;
import net.coobird.thumbnailator.Thumbnails;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * 生成缩略图
 */
public class ThumbnailUtil {
    public static Image getThumbnail(BufferedImage image,int width,int height){
        try {
            BufferedImage thumbnail =  Thumbnails.of(image)
                    .size(width, height).asBufferedImage();
            SwingFXUtils.toFXImage(thumbnail,null);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Image getFileThumbnail(File file){
        if(file.isDirectory()){
            return new Image("/icons/dir.jpg");
        }
        if(!FileTypeHandler.docFilter.accept(file)){
            return new Image("/icons/file.jpg");
        }
        if(FileTypeHandler.imgFilter.accept(file)) {
            String path = "file:" + file.getAbsolutePath();
            return new Image(path);
        }
//        String fileName=file.getName();
//        String type = fileName.substring(fileName.lastIndexOf("."));
//        if(type.equals(Const.TYPE_PDF)) {
//            return Pdf.getImage(0);
//        }
//        if(type.equals(Const.TYPE_ZIP)){
//           return Zip.getImage(0);
//        }
        return null;
    }
}
