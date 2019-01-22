package utils;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;
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
        if(!FileUtil.docFilter.accept(file)){
            return new Image("/icon.jpg");
        }
        final javax.swing.JFileChooser fc = new javax.swing.JFileChooser();
        javax.swing.Icon icon = fc.getUI().getFileView(fc).getIcon(file);

        BufferedImage bufferedImage = new BufferedImage(
                icon.getIconWidth(),
                icon.getIconHeight(),
                BufferedImage.TYPE_INT_ARGB
        );
        return SwingFXUtils.toFXImage(bufferedImage,null);
    }
}
