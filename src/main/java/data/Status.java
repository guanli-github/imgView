package data;

import utils.FileUtil;

import java.io.File;
import java.util.Arrays;
import java.util.Comparator;

//阅读设定，用来保存和在页面间传递信息
public class Status {

    public static int orient = Const.R2L; //指示翻页方向

    public static int renderDpi = 800; //pdf界面，渲染的dpi值

    public static File deafultDir =  new File("E:\\");
    public static File currentDir =  null;
    public static File[] currentFileList = null;
    public static int currentFileIndex = 0;

    //当前目录改变时调用
    public static boolean onChangeDir(File f){
        if(!f.isDirectory()) return false;
        currentDir = f;
        currentFileList = f.listFiles(FileUtil.fileFilter);
        return true;
    }
    //在当前目录下点击文件时调用
    public static boolean onClickFile(File f){
        if(f.isDirectory()) return false;
        if(currentDir == null ||
                currentFileList == null ||
                !currentDir.equals(f.getParentFile())){
            currentDir = f.getParentFile();
            currentFileList = currentDir.listFiles(FileUtil.fileFilter);
            Arrays.sort(currentFileList, new Comparator<File>() {
                @Override
                public int compare(File o1, File o2) {
                    String o1Name = o1.getName().substring(
                            0,
                            o1.getName().lastIndexOf(".")
                    );
                    String o2Name = o2.getName().substring(
                            0,
                            o2.getName().lastIndexOf(".")
                    );
                    return FileUtil.sortByName(o1Name,o2Name);
                }
            });

        }
        for(int i=0;i<currentFileList.length;i++){
            if (currentFileList[i].equals(f)){
                currentFileIndex = i;
                return true;
            }
        }
        return false;
    }

}
