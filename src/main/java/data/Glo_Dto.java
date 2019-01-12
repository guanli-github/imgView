package data;

import java.io.File;
import java.io.FileFilter;

//阅读设定，用来保存和在页面间传递信息
public class Glo_Dto {

    public static int orient = Const.R2L; //指示翻页方向

    public static int renderDpi = 800; //pdf界面，渲染的dpi值

    public static File deafultDir =  new File("E:\\");
    public static File currentDir =  new File("E:\\");
    private static FileFilter fileFilter = new FileFilter() {
        @Override
        public boolean accept(File file) {
            if(file.isDirectory()){
                return true;
            }
            String fileName = file.getName();
            String type = fileName.substring(fileName.lastIndexOf("."));
            for (int i = 0; i < Const.file_types.length; i++) {
                if (type.equals(Const.file_types[i])) {
                    return true;
                }
            }
            return false;
        }
    };
    public static File[] currentFileList = currentDir.listFiles(fileFilter);
    public static int currentFileIndex = 0;

    public static boolean isSupportImg(String type){
        for (int i = 0; i < Const.img_types.length; i++) {
            if (type.equals(Const.img_types[i])) {
                return true;
            }
        }
        return false;
    }
}
