package utils;

import data.Const;
import data.Setting;
import extractor.FileParser;
import extractor.Img;
import extractor.Pdf;
import extractor.Zip;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;
import net.coobird.thumbnailator.Thumbnails;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.UUID;

/**
 * 缩略图相关
 */
public class ThumbnailUtil {
    static{
        if(!new File(Const.thumbnailPath).exists()){
            new File(Const.thumbnailPath).mkdir();
        }
    }
    public static Image newThumbnail(InputStream imgIs){
        BufferedImage thumbnail;
        try {
            thumbnail =  Thumbnails.of(imgIs)
                    .size(Setting.iconSize, Setting.iconSize)
                    .asBufferedImage();
            FileParser.closeFile();
            return SwingFXUtils.toFXImage(thumbnail,null);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
    private static void saveThumbnail(String filePath,Image thumbnail){
        BufferedImage bImage = SwingFXUtils.fromFXImage(thumbnail, null);

        String thumbnailName = Const.thumbnailPath+ UUID.randomUUID()+".png";
        try {
            ImageIO.write(bImage, "png", new File(thumbnailName));
        } catch (IOException e) {
            e.printStackTrace();
        }
        ConfigUtils.setConfig(Const.THUMBNAIL,filePath, thumbnailName);
    }

    //清除失效文件的缩略图
    public static void cleanThumbnails(){
        List<String> filePaths = ConfigUtils.listKeys(Const.THUMBNAIL);
        for(String path: filePaths){
            String thumnbNailPath = ConfigUtils.getConfig(Const.THUMBNAIL,path);
            if(!new File(path).exists()){
                new File(thumnbNailPath).delete();//清理缩略图文件
                ConfigUtils.removeConfig(Const.THUMBNAIL,path);//删除记录
            }else if(!new File(thumnbNailPath).exists()){

                ConfigUtils.removeConfig(Const.THUMBNAIL,path);//清理无效记录
            }
        }

    }
    //删除指定文件的缩略图
    public static boolean deleteThumbnails(File[] choosed){
        for(File f:choosed){
            String path = f.getAbsolutePath();
            String thumnbNailPath = ConfigUtils.getConfig(Const.THUMBNAIL,path);//查询缩略图记录
            if(null == thumnbNailPath){
                continue;
            }
            File thumb = new File(thumnbNailPath);
            if(!thumb.exists()){
                ConfigUtils.removeConfig(Const.THUMBNAIL,path);//清理无效记录
            }else {
                new File(thumnbNailPath).delete();//清理缩略图文件
                ConfigUtils.removeConfig(Const.THUMBNAIL, path);//删除记录
            }
        }
        return true;
    }
    public static Image getFileThumbnail(File file){
        String thumnbNailPath = ConfigUtils.getConfig(Const.THUMBNAIL,file.getAbsolutePath());
        if(null != thumnbNailPath){
            String path = "file:" + thumnbNailPath;
            return new Image(path);
        }
        //如果记录中没有，就创建新的缩略图
        Image thumnbNail = null;

        String type = FileTypeHandler.getFileType(file);
        if(FileTypeHandler.imgFilter.accept(file)) {
            thumnbNail = Img.getThumnbnail(file);
        }else if(type.equals(Const.TYPE_PDF)) {
            thumnbNail = Pdf.getThumnbnail(file);
        }else if(type.equals(Const.TYPE_ZIP)){
            thumnbNail = Zip.getThumnbnail(file);
        }
        if(null != thumnbNail){
            saveThumbnail(file.getAbsolutePath(),thumnbNail);
            return thumnbNail;
        }
        return new Image("/icons/file.png");
    }
}
