package utils;

import com.sun.jna.platform.FileUtils;
import data.Const;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FileUtil {

    //按文件名排序（同一目录下）
    public static int sortByName(String name1,String name2){
        //将所有非数字替换成空格，便于后续处理
        long[] tmp1=clearDelimitor(name1);
        long[] tmp2=clearDelimitor(name2);
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
        //使用String的比较方法作为最后手段
        return name1.compareTo(name2);
    }
    //清除分隔符号，（待定）将字母转换成可比较的数字，便于进一步比较
    private static long[] clearDelimitor(String str) {
        String[] tmpArr =  str.replaceAll("[^0-9]", " ")
                .split(" ");
        List<String> tmp = new ArrayList<>();
        for(int i=0;i<tmpArr.length;i++){
            if(!"".equals(tmpArr[i])){
                tmp.add(tmpArr[i]);
            }
        }
        int n = tmp.size();
        long[] result=new long[n];
        for(int i=0;i<n;i++){
            result[i] = Long.parseLong(tmp.get(i));
        }
        return result;
    }
    //移动文件至回收站
    public static boolean moveFileToTrash(File[] filesToDel) {
        FileUtils fileUtils = FileUtils.getInstance();
        if (!fileUtils.hasTrash()) {
            return false;
        }
        try {
            fileUtils.moveToTrash(filesToDel);
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    public static boolean isSupportImg(String type){
        for (int i = 0; i < Const.img_types.length; i++) {
            if (type.equals(Const.img_types[i])) {
                return true;
            }
        }
        return false;
    }
}
