package utils;

import data.BookMark;
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
        if(BookMark.isReaded(file)){ //已读
            return new Image("/icons/readed.jpg");
        }
        if(BookMark.read(file) != 1){ //正在读
            return new Image("/icons/reading.jpg");
        }
        if(file.isDirectory()){
            return new Image("/icons/dir.jpg");
        }
        if(!FileTypeHandler.docFilter.accept(file)){
            return new Image("/icons/file.jpg");
        }
//        if(FileTypeHandler.imgFilter.accept(file)) {
//            String path = "file:" + file.getAbsolutePath();
//            return Img.getThumnbnail(file);
//        }
//        String fileName=file.getName();
//        String type = fileName.substring(fileName.lastIndexOf("."));
//        if(type.equals(Const.TYPE_PDF)) {
//            return Pdf.getThumnbnail(file);
//        }
//        if(type.equals(Const.TYPE_ZIP)){
//           return Zip.getThumnbnail(file);
//        }


        return new Image("/icons/file.jpg");
    }
}
