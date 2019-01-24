package data;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.File;

//阅读设定，用来保存和在页面间传递信息
public class Status {

    public static File deafultDir =  new File("E:\\");
    public static File currentDir =  null;
    public static ObservableList<File> currentFileList = FXCollections.observableArrayList();
    public static int currentFileIndex = 1;

    //当前目录改变时调用
    public static boolean onChangeDir(File f){
        if(!f.isDirectory()) return false;
        currentDir = f;
        currentFileList.setAll(currentDir.listFiles());
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

    public static File getCurrentFile(){
        return currentFileList.get(currentFileIndex);
    }

}
