package utils;

import data.Const;

import java.io.File;
import java.io.FileFilter;
import java.util.ArrayList;
import java.util.List;

public class FileUtil {

    public static FileFilter imgFilter = new FileFilter() {
        @Override
        public boolean accept(File file) {
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
        }
    };

    //按文件名排序（同一目录下）
    public static int sortByName(String name1,String name2){
        //将所有非数字替换成空格，便于后续处理
        int[] tmp1=clearDelimitor(name1);
        int[] tmp2=clearDelimitor(name2);
        int len = Math.min(tmp1.length,tmp2.length);
        for(int i=0;i<len;i++){
            if(tmp1[i] == tmp2[i]){//这一段相同则继续
                continue;
            }
            return tmp1[i] > tmp2[i]?
                    1
                    :
                    -1;
        }
        return 0;
    }
    //清除分隔符号，（待定）将字母转换成可比较的数字，便于进一步比较
    private static int[] clearDelimitor(String str) {
        String[] tmpArr =  str.replaceAll("[^0-9]", " ")
                .split(" ");
        List<String> tmp = new ArrayList<>();
        for(int i=0;i<tmpArr.length;i++){
            if(!"".equals(tmpArr[i])){
                tmp.add(tmpArr[i]);
            }
        }
        int n = tmp.size();
        int[] result=new int[n];
        for(int i=0;i<n;i++){
            result[i] = Integer.parseInt(tmp.get(i));
        }
        return result;
    }

    public static FileFilter fileFilter = new FileFilter() {
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
    public static FileFilter docFilter = new FileFilter() {
        @Override
        public boolean accept(File file) {
            if(file.isDirectory()){
                return false;
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
    public static boolean isSupportImg(String type){
        for (int i = 0; i < Const.img_types.length; i++) {
            if (type.equals(Const.img_types[i])) {
                return true;
            }
        }
        return false;
    }
}
