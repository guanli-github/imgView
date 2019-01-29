package data.dto;

import data.FileTypeHandler;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import utils.FileUtil;

import java.io.File;

//阅读设定，用来保存和在页面间传递信息
public class FileDto {

    public static File currentDir =  null;
    public static ObservableList<File> currentFileList = FXCollections.observableArrayList();
    public static int currentFileIndex = 1;

    //当前目录改变时调用
    public static boolean onChangeDir(File f){
        if(!f.isDirectory()) return false;
        currentDir = f;
        currentFileList.setAll(currentDir.listFiles(FileTypeHandler.fullFilter));
        currentFileList.sort((o1,o2)->{
            //文件夹排在文件前面
            if(o1.isDirectory()){
                return -1;
            }
            if( o2.isDirectory()){
                return 1;
            }
            String o1Name = o1.getName().substring(
                    o1.getName().lastIndexOf("/")+1,
                    o1.getName().lastIndexOf(".")
            );
            String o2Name = o2.getName().substring(
                    o2.getName().lastIndexOf("/")+1,
                    o2.getName().lastIndexOf(".")
            );
            return FileUtil.sortByName(o1Name,o2Name);
        });
        return true;
    }
    //在当前目录下点击文件时调用
    public static boolean onClickFile(File f){
        if(f.isDirectory()) return false;
        if(currentDir == null ||
                currentFileList == null ||
                !currentDir.equals(f.getParentFile())) {
            currentDir = f.getParentFile();
            onChangeDir(f);
        }
        currentFileIndex = currentFileList.indexOf(f);
        return true;
    }

    //跳转根目录列表
    public static void changeToRoot(){
        File[] roots = File.listRoots();
        currentFileList.setAll(roots);
    }
    public static File getCurrentFile(){
        return currentFileList.get(currentFileIndex);
    }
    //打开文件夹中下一文件
    public static File nextFile(){
        if(currentFileIndex < currentFileList.size()){
            return currentFileList.get(currentFileIndex+1);
        }
        return getCurrentFile();
    }
    //打开文件夹中上一文件
    public static File preFile(){
        if(currentFileIndex > 1){
            return currentFileList.get(currentFileIndex-1);
        }
        return getCurrentFile();
    }
}
