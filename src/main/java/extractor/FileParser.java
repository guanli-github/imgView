package extractor;

import data.BookMark;
import data.Const;
import data.FileTypeHandler;
import javafx.scene.image.Image;

import java.io.File;

/**
 * 1 负责文件类型识别
 * 2 负责指针状态更新
 */
public class FileParser {
    public static int totalPage = 0;
    public static String fileType = "img";//默认解析成图片
    public static int currentPage = 1;


    /**
     * 打开一个新的文件/文件夹时调用
     * @param file
     */
    public static boolean refreash(File file){
        currentPage = BookMark.read(file);

        if(FileTypeHandler.imgFilter.accept(file)) {
            fileType = Const.TYPE_IMG;
            Img.reInit(file);
            return true;
        }
        String fileName=file.getName();
        String type = fileName.substring(fileName.lastIndexOf("."));
        if(type.equals(Const.TYPE_PDF)) {
            Pdf.reInit(file);
            return true;
        }
        if(type.equals(Const.TYPE_ZIP)){
            fileType = Const.TYPE_ZIP;
            Zip.reInit(file);
            return true;
        }
        return false;
    }

    public static Image getImage(int page) {
        //文件排序以0开始
        page -= 1;
        if(fileType.equals(Const.TYPE_PDF)) return Pdf.getImage(page);
        if(fileType.equals(Const.TYPE_ZIP)) return Zip.getImage(page);
        if(fileType.equals(Const.TYPE_IMG)) return Img.getImage(page);

        return null;
    }

}
