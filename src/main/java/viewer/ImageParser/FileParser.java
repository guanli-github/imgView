package viewer.ImageParser;

import data.Const;
import data.Status;
import javafx.scene.image.Image;

import java.io.File;
import java.io.FileFilter;

/**
 * 1 负责文件类型识别
 * 2 负责指针状态更新
 */
public class FileParser {
    public static int totalPage = 0;
    public static String fileType = "img";//默认解析成图片
    public static int currentPage = 1;

    protected static FileFilter imgFilter = new FileFilter() {
        @Override
        public boolean accept(File file) {
            String fileName = file.getName();
            String type = fileName.substring(fileName.lastIndexOf("."));
            for (int i = 0; i < Const.img_types.length; i++) {
                if (type.equals(Const.img_types[i])) {
                    return true;
                }
            }
            return false;
        }
    };
    /**
     * 打开一个新的文件/文件夹时调用
     * @param file
     */
    public static boolean resetSetting(File file){
        currentPage = 1;

        if(imgFilter.accept(file)) {
            fileType = Const.TYPE_IMG;
            currentPage = Status.currentFileIndex;
            Img.reInitImg(file);
            return true;
        }
        String fileName=file.getName();
        String type = fileName.substring(fileName.lastIndexOf("."));
        if(type.equals(Const.TYPE_PDF)) {
            Pdf.reInitPdf(file);
            return true;
        }
        if(type.equals(Const.TYPE_ZIP)){
            fileType = Const.TYPE_ZIP;
            Zip.reInitZip(file);
            return true;
        }
        return false;
    }

    public static Image getImage(int page) {
        if(fileType.equals(Const.TYPE_PDF)) return Pdf.getImageFromPdf(page);
        if(fileType.equals(Const.TYPE_ZIP)) return Zip.getImageFromZip(page);
        if(fileType.equals(Const.TYPE_IMG)) return Img.getImageFromImg(page);

        return null;
    }

}
