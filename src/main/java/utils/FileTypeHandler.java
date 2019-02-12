package utils;

import data.Const;

import java.io.File;
import java.io.FileFilter;

public class FileTypeHandler {
    //将文件类型与处理者绑定
//    public IExtractor getHandler(String fileType){
//        Map<String,Class> typeMap = new HashMap();
//        return null;
//    }

    public static FileFilter imgFilter = ((File file)->{
            if(file.isDirectory()){
                return false;
            }
            String fileName = file.getName();
            String type = fileName.substring(fileName.lastIndexOf("."));
            for (int i = 0; i < Const.img_types.length; i++) {
                if (type.equals(Const.img_types[i])) {
                    return true;
                }
            }
            return false;
    });

    public static FileFilter txtFilter = ((File file)->{
        if(file.isDirectory()){
            return false;
        }
        String fileName = file.getName();
        String type = fileName.substring(fileName.lastIndexOf("."));
        for (int i = 0; i < Const.txt_types.length; i++) {
            if (type.equals(Const.txt_types[i])) {
                return true;
            }
        }
        return false;
    });

    public static FileFilter docFilter = ((File file)->{
            if(file.isDirectory()){
                return false;
            }
            String type = getFileType(file);
            for (int i = 0; i < Const.doc_types.length; i++) {
                if (type.equals(Const.doc_types[i])) {
                    return true;
                }
            }
        for (int i = 0; i < Const.img_types.length; i++) {
            if (type.equals(Const.img_types[i])) {
                return true;
            }
        }
        return false;
        });
    public static FileFilter fullFilter = ((File file)->{
        if(file.isDirectory()){
            return true;
        }
        String type = getFileType(file);
        for (int i = 0; i < Const.doc_types.length; i++) {
            if (type.equals(Const.doc_types[i])) {
                return true;
            }
        }
        for (int i = 0; i < Const.img_types.length; i++) {
            if (type.equals(Const.img_types[i])) {
                return true;
            }
        }
        for (int i = 0; i < Const.txt_types.length; i++) {
            if (type.equals(Const.txt_types[i])) {
                return true;
            }
        }
        return false;
    });

    public static String getFileType(File file){
        String fileName=file.getName();
        String type = "";
        try{
            type = fileName.substring(fileName.lastIndexOf("."));
        }catch(StringIndexOutOfBoundsException e){
            return "";
        }
        return type;
    }
}
