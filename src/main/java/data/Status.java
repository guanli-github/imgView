package data;

import javafx.beans.InvalidationListener;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ListProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableNumberValue;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import utils.FileUtil;

import java.io.File;
import java.util.*;

//阅读设定，用来保存和在页面间传递信息
public class Status {

    public static int orient = Const.R2L; //指示翻页方向

    public static int renderDpi = 800; //pdf界面，渲染的dpi值

    public static File deafultDir =  new File("E:\\");
    public static File currentDir =  null;
    public static ObservableList<File> currentFileList = FXCollections.observableArrayList();
    public static int currentFileIndex = 1;

    //当前目录改变时调用
    public static boolean onChangeDir(File f){
        if(!f.isDirectory()) return false;
        currentDir = f;
        currentFileList.setAll(currentDir.listFiles(FileUtil.fileFilter));
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
        return false;
    }

    public static File getCurrentFile(){
        return currentFileList.get(currentFileIndex);
    }

}
