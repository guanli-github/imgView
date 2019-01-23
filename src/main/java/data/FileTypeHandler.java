package data;

import extractor.IExtractor;

import java.io.File;

//将文件类型与处理者绑定
public class FileTypeHandler {
    public static String getFileType(File file){
        String fileName=file.getName();
        String type = fileName.substring(fileName.lastIndexOf("."));
        return type;
    }

    public IExtractor getHandler(String fileType){

        return null;
    }
}
