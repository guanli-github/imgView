package data;

import extractor.IExtractor;

import java.io.File;
import java.io.FileFilter;

//将文件类型与处理者绑定
public class FileTypeHandler {
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

    public static FileFilter docFilter = ((File file)->{
            if(file.isDirectory()){
                return false;
            }
            String type = getFileType(file);
            for (int i = 0; i < Const.file_types.length; i++) {
                if (type.equals(Const.file_types[i])) {
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
            return null;
        }
        return type;
    }

    public IExtractor getHandler(String fileType){

        return null;
    }
}
